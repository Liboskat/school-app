<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/layouts" %>

<t:admin_account>
    <jsp:body>
        <div class="userdata_input">
            <input type="text" placeholder="Имя" name="teacher_name" required>
        </div>
    </jsp:body>
</t:admin_account>
