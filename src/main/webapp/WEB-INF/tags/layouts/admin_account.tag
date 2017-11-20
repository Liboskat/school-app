<%@tag description="Default Layout Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>

<%@attribute name="title" %>
<%@attribute name="navigation" fragment="true" %>

<t:main_layout>
    <jsp:attribute name="navigation">
        <c:choose>
            <c:when test="${category eq 'invite_student'}">
                <a class="chosen_navigation_button">
                    <div class="inside_button_text">
                        Добавить студента
                    </div>
                </a>
                <a class="navigation_button" href=<c:url value="/account?category=invite_teacher"/>>
                    <div class="inside_button_text">
                        Добавить учителя
                    </div>
                </a>
                <a class="leave_navigation_button" href=<c:url value="/logout"/>>
                    <div class="inside_button_text">
                        Выйти из аккаунта
                    </div>
                </a>
            </c:when>
            <c:when test="${category eq 'invite_teacher'}">
                <a class="navigation_button" href=<c:url value="/account?category=invite_student"/>>
                    <div class="inside_button_text">
                        Добавить студента
                    </div>
                </a>
                <a class="chosen_navigation_button">
                    <div class="inside_button_text">
                        Добавить учителя
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
        <div>
        <form method="post" class="userdata_form">
            <div class="userdata_header">
                Сгенерировать пригласительный код
            </div>
            <c:if test="${not empty error}">
                <div class="error">
                    <div id="error_head">
                        Ошибка
                    </div>
                    <div id="error_content">
                            ${error}
                    </div>
                </div>
            </c:if>
            <jsp:doBody/>
            <button type="submit" name="submit">Generate</button>

            <c:if test="${not empty invite_code}">
                <div class="invite_code">
                    <div id="invite_code_head">
                        Пригласительный код
                    </div>
                    <div id="invite_code_content">
                            ${invite_code}
                    </div>
                </div>
            </c:if>
        </form>
        </div>
    </jsp:body>
</t:main_layout>