package controllers;

import entities.Etudiant;
import services.EtudiantService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EtudiantController", urlPatterns = {"/etudiants"})
public class EtudiantController extends HttpServlet {

    private EtudiantService etudiantService = new EtudiantService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Etudiant> etudiants = etudiantService.findAll();
        request.setAttribute("etudiants", etudiants);
        request.getRequestDispatcher("etudiant.jsp").forward(request, response);
    }

    // POST pour ajouter un étudiant
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String classe = request.getParameter("classe");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Etudiant etudiant = new Etudiant(nom, prenom, classe, email, motDePasse);
        etudiantService.create(etudiant);

        response.sendRedirect("etudiants");
    }
}
