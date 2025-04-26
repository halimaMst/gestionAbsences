package controllers;

import entities.Enseignant;
import services.EnseignantService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EnseignantController", urlPatterns = {"/enseignants"})
public class EnseignantController extends HttpServlet {

    private EnseignantService enseignantService = new EnseignantService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Enseignant> enseignants = enseignantService.findAll();
        request.setAttribute("enseignants", enseignants);
        request.getRequestDispatcher("enseignant.jsp").forward(request, response);
    }

    // POST pour ajouter un enseignant
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Enseignant enseignant = new Enseignant(nom, email, motDePasse);
        enseignantService.create(enseignant);

        response.sendRedirect("enseignants");
    }
}
