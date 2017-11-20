<%@tag description="Default Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="elem" tagdir="/WEB-INF/tags/elements" %>

<%@attribute name="subjects" type="java.util.List" required="true" %>
<%@attribute name="current_grade" type="java.lang.String" required="true" %>
<%@attribute name="current_subject" type="java.lang.String" required="true" %>

<form action="<c:url value="/account"/>" method="get">
    <div>
        <input type="hidden" name="category" value="marks" />
        <input type="hidden" name="current_grade" value="${current_grade}">
        <select name="current_subject" title="Выберите предмет">
            <c:if test="${empty current_subject}">
                <option selected disabled>Выберите предмет</option>
            </c:if>
            <c:forEach items="${subjects}" var="subject">
                <c:choose>
                    <c:when test="${subject.id eq current_subject}">
                        <option value="${subject.id}" selected>${subject.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${subject.id}">${subject.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <button type="submit">Выбрать</button>
    </div>
</form>
