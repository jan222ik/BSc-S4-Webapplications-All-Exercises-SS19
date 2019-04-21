package clazzes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Deprecated
//@WebServlet(name = "clazzes.IndexRedirectServlet", urlPatterns = {"//*","/redirectMe"})
public class IndexRedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        boolean found = false;
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if ("last".equalsIgnoreCase(c.getName())) {
                    resp.sendRedirect(c.getValue());
                    c.setMaxAge(0);
                    found = true;
                    break;
                }
            }
        }
        System.out.println(found);
        if (!found) {
            resp.sendRedirect(req.getContextPath() + "/start.html");
        }
    }
}
