<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>
<%@taglib prefix="elem" tagdir="/WEB-INF/tags/elements" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<t:teacher_account>
    <div class="teacher_marks_container">
    <div class="teacher_marks_header">
            <div id="teacher_choose_grade_form">
                Выберите класс
                <elem:teacher_choose_grade_form current_grade="${current_grade}" grades="${grades}"/>
            </div>
            <c:if test="${not empty current_grade}">
            <div id=teacher_choose_subject_form>
                    Выберите предмет
                    <elem:teacher_choose_subject_form subjects="${subjects}" current_grade="${current_grade}" current_subject="${current_subject}"/>
            </div>
            </c:if>
            <div id="teacher_choose_mark_month_form">
                Выберите месяц
                <elem:teacher_choose_mark_month_form current_grade="${current_grade}" current_subject="${current_subject}" marks_month="${marks_month}"/>
            </div>
    </div>

    <div class="teacher_marks_middle">
        <c:if test="${not empty current_grade and not empty current_subject}">
            <elem:mark_table map="${marks_by_month}" month_days_number="${month_days_number}"/>
        </c:if>
    </div>

    <div class="teacher_marks_footer">
        <c:if test="${not empty current_grade and not empty current_subject}">
        <div class="teacher_marks_footer_left">
            <elem:teacher_set_mark_form students="${students}" dates="${dates}" current_grade="${current_grade}"
                                        current_subject="${current_subject}" get_homework_date="${get_homework_date}"/>
        </div>

        <div class="teacher_marks_footer_right">
            <div>
                <elem:teacher_find_homework_form dates="${dates}" current_grade="${current_grade}"
                                                 current_subject="${current_subject}" get_homework_date="${get_homework_date}" />
            </div>
            <div>
                <elem:teacher_homework_actions current_grade="${current_grade}" current_subject="${current_subject}"
                                               get_homework_date="${get_homework_date}" found_homework="${found_homework}"/>
            </div>
        </div>
        </c:if>
    </div>
    </div>
</t:teacher_account>