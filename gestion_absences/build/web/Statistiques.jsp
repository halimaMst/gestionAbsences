<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<!DOCTYPE html>
<html>
<head>
    <title>Statistiques des absences par classe</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .container {
            width: 90%;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .header {
            background-color: #3498db;
            color: white;
            padding: 20px;
            text-align: center;
            border-radius: 5px 5px 0 0;
            margin-bottom: 20px;
        }
        .chart-container {
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            padding: 20px;
            margin-bottom: 20px;
            height: 500px;
        }
        .summary {
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            padding: 20px;
            margin-bottom: 20px;
        }
        .summary-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 15px;
            color: #3498db;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .footer {
            text-align: center;
            margin-top: 30px;
            color: #777;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Statistiques des absences</h1>
            <p>Tableau de bord de suivi des absences par classe</p>
        </div>
        
        <div class="chart-container">
            <h2>Taux d'absence par classe</h2>
            <canvas id="absenceChart"></canvas>
        </div>
        
        <div class="summary">
            <div class="summary-title">Résumé des taux d'absence</div>
            <table>
                <thead>
                    <tr>
                        <th>Classe</th>
                        <th>Taux d'absence</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        Map<String, Double> tauxAbsenceParClasse = (Map<String, Double>) request.getAttribute("tauxAbsenceParClasse");
                        if (tauxAbsenceParClasse == null) {
                            tauxAbsenceParClasse = new java.util.HashMap<String, Double>();
                        }
                        
                        for (Map.Entry<String, Double> entry : tauxAbsenceParClasse.entrySet()) {
                            String classe = entry.getKey();
                            Double taux = entry.getValue();
                            out.println("<tr>");
                            out.println("<td>" + classe + "</td>");
                            out.println("<td>" + String.format("%.2f%%", taux * 100) + "</td>");
                            out.println("</tr>");
                        }
                    %>
                </tbody>
            </table>
        </div>
        
        <div class="footer">
            <p>Système de gestion des absences - WebAbsence &copy; 2025</p>
        </div>
                 <div class="mt-4 text-center">
                <a href="dashboard-enseignant.jsp" class="btn btn-secondary">Retour au tableau de bord</a>
            </div>
        
    </div>
    
    <%
        Set<String> classes = tauxAbsenceParClasse.keySet();
    %>
    <script>
        const ctx = document.getElementById('absenceChart').getContext('2d');
        const labels = [<%
            boolean first = true;
            for (String classe : classes) {
                if (!first) out.print(",");
                out.print("'" + classe + "'");
                first = false;
            }
        %>];
        const data = [<%
            first = true;
            for (String classe : classes) {
                if (!first) out.print(",");
                out.print(tauxAbsenceParClasse.get(classe));
                first = false;
            }
        %>];
        
        // Création du graphique avec un design amélioré
        const absenceChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Taux d\'absence',
                    data: data,
                    backgroundColor: 'rgba(52, 152, 219, 0.7)',
                    borderColor: 'rgba(52, 152, 219, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 1,
                        ticks: {
                            callback: function(value) {
                                return (value * 100).toFixed(1) + '%';
                            }
                        },
                        title: {
                            display: true,
                            text: 'Taux d\'absence (%)',
                            font: {
                                size: 14,
                                weight: 'bold'
                            }
                        }
                    },
                    x: {
                        title: {
                            display: true,
                            text: 'Classes',
                            font: {
                                size: 14,
                                weight: 'bold'
                            }
                        }
                    }
                },
                plugins: {
                    title: {
                        display: false
                    },
                    legend: {
                        position: 'top'
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return context.dataset.label + ': ' + (context.raw * 100).toFixed(2) + '%';
                            }
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>