<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<head>
    <title><spring:message code="meal.title"/></title>
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>

    <hr>
    <%--<h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>--%>
    <h2><c:choose>
        <c:when test="${param.action == 'create'}">
            <spring:message code="meal.editor.create"/>
            <br/>
        </c:when>
        <c:otherwise>
            <spring:message code="meal.editor.edit"/>
            <br/>
        </c:otherwise>
    </c:choose></h2>

    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <%--<jsp:useBean id="user" type="ru.javawebinar.topjava.model.User" scope="application"/>--%>

    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <%--<input type="hidden" name="user" value="${meal.user}">--%>
        <dl>
            <dt><spring:message code="meal.datetime"/>:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/>:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/>:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="meal.editor.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="meal.editor.cancel"/></button>
    </form>
</section>
</body>
</html>
