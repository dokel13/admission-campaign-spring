<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle var="messages" basename="messages"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <style><%@include file="/WEB-INF/css/error.css"%></style>
    <style><%@include file="/WEB-INF/css/back_button.css" %></style>
    <title><fmt:message key="error" bundle="${messages}"/></title>
</head>
<body>
    <fmt:message var="back_button" key="back" bundle="${messages}"/>
    <fmt:message var="back_message" key="back.message" bundle="${messages}"/>
    <a class="back-button" href="${pageContext.request.contextPath}/api/home?locale=${sessionScope.locale}"
        title="${back_message}"><span class="symbol">&#11013;</span>${back_button}</a>
    <h1><fmt:message key="error.message" bundle="${messages}"/></h1>
</body>
</html>
