package servlet;

import entities.Enseignant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

@WebServlet(name = "PlanificationSeances", urlPatterns = {"/PlanificationSeances"})
public class PlanificationSeancesServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/absence?zeroDateTimeBehavior=convertToNull";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Affiche la page de planification
        request.getRequestDispatcher("/planification-seances.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"enseignant".equals(session.getAttribute("userType"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        int enseignantId = (int) session.getAttribute("userId");
        String dateStr = request.getParameter("date");
        String heureStr = request.getParameter("heure");
        String matiere = request.getParameter("matiere");

        if (dateStr == null || heureStr == null || matiere == null ||
                dateStr.isEmpty() || heureStr.isEmpty() || matiere.isEmpty()) {
            request.setAttribute("errorMessage", "Tous les champs sont obligatoires.");
            request.getRequestDispatcher("/planification-seances.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Conversion date et heure
            java.sql.Date date = java.sql.Date.valueOf(dateStr);
            Time heure = Time.valueOf(heureStr + ":00"); // format HH:mm:ss

            String sql = "INSERT INTO seances_cours (date, heure, matiere, enseignant_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setTime(2, heure);
            ps.setString(3, matiere);
            ps.setInt(4, enseignantId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                request.setAttribute("successMessage", "Séance planifiée avec succès !");
            } else {
                request.setAttribute("errorMessage", "Erreur lors de la planification.");
            }

            request.getRequestDispatcher("/planification-seances.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
