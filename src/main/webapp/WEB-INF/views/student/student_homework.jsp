<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="elem" tagdir="/WEB-INF/tags/elements" %>

<t:student_account>
    <fmt:parseNumber var = "week_number" type = "number" value = "${homework_week}" integerOnly="true"/>
    <div class="homework_weeks_wrap">
    <div class="homework_weeks">
        <a href="<c:url value="/account?category=homework&homework_week=${week_number - 1}"/> ">Предыдущая неделя</a>
        <a>Неделя ${homework_week}: <fmt:formatDate value="${start_of_week}" pattern="dd.MM"/> -
            <fmt:formatDate value="${end_of_week}" pattern="dd.MM"/></a>
        <a href="<c:url value="/account?category=homework&homework_week=${week_number + 1}"/> ">Следующая неделя</a>
    </div>
    </div>

    <elem:student_homework homework="${homework}"/>
</t:student_account>
