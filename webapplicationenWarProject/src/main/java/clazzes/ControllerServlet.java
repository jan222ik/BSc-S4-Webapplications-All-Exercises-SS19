package clazzes;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.jetbrains.annotations.NotNull;

import static clazzes.STATIC_NAMES.*;
import static clazzes.STATIC_NAMES.LoginForm.LOGIN_PASSWORD_PARAMETER_STRING;
import static clazzes.STATIC_NAMES.LoginForm.LOGIN_USERNAME_PARAMETER_STRING;
import static clazzes.STATIC_NAMES.SignUpForm.*;

@WebServlet(name = "con", urlPatterns = {"//*", "/con"})
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getRequestURI());
        String dispatchTo = req.getParameter(DISPATCH_TO_PARAMETER_STRING);
        dispatchTo = (dispatchTo != null) ? dispatchTo.toLowerCase() : "";
        System.out.println(DISPATCH_TO_PARAMETER_STRING + " = " + dispatchTo);
        Pages targetPage = Pages.parse(dispatchTo);
        handleGet(targetPage, req, resp);
    }

    private void handleGet(Pages targetPage, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        switch (targetPage) {
            case EMPTY:
                //toLastSeenPage(req, resp); break; Bug mit redirect
            case START:
                forwardTo(Pages.START, req, resp);
                break;
            case MEMBER:
                forwardTo(Pages.MEMBER, req, resp);
                break;
            case LOGIN_CHECK:
                checkLoginForm(req, resp);
                break;
            case REGISTER: checkRegisterForm(req, resp);break;
            case GUEST:
                handleGuest(req, resp);
                break;
            case A11:
                forwardTo(Pages.A11, req, resp);
                break;
            case LOGIN:
                forwardTo(Pages.LOGIN, req, resp);
                break;
            case LICHTGEWEHR:
                checkLoginStatus(Pages.LICHTGEWEHR, req, resp);
                break;
            case LUFTGEWEHR:
                checkLoginStatus(Pages.LUFTGEWEHR, req, resp);
                break;
            case LUFTPISTOLE:
                checkLoginStatus(Pages.LUFTPISTOLE, req, resp);
                break;
            case HISTORY:
                forwardTo(Pages.HISTORY, req, resp);
                break;
            default:
                error404(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GuestBookSaveServlet.handlePost(req, resp);
    }

    private void handleGuest(HttpServletRequest req, HttpServletResponse resp) {
        logInHistory(Pages.GUEST, req);
        try {
            GuestBookSaveServlet.handleGet(req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkRegisterForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter(SIGN_UP_FIRST_NAME_PARAMETER_STRING);
        String lastName = req.getParameter(SIGN_UP_LAST_NAME_PARAMETER_STRING);
        String access = req.getParameter(SIGN_UP_ACCESS_PARAMETER_STRING);
        String username = req.getParameter(SIGN_UP_USERNAME_PARAMETER_STRING);
        String password = req.getParameter(SIGN_UP_PASSWORD_PARAMETER_STRING);
        //System.out.println("firstname = " + firstName);
        //System.out.println("lastname = " + lastName);
        //System.out.println("access = " + access);
        //System.out.println("username = " + username);
        //System.out.println("password = " + password);
        UserBean newUser = new UserBean(username, password);
        try {
            UserDAO.saveBean(newUser);
            req.getSession().setAttribute(USER_SESSION_ATTRIBUTE_STRING, newUser);
            forwardTo(Pages.LOGIN_SUCCESS, req, resp);
            HSQLDBEmbeddedServer
                    .getInstance()
                    .getConnection()
                    .prepareStatement("INSERT INTO UserData (personId, fname, lname, access) VALUES ('" + newUser.personId + "','" + firstName + "','" + lastName + "','" + access + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            forwardTo(Pages.ERRORPAGE, req, resp);
        }
    }

    private void checkLoginForm(@NotNull HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //System.out.println("CHECK LOGIN FORM");
            String usernameParam = req.getParameter(LOGIN_USERNAME_PARAMETER_STRING);
            String passwordParam = req.getParameter(LOGIN_PASSWORD_PARAMETER_STRING);
            UserBean user = UserDAO.getBean(usernameParam);
            if (user != null) {
                if (user.checkUsernameAndPassword(usernameParam, passwordParam)) {
                    //System.out.println("LOGIN SUCCESSFUL");
                    req.getSession().setAttribute(USER_SESSION_ATTRIBUTE_STRING, user);
                    forwardTo(Pages.LOGIN_SUCCESS, req, resp);
                } else {
                    //System.out.println("PASSWORD WRONG");
                    forwardTo(Pages.LOGIN_INVALID, req, resp);
                }
            } else {
                //System.out.println("USERNAME UNKNOWN");
                forwardTo(Pages.LOGIN_INVALID, req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if user is logged in.
     *
     * @param dispatchTo desired site.
     * @param req        request
     * @param resp       response
     * @throws ServletException if error occurs
     * @throws IOException      if error occurs
     */
    private void checkLoginStatus(Pages dispatchTo, @NotNull HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserBean user = (UserBean) session.getAttribute(USER_SESSION_ATTRIBUTE_STRING);
        if (user != null) {
            forwardTo(dispatchTo, req, resp);
        } else {
            forwardTo(Pages.LOGIN, req, resp);
        }
    }

    /**
     * Sets 404 status and forwards to 404 page.
     *
     * @param req  request
     * @param resp response
     * @throws ServletException if error occurs
     * @throws IOException      if error occurs
     */
    private void error404(HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(404);
        forwardTo(Pages.ERRORPAGE, req, resp);
    }

    /**
     * Forwards to specified page.
     *
     * @param site site to forward to
     * @param req  request
     * @param resp response
     * @throws ServletException if error occurs
     * @throws IOException      if error occurs
     */
    private void forwardTo(Pages site, @NotNull HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("FORWARDING TO " + site.name());
        logInHistory(site, req);
        getServletContext().getRequestDispatcher(site.getFileNameWithSlash()).forward(req, resp);
    }

    /**
     * Logs user activity in user's history, if site has a display name.
     *
     * @param site Site to log
     * @param req  request
     */
    private void logInHistory(@NotNull Pages site, @NotNull HttpServletRequest req) {
        //System.out.println("LOG HISTORY");
        if (site.getDisplayName() != null) {
            UserBean user = (UserBean) req.getSession().getAttribute(USER_SESSION_ATTRIBUTE_STRING);
            if (user != null) {
                //System.out.println("USER IS LOGGED IN");
                HistoryEntry entry = new HistoryEntry(site, LocalDateTime.now());
                HistoryContainer container = (HistoryContainer) req.getSession().getAttribute(HISTORY_CONTAINER_ATTRIBUTE_STRING);
                if (container == null) {
                    container = new HistoryContainer();
                }
                container.getEntries().addLast(entry);
                req.getSession().setAttribute(HISTORY_CONTAINER_ATTRIBUTE_STRING, container);
                //System.out.println("LOGGED: " + entry.toDisplayString() + container);
            }
        }
    }

    @Deprecated
    private void toLastSeenPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //System.out.println("FORWARD TO LAST SEEN");
        Cookie[] cookies = req.getCookies();
        boolean found = false;
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if ("last".equalsIgnoreCase(c.getName())) {
                    //System.out.println("COOKIE FOUND");
                    handleGet(Pages.parse(c.getValue().split("=")[1]), req, resp);
                    c.setMaxAge(0);
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            forwardTo(Pages.START, req, resp);
        }
    }

    @Override
    public void destroy() {
        try {
            HSQLDBEmbeddedServer.getInstance().stop();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
