<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Justifier une Absence</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">

    <%
        // Vérification session
        Integer userId = (Integer) session.getAttribute("userId");
        String userType = (String) session.getAttribute("userType");
        if (userId == null || !"etudiant".equals(userType)) {
            response.sendRedirect("Login");
            return;
        }

        // Récupération des paramètres
        Integer seanceId = (Integer) request.getAttribute("seanceId");
        Integer etudiantId = (Integer) request.getAttribute("etudiantId");
        
        // Vérification que les IDs sont présents
        if (seanceId == null || etudiantId == null) {
            response.sendRedirect("MesAbsences");
            return;
        }
        
        // Vérification que l'étudiant connecté est bien celui qui justifie
        if (!userId.equals(etudiantId)) {
            response.sendRedirect("MesAbsences");
            return;
        }
        
        // Récupérer les messages éventuels
        String errorMessage = (String) request.getAttribute("errorMessage");
        String successMessage = (String) request.getAttribute("successMessage");
    %>

    <h2>Justifier une absence</h2>
    
    <div class="mb-3">
        <a href="MesAbsences" class="btn btn-secondary">Retour à mes absences</a>
    </div>
    
    <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <div class="alert alert-danger"><%= errorMessage %></div>
    <% } %>
    
    <% if (successMessage != null && !successMessage.isEmpty()) { %>
        <div class="alert alert-success"><%= successMessage %></div>
    <% } %>
    
    <div class="card">
        <div class="card-header bg-primary text-white">
            Formulaire de justification
        </div>
        <div class="card-body">
            <form action="JustifierAbsence" method="post">
                <input type="hidden" name="seanceId" value="<%= seanceId %>">
                <input type="hidden" name="etudiantId" value="<%= etudiantId %>">
                
                <div class="form-group">
                    <label for="justification">Motif de l'absence :</label>
                    <textarea class="form-control" id="justification" name="justification" rows="5" required></textarea>
                    <small class="form-text text-muted">Veuillez fournir une explication détaillée pour votre absence. Si vous disposez d'un justificatif médical, merci de le préciser et de prévoir de le présenter au secrétariat.</small>
                </div>
                
                <button type="submit" class="btn btn-primary">Soumettre ma justification</button>
            </form>
        </div>
    </div>
    
    <div class="mt-3">
        <p><strong>Note importante :</strong> La justification sera examinée par l'administration. Une absence justifiée ne sera définitivement validée qu'après approbation.</p>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>