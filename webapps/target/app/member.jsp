<%@ page import="static clazzes.STATIC_NAMES.SignUpForm.SIGN_UP_FIRST_NAME_PARAMETER_STRING" %>
<%@ page import="static clazzes.STATIC_NAMES.SignUpForm.SIGN_UP_LAST_NAME_PARAMETER_STRING" %>
<%@ page import="static clazzes.STATIC_NAMES.SignUpForm.*" %>
<%@ page import="static clazzes.STATIC_NAMES.DISPATCH_TO_PARAMETER_STRING" %><%--
  Created by IntelliJ IDEA.
  User: jan22
  Date: 16.04.2019
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="de">
<head>
    <meta charset="utf-8">
    <meta name =”robots” content=”index”>
    <meta name =”robots” content=”follow”>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <meta name="description" content="Abteilung Luftpistole">
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


    <title>Freischütz Geltendorf - Mitglieder</title>

    <link rel="stylesheet" type="text/css" href="style.css?v=4.0">
    <link rel="stylesheet" type="text/css" href="webVars.css?v=4.0">
    <link rel="stylesheet" type="text/css" href="printVars.css?v=4.0" media="print" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script>
        document.cookie = "last=" + window.location.href + "; expires=" + new Date(new Date().getTime() + 24 * 60 * 60 * 1000) + ";";
        $(document).ready(function(){
            $('#nav').load("nav.html");
            $('#footer').load("footer.html");
        });
    </script>
    <script src="member.js"></script>
</head>

<body>
<nav id="nav">

</nav>
<div id="content">
    <header>
        <h1>
            Mitgliederportal
        </h1>
    </header>
    <main>
        <form id="usrform" action="con" method="get" enctype="text/plain" accept-charset="UTF-8">
            <label>
                Vorname: <input type="text" name="<%=SIGN_UP_FIRST_NAME_PARAMETER_STRING%>" id="fname"><br>
                Nachname: <input type="text" name="<%=SIGN_UP_LAST_NAME_PARAMETER_STRING%>" id="lname"><br>
                Addresse: <br>
                <textarea name="address" id="address" rows="3"></textarea><br>
                Geschlecht:<br>
                <span class="tab-indent">
                        <input type="radio" name="gender" value="männlich" id="male"> Male<br>
                        <input type="radio" name="gender" value="weiblich" id="female"> Female<br>
                        <input type="radio" name="gender" value="anders" id="other" checked> Other<br>
                    </span>
                Username: <input type="text" name="<%=SIGN_UP_USERNAME_PARAMETER_STRING%>" id="username"><br>
                Password: <input type="password" name="<%=SIGN_UP_PASSWORD_PARAMETER_STRING%>" id="password"><br>
                Password wiederholen: <input type="password" name="passwordRepeat" id="passwordRepeat"><br>
                Land:<br>
                <select>
                    <option value="austria">Österreich</option>
                    <option value="switzerland">Schweiz</option>
                    <option value="germany">Deutschland</option>
                    <option value="other">Anderes</option>
                </select><br>
                Disziplin:<br>
                <select>
                    <option value="lg">Luftgewehr</option>
                    <option value="lp">Luftpistole</option>
                    <option value="li">Lichtgewehr</option>
                </select><br>
                Berechtigung:<br>
                <span class="tab-indent">
                        <input type="checkbox" name="<%=SIGN_UP_ACCESS_PARAMETER_STRING%>" value="shooter" id="shooter" checked>Schütze<br>
                        <input type="checkbox" name="<%=SIGN_UP_ACCESS_PARAMETER_STRING%>" value="leader" id="leader">Abteilungsleiter<br>
                        <input type="checkbox" name="<%=SIGN_UP_ACCESS_PARAMETER_STRING%>" value="admin" id="admin">Admin<br/>
                        E-Mail: <input type="text" name="email" id="email"><br>
                    </span>
                <input type="hidden" name="<%=DISPATCH_TO_PARAMETER_STRING%>" value="register">
                <button type="reset">Zurücksetzen</button>
                <button type="button" onclick="submitCheck()">Absenden</button>
            </label>
        </form>
    </main>
</div>
<footer id="footer">

</footer>
</body>
</html>
