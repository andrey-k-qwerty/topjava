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
<form method="POST" action='meals' name="frmAddMeal">

    <p><input type="time" name="date"></p>
    <p><input type="text" name="description" value=""></p>
    <p><input type="text" name="calories" value=""></p>
    <p><input type="submit" value="Отправить"></p>
</form>

</body>
</html>
