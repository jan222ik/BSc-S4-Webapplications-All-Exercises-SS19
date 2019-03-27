import org.hsqldb.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HSQLDBEmbeddedServer {

    Server hsqlServer;
    Connection connection = null;

    public HSQLDBEmbeddedServer() {
        hsqlServer = new Server();
        hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(true);
        hsqlServer.setDatabaseName(0, "xdb");
        hsqlServer.setDatabasePath(0, "file:testdb");
    }

    public void start() {
        hsqlServer.start();
        connection = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            // Default user of the HSQLDB is 'sa'
            // with an empty password
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "sa", "");
            //CREATE Table
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS GuestEntry (name VARCHAR(50),email VARCHAR(50), comment VARCHAR(144));").execute();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void stop() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        if (hsqlServer != null) {
            hsqlServer.stop();
        }
    }

    public void reset() {
        try {
            connection.prepareStatement("DROP TABLE IF EXISTS GuestEntry").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS GuestEntry (name VARCHAR(50),email VARCHAR(50), comment VARCHAR(144));").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
