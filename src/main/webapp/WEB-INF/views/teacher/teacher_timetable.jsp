<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>

<t:teacher_account>
    <jsp:body>
        <table id="mark_table">
            <tr>
                <th></th>
                <th>Понедельник</th>
                <th>Вторник</th>
                <th>Среда</th>
                <th>Четверг</th>
                <th>Пятница</th>
                <th>Суббота</th>
            </tr>
            <c:forEach items="${lessons}" var="row">
                <tr>
                    <td>
                            ${row.key}
                    </td>
                    <c:forEach items="${row.value}" var="lesson">
                        <td title="${lesson.value}">
                            <c:if test="${not empty lesson.value}">
                                ${lesson.value.grade}(${lesson.value.subject.name})
                            </c:if>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </jsp:body>
</t:teacher_account>