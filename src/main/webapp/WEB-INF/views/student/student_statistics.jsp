<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>

<t:student_account>
    <%--<form action="<c:url value="/account"/>" method="get">--%>
        <%--<div>--%>
            <%--<input type="hidden" name="category" value="statistics" />--%>
            <%--<div>--%>
                <%--<select title="quarter" name="quarter">--%>
                    <%--<option value="1" <c:if test="${quarter eq 1}">selected</c:if>>1 четверть</option>--%>
                    <%--<option value="2" <c:if test="${quarter eq 2}">selected</c:if>>2 четверть</option>--%>
                    <%--<option value="3" <c:if test="${quarter eq 3}">selected</c:if>>3 четверть</option>--%>
                    <%--<option value="4" <c:if test="${quarter eq 4}">selected</c:if>>4 четверть</option>--%>
                    <%--<option value="5" <c:if test="${quarter eq 5}">selected</c:if>>год</option>--%>
                <%--</select>--%>
            <%--</div>--%>
            <%--<div>--%>
                <%--<button type="submit">Выбрать</button>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</form>--%>
    <div id="statistics_container">
        <table id="mark_table">
            <tr><th></th>
            <c:forEach begin="1" end="5" step="1" varStatus="loop">
                <th>
                    <c:choose>
                        <c:when test="${loop.index eq 1}">1 четверть</c:when>
                        <c:when test="${loop.index eq 2}">2 четверть</c:when>
                        <c:when test="${loop.index eq 3}">3 четверть</c:when>
                        <c:when test="${loop.index eq 4}">4 четверть</c:when>
                        <c:when test="${loop.index eq 5}">Годовая</c:when>
                    </c:choose>
                </th>
            </c:forEach>
            </tr>
            <c:forEach items="${averages}" var="subject">
                <tr>
                    <td>${subject.key}</td>
                    <c:forEach items="${subject.value}" var="quarter">
                        <td>
                            ${quarter.value}
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
</t:student_account>
