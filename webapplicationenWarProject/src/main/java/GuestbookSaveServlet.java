import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;

/**
 * @author Janik Mayr on 27.03.2019
 */
public class GuestbookSaveServlet extends HttpServlet {
    private Vector entries = new Vector();
    private long lastModified = 0;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        appendHeader(out);
        appendForm(out);
        printMessages(out);
        printFooter(out);
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        handleForm(req, res);
        doGet(req, res);
    }

    private void appendHeader(@NotNull PrintWriter out) {
        out.println("<HTML><HEAD><TITLE>Guestbook</TITLE></HEAD>");
        out.println("<BODY>");
    }

    private void appendForm(@NotNull PrintWriter out) {
        out.println("<FORM METHOD=POST>");  // posts to itself
        out.println("<B>Please submit your feedback:</B><BR>");
        out.println("Your name: <INPUT TYPE=TEXT NAME=name><BR>");
        out.println("Your email: <INPUT TYPE=TEXT NAME=email><BR>");
        out.println("Comment: <INPUT TYPE=TEXT SIZE=50 NAME=comment><BR>");
        out.println("<INPUT TYPE=SUBMIT VALUE=\"Send Feedback\"><BR>");
        out.println("</FORM>");
        out.println("<HR>");
    }

    private void printMessages(PrintWriter out) {
        String name, email, comment;

        Enumeration e = entries.elements();
        while (e.hasMoreElements()) {
            SiteEntry entry = (SiteEntry) e.nextElement();
            name = entry.name;
            if (name == null) name = "Unknown user";
            email = entry.email;
            if (email == null) email = "Unknown email";
            comment = entry.comment;
            if (comment == null) comment = "No comment";
            out.println("<DL>");
            out.println("<DT><B>" + name + "</B> (" + email + ") says");
            out.println("<DD><PRE>" + comment + "</PRE>");
            out.println("</DL>");

            try { Thread.sleep(500); } catch (InterruptedException ignored) { }
        }
    }

    private void printFooter(@NotNull PrintWriter out) {
        out.println("</BODY>");
    }

    @SuppressWarnings("unchecked")
    private void handleForm(@NotNull HttpServletRequest request, HttpServletResponse response) {
        SiteEntry entry = new SiteEntry();

        entry.name = request.getParameter("name");
        entry.email = request.getParameter("email");
        entry.comment = request.getParameter("comment");

        entries.addElement(entry);

        // Update timestamp
        lastModified = System.currentTimeMillis();
    }

    @Override
    public long getLastModified(HttpServletRequest req) {
        return lastModified;
    }
}

class SiteEntry {
    public String name;
    public String email;
    public String comment;
}
