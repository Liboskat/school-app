<%@tag description="Default Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="elem" tagdir="/WEB-INF/tags/elements" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="students" type="java.util.List" required="true" %>
<%@attribute name="dates" type="java.util.TreeSet" required="true" %>
<%@attribute name="current_grade" type="java.lang.String" required="true" %>
<%@attribute name="current_subject" type="java.lang.String" required="true" %>
<%@attribute name="get_homework_date" type="java.util.Date" required="true" %>

<form method="post" action="<c:url value="/account"/>">
    <input type="hidden" name="category" value="marks" />
    <input type="hidden" name="current_grade" value="${current_grade}">
    <input type="hidden" name="current_subject" value="${current_subject}">
    <input type="hidden" name="get_homework_date" value="${get_homework_date.time}">
    <div>
        <select name="set_mark_student" title="Выберите студента">
            <option selected disabled>Выберите студента</option>
            <c:forEach items="${students}" var="student">
                <option value="${student.id}">${student}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <select name="set_mark_date" title="Выберите число">
            <option selected disabled>Выберите число</option>
            <c:forEach items="${dates}" var="uri">
                <option value="${uri.time}"><fmt:formatDate value="${uri}" pattern="dd"/></option>
            </c:forEach>
        </select>
    </div>
    <div>
        <select name="set_mark_value" title="Выберите оценку">
            <option selected disabled>Выберите оценку</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select>
    </div>
    <div>
        <button type="submit">Поставить</button>
    </div>
</form>