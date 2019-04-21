package clazzes;

public interface STATIC_NAMES {
    String USER_SESSION_ATTRIBUTE_STRING = "user";
    String HISTORY_CONTAINER_ATTRIBUTE_STRING = "history";
    String DISPATCH_TO_PARAMETER_STRING = "dispatchTo";

    interface LoginForm {
        String LOGIN_USERNAME_PARAMETER_STRING = "usernameParam";
        String LOGIN_PASSWORD_PARAMETER_STRING = "passwordParam";
    }

    interface SignUpForm {
        String SIGN_UP_FIRST_NAME_PARAMETER_STRING = "firstname";
        String SIGN_UP_LAST_NAME_PARAMETER_STRING = "lastname";
        String SIGN_UP_ACCESS_PARAMETER_STRING = "access";
        String SIGN_UP_USERNAME_PARAMETER_STRING = "username";
        String SIGN_UP_PASSWORD_PARAMETER_STRING = "password";
    }

}
