<%-- 
    Document   : a
    Created on : Jan 19, 2026, 10:51:49 AM
    Author     : THIS PC
--%>

<%@page import="model.UserDTO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${not empty user}">
            <h1>Welcome, ${user.fullName} </h1>
        <a href="MainController?action=logout&">Logout</a><br/>
        <a href="search.jsp">Search</a><br/>
        </c:if>
        
        <c:if test="${empty user}"> 
            <c:redirect url="login.jsp"/>
        </c:if>  
    </body>
</html>
