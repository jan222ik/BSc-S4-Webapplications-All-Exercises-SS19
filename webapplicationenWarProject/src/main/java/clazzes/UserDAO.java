package clazzes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean check(UserBean bean) {
        return true; //TODO
    }

    public static UserBean getBean(String username) throws SQLException {
        HSQLDBEmbeddedServer embeddedServer = HSQLDBEmbeddedServer.getInstance();
        ResultSet resultSet = embeddedServer.getConnection().prepareStatement("SELECT * FROM Users WHERE username = '" + username + "';").executeQuery();
        return (resultSet.next()) ? new UserBean(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)):null;
    }

    public static void saveBean(UserBean bean) throws SQLException {
        int id = 0;
        HSQLDBEmbeddedServer embeddedServer = HSQLDBEmbeddedServer.getInstance();
        ResultSet resultSet = embeddedServer.getConnection().prepareStatement("SELECT * FROM Users;").executeQuery();
        while (resultSet.next()) {
            int anInt = Integer.parseInt(resultSet.getString(1));
            id = Math.max(id, anInt);
        }
        id++;
        bean.setPersonId("" + id);
        embeddedServer.getConnection().prepareStatement("INSERT INTO Users (personId, username, password) VALUES ('" + id + "','" + bean.getUsername() + "','" + bean.getPassword() + "')").execute();
    }
}
