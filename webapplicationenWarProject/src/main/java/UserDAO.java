import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean check(UserBean bean) {
        return true; //TODO
    }

    public static UserBean getBean(String username) throws SQLException {
        HSQLDBEmbeddedServer embeddedServer = HSQLDBEmbeddedServer.getInstance();
        ResultSet resultSet = embeddedServer.getConnection().prepareStatement("SELECT * FROM User WHERE username = '" + username + "';").executeQuery();
        return (resultSet.next()) ? new UserBean(resultSet.getString(0), resultSet.getString(1), resultSet.getString(2)):null;
    }
}
