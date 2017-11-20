<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>
<%@taglib prefix="elem" tagdir="/WEB-INF/tags/elements" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<t:student_account>
    <div id="select_student_marks_month">
        <form action="<c:url value="/account"/>" method="get">
            <input type="hidden" name="category" value="marks" />

            <elem:month_list_select name="marks_month" current_month="${marks_month}"/>
            <button type="submit">Выбрать</button>
        </form>
    </div>
    <div>
        <elem:mark_table map="${marks_by_month}" month_days_number="${month_days_number}"/>
    </div>
</t:student_account>