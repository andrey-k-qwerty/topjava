<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form action="users" method="post">
    <select name="users">
        <option value="1">Admin</option>
        <option value="2">User</option>
    </select>

    <button type="submit">Set</button>
</form>
</body>
</html>