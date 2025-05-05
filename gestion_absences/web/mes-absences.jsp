<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entities.Absence"%>
<%@page import="entities.Etudiant"%>
<%@page import="entities.SeanceCours"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mes Absences</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .justifiee { color: green; font-weight: bold; }
        .non-justifiee { color: red; font-weight: bold; }
    </style>
</head>
<body>
<div class="container mt-4">

    <%
        // Vérification session (optionnel si déjà fait en servlet)
        Integer userId = (Integer) session.getAttribute("userId");
        String userType = (String) session.getAttribute("userType");
        if (userId == null || !"etudiant".equals(userType)) {
            response.sendRedirect("Login");
            return;
        }

        // Récupération des attributs passés par la servlet
        Etudiant etudiant = (Etudiant) request.getAttribute("etudiant");
        List<Absence> absences = (List<Absence>) request.getAttribute("absences");
        String filter = (String) request.getAttribute("filter");

        // Formats pour date et heure
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    %>

    <h2>Mes absences - <%= etudiant.getPrenom() + " " + etudiant.getNom() %></h2>
    <p>Classe : <%= etudiant.getClasse() %></p>

    <div class="mb-3">
        <a href="dashboard-etudiant.jsp" class="btn btn-secondary">Retour au tableau de bord</a>
    </div>

    <div class="mb-3">
        <strong>Filtrer les absences :</strong>
        <a href="MesAbsences" class="btn btn-sm btn-outline-primary">Toutes</a>
        <a href="MesAbsences?filter=justified" class="btn btn-sm btn-outline-success">Justifiées</a>
        <a href="MesAbsences?filter=unjustified" class="btn btn-sm btn-outline-danger">Non justifiées</a>
    </div>

    <%
        if (absences == null || absences.isEmpty()) {
    %>
        <div class="alert alert-info">Aucune absence enregistrée.</div>
    <%
        } else {
    %>

    <table class="table table-bordered table-striped">
        <thead class="thead-light">
            <tr>
                <th>Matière</th>
                <th>Date</th>
                <th>Heure</th>
                <th>Justifiée</th>
                <th>Justification</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <%
            for (Absence absence : absences) {
                boolean afficher = true;
                if ("justified".equals(filter)) {
                    afficher = absence.isJustifiee();
                } else if ("unjustified".equals(filter)) {
                    afficher = !absence.isJustifiee();
                }
                if (!afficher) continue;

                SeanceCours seance = absence.getSeanceCours();
        %>
            <tr>
                <td><%= seance.getMatiere() %></td>
                <td><%= dateFormat.format(seance.getDate()) %></td>
                <td><%= timeFormat.format(seance.getHeure()) %></td>
                <td class="<%= absence.isJustifiee() ? "justifiee" : "non-justifiee" %>">
                    <%= absence.isJustifiee() ? "Oui" : "Non" %>
                </td>
                <td><%= absence.getJustification() != null ? absence.getJustification() : "-" %></td>
                <td>
                    <%
                        if (!absence.isJustifiee()) {
                    %>
                    <a href="JustifierAbsence?seanceId=<%= seance.getId() %>&etudiantId=<%= etudiant.getId() %>" class="btn btn-sm btn-primary">
                        Justifier
                    </a>
                    <%
                        } else {
                    %>
                    <span class="text-muted">-</span>
                    <%
                        }
                    %>
                </td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <%
        }
    %>

</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
