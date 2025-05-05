package servlet;

import dao.AbsenceDao;
import entities.Absence;
import entities.AbsenceId;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "JustifierAbsence", urlPatterns = {"/JustifierAbsence"})
public class JustifierAbsence extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Vérifier si l'utilisateur est connecté et est un étudiant
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String userType = (String) session.getAttribute("userType");
        
        if (userId == null || !"etudiant".equals(userType)) {
            response.sendRedirect("Login");
            return;
        }
        
        // Récupérer les paramètres
        String seanceIdParam = request.getParameter("seanceId");
        String etudiantIdParam = request.getParameter("etudiantId");
        
        if (seanceIdParam == null || etudiantIdParam == null) {
            response.sendRedirect("MesAbsences");
            return;
        }
        
        try {
            int seanceId = Integer.parseInt(seanceIdParam);
            int etudiantId = Integer.parseInt(etudiantIdParam);
            
            // Vérifier que l'étudiant ne tente pas de justifier l'absence d'un autre étudiant
            if (userId != etudiantId) {
                response.sendRedirect("MesAbsences");
                return;
            }
            
            // Afficher le formulaire de justification
            request.setAttribute("seanceId", seanceId);
            request.setAttribute("etudiantId", etudiantId);
            request.getRequestDispatcher("/justifier-absence.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("MesAbsences");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Vérifier si l'utilisateur est connecté et est un étudiant
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String userType = (String) session.getAttribute("userType");
        
        if (userId == null || !"etudiant".equals(userType)) {
            response.sendRedirect("Login");
            return;
        }
        
        // Récupérer les paramètres
        String seanceIdParam = request.getParameter("seanceId");
        String etudiantIdParam = request.getParameter("etudiantId");
        String justification = request.getParameter("justification");
        
        if (seanceIdParam == null || etudiantIdParam == null || justification == null || justification.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Veuillez fournir une justification.");
            request.getRequestDispatcher("/justifier-absence.jsp").forward(request, response);
            return;
        }
        
        try {
            int seanceId = Integer.parseInt(seanceIdParam);
            int etudiantId = Integer.parseInt(etudiantIdParam);
            
            // Vérifier que l'étudiant ne tente pas de justifier l'absence d'un autre étudiant
            if (userId != etudiantId) {
                response.sendRedirect("MesAbsences");
                return;
            }
            
            // Récupérer l'absence
            AbsenceDao absenceDao = new AbsenceDao();
            AbsenceId absenceId = new AbsenceId(seanceId, etudiantId);
            Absence absence = absenceDao.findById(absenceId);
            
            if (absence == null) {
                response.sendRedirect("MesAbsences");
                return;
            }
            
            // Mettre à jour l'absence avec la justification
            absence.setJustifiee(true);
            absence.setJustification(justification);
            absence.setDateJustification(new Date());
            
            // Enregistrer les modifications
            boolean success = absenceDao.update(absence);
            
            if (success) {
                request.setAttribute("successMessage", "Votre absence a été justifiée avec succès.");
            } else {
                request.setAttribute("errorMessage", "Une erreur est survenue lors de la justification de votre absence.");
            }
            
            // Rediriger vers la liste des absences
            response.sendRedirect("MesAbsences");
            
        } catch (NumberFormatException e) {
            response.sendRedirect("MesAbsences");
        }
    }

    @Override
    public String getServletInfo() {
        return "Justifier Absence Servlet";
    }
}