<%@ page import="clazzes.HSQLDBEmbeddedServer" %>
<%@ page import="static clazzes.STATIC_NAMES.GuestEntryTable.GUEST_ENTRY_TABLE_NAME_STRING" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="static clazzes.STATIC_NAMES.GuestEntryTable.GUEST_ENTRY_NAME_ATTRIBUTE_STRING" %>
<%@ page import="static clazzes.STATIC_NAMES.GuestEntryTable.*" %>
<%@ page import="static clazzes.STATIC_NAMES.UsersTable.USERS_TABLE_NAME_STRING" %>
<%@ page import="static clazzes.STATIC_NAMES.UsersTable.USERS_PERSON_ID_ATTRIBUTE_STRING" %>
<%@ page import="static clazzes.STATIC_NAMES.UsersTable.*" %>
<%@ page import="static clazzes.STATIC_NAMES.UserData.USER_DATA_TABLE_NAME_STRING" %>
<%@ page import="static clazzes.STATIC_NAMES.UserData.USER_DATA_PERSON_ID_ATTRIBUTE_STRING" %>
<%@ page import="static clazzes.STATIC_NAMES.UserData.*" %>
<%@ page import="static clazzes.STATIC_NAMES.CompetitionParticipation.COMPETITION_PARTICIPATION_TABLE_NAME_STRING" %>
<%@ page
        import="static clazzes.STATIC_NAMES.CompetitionParticipation.COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING" %>
<%@ page import="static clazzes.STATIC_NAMES.CompetitionParticipation.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>

<html lang="de">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <meta name="description" content="DATABASE DATA VIEW">
    <meta name="author" content="Janik Mayr">
    <meta name="rating" content="safe for kids">
    <meta name="reply-to" content="admin@host.com">
    <meta name="distribution" content="global">
    <meta http-equiv="refresh" content="43200">
    <meta name="keywords"
          content="Schützenverein,Freischütz,Geltendorf,
                    Luftgewehr,Luftpistole,Lichtgewehr,
                    Jugend,Gemeinschaft,1929,
                    82269,Verein"
    >

    <title>DEBUG - DATABASE DATA</title>

    <link rel="stylesheet" type="text/css" href="style.css?v=4.0">
    <link rel="stylesheet" type="text/css" href="webVars.css?v=4.0">
    <link rel="stylesheet" type="text/css" href="printVars.css?v=4.0" media="print"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script>
        document.cookie = "last=" + window.location.href + "; expires=" + new Date(new Date().getTime() + 24 * 60 * 60 * 1000) + ";";
        $(document).ready(function () {
            $('#nav').load("nav.jsp");
            $('#footer').load("footer.html");
        });
    </script>
</head>

<body>
<nav id="nav"></nav>
<div id="content">
    <header>
        <h1>
            DATABASE - DATA
        </h1>
    </header>
    <main>
        <%=GUEST_ENTRY_TABLE_NAME_STRING%>
        <table>
            <thead>
            <tr>
                <th>
                    <%=GUEST_ENTRY_NAME_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=GUEST_ENTRY_EMAIL_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=GUEST_ENTRY_COMMENT_ATTRIBUTE_STRING%>
                </th>
            </tr>
            </thead>
            <tbody>
            <%
                try {
                    ResultSet resultSet = HSQLDBEmbeddedServer.getInstance().getConnection().prepareStatement("SELECT * FROM " + GUEST_ENTRY_TABLE_NAME_STRING + ";").executeQuery();
                    while (resultSet.next()) {
                        out.print("<tr><td>"
                                + resultSet.getString(GUEST_ENTRY_NAME_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(GUEST_ENTRY_EMAIL_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(GUEST_ENTRY_COMMENT_ATTRIBUTE_STRING) + "</td></tr>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            %>
            </tbody>
        </table>
        <%=USERS_TABLE_NAME_STRING%>
        <table>
            <thead>
            <tr>
                <th>
                    <%=USERS_PERSON_ID_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=USERS_USERNAME_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=USERS_PASSWORD_ATTRIBUTE_STRING%>
                </th>
            </tr>
            </thead>
            <tbody>
            <%
                try {
                    ResultSet resultSet = HSQLDBEmbeddedServer.getInstance().getConnection().prepareStatement("SELECT * FROM " + USERS_TABLE_NAME_STRING + ";").executeQuery();
                    while (resultSet.next()) {
                        out.print("<tr><td>"
                                + resultSet.getString(USERS_PERSON_ID_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(USERS_USERNAME_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(USERS_PASSWORD_ATTRIBUTE_STRING) + "</td></tr>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            %>
            </tbody>
        </table>
        <%=USER_DATA_TABLE_NAME_STRING%>
        <table>
            <thead>
            <tr>
                <th>
                    <%=USER_DATA_PERSON_ID_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=USER_DATA_FNAME_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=USER_DATA_LNAME_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=USER_DATA_ACCESS_ATTRIBUTE_STRING%>
                </th>
            </tr>
            </thead>
            <tbody>
            <%
                try {
                    ResultSet resultSet = HSQLDBEmbeddedServer.getInstance().getConnection().prepareStatement("SELECT * FROM " + USER_DATA_TABLE_NAME_STRING + ";").executeQuery();
                    while (resultSet.next()) {
                        out.print("<tr><td>"
                                + resultSet.getString(USER_DATA_PERSON_ID_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(USER_DATA_FNAME_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(USER_DATA_LNAME_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(USER_DATA_ACCESS_ATTRIBUTE_STRING) + "</td></tr>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            %>
            </tbody>
        </table>
        <%=COMPETITION_PARTICIPATION_TABLE_NAME_STRING%>
        <table>
            <thead>
            <tr>
                <th>
                    <%=COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=COMPETITION_PARTICIPATION_ATTEND_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=COMPETITION_PARTICIPATION_LIFT_PLACES_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=COMPETITION_PARTICIPATION_SELF_ATTRIBUTE_STRING%>
                </th>
                <th>
                    <%=COMPETITION_PARTICIPATION_COMMENT_ATTRIBUTE_STRING%>
                </th>
            </tr>
            </thead>
            <tbody>
            <%
                try {
                    ResultSet resultSet = HSQLDBEmbeddedServer.getInstance().getConnection().prepareStatement("SELECT * FROM " + COMPETITION_PARTICIPATION_TABLE_NAME_STRING + ";").executeQuery();
                    while (resultSet.next()) {
                        out.print("<tr><td>"
                                + resultSet.getString(COMPETITION_PARTICIPATION_NAME_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(COMPETITION_PARTICIPATION_TEAM_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(COMPETITION_PARTICIPATION_COMPETITION_ID_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(COMPETITION_PARTICIPATION_ATTEND_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(COMPETITION_PARTICIPATION_LIFT_PLACES_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(COMPETITION_PARTICIPATION_SELF_ATTRIBUTE_STRING) + "</td><td>"
                                + resultSet.getString(COMPETITION_PARTICIPATION_COMMENT_ATTRIBUTE_STRING) + "</td></tr>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            %>
            </tbody>
        </table>
    </main>
</div>
<footer id="footer">
</footer>
</body>
</html>
