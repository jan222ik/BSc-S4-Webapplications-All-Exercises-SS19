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

    interface GuestEntryTable {
        String GUEST_ENTRY_TABLE_NAME_STRING = "GuestEntry";
        String GUEST_ENTRY_NAME_ATTRIBUTE_STRING = "name";
        String GUEST_ENTRY_EMAIL_ATTRIBUTE_STRING = "email";
        String GUEST_ENTRY_COMMENT_ATTRIBUTE_STRING = "comment";

    }

    interface UsersTable {
        String USERS_TABLE_NAME_STRING = "Users";
        String USERS_PERSON_ID_ATTRIBUTE_STRING = "personId";
        String USERS_USERNAME_ATTRIBUTE_STRING = "username";
        String USERS_PASSWORD_ATTRIBUTE_STRING = "password";
    }

    interface UserData {
        String USER_DATA_TABLE_NAME_STRING = "UserData";
        String USER_DATA_PERSON_ID_ATTRIBUTE_STRING = "personId";
        String USER_DATA_FNAME_ATTRIBUTE_STRING = "fname";
        String USER_DATA_LNAME_ATTRIBUTE_STRING = "lname";
        String USER_DATA_ACCESS_ATTRIBUTE_STRING = "access";
    }

    interface CompetitionParticipation {
        String COMPETITION_PARTICIPATION_TABLE_NAME_STRING = "competition_participation";
        String COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING = "name";
        String COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING = "team";
        String COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING = "compId";
        String COMPETITION_PARTICIPATION_ATTEND_ATTRIBUTE_STRING = "attendance";
        String COMPETITION_PARTICIPATION_SELF_ATTRIBUTE_STRING = "self";
        String COMPETITION_PARTICIPATION_LIFT_PLACES_ATTRIBUTE_STRING = "lift";
        String COMPETITION_PARTICIPATION_COMMENT_ATTRIBUTE_STRING = "comment";
    }


}
