package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Map;
import java.util.Set;

public final class Statistiques_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <title>Statistiques des absences par classe</title>\n");
      out.write("    <script src=\"https://cdn.jsdelivr.net/npm/chart.js\"></script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <h2>Taux d'absence par classe</h2>\n");
      out.write("    <canvas id=\"absenceChart\" width=\"600\" height=\"400\"></canvas>\n");
      out.write("\n");
      out.write("    ");

        Map<String, Double> tauxAbsenceParClasse = (Map<String, Double>) request.getAttribute("tauxAbsenceParClasse");
        if (tauxAbsenceParClasse == null) {
            tauxAbsenceParClasse = new java.util.HashMap<>();
        }
        Set<String> classes = tauxAbsenceParClasse.keySet();
    
      out.write("\n");
      out.write("\n");
      out.write("    <script>\n");
      out.write("        const ctx = document.getElementById('absenceChart').getContext('2d');\n");
      out.write("        const labels = [");

            boolean first = true;
            for (String classe : classes) {
                if (!first) out.print(",");
                out.print("'" + classe + "'");
                first = false;
            }
        
      out.write("];\n");
      out.write("        const data = [");

            first = true;
            for (String classe : classes) {
                if (!first) out.print(",");
                out.print(tauxAbsenceParClasse.get(classe));
                first = false;
            }
        
      out.write("];\n");
      out.write("\n");
      out.write("        const absenceChart = new Chart(ctx, {\n");
      out.write("            type: 'bar',\n");
      out.write("            data: {\n");
      out.write("                labels: labels,\n");
      out.write("                datasets: [{\n");
      out.write("                    label: 'Taux d\\'absence',\n");
      out.write("                    data: data,\n");
      out.write("                    backgroundColor: 'rgba(54, 162, 235, 0.7)'\n");
      out.write("                }]\n");
      out.write("            },\n");
      out.write("            options: {\n");
      out.write("                scales: {\n");
      out.write("                    y: {\n");
      out.write("                        beginAtZero: true,\n");
      out.write("                        max: 1,\n");
      out.write("                        ticks: {\n");
      out.write("                            callback: function(value) {\n");
      out.write("                                return (value * 100).toFixed(1) + '%';\n");
      out.write("                            }\n");
      out.write("                        },\n");
      out.write("                        title: {\n");
      out.write("                            display: true,\n");
      out.write("                            text: 'Taux d\\'absence (%)'\n");
      out.write("                        }\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("        });\n");
      out.write("    </script>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
