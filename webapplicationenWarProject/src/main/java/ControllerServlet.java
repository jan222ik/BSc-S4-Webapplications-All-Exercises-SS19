import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "con", urlPatterns = {"//*", "/con"})
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getRequestURI());
        String dispatchTo = req.getParameter("dispatchTo");
        String dispatchAfter = req.getParameter("dispatchAfter");
        dispatchTo = (dispatchTo != null) ? dispatchTo.toLowerCase() : "";
        System.out.println("dispatchTo = " + dispatchTo);
        Pages targetPage = Pages.parse(dispatchTo);
        switch (targetPage) {
            case EMPTY:
                empty(req, resp);
                break;
            case START:
                forwardSide(Pages.START, req, resp);
                break;
            case MEMBER:
                forwardSide(Pages.MEMBER, req, resp);
                break;
            case GUEST:
                forwardSide(Pages.GUEST, req, resp);
                break;
            case A11:
                forwardSide(Pages.A11, req, resp);
                break;
            case LOGIN:
                checkLoginStatus(Pages.START, req, resp);
                break;
            case LOGIN_CONTROLER:
                checkLoginForm(dispatchAfter, req, resp);
                break;
            //Sites where you have to be logged in.
            case LICHTGEWEHR:
                checkLoginStatus(Pages.LICHTGEWEHR, req, resp);
                break;
            case LUFTGEWEHR:
                checkLoginStatus(Pages.LUFTGEWEHR, req, resp);
                break;
            case LUFTPISTOLE:
                checkLoginStatus(Pages.LUFTPISTOLE, req, resp);
                break;
            default:
                show404(req, resp);
        }

    }

    private void checkLoginForm(String dispatchAfter, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String usernameParam = req.getParameter("usernameParam");
            String passwordParam = req.getParameter("passwordParam");
            UserBean user = UserDAO.getBean(usernameParam);
            if (user != null && user.checkUsernameAndPassword(usernameParam, passwordParam)) {
                if (dispatchAfter == null || dispatchAfter.equals("")) {
                    dispatchAfter = "start";
                }
                forwardSide(dispatchAfter, req, resp);
            } else {
                getServletContext().getRequestDispatcher("/invalidLogin.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkLoginStatus(Pages dispatchTo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserBean user = (UserBean) session.getAttribute("user");
        if (user != null) {
            forwardSide(dispatchTo, req, resp);
        } else {
            getServletContext().getRequestDispatcher("/loginPage.jsp?dispatchAfter=" + dispatchTo).forward(req, resp);
        }
    }

    private void show404(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/404.jsp").forward(req, resp);
    }

    private void forwardSide(Pages site, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(site.getFileNameWithSlash()).forward(req, resp);
    }


    /**
     * Redirect.
     *
     * @param req  request
     * @param resp response
     * @throws IOException if file does not exist
     */
    private void empty(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
            forwardSide(Pages.START, req, resp);
        }
    }

    private enum Pages {
        START("start", "start.jsp"),
        GUEST("guest", "guest"),
        MEMBER("member", "member.jsp"),
        LICHTGEWEHR("lichtgewehr", "lichtgewehr.jsp"),
        LUFTPISTOLE("luftpistole", "luftpistole.jsp"),
        LUFTGEWEHR("luftgewehr", "luftgewehr.jsp"),
        LOGIN("login", "loginPage.jsp"),
        LOGIN_CONTROLER("logincontroller", "404.jsp"),
        ERRORPAGE("404", "404.jsp"),
        A11("a11", "dynTableForm.jsp"),
        EMPTY("", "empty") ;


        private final String dispatchString;
        private final String fileName;

        Pages(String dispatchString, String fileName) {
            this.dispatchString = dispatchString;
            this.fileName = fileName;
        }

        public static Pages parse(String dispatchTo) {
            return START;
            //TODO
        }

        public String getDispatchString() {
            return dispatchString;
        }

        public String getFileName() {
            return fileName;
        }

        public String getFileNameWithSlash() {
            return "/" + fileName;
        }
    }

}
