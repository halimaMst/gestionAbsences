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
import javax.servlet.http.HttpSession;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    
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
        // Affichage de la page de login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");
        
        // Validation des champs
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty() ||
            userType == null || userType.trim().isEmpty()) {
            
            request.setAttribute("errorMessage", "Veuillez remplir tous les champs.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Établir la connexion à la base de données
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Vérifier les identifiants dans la table users
            String sql = "SELECT u.id, u.nom, u.email FROM users u ";
            
            if ("etudiant".equals(userType)) {
                sql += "INNER JOIN etudiants e ON u.id = e.id ";
            } else if ("enseignant".equals(userType)) {
                sql += "INNER JOIN enseignant e ON u.id = e.id ";
            }
            
            sql += "WHERE u.email = ? AND u.motDePasse = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Utilisateur trouvé
                int userId = rs.getInt("id");
                String nom = rs.getString("nom");
                
                // Récupérer des informations supplémentaires selon le type d'utilisateur
                String prenom = null;
                String classe = null;
                
                if ("etudiant".equals(userType)) {
                    // Requête pour récupérer les informations spécifiques à l'étudiant
                    PreparedStatement pstmtEtudiant = conn.prepareStatement(
                            "SELECT prenom, classe FROM etudiants WHERE id = ?");
                    pstmtEtudiant.setInt(1, userId);
                    ResultSet rsEtudiant = pstmtEtudiant.executeQuery();
                    
                    if (rsEtudiant.next()) {
                        prenom = rsEtudiant.getString("prenom");
                        classe = rsEtudiant.getString("classe");
                    }
                    
                    rsEtudiant.close();
                    pstmtEtudiant.close();
                }
                
                // Création de la session utilisateur
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("userType", userType);
                session.setAttribute("nom", nom);
                session.setAttribute("email", email);
                
                if ("etudiant".equals(userType)) {
                    session.setAttribute("prenom", prenom);
                    session.setAttribute("classe", classe);
                    response.sendRedirect("dashboard-etudiant.jsp");
                } else {
                    response.sendRedirect("dashboard-enseignant.jsp");
                }
            } else {
                // Utilisateur non trouvé ou mot de passe incorrect
                request.setAttribute("errorMessage", "Email ou mot de passe incorrect.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
            
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erreur lors de la connexion: " + e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } finally {
            // Fermeture des ressources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources: " + e.getMessage());
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Login Servlet";
    }
}