<%@tag description="Default Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="elem" tagdir="/WEB-INF/tags/elements" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="dates" type="java.util.TreeSet" required="true" %>
<%@attribute name="current_grade" type="java.lang.String" required="true" %>
<%@attribute name="current_subject" type="java.lang.String" required="true" %>
<%@attribute name="get_homework_date" type="java.util.Date" required="true" %>

<form method="get" action="<c:url value="/account"/>">

    <input type="hidden" name="category" value="marks" />
    <input type="hidden" name="current_grade" value="${current_grade}">
    <input type="hidden" name="current_subject" value="${current_subject}">

    <div id = "findHomeworkButtons">
        <select name="get_homework_date" title="Выберите число" >
            <c:if test="${empty get_homework_date.time}">
                <option selected disabled>Выберите число</option>
            </c:if>
            <c:forEach items="${dates}" var="uri">
                <c:choose>
                    <c:when test="${get_homework_date.time eq uri.time}">
                        <option value="${uri.time}" selected><fmt:formatDate value="${uri}" pattern="dd"/></option>
                    </c:when>
                    <c:otherwise>
                        <option value="${uri.time}"><fmt:formatDate value="${uri}" pattern="dd"/></option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <button type="submit">Найти</button>
    </div>
</form>