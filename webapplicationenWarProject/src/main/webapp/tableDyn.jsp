<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    int columns = Integer.parseInt(request.getParameter("row"));
    int rows = Integer.parseInt(request.getParameter("col"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Dynamic Table JSP</title>
</head>
<body>
<h1>Table of size:<%out.print(rows + "x" + columns);%></h1>
<hr>
<table border="2px" width="100%">
    <%
        for (int i = 1; i <= rows; i++) {
            String rowColor = num2Hex(16 - i % 16);
    %>
    <tr><%
        for (int j = 1; j <= columns; j++) {
            String colColor = num2Hex(16 - j % 16);
            String color = "#" + rowColor + rowColor + colColor + colColor + rowColor + colColor;
    %>
        <td bgcolor=<%
                        out.print(color + " style='color:" + contrastColor(color) + ";'> R: " + i + ", C: " + j);
                %></td><%
            }%>
    </tr>
    <%
        }
    %>
</table>
</body>

</html>

<%! private String num2Hex(int num) {
    return Integer.toHexString(num);
}%>

<%! private int hex2Num(String hex) {
    return (int) Long.parseLong(hex, 16);
}%>

<%! private String contrastColor(String color) {
    // Counting the perceptive luminance - human eye favors green color...
    double a = 1 - (0.299 * hex2Num(color.substring(1, 2)) + 0.587 * hex2Num(color.substring(3, 4)) + 0.114 * hex2Num(color.substring(5))) / 255;
    return a < 0.5 ? "#FFF" : "#000";
}%>