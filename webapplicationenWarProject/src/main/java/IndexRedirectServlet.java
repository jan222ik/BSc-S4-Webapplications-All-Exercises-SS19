import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexRedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("I GET CAQLLED");
        Cookie[] cookies = req.getCookies();
        if (cookies != null && cookies.length > 0) {
            boolean found = false;
            for (Cookie c : cookies) {
                if ("last".equalsIgnoreCase(c.getName())) {
                    resp.sendRedirect(c.getValue());
                    found = true;
                    break;
                }
            }
            if (!found) {
                resp.sendRedirect(req.getContextPath() + "/start.html");
                //RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/start.html");
                //dispatcher.forward(req, resp);
            }
        }
    }
}
