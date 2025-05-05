<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="entities.SeanceCours"%>
<%@page import="entities.Etudiant"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Saisie des absences</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            .container {
                max-width: 900px;
                margin: 30px auto;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
            }
            .error-message {
                color: red;
                margin-bottom: 15px;
            }
            .success-message {
                color: green;
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center mb-4">Saisie des absences</h2>
            
            <% if(request.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <% if(request.getAttribute("successMessage") != null) { %>
                <div class="success-message">
                    <%= request.getAttribute("successMessage") %>
                </div>
            <% } %>
            
            <form action="SaisieAbsencesServlet" method="post">
                <div class="form-group">
                    <label for="seanceId">Séance de cours:</label>
                    <select class="form-control" id="seanceId" name="seanceId" required>
                        <option value="">-- Sélectionnez une séance --</option>
                        <% 
                        List<SeanceCours> seances = (List<SeanceCours>) request.getAttribute("seances");
                        if (seances != null) {
                            for (SeanceCours seance : seances) {
                        %>
                            <option value="<%= seance.getId() %>">
                                <%= seance.getMatiere() %> - <%= seance.getDate() %> à <%= seance.getHeure() %>
                            </option>
                        <%
                            }
                        }
                        %>
                    </select>
                </div>
                
                <h4 class="mt-4">Liste des étudiants</h4>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead class="thead-light">
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Prénom</th>
                                <th>Classe</th>
                                <th>Absent</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                            List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
                            if (etudiants != null) {
                                for (Etudiant etudiant : etudiants) {
                            %>
                                <tr>
                                    <td><%= etudiant.getId() %></td>
                                    <td><%= etudiant.getNom() %></td>
                                    <td><%= etudiant.getPrenom() %></td>
                                    <td><%= etudiant.getClasse() %></td>
                                    <td>
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" 
                                                   name="absents" value="<%= etudiant.getId() %>" 
                                                   id="absent-<%= etudiant.getId() %>">
                                        </div>
                                    </td>
                                </tr>
                            <%
                                }
                            }
                            %>
                        </tbody>
                    </table>
                </div>
                
                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-primary">Enregistrer les absences</button>
                </div>
            </form>
            
            <div class="mt-4 text-center">
                <a href="dashboard-enseignant.jsp" class="btn btn-secondary">Retour au tableau de bord</a>
            </div>
        </div>
        
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>