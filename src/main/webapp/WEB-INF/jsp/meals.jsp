<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="meal.title"/></h3>
        <%-- <form method="get" action="meals/filter">
             <dl>
                 <dt><spring:message code="meal.startDate"/>:</dt>
                 <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
             </dl>
             <dl>
                 <dt><spring:message code="meal.endDate"/>:</dt>
                 <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
             </dl>
             <dl>
                 <dt><spring:message code="meal.startTime"/>:</dt>
                 <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
             </dl>
             <dl>
                 <dt><spring:message code="meal.endTime"/>:</dt>
                 <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
             </dl>
             <button type="submit"><spring:message code="meal.filter"/></button>
         </form>--%>
<%--        <div class="card ">--%>
            <form id="filter">
                <div class="form-group col-3 ">
                    <label for="startDate"><spring:message code="meal.startDate"/></label>
                    <input type="date" class="form-control" name="startDate" id="startDate">

                </div>
                <div class="form-group col-3">
                    <label for="endDate"><spring:message code="meal.endDate"/></label>
                    <input type="date" class="form-control" name="endDate" id="endDate">
                </div>
                <div class="form-group col-2">
                    <label for="startTime"><spring:message code="meal.startTime"/></label>
                    <input type="time" class="form-control" name="startTime" id="startTime">
                </div>
                <div class="form-group col-2">
                    <label for="endTime"><spring:message code="meal.endTime"/></label>
                    <input type="time" class="form-control" name="endTime" id="endTime">
                </div>

            </form>
        <button class="btn btn-primary  " onclick="filterTable()"><spring:message
                code="meal.filter"/></button>
        <button class="btn btn-secondary" onclick="cancelFilter()"><spring:message code="common.cancel"/></button>

<%--        </div>--%>
        <%--   <div class="card border-dark">
               <div class="card-body pb-0">
                   <form id="filter2">
                       <div class="row">
                           <div class="offset-1 col-2">
                               <label for="startDate">От даты</label>
                               <input class="form-control" name="startDate" id="startDate2">
                           </div>
                           <div class="col-2">
                               <label for="endDate">До даты</label>
                               <input class="form-control" name="endDate" id="endDate2">
                           </div>

                           <div class="offset-2 col-2">
                               <label for="startTime">От времени</label>
                               <input class="form-control" name="startTime" id="startTime2">
                           </div>
                           <div class="col-2">
                               <label for="endTime">До времени</label>
                               <input class="form-control" name="endTime" id="endTime2">
                           </div>
                       </div>
                   </form>
               </div>
               <div class="card-footer text-center">
                   &lt;%&ndash;                <button class="btn btn-danger" onclick="clearFilter()">&ndash;%&gt;
                   &lt;%&ndash;                    <span class="fa fa-remove"></span>&ndash;%&gt;
                   &lt;%&ndash;                    Отменить&ndash;%&gt;
                   &lt;%&ndash;                </button>&ndash;%&gt;
                   <button class="btn btn-primary" onclick="filterTable()">
                       <span class="fa fa-filter"></span>
                       Отфильтровать
                   </button>
               </div>
           </div>--%>

        <hr>
        <%--        <a href="meals/create"><spring:message code="meal.add"/></a>--%>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="meal.add"/>
        </button>
        <hr>
        <table class="table table-striped " id="datatable">
            <thead>
            <tr>

                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>

            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
                <tr data-mealExcess="${meal.excess}" id="${meal.id}">
                    <td>
                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                            <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                            <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                            ${fn:formatDateTime(meal.dateTime)}
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                        <%--                    <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>--%>
                        <%--                    <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>--%>
                    <td><a class="update" href="meals/update?id=${meal.id}"><span class="fa fa-pencil"></span></a></td>
                    <td><a class="delete"><span class="fa fa-remove"></span></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">

                    <div class="form-group">
                        <input type="hidden" class="form-control" id="id" name="id" value="null">
                    </div>

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                               placeholder="<spring:message code="meal.dateTime"/>">
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="meal.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="meal.description"/>">
                    </div>

                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="text" class="form-control" id="calories" name="calories" value="1000"
                               placeholder="<spring:message code="meal.calories"/>">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="saveWithFilter()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>