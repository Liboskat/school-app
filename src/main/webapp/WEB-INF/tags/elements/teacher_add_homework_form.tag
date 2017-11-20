<%@tag description="Default Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="elem" tagdir="/WEB-INF/tags/elements" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="current_grade" type="java.lang.String" required="true" %>
<%@attribute name="current_subject" type="java.lang.String" required="true" %>
<%@attribute name="get_homework_date" type="java.util.Date" required="true" %>

<form method="post" action="<c:url value="/account"/>">
    <input type="hidden" name="category" value="marks" />
    <input type="hidden" name="current_grade" value="${current_grade}">
    <input type="hidden" name="current_subject" value="${current_subject}">
    <input type="hidden" name="get_homework_date" value="${get_homework_date.time}">

    <div>
        <textarea name="save_homework_text" title="Введите текст" placeholder="На этот день нет домашнего задания" class="homework_textarea"></textarea>
        <button type="submit" id="add_homework_button">
            Добавить
        </button>
    </div>
</form>