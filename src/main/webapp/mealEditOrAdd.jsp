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
    <p>ID:       <input type="text" name="id" value="${meal.id}" ></p>
    <p>Дата:     <input type="datetime-local" name="date" value="${meal.dateTime}" ></p>
    <p>Описание: <input type="text" name="description" value="${meal.description}" ></p>
    <p>Каллории: <input type="text" name="calories" value="${meal.calories}" ></p>
    <p><input type="submit" value="Отправить"></p>
</form>

</body>
</html>
