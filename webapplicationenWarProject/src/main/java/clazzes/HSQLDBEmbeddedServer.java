package clazzes;

import org.hsqldb.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static clazzes.STATIC_NAMES.CompetitionParticipation.*;
import static clazzes.STATIC_NAMES.GuestEntryTable.*;
import static clazzes.STATIC_NAMES.UserData.*;
import static clazzes.STATIC_NAMES.UsersTable.*;

@SuppressWarnings({"Duplicates", "SqlNoDataSourceInspection"})
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
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + GUEST_ENTRY_TABLE_NAME_STRING + " (" + GUEST_ENTRY_NAME_ATTRIBUTE_STRING + " VARCHAR(50)," + GUEST_ENTRY_EMAIL_ATTRIBUTE_STRING + " VARCHAR(50), " + GUEST_ENTRY_COMMENT_ATTRIBUTE_STRING + " VARCHAR(144));").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + USERS_TABLE_NAME_STRING +" (" + USERS_PERSON_ID_ATTRIBUTE_STRING + " BIGINT, " + USERS_USERNAME_ATTRIBUTE_STRING + " VARCHAR(50), " + USERS_PASSWORD_ATTRIBUTE_STRING + " VARCHAR(50), PRIMARY KEY (" + USERS_PERSON_ID_ATTRIBUTE_STRING + "));").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + USER_DATA_TABLE_NAME_STRING +" (" + USER_DATA_PERSON_ID_ATTRIBUTE_STRING + " BIGINT, " + USER_DATA_FNAME_ATTRIBUTE_STRING + " VARCHAR(50), " + USER_DATA_LNAME_ATTRIBUTE_STRING + " VARCHAR(50), " + USER_DATA_ACCESS_ATTRIBUTE_STRING + " VARCHAR(50), FOREIGN KEY (" + USER_DATA_PERSON_ID_ATTRIBUTE_STRING + ") REFERENCES  " + USERS_TABLE_NAME_STRING + "(" + USERS_PERSON_ID_ATTRIBUTE_STRING +"))").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + COMPETITION_PARTICIPATION_TABLE_NAME_STRING
                    + " ("
                        + COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING + " VARCHAR(50),"
                        + COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING + " VARCHAR(50), "
                        + COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING + " VARCHAR(50),"
                        + COMPETITION_PARTICIPATION_ATTEND_ATTRIBUTE_STRING + " VARCHAR(50),"
                        + COMPETITION_PARTICIPATION_SELF_ATTRIBUTE_STRING + " VARCHAR(50),"
                        + COMPETITION_PARTICIPATION_LIFT_PLACES_ATTRIBUTE_STRING + " VARCHAR(50),"
                        + COMPETITION_PARTICIPATION_COMMENT_ATTRIBUTE_STRING + " VARCHAR(144),"
                    + " PRIMARY KEY(" + COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING + ", "
                    + COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING + ", "
                    + COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING  + "))"
            ).execute();
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
            connection.prepareStatement("DROP TABLE IF EXISTS " + GUEST_ENTRY_TABLE_NAME_STRING).execute();
            //connection.prepareStatement("DROP TABLE IF EXISTS " + USERS_TABLE_NAME_STRING).execute();
           // connection.prepareStatement("DROP TABLE IF EXISTS " + USER_DATA_TABLE_NAME_STRING).execute();
            connection.prepareStatement("DROP TABLE IF EXISTS " + COMPETITION_PARTICIPATION_TABLE_NAME_STRING).execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + GUEST_ENTRY_TABLE_NAME_STRING + " (" + GUEST_ENTRY_NAME_ATTRIBUTE_STRING + " VARCHAR(50)," + GUEST_ENTRY_EMAIL_ATTRIBUTE_STRING + " VARCHAR(50), " + GUEST_ENTRY_COMMENT_ATTRIBUTE_STRING + " VARCHAR(144));").execute();
            //connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + USERS_TABLE_NAME_STRING +" (" + USERS_PERSON_ID_ATTRIBUTE_STRING + " BIGINT, " + USERS_USERNAME_ATTRIBUTE_STRING + " VARCHAR(50), " + USERS_PASSWORD_ATTRIBUTE_STRING + " VARCHAR(50), PRIMARY KEY (" + USERS_PERSON_ID_ATTRIBUTE_STRING + "));").execute();
            //connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + USER_DATA_TABLE_NAME_STRING +" (" + USER_DATA_PERSON_ID_ATTRIBUTE_STRING + " BIGINT, " + USER_DATA_FNAME_ATTRIBUTE_STRING + " VARCHAR(50), " + USER_DATA_LNAME_ATTRIBUTE_STRING + " VARCHAR(50), " + USER_DATA_ACCESS_ATTRIBUTE_STRING + " VARCHAR(50), FOREIGN KEY (" + USER_DATA_PERSON_ID_ATTRIBUTE_STRING + ") REFERENCES  " + USERS_TABLE_NAME_STRING + "(" + USERS_PERSON_ID_ATTRIBUTE_STRING +"))").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + COMPETITION_PARTICIPATION_TABLE_NAME_STRING
                    + " ("
                    + COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING + " VARCHAR(50),"
                    + COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING + " VARCHAR(50), "
                    + COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING + " VARCHAR(50),"
                    + COMPETITION_PARTICIPATION_ATTEND_ATTRIBUTE_STRING + " VARCHAR(50),"
                    + COMPETITION_PARTICIPATION_SELF_ATTRIBUTE_STRING + " VARCHAR(50),"
                    + COMPETITION_PARTICIPATION_LIFT_PLACES_ATTRIBUTE_STRING + " VARCHAR(50),"
                    + COMPETITION_PARTICIPATION_COMMENT_ATTRIBUTE_STRING + " VARCHAR(144),"
                    + " PRIMARY KEY(" + COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING + ", "
                    + COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING + ", "
                    + COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING  + "))"
            ).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDummyData() {
        try {
            getConnection().prepareStatement(" insert into competition_participation (name, team, compId, attendance, self, lift, comment) values ('Hans', 'Luftgewehr 1', '0', 'ja;', 'true', '3', 'Lorem ipsum dolor sit amet.');").execute();
            getConnection().prepareStatement(" insert into competition_participation (name, team, compId, attendance, self, lift, comment) values ('Johann', 'Luftgewehr 1', '0', 'ja;', 'false', '0', 'Lorem ipsum dolor sit amet.');" ).execute();
            getConnection().prepareStatement(" insert into competition_participation (name, team, compId, attendance, self, lift, comment) values ('Joseph', 'Luftgewehr 1', '0', 'ja;', 'false', '0', 'Lorem ipsum dolor sit amet.');" ).execute();
            getConnection().prepareStatement(" insert into competition_participation (name, team, compId, attendance, self, lift, comment) values ('Hans', 'Luftgewehr 1', '1', 'ja;', 'true', '1', 'Lorem ipsum dolor sit amet.');" ).execute();
            getConnection().prepareStatement(" insert into competition_participation (name, team, compId, attendance, self, lift, comment) values ('Hans', 'Luftgewehr 1', '2', 'ja;', 'false', '0', 'Lorem ipsum dolor sit amet.');" ).execute();
            getConnection().prepareStatement(" insert into competition_participation (name, team, compId, attendance, self, lift, comment) values ('Hans', 'Luftpistole 1', '4', 'ja;', 'true', '9', 'Lorem ipsum dolor sit amet.');" ).execute();
            getConnection().prepareStatement(" insert into competition_participation (name, team, compId, attendance, self, lift, comment) values ('Hans', 'Luftpistole 1', '5', 'ja;', 'true', '0', 'Lorem ipsum dolor sit amet.');" ).execute();
            getConnection().prepareStatement(" insert into competition_participation (name, team, compId, attendance, self, lift, comment) values ('Joseph', 'Luftpistole 1', '5', 'ja;', 'false', '0', 'Lorem ipsum dolor sit amet.');" ).execute();
            getConnection().prepareStatement(" insert into competition_participation (name, team, compId, attendance, self, lift, comment) values ('Hans', 'Luftpistole 1', '3', 'ja;', 'true', '8', 'Lorem ipsum dolor sit amet.');" ).execute();
            getConnection().prepareStatement(" insert into competition_participation (name, team, compId, attendance, self, lift, comment) values ('Johann', 'Luftpistole 1', '3', 'ja;', 'false', '0', 'Lorem ipsum dolor sit amet.');").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetWettkampfTables() {
        try {
            connection.prepareStatement("DROP TABLE IF EXISTS " + COMPETITION_PARTICIPATION_TABLE_NAME_STRING).execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + COMPETITION_PARTICIPATION_TABLE_NAME_STRING
                    + " ("
                    + COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING + " VARCHAR(50),"
                    + COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING + " VARCHAR(50), "
                    + COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING + " VARCHAR(50),"
                    + COMPETITION_PARTICIPATION_ATTEND_ATTRIBUTE_STRING + " VARCHAR(50),"
                    + COMPETITION_PARTICIPATION_SELF_ATTRIBUTE_STRING + " VARCHAR(50),"
                    + COMPETITION_PARTICIPATION_LIFT_PLACES_ATTRIBUTE_STRING + " VARCHAR(50),"
                    + COMPETITION_PARTICIPATION_COMMENT_ATTRIBUTE_STRING + " VARCHAR(144))"
            ).execute();
            insertDummyData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("CREATE TABLE IF NOT EXISTS " + COMPETITION_PARTICIPATION_TABLE_NAME_STRING
                + " ("
                + COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING + " VARCHAR(50),"
                + COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING + " VARCHAR(50), "
                + COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING + " VARCHAR(50),"
                + COMPETITION_PARTICIPATION_ATTEND_ATTRIBUTE_STRING + " VARCHAR(50),"
                + COMPETITION_PARTICIPATION_SELF_ATTRIBUTE_STRING + " VARCHAR(50),"
                + COMPETITION_PARTICIPATION_LIFT_PLACES_ATTRIBUTE_STRING + " VARCHAR(50),"
                + COMPETITION_PARTICIPATION_COMMENT_ATTRIBUTE_STRING + " VARCHAR(144),"
                + " PRIMARY KEY(" + COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING + ", "
                + COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING + ", "
                + COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING  + "))"
        );
        System.out.println("CREATE TABLE IF NOT EXISTS " + USER_DATA_TABLE_NAME_STRING +" (" + USER_DATA_PERSON_ID_ATTRIBUTE_STRING + " BIGINT, " + USER_DATA_FNAME_ATTRIBUTE_STRING + " VARCHAR(50), " + USER_DATA_LNAME_ATTRIBUTE_STRING + " VARCHAR(50), " + USER_DATA_ACCESS_ATTRIBUTE_STRING + " VARCHAR(50), FOREIGN KEY (" + USER_DATA_PERSON_ID_ATTRIBUTE_STRING + ") REFERENCES  " + USERS_TABLE_NAME_STRING + "(" + USERS_PERSON_ID_ATTRIBUTE_STRING +"))");
        System.out.println("CREATE TABLE IF NOT EXISTS " + USERS_TABLE_NAME_STRING +" (" + USERS_PERSON_ID_ATTRIBUTE_STRING + " BIGINT, " + USERS_USERNAME_ATTRIBUTE_STRING + " VARCHAR(50), " + USERS_PASSWORD_ATTRIBUTE_STRING + " VARCHAR(50), PRIMARY KEY (" + USERS_PERSON_ID_ATTRIBUTE_STRING + "));");
        System.out.println("CREATE TABLE IF NOT EXISTS " + GUEST_ENTRY_TABLE_NAME_STRING + " (" + GUEST_ENTRY_NAME_ATTRIBUTE_STRING + " VARCHAR(50)," + GUEST_ENTRY_EMAIL_ATTRIBUTE_STRING + " VARCHAR(50), " + GUEST_ENTRY_COMMENT_ATTRIBUTE_STRING + " VARCHAR(144));");
        String team = "Team";
        System.out.println("SELECT " + COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING
                + ", " + COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING
                + ", " + COMPETITION_PARTICIPATION_ATTEND_ATTRIBUTE_STRING
                + ", " + COMPETITION_PARTICIPATION_LIFT_PLACES_ATTRIBUTE_STRING
                + ", " + COMPETITION_PARTICIPATION_SELF_ATTRIBUTE_STRING
                + " FROM " + COMPETITION_PARTICIPATION_TABLE_NAME_STRING
                + " WHERE " + COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING + " = '" + team + "'");
    }
}
