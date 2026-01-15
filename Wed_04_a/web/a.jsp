<%-- 
    Document   : a
    Created on : Jan 8, 2026, 11:38:52 PM
    Author     : THIS PC
--%>

<%@page import="model.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            UserDTO u = (UserDTO)request.getAttribute("user");
        %>
        <h1>Welcome, <%=u.getFullName()%> </h1>
        <h2>Bang dieu khien</h2>
        Tinh nang 1 <br/>
        Tinh nang 2 <br/>
        Tinh nang 3 <br/>
    </body>
</html>
