<%@tag description="Default Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="elem" tagdir="/WEB-INF/tags/elements" %>

<%@attribute name="grades" type="java.util.List" required="true" %>
<%@attribute name="current_grade" type="java.lang.String" required="true" %>

<form action="<c:url value="/account"/>" method="get">
    <input type="hidden" name="category" value="marks" />
        <select name="current_grade" title="Выберите класс">
            <c:if test="${empty current_grade}"><option selected disabled>Выберите класс</option></c:if>
            <c:forEach items="${grades}" var="grade">
                <c:choose>
                    <c:when test="${grade.id eq current_grade}">
                        <option value="${grade.id}" selected>${grade}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${grade.id}">${grade}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <button type="submit">Выбрать</button>

</form>