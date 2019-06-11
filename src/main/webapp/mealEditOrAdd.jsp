<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 10.06.2019
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Редактор</title>
</head>
<body>
 <H3>Редактор</H3>
<form method="POST" action='meals' name="frmAddMeal" accept-charset="UTF-8">
    <p><label for="id">ID: </label>      <input type="text"  readonly="readonly" id="id" name="id" value="${meal.id}" ></p>
    <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
    <fmt:formatDate var="myDate" pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
    <p>Дата:     <input type="datetime-local"   id="date" name="date" value="${myDate}" ></p>
    <p>Описание: <input type="text" name="description" value="${meal.description}" ></p>
    <p>Каллории: <input type="number" min="0" max="10000" name="calories" value="${meal.calories}" ></p>
    <p><input type="submit" value="Отправить"></p>
</form>

</body>
</html>
