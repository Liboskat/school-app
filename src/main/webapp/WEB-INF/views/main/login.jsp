<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>

<t:main_layout>
    <jsp:body>
        <div>
        <form method="post" class="userdata_form">
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
            <div class="userdata_header">
                Войти
            </div>
            <div class="userdata_input">
                <input type="text" name="login" placeholder="Логин" class="userdata_input" required>
            </div>
            <div class="userdata_input">
                <input type="password" name="password" placeholder="Пароль" class="userdata_input" required/>
            </div>

            <div class="userdata_input">
                <label for="remember">Запомнить меня</label>
                <input type="checkbox" id="remember" name="remember" class="userdata_input" value="checked">
            </div>

            <button type="submit" name="login">Вход</button>

            <a href="<c:url value="/registration"/>">Еще не зарегистрированы?</a>
        </form>
        </div>
    </jsp:body>
</t:main_layout>
