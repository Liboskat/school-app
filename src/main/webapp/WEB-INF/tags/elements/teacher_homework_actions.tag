<%@tag description="Default Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="elem" tagdir="/WEB-INF/tags/elements" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="current_grade" type="java.lang.String" required="true" %>
<%@attribute name="current_subject" type="java.lang.String" required="true" %>
<%@attribute name="get_homework_date" type="java.util.Date" required="true" %>
<%@attribute name="found_homework" type="ru.kpfu.entities.Homework" required="true" %>

<c:choose>
    <c:when test="${empty found_homework}">
        <div>
            <elem:teacher_add_homework_form current_grade="${current_grade}" current_subject="${current_subject}" get_homework_date="${get_homework_date}"/>
        </div>
    </c:when>
    <c:otherwise>
        <div>
                <elem:teacher_update_homework_form current_grade="${current_grade}" current_subject="${current_subject}"
                                                   get_homework_date="${get_homework_date}" found_homework="${found_homework}"/>

                <elem:teacher_delete_homework_form current_grade="${current_grade}" current_subject="${current_subject}"
                                                   get_homework_date="${get_homework_date}" found_homework="${found_homework}"/>
        </div>
    </c:otherwise>
</c:choose>
