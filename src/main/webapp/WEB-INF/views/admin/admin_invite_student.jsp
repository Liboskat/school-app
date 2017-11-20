<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>

<t:admin_account>
    <jsp:body>
        <div class="userdata_input">
            <input type="text" placeholder="Имя" name="student_name" required>
        </div>

        <div class="userdata_input">
            <input type="text" placeholder="Фамилия" name="student_surname" required>
        </div>


        <div class="userdata_input">
            <select name="grade" required>
                <option value="" disabled selected>Выберите класс</option>
                <c:forEach items="${grades}" var="grade">
                    <option value="${grade.id}">${grade}</option>
                </c:forEach>
            </select>
        </div>
    </jsp:body>
</t:admin_account>
