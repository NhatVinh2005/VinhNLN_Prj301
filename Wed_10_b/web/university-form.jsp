<%-- 
    Document   : university-form
    Created on : Feb 2, 2026, 10:00:16 AM
    Author     : THIS PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="MainController" method="POST">
            <%--Giu lai action de Controller biet can lam gi--%>
            <input type="hidden" name="action" value="${mode=='update'?'saveUpdateUniversity':'addUniversity'}"/>
            
            ID: <input type="text" name="id" value="${u.id}" ${mode == 'update' ? 'readonly' : ''}/> </br>
            
            Name: <input type="text" name="name" value="${u.name}"/> </br>
            shortName: <input type="text" name="shortName" value="${u.shortName}"/> </br>
            description: <input type="text" name="description" value="${u.description}"/> </br>
            foundedYear: <input type="text" name="foundedYear" value="${u.foundedYear}"/> </br>
            address: <input type="text" name="address" value="${u.address}"/> </br>
            city: <input type="text" name="city" value="${u.city}"/> </br>
            region: <input type="text" name="region" value="${u.region}"/> </br>
            type: <input type="text" name="type" value="${u.type}"></br>
            totalStudents: <input type="number" step="1" min="0" name="totalStudents" value="${u.totalStudents}"/> </br>
            totalFaculties: <input type="number" step="1" min="0" name="totalFaculties" value="${u.totalFaculties}"/> </br>
            
            <%-- Xu ly checkbox isDraft --%>
            isDraft: <input type="checkbox" ${u.isDraft ? 'checked' : ''} name="isDraft"/> </br>
            
            <c:choose>
                <c:when test="${mode == 'update'}">
                    <input type="submit" value="Update"/>
                </c:when>
                <c:otherwise>
                    <input type="submit" value="Add"/>
                </c:otherwise>
            </c:choose>
        </form>
        <p style="color: green">${msg}</p>
        <p style="color: red">${error}</p>
    </body>
</html>
