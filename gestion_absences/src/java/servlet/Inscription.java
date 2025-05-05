package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Inscription", urlPatterns = {"/Inscription"})
public class Inscription extends HttpServlet {
    
    // Paramètres de connexion à la base de données
    private static final String DB_URL = "jdbc:mysql://localhost:3306/absence?zeroDateTimeBehavior=convertToNull";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    @Override
    public void init() {
        try {
            // Charger le driver JDBC pour MySQL 5.1
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de chargement du driver JDBC: " + e.getMessage());
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Affichage de la page d'inscription
        request.getRequestDispatcher("/inscription.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Récupération des paramètres communs
        String userType = request.getParameter("userType");
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validation des champs communs
        if (userType == null || userType.trim().isEmpty() ||
            nom == null || nom.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            
            request.setAttribute("errorMessage", "Veuillez remplir tous les champs obligatoires.");
            request.getRequestDispatcher("/inscription.jsp").forward(request, response);
            return;
        }
        
        // Vérification de la correspondance des mots de passe
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            request.getRequestDispatcher("/inscription.jsp").forward(request, response);
            return;
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Établir la connexion à la base de données
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Vérifier si l'email existe déjà (table users)
            String checkUserSql = "SELECT COUNT(*) FROM users WHERE email = ?";
            pstmt = conn.prepareStatement(checkUserSql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            rs.next();
            int countUser = rs.getInt(1);
            
            // Fermer les ressources
            rs.close();
            pstmt.close();
            
            if (countUser > 0) {
                request.setAttribute("errorMessage", "Cet email est déjà utilisé.");
                request.getRequestDispatcher("/inscription.jsp").forward(request, response);
                return;
            }
            
            // Insertion d'un nouvel utilisateur et démarrage d'une transaction
            conn.setAutoCommit(false);
            
            // Insertion dans la table users
            String insertUserSql = "INSERT INTO users (email, motDePasse, nom) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(insertUserSql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, nom);
            pstmt.executeUpdate();
            
            // Récupération de l'ID généré
            rs = pstmt.getGeneratedKeys();
            int userId;
            if (rs.next()) {
                userId = rs.getInt(1);
            } else {
                throw new SQLException("Échec de la création de l'utilisateur, aucun ID généré.");
            }
            
            // Fermer les ressources
            rs.close();
            pstmt.close();
            
            // Traitement selon le type d'utilisateur
            if ("etudiant".equals(userType)) {
                // Paramètres spécifiques à l'étudiant
                String prenom = request.getParameter("prenom");
                String classe = request.getParameter("classe");
                
                // Validation des champs spécifiques
                if (prenom == null || prenom.trim().isEmpty() ||
                    classe == null || classe.trim().isEmpty()) {
                    
                    // Annuler la transaction
                    conn.rollback();
                    
                    request.setAttribute("errorMessage", "Veuillez remplir tous les champs pour l'étudiant.");
                    request.getRequestDispatcher("/inscription.jsp").forward(request, response);
                    return;
                }
                
                // Insertion de l'étudiant dans la table etudiants
                String insertEtudiantSql = "INSERT INTO etudiants (id, prenom, classe) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(insertEtudiantSql);
                pstmt.setInt(1, userId);
                pstmt.setString(2, prenom);
                pstmt.setString(3, classe);
                pstmt.executeUpdate();
                
            } else if ("enseignant".equals(userType)) {
                // Insertion de l'enseignant dans la table enseignant
                String insertEnseignantSql = "INSERT INTO enseignant (id) VALUES (?)";
                pstmt = conn.prepareStatement(insertEnseignantSql);
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
            }
            
            // Valider la transaction
            conn.commit();
            
            // Message de succès et redirection vers la page de login
            request.setAttribute("successMessage", "Inscription réussie! Vous pouvez maintenant vous connecter.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            
        } catch (SQLException e) {
            // En cas d'erreur, annuler la transaction
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Erreur lors du rollback: " + ex.getMessage());
            }
            
            request.setAttribute("errorMessage", "Erreur lors de l'inscription: " + e.getMessage());
            request.getRequestDispatcher("/inscription.jsp").forward(request, response);
        } finally {
            // Fermeture des ressources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources: " + e.getMessage());
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Inscription Servlet";
    }
}