package controllers;

import entities.Absence;
import entities.AbsenceId;
import entities.Etudiant;
import entities.SeanceCours;
import services.AbsenceService;
import services.EtudiantService;
import services.SeanceCoursService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AbsenceController", urlPatterns = {"/absences"})
public class AbsenceController extends HttpServlet {

    private AbsenceService absenceService = new AbsenceService();
    private EtudiantService etudiantService = new EtudiantService();
    private SeanceCoursService seanceCoursService = new SeanceCoursService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Absence> absences = absenceService.findAll();
        List<Etudiant> etudiants = etudiantService.findAll();
        List<SeanceCours> seances = seanceCoursService.findAll();

        request.setAttribute("absences", absences);
        request.setAttribute("etudiants", etudiants);
        request.setAttribute("seances", seances);

        request.getRequestDispatcher("absence.jsp").forward(request, response);
    }

    // Pour simplifier, on ne gère que l'ajout via POST ici
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int seanceId = Integer.parseInt(request.getParameter("seanceCoursId"));
            int etudiantId = Integer.parseInt(request.getParameter("etudiantId"));

            SeanceCours seance = seanceCoursService.findById(seanceId);
            Etudiant etudiant = etudiantService.findById(etudiantId);

            if (seance != null && etudiant != null) {
                Absence absence = new Absence(seance, etudiant);
                // Création de l'ID composite
                absence.setId(new AbsenceId(seanceId, etudiantId));

                absenceService.create(absence);
            }

            response.sendRedirect("absences");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("absences?error=1");
        }
    }
}
