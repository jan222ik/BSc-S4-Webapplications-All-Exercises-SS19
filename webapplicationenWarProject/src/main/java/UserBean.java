public class UserBean {
    String personId;
    String username;
    String password;

    public UserBean(String personId, String username, String password) {
        this.personId = personId;
        this.username = username;
        this.password = password;
    }

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkUsernameAndPassword(String username, String password) {
        return checkUsername(username) && checkPassword(password);
    }

    private boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    private boolean checkUsername(String username) {
        return this.username.equals(username);
    }
}
