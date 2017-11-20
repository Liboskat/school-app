<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>

<t:main_layout title="Registration form">
    <jsp:body>
        <div>
        <form method="post" class="userdata_form">
            <div class="userdata_header">
                Регистрация
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
            <div class="userdata_input">
                <input type="text" placeholder="Пригласительный код" name="invite" required>
            </div>

            <div class="userdata_input">
                <input type="text" name="login" placeholder="Логин" required>
            </div>
            <div class="userdata_input">
                <input type="password" name="password" placeholder="Пароль" required/>
            </div>

            <div class="userdata_input">
                <input type="password" placeholder="Повторите пароль" name="repeat" required/>
            </div>

            <button type="submit" name="submit">Зарегистрироваться</button>
        </form>
        </div>
    </jsp:body>
</t:main_layout>
