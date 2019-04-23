package clazzes;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum Pages {
    START("start", "start.jsp", "Willkommen"),
    LICHTGEWEHR("lichtgewehr", "lichtgewehr.jsp", "Lichtgewehr"),
    LUFTPISTOLE("luftpistole", "luftpistole.jsp", "Luftpistole"),
    LUFTGEWEHR("luftgewehr", "luftgewehr.jsp", "Luftgewehr"),
    GUEST("guest", "guest", "Gästebuch"),
    MEMBER("member", "member.jsp", "Mitglieder"),
    LOGIN("login", "loginPage.jsp", "Login"),
    HISTORY("history", "history.jsp", "History"),
    WETTKAMPF_TEILNAHME("teilnahme", "teilnahme.jsf", "Wettkampf Teilnahme"),
    A11("a11", "dynTableForm.jsp", "Task 11"),
    //INTERNAL SITES
    REGISTER("register", "", null),
    LOGIN_SUCCESS("loginsuccess", "loginSuccess.jsp", null),
    LOGIN_INVALID("invalidlogin", "invalidLogin.jsp", null),
    LOGIN_CHECK("logincheck", "", null),
    ERRORPAGE("404", "404.jsp", null),
    EMPTY("", "", null);


    private final String dispatchString;
    private final String fileName;
    private final String displayName;

    @Contract(pure = true)
    Pages(String dispatchString, String fileName, String displayName) {
        this.dispatchString = dispatchString;
        this.fileName = fileName;
        this.displayName = displayName;
    }

    public static Pages parse(String dispatchTo) {
        for (Pages p : Pages.values()) {
            if (p.getDispatchString().equals(dispatchTo)) {
                return p;
            }
        }
        System.out.println("FAILED PARSE FOR: " + dispatchTo);
        return ERRORPAGE;
    }

    @Contract(pure = true)
    public String getDispatchString() {
        return dispatchString;
    }

    @Contract(pure = true)
    public String getFileName() {
        return fileName;
    }

    @NotNull
    @Contract(pure = true)
    public String getFileNameWithSlash() {
        return "/" + fileName;
    }

    public String getDisplayName() {
        return displayName;
    }
}