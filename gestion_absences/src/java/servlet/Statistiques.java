package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Statistiques", urlPatterns = {"/Statistiques"})
public class Statistiques extends HttpServlet {
    
    // Paramètres de connexion à la base de données
    private static final String DB_URL = "jdbc:mysql://localhost:3306/absence?zeroDateTimeBehavior=convertToNull";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    @Override
    public void init() {
        try {
            // Charger le driver JDBC pour MySQL 
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de chargement du driver JDBC: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"enseignant".equals(session.getAttribute("userType"))) {
            response.sendRedirect("Login");
            return;
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Calculer le taux d'absence par classe
            Map<String, Double> tauxAbsenceParClasse = new HashMap<String, Double>();
            
            String sql = "SELECT e.classe, " +
             "COUNT(a.etudiant_id) as nb_absences, " +
             "COUNT(DISTINCT s.id) as nb_seances, " +
             "COUNT(DISTINCT e.id) as nb_etudiants " +
             "FROM etudiants e " +
             "CROSS JOIN seances_cours s " +
             "LEFT JOIN absences a ON a.etudiant_id = e.id AND a.seance_cours_id = s.id " +
             "GROUP BY e.classe";

            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String classe = rs.getString("classe");
                int nbAbsences = rs.getInt("nb_absences");
                int nbSeances = rs.getInt("nb_seances");
                int nbEtudiants = rs.getInt("nb_etudiants");
                
                // Calculer le taux d'absence (nb absences / (nb séances * nb étudiants))
                double tauxAbsence = 0.0;
                if (nbSeances > 0 && nbEtudiants > 0) {
                    tauxAbsence = (double) nbAbsences / (nbSeances * nbEtudiants);
                }
                
                tauxAbsenceParClasse.put(classe, tauxAbsence);
            }
            
            request.setAttribute("tauxAbsenceParClasse", tauxAbsenceParClasse);
            request.getRequestDispatcher("/Statistiques.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des statistiques", e);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet pour les statistiques d'absences";
    }
}