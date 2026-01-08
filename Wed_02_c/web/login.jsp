<%-- 
    Document   : login
    Created on : Jan 8, 2026, 11:22:25 AM
    Author     : THIS PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="MainController" method="post">
            Username: <input type="text" name="txtUsername"/> <br/>
            Password; <input type ="password" name="txtPassword"/> <br/>
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>
