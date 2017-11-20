<%@ taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:main_layout>
    <jsp:attribute name="navigation">
        <a class="navigation_button" href="<c:url value="/login"/>">
            <div class="inside_button_text">
                Перейти на начальную страницу
            </div>
        </a>
    </jsp:attribute>
    <jsp:body>
        <div id="error_page_text">
            Возникла непредвиденная ошибка
        </div>
    </jsp:body>
</t:main_layout>