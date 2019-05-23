<%@ page import="clazzes.Pages" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: jan22
  Date: 17.04.2019
  Time: 23:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<img src="https://freischuetz-geltendorf.de/images/hader.jpg" alt="vereinLogo">
<h3 id="menuHeader"><a href="con?dispatchTo=start">Menü:</a></h3>
<ul>
    <%
        for (Pages e :Pages.values()) {
            if (e.getDisplayName() != null) {
                out.println("<li>\n<a href=\"con?dispatchTo=" + e.getDispatchString() + "\">" + e.getDisplayName() + "</a>\n</li>");
            }
        }
    %>
</ul>