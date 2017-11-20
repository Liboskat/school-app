<%@tag description="Default Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="month_days_number" type="java.lang.Integer" required="true" %>
<%@attribute name="map" type="java.util.HashMap" required="true" %>

<table id="mark_table">
    <tr>
        <th></th>
        <c:forEach begin="1" end="${month_days_number}" varStatus="loop">
            <th>${loop.index}</th>
        </c:forEach>
    </tr>
    <c:forEach items="${map}" var="entry">
        <tr>
            <td>${entry.key}</td>
            <c:forEach begin="0" end="${month_days_number - 1}" varStatus="loop">
                <td>
                    <c:if test="${not empty entry.value[loop.index]}">
                        ${entry.value[loop.index]}
                    </c:if>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>

<script type="text/javascript">
    sortTable();
</script>