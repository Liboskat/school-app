<%@tag description="Default Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="current_month" type="java.lang.Integer" required="true" %>
<%@attribute name="name" type="java.lang.String" required="true" %>

<select name="${name}" title="Выберите месяц">
    <c:choose>
        <c:when test="${current_month eq 9}">
            <option value="9" selected>Сентябрь</option>
        </c:when>
        <c:otherwise>
            <option value="9">Сентябрь</option>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${current_month eq 10}">
            <option value="10" selected>Октябрь</option>
        </c:when>
        <c:otherwise>
            <option value="10">Октябрь</option>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${current_month eq 11}">
            <option value="11" selected>Ноябрь</option>
        </c:when>
        <c:otherwise>
            <option value="11">Ноябрь</option>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${current_month eq 12}">
            <option value="12" selected>Декабрь</option>
        </c:when>
        <c:otherwise>
            <option value="12">Декабрь</option>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${current_month eq 1}">
            <option value="1" selected>Январь</option>
        </c:when>
        <c:otherwise>
            <option value="1">Январь</option>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${current_month eq 2}">
            <option value="2" selected>Февраль</option>
        </c:when>
        <c:otherwise>
            <option value="2">Февраль</option>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${current_month eq 3}">
            <option value="3" selected>Март</option>
        </c:when>
        <c:otherwise>
            <option value="3">Март</option>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${current_month eq 4}">
            <option value="4" selected>Апрель</option>
        </c:when>
        <c:otherwise>
            <option value="4">Апрель</option>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${current_month eq 5}">
            <option value="5" selected>Май</option>
        </c:when>
        <c:otherwise>
            <option value="5">Май</option>
        </c:otherwise>
    </c:choose>
</select>