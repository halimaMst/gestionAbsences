package controllers;

import entities.Enseignant;
import entities.Etudiant;
import services.EnseignantService;
import services.EtudiantService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "InscriptionController", urlPatterns = {"/inscription"})
public class InscriptionController extends HttpServlet {

    private EnseignantService enseignantService = new EnseignantService();
    private EtudiantService etudiantService = new EtudiantService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("inscription.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type"); // "enseignant" ou "etudiant"

        if ("enseignant".equalsIgnoreCase(type)) {
            String nom = request.getParameter("nom");
            String email = request.getParameter("email");
            String motDePasse = request.getParameter("motDePasse");

            Enseignant enseignant = new Enseignant(nom, email, motDePasse);
            enseignantService.create(enseignant);

        } else if ("etudiant".equalsIgnoreCase(type)) {
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String classe = request.getParameter("classe");
            String email = request.getParameter("email");
            String motDePasse = request.getParameter("motDePasse");

            Etudiant etudiant = new Etudiant(nom, prenom, classe, email, motDePasse);
            etudiantService.create(etudiant);
        }

        response.sendRedirect("login.jsp");
    }
}
