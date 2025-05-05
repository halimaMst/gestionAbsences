<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Planification des séances</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            color: #333;
        }
        .container {
            width: 90%;
            max-width: 800px;
            margin: 30px auto;
            padding: 20px;
        }
        .card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            padding: 25px;
            margin-bottom: 20px;
        }
        .header {
            background-color: #3498db;
            color: white;
            padding: 20px;
            text-align: center;
            border-radius: 8px 8px 0 0;
            margin-bottom: 0;
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }
        input[type="date"],
        input[type="time"],
        input[type="text"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }
        input[type="date"]:focus,
        input[type="time"]:focus,
        input[type="text"]:focus {
            border-color: #3498db;
            outline: none;
            box-shadow: 0 0 5px rgba(52, 152, 219, 0.5);
        }
        button {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 14px 20px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #2980b9;
        }
        .message {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .error {
            background-color: #ffebee;
            color: #c62828;
            border-left: 5px solid #c62828;
        }
        .success {
            background-color: #e8f5e9;
            color: #2e7d32;
            border-left: 5px solid #2e7d32;
        }
        .footer {
            text-align: center;
            margin-top: 30px;
            color: #777;
            font-size: 14px;
        }
        /* Icônes pour les champs */
        .input-container {
            position: relative;
        }
        .input-icon {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            color: #999;
        }
        .required-field::after {
            content: "*";
            color: #e74c3c;
            margin-left: 4px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Planification des séances</h1>
        </div>
        
        <div class="card">
            <c:if test="${not empty errorMessage}">
                <div class="message error">
                    ${errorMessage}
                </div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="message success">
                    ${successMessage}
                </div>
            </c:if>
            
            <form method="post" action="PlanificationSeances">
                <div class="form-group">
                    <label class="required-field">Date</label>
                    <div class="input-container">
                        <input type="date" name="date" required placeholder="YYYY-MM-DD"/>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="required-field">Heure</label>
                    <div class="input-container">
                        <input type="time" name="heure" required placeholder="HH:MM"/>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="required-field">Matière</label>
                    <div class="input-container">
                        <input type="text" name="matiere" required placeholder="Ex: Mathématiques"/>
                    </div>
                </div>
                
                <button type="submit">Planifier la séance</button>
            </form>
        </div>
         <div class="mt-4 text-center">
                <a href="dashboard-enseignant.jsp" class="btn btn-secondary">Retour au tableau de bord</a>
            </div>
        </div>
        
        <div class="footer">
            <p>Système de gestion des absences - WebAbsence &copy; 2025</p>
        </div>
    </div>
</body>
</html>