<%@tag description="Default Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="elem" tagdir="/WEB-INF/tags/elements" %>
<%@attribute name="homework" type="java.util.HashMap" required="true" %>

<c:forEach items="${homework}" var="day">
    <div class="week">
        <div class="day">
            <c:choose>
                <c:when test="${day.key eq 1}">
                    <h2>Понедельник</h2>
                </c:when>
                <c:when test="${day.key eq 2}">
                    <h2>Вторник</h2>
                </c:when>
                <c:when test="${day.key eq 3}">
                    <h2>Среда</h2>
                </c:when>
                <c:when test="${day.key eq 4}">
                    <h2>Четверг</h2>
                </c:when>
                <c:when test="${day.key eq 5}">
                    <h2>Пятница</h2>
                </c:when>
                <c:when test="${day.key eq 6}">
                    <h2>Суббота</h2>
                </c:when>
            </c:choose>
                <c:if test="${empty day.value}">
                    <div>
                        На этот день нет домашнего задания :)
                    </div>
                </c:if>
                <table class="homework_table">
                    <c:forEach items="${day.value}" var="lesson">
                        <tr>
                            <th>
                                    ${lesson.key.subject}
                            </th>
                            <td>
                                    ${lesson.value.task}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
        </div>
    </div>
</c:forEach>