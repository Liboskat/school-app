<%@tag description="Default Layout Tag" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@attribute name="title" %>
<%@attribute name="navigation" fragment="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <c:if test="${not empty title}" >${title}</c:if>
    </title>
    <script type="text/javascript" src="<c:url value="/js/table_sorter.js"/>"></script>
    <link href="<c:url value="/css/main_style.css" />" rel="stylesheet">
    <link href="<c:url value="/css/table.css" />" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row header">
    </div>

    <div class="row navigation">
        <jsp:invoke fragment="navigation"/>
    </div>

    <div class="row left_sidebar">
    </div>

    <div class="row content">
        <jsp:doBody/>
    </div>

    <div class="row right_sidebar">
    </div>

    <div class="row footer">
        <div id="left_footer_text">СОШ №22 г.Ульяновск</div>
        <div id="right_footer_text">mail@ulsosh22.ru</div>
    </div>
</div>
</body>
</html>