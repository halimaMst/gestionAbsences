<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inscription</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            .register-container {
                max-width: 700px;
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
            #etudiantFields, #enseignantFields {
                display: none;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="register-container">
                <h2 class="text-center mb-4">Création de compte</h2>
                
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
                
                <form action="Inscription" method="post">
                    <div class="form-group">
                        <label>Type d'utilisateur:</label>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="userType" id="etudiantRadio" value="etudiant" checked>
                            <label class="form-check-label" for="etudiantRadio">
                                Étudiant
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="userType" id="enseignantRadio" value="enseignant">
                            <label class="form-check-label" for="enseignantRadio">
                                Enseignant
                            </label>
                        </div>
                    </div>
                    
                    <!-- Champs communs -->
                    <div class="form-group">
                        <label for="nom">Nom:</label>
                        <input type="text" class="form-control" id="nom" name="nom" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="password">Mot de passe:</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="confirmPassword">Confirmer le mot de passe:</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                    </div>
                    
                    <!-- Champs spécifiques à l'étudiant -->
                    <div id="etudiantFields">
                        <div class="form-group">
                            <label for="prenom">Prénom:</label>
                            <input type="text" class="form-control" id="prenom" name="prenom">
                        </div>
                        
                        <div class="form-group">
                            <label for="classe">Classe:</label>
                            <input type="text" class="form-control" id="classe" name="classe">
                        </div>
                    </div>
                    
                    <!-- Champs spécifiques à l'enseignant, si nécessaire -->
                    <div id="enseignantFields">
                        <!-- Ajoutez ici des champs supplémentaires pour les enseignants si besoin -->
                    </div>
                    
                    <button type="submit" class="btn btn-primary btn-block">S'inscrire</button>
                </form>
                
                <div class="text-center mt-3">
                    <p>Vous avez déjà un compte? <a href="Login">Se connecter</a></p>
                </div>
            </div>
        </div>
        
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        
        <script>
            // Script pour afficher/masquer les champs selon le type d'utilisateur
            $(document).ready(function() {
                // Afficher initialement les champs d'étudiant
                $('#etudiantFields').show();
                
                // Gérer le changement de type d'utilisateur
                $('input[name="userType"]').change(function() {
                    if ($(this).val() === 'etudiant') {
                        $('#etudiantFields').show();
                        $('#enseignantFields').hide();
                        // Rendre les champs étudiants obligatoires
                        $('#prenom, #classe').prop('required', true);
                    } else {
                        $('#etudiantFields').hide();
                        $('#enseignantFields').show();
                        // Rendre les champs étudiants non obligatoires
                        $('#prenom, #classe').prop('required', false);
                    }
                });
            });
        </script>
    </body>
</html>