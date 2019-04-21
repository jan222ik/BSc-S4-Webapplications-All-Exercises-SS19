package clazzes;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Janik Mayr on 27.03.2019
 */
//@WebServlet(name = "clazzes.GuestBookSaveServlet", urlPatterns = {"/guest"})
public class GuestBookSaveServlet extends HttpServlet {

    @Override
    public void destroy() {
        try {
            HSQLDBEmbeddedServer.getInstance().stop();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleGet(request, response);
    }

    public static void handleGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String queryString = request.getQueryString();
        if (queryString != null && queryString.equalsIgnoreCase("database=reset")) {
            HSQLDBEmbeddedServer.getInstance().reset();
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        appendBeforeForm(out);
        appendForm(out);
        printMessages(out);
        appendAfterMessages(out);
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        handlePost(req, res);
    }

    public static void handlePost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        handleForm(req);
        handleGet(req, res);
    }

    private static void appendBeforeForm(@NotNull PrintWriter out) {
        out.print("<head>\n");
        out.print("    <meta charset=\"utf-8\">\n");
        out.print("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=yes\">\n");
        out.print("    <meta name=\"description\" content=\"Gästebuch\">\n");
        out.print("    <meta name=\"author\" content=\"Janik Mayr\">\n");
        out.print("    <meta name=\"rating\" content=\"safe for kids\">\n");
        out.print("    <meta name=\"reply-to\" content=\"admin@host.com\">\n");
        out.print("    <meta name=\"distribution\" content=\"global\">\n");
        out.print("    <meta http-equiv=\"refresh\" content=\"43200\">\n");
        out.print("    <meta name=\"keywords\"\n");
        out.print("          content=\"Schützenverein,Freischütz,Geltendorf,\n");
        out.print("                    Luftgewehr,Luftpistole,Lichtgewehr,\n");
        out.print("                    Jugend,Gemeinschaft,1929,\n");
        out.print("                    82269,Verein\"\n");
        out.print("    >\n");
        out.print("\n");
        out.print("    <title>Freischütz Geltendorf - Gästebuch</title>\n");
        out.print("\n");
        out.print("    <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css?v=4.0\">\n");
        out.print("    <link rel=\"stylesheet\" type=\"text/css\" href=\"webVars.css?v=4.0\">\n");
        out.print("    <link rel=\"stylesheet\" type=\"text/css\" href=\"printVars.css?v=4.0\" media=\"print\" />\n");
        out.print("    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Roboto\">\n");
        out.print("    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n");
        out.print("    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n");
        out.print("<script>document.cookie = \"last=\" + window.location.href + \"; expires=\" + new Date(new Date().getTime() + 24 * 60 * 60 * 1000) + \";\";$(document).ready(function () {\n"
                + "            $('#nav').load(\"nav.jsp\");\n"
                + "            $('#footer').load(\"footer.html\");\n"
                + "        });</script>");
        out.print("\n\n");
        out.print("<body>\n");
        out.print("<nav id=\"nav\">\n");
        out.print("</nav>\n");
        out.print("<div id=\"content\">\n");
        out.print("<header>\n");
        out.print("    <h1>\n");
        out.print("        Gästebuch\n");
        out.print("    </h1>\n");
        out.print("</header>\n");
        out.print("<main>\n");
    }

    private static void appendForm(@NotNull PrintWriter out) {
        out.println("<form method=post>");
        out.println("<b>Hinterlasse einen Kommentar</b><br>");
        out.println("Name: <input type=text name=name><br>");
        out.println("E-Mail: <input type=text name=email><br>");
        out.println("Kommentar: <textarea rows=5  cols = 5 name=comment></textarea><br>");
        out.println("<input type=submit value=\"Absenden\"><br>");
        out.println("</form>");
        out.println("<hr>");
    }

    private static void printMessages(PrintWriter out) {
        try {
            System.out.println("Get Data From DB");
            ResultSet resultSet = HSQLDBEmbeddedServer
                    .getInstance()
                    .getConnection()
                    .prepareStatement("SELECT * FROM GuestEntry;")
                    .executeQuery();
            while (resultSet.next()) {
                System.out.println("Element discovered");
                SiteEntry entry = new SiteEntry(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                System.out.println(entry);
                if ("".equals(entry.name)) {
                    entry.name = "Unbekannter Nutzer";
                }
                if ("".equals(entry.email)) {
                    entry.email = "Unbekannte Email";
                }
                if (!"".equals(entry.comment)) {
                    out.println("<DL>");
                    out.println("<DT><B>" + entry.name + "</B> (" + entry.email + ") sagt");
                    out.println("<DD><PRE>" + entry.comment + "</PRE>");
                    out.println("</DL>");
                }
            }
        } catch (SQLException ignored) {
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        } //idk why it should wait but it does have to
    }

    private static void appendAfterMessages(@NotNull PrintWriter out) {
        out.println("</main></div><footer id=\"footer\"></footer></body></html>");
    }

    private static void handleForm(@NotNull HttpServletRequest request) {
        SiteEntry entry = new SiteEntry();

        entry.name = request.getParameter("name");
        entry.email = request.getParameter("email");
        entry.comment = request.getParameter("comment");
        System.out.println("Saveing " + entry);
        try {
            HSQLDBEmbeddedServer
                    .getInstance()
                    .getConnection()
                    .prepareStatement(
                            "INSERT INTO GuestEntry(name, email, comment) VALUES ('"
                                    + entry.name + "','"
                                    + entry.email + "','"
                                    + entry.comment + "');"
                    ).execute();
        } catch (SQLException ignored) {
        }
    }


}

@SuppressWarnings("WeakerAccess")
class SiteEntry {
    public String name;
    public String email;
    public String comment;

    public SiteEntry(String name, String email, String comment) {
        this.name = name;
        this.email = email;
        this.comment = comment;
    }

    public SiteEntry() {

    }

    @Override
    public String toString() {
        return "SiteEntry{"
                + "name='" + name
                + "', email='" + email
                + "', comment='" + comment
                + "'}";
    }
}
