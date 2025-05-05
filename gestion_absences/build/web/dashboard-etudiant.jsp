<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="entities.Absence"%>
<%@page import="dao.AbsenceDao"%>
<%@page import="entities.AbsenceId"%>
<%@page import="entities.Etudiant"%>
<%@page import="dao.EtudiantDao"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tableau de bord - Étudiant</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            .dashboard-header {
                background-color: #f8f9fa;
                padding: 20px 0;
                margin-bottom: 30px;
                border-bottom: 1px solid #e3e3e3;
            }
            .dashboard-card {
                box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
                border-radius: 5px;
                padding: 20px;
                margin-bottom: 20px;
                transition: transform 0.3s;
            }
            .dashboard-card:hover {
                transform: translateY(-5px);
            }
            .dashboard-stats {
                font-size: 24px;
                font-weight: bold;
                color: #007bff;
            }
        </style>
    </head>
    <body>
        <%
            // Vérifier si l'utilisateur est connecté et est un étudiant
            Integer userId = (Integer) session.getAttribute("userId");
            String userType = (String) session.getAttribute("userType");
            
            if (userId == null || !"etudiant".equals(userType)) {
                response.sendRedirect("Login");
                return;
            }
            
            // Récupérer les informations de l'étudiant
            String nom = (String) session.getAttribute("nom");
            String prenom = (String) session.getAttribute("prenom");
            String classe = (String) session.getAttribute("classe");
            
            // Calculer les statistiques d'absences
            EtudiantDao etudiantDao = new EtudiantDao();
            Etudiant etudiant = etudiantDao.findById(userId);
            
            int totalAbsences = 0;
            int absencesJustifiees = 0;
            int absencesNonJustifiees = 0;
            
            if (etudiant != null && etudiant.getAbsences() != null) {
                List<Absence> absences = etudiant.getAbsences();
                totalAbsences = absences.size();
                
                for (Absence absence : absences) {
                    if (absence.isJustifiee()) {
                        absencesJustifiees++;
                    } else {
                        absencesNonJustifiees++;
                    }
                }
            }
        %>
        
        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="#">Gestion des Absences</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="dashboard-etudiant.jsp">Tableau de bord</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="MesAbsences">Mes absences</a>
                        </li>
                    </ul>
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="Logout">Déconnexion</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        
        <!-- Header -->
        <div class="dashboard-header">
            <div class="container">
                <h1>Bienvenue, <%= prenom %> <%= nom %></h1>
                <p>Classe: <%= classe %></p>
            </div>
        </div>
        
        <!-- Dashboard Content -->
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <div class="dashboard-card">
                        <h3>Total des absences</h3>
                        <div class="dashboard-stats"><%= totalAbsences %></div>
                        <p>Nombre total d'absences enregistrées</p>
                        <a href="MesAbsences" class="btn btn-primary">Voir détails</a>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="dashboard-card">
                        <h3>Absences justifiées</h3>
                        <div class="dashboard-stats"><%= absencesJustifiees %></div>
                        <p>Nombre d'absences avec justification</p>
                        <a href="MesAbsences?filter=justified" class="btn btn-success">Voir détails</a>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="dashboard-card">
                        <h3>Absences non justifiées</h3>
                        <div class="dashboard-stats"><%= absencesNonJustifiees %></div>
                        <p>Nombre d'absences sans justification</p>
                        <a href="MesAbsences?filter=unjustified" class="btn btn-danger">Voir détails</a>
                    </div>
                </div>
            </div>
        </div>
        
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>