<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard Enseignant</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            .dashboard-container {
                max-width: 900px;
                margin: 30px auto;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
                background-color: white;
            }
            .card {
                margin-bottom: 20px;
                transition: transform 0.3s;
            }
            .card:hover {
                transform: translateY(-5px);
            }
            .profile-section {
                background-color: #f8f9fa;
                border-radius: 5px;
                padding: 20px;
                margin-bottom: 20px;
            }
            .seance-item {
                border-bottom: 1px solid #e9ecef;
                padding: 15px 0;
            }
        </style>
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand" href="#">Système de Gestion des Absences</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="#">Accueil</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="PlanificationSeances">Planifier une séance</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="SaisieAbsences">Saisir les absences</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Statistiques">Statistiques</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Logout">Déconnexion</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        
        <div class="container">
            <div class="dashboard-container">
                <div class="profile-section">
                    <div class="row">
                        <div class="col-md-8">
                            <h3>Bonjour, ${nom}</h3>
                            <p>Email: ${email}</p>
                        </div>
                        <div class="col-md-4 text-center text-md-right">
                            <div class="alert alert-info mb-0">
                                <strong>Séances aujourd'hui:</strong> <span id="seancesAujourdhui">2</span>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-body text-center">
                                <h5 class="card-title">Planifier une séance</h5>
                                <p class="card-text">Créez de nouvelles séances de cours pour vos classes.</p>
                                <a href="PlanificationSeances" class="btn btn-primary">Planifier</a>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-body text-center">
                                <h5 class="card-title">Saisir les absences</h5>
                                <p class="card-text">Marquez les étudiants présents ou absents à votre cours.</p>
                                <a href="SaisieAbsences" class="btn btn-primary">Saisir</a>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-body text-center">
                                <h5 class="card-title">Voir les statistiques</h5>
                                <p class="card-text">Consultez les taux d'absences par classe et par matière.</p>
                                <a href="Statistiques" class="btn btn-primary">Consulter</a>
                            </div>
                        </div>
                    </div>
                </div>
                
              
                
          
                
               
            </div>
        </div>
        
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>