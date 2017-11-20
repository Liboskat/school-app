<%@tag description="Default Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="elem" tagdir="/WEB-INF/tags/elements" %>

<%@attribute name="current_grade" type="java.lang.String" required="true" %>
<%@attribute name="current_subject" type="java.lang.String" required="true" %>
<%@attribute name="marks_month" type="java.lang.Integer" required="true" %>

<form action="<c:url value="/account"/>" method="get">
    <div>
        <input type="hidden" name="category" value="marks" />
        <input type="hidden" name="current_grade" value="${current_grade}">
        <input type="hidden" name="current_subject" value="${current_subject}">

        <elem:month_list_select name="marks_month" current_month="${marks_month}"/>
        <button type="submit">Выбрать</button>
    </div>
</form>