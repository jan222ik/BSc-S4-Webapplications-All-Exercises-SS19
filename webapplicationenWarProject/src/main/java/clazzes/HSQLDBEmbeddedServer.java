package clazzes;

import org.hsqldb.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HSQLDBEmbeddedServer {

    Server hsqlServer;
    Connection connection = null;
    private static HSQLDBEmbeddedServer embeddedServer;

    private HSQLDBEmbeddedServer() {
        hsqlServer = new Server();
        hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(true);
        hsqlServer.setDatabaseName(0, "xdb");
        hsqlServer.setDatabasePath(0, "file:testdb");
    }

    public static HSQLDBEmbeddedServer getInstance() {
        if (embeddedServer == null) {
            embeddedServer = new HSQLDBEmbeddedServer();
            embeddedServer.start();
        }
        return embeddedServer;
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
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS Users (personId BIGINT, username VARCHAR(50), password VARCHAR(50), PRIMARY KEY (personId));").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS UserData (personId BIGINT, fname VARCHAR(50), lname VARCHAR(50), access VARCHAR(50), FOREIGN KEY (personId) REFERENCES  Users(personId))").execute();
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
            connection.prepareStatement("DROP TABLE IF EXISTS Users").execute();
            connection.prepareStatement("DROP TABLE IF EXISTS UserData").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS GuestEntry (name VARCHAR(50),email VARCHAR(50), comment VARCHAR(144));").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS Users (personId BIGINT, username VARCHAR(50), password VARCHAR(50), PRIMARY KEY (personId));").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS UserData (personId BIGINT, fname VARCHAR(50), lname VARCHAR(50), access INT, FOREIGN KEY (personId) REFERENCES  Users(personId))").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
