<%@tag description="Default Layout Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>

<%@attribute name="title" %>
<%@attribute name="navigation" fragment="true" %>

<t:main_layout>
    <jsp:attribute name="navigation">
        <c:choose>
            <c:when test="${category eq 'timetable'}">
                <a class="chosen_navigation_button">
                    <div class="inside_button_text">
                        Расписание
                    </div>
                </a>
                <a class="navigation_button" href=<c:url value="/account?category=marks"/>>
                    <div class="inside_button_text">
                        Оценки
                    </div>
                </a>
                <a class="navigation_button" href=<c:url value="/logout"/>>
                    <div class="inside_button_text">
                        Выйти из аккаунта
                    </div>
                </a>
            </c:when>
            <c:when test="${category eq 'marks'}">
                <a class="navigation_button" href=<c:url value="/account?category=timetable"/>>
                    <div class="inside_button_text">
                        Расписание
                    </div>
                </a>
                <a class="chosen_navigation_button">
                    <div class="inside_button_text">
                        Оценки
                    </div>
                </a>
                <a class="navigation_button" href=<c:url value="/logout"/>>
                    <div class="inside_button_text">
                        Выйти из аккаунта
                    </div>
                </a>
            </c:when>
        </c:choose>
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</t:main_layout>