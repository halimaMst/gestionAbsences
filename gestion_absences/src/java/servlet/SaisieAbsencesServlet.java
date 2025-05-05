package servlet;

import dao.AbsenceDao;
import dao.EtudiantDao;
import dao.SeanceCoursDao;
import entities.Absence;
import entities.AbsenceId;
import entities.Etudiant;
import entities.SeanceCours;
import entities.Enseignant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "SaisieAbsencesServlet", urlPatterns = {"/SaisieAbsences", "/SaisieAbsencesServlet"})
public class SaisieAbsencesServlet extends HttpServlet {

    private SeanceCoursDao seanceCoursDao;
    private EtudiantDao etudiantDao;
    private AbsenceDao absenceDao;

    @Override
    public void init() throws ServletException {
        super.init();
        seanceCoursDao = new SeanceCoursDao();
        etudiantDao = new EtudiantDao();
        absenceDao = new AbsenceDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"enseignant".equals(session.getAttribute("userType"))) {
            response.sendRedirect("Login");
            return;
        }
        
        int enseignantId = (int) session.getAttribute("userId");
        
        try {
            // Récupérer les séances de cours de l'enseignant
            List<SeanceCours> seances = seanceCoursDao.findAll();
            
            // Pour l'instant, on affiche toutes les séances sans filtrage
            // Vous pourrez réactiver le filtrage plus tard quand la relation enseignant sera bien configurée
            List<SeanceCours> seancesEnseignant = seances;
            
            // Récupérer tous les étudiants
            List<Etudiant> etudiants = etudiantDao.findAll();
            
            request.setAttribute("seances", seancesEnseignant);
            request.setAttribute("etudiants", etudiants);
            
            request.getRequestDispatcher("/saisieabsence.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors du chargement des données: " + e.getMessage());
            request.getRequestDispatcher("/saisieabsence.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || !"enseignant".equals(session.getAttribute("userType"))) {
            response.sendRedirect("Login");
            return;
        }
        
        String seanceIdStr = request.getParameter("seanceId");
        String[] absentsArray = request.getParameterValues("absents");
        
        if (seanceIdStr == null || seanceIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Veuillez sélectionner une séance de cours.");
            doGet(request, response);
            return;
        }
        
        try {
            int seanceId = Integer.parseInt(seanceIdStr);
            SeanceCours seance = seanceCoursDao.findById(seanceId);
            
            if (seance == null) {
                request.setAttribute("errorMessage", "La séance de cours sélectionnée n'existe pas.");
                doGet(request, response);
                return;
            }
            
            // Si des étudiants sont marqués absents
            if (absentsArray != null && absentsArray.length > 0) {
                for (String etudiantIdStr : absentsArray) {
                    int etudiantId = Integer.parseInt(etudiantIdStr);
                    Etudiant etudiant = etudiantDao.findById(etudiantId);
                    
                    if (etudiant != null) {
                        // Créer l'identifiant composite
                        AbsenceId absenceId = new AbsenceId(seanceId, etudiantId);
                        
                        // Vérifier si l'absence existe déjà
                        Absence existingAbsence = absenceDao.findById(absenceId);
                        
                        if (existingAbsence == null) {
                            // Créer une nouvelle absence
                            Absence absence = new Absence();
                            absence.setId(absenceId);
                            absence.setEtudiant(etudiant);
                            absence.setSeanceCours(seance);
                            
                            // Sauvegarder l'absence
                            absenceDao.create(absence);
                        }
                    }
                }
                
                request.setAttribute("successMessage", "Les absences ont été enregistrées avec succès.");
            } else {
                request.setAttribute("successMessage", "Aucune absence n'a été enregistrée.");
            }
            
            doGet(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Erreur lors de la conversion des identifiants.");
            doGet(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de l'enregistrement des absences: " + e.getMessage());
            doGet(request, response);
        }
    }
}