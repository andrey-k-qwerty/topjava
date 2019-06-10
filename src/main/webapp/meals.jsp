<%--@elvariable id="name" type=""--%>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 09.06.2019
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table border=1>
    <thead>
    <tr>
        <th>ID</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>

    <jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>

    <c:forEach items="${meals}" var="meal">
        <%--<c:forEach items="${pageContext.request.getAttribute(\"meals\")}" var="meal">--%>
        <tr style=${meal.excess ? "color:red" : "color:green"}>
                <%--<td><c:out value="${meal.dateTime}" /></td>--%>
            <td><c:out value="${meal.id}"/></td>
            <td>
                <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm " value="${ parsedDateTime }"/>
            </td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
                <%--<td><c:out value="${user.lastName}" /></td>--%>
                <%--<td><fmt:formatDate pattern="yyyy-MMM-dd" value="${user.dob}" /></td>--%>
                <%--<td><c:out value="${user.email}" /></td>--%>
                <%--<td><a href="UserController?action=edit&userId=<c:out value="${user.userid}"/>">Update</a></td>--%>
                <%--<td><a href="UserController?action=delete&userId=<c:out value="${user.userid}"/>">Delete</a></td>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<h3><a href="meals?action=add">Добавить</a></h3>
</body>
</html>
