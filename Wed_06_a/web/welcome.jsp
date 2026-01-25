<%-- 
    Document   : a
    Created on : Jan 19, 2026, 10:51:49 AM
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
            UserDTO u = (UserDTO) session.getAttribute("user");
            if (u != null){
        %>
        <h1>Welcome, <%=u.getFullName()%> </h1>
        <a href="MainController?action=logout&">Logout</a><br/>
        <a href="search.jsp">Search</a><br/>
        <%} else {
                response.sendRedirect("login.jsp");
            }
        %>    
    </body>
</html>
