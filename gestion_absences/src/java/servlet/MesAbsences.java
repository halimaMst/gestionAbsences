package servlet;

import dao.AbsenceDao;
import dao.EtudiantDao;
import entities.Absence;
import entities.Etudiant;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MesAbsences", urlPatterns = {"/MesAbsences"})
public class MesAbsences extends HttpServlet {

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

// Récupérer les informations de l'étudiant
EtudiantDao etudiantDao = new EtudiantDao();
Etudiant etudiant = etudiantDao.findById(userId);

if (etudiant == null) {
response.sendRedirect("Login");
return;
}

// Récupérer explicitement les absences de l'étudiant via AbsenceDao
AbsenceDao absenceDao = new AbsenceDao();
List<Absence> absences = absenceDao.findByEtudiantId(userId);

// Passer les données à la JSP
request.setAttribute("etudiant", etudiant);
request.setAttribute("absences", absences);

// Récupérer le filtre s'il existe
String filter = request.getParameter("filter");
if (filter != null) {
request.setAttribute("filter", filter);
}

// Afficher la page des absences
request.getRequestDispatcher("/mes-absences.jsp").forward(request, response);
}

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
// Rediriger les requêtes POST vers doGet
doGet(request, response);
}

@Override
public String getServletInfo() {
return "Mes Absences Servlet";
}}