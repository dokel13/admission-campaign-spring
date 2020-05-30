<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle var="messages" basename="messages"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><fmt:message key="rating.page" bundle="${messages}"/></title>
        <style><%@include file="/WEB-INF/css/student/rating.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <fmt:message var="choose_ukrainian" key="choose.ukrainian" bundle="${messages}"/>
                <a href="${pageContext.request.contextPath}/api/student/rating?page=${page}&locale=ua" title="${choose_ukrainian}">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student/rating?page=${page}&locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <fmt:message var="back_button" key="back" bundle="${messages}"/>
        <fmt:message var="back_message" key="back.message" bundle="${messages}"/>
        <a class="back-button" href="${pageContext.request.contextPath}/api/home?locale=${sessionScope.locale}"
                                            title="${back_message}"><span class="symbol">&#11013;</span>${back_button}</a>
        <h3 class="title"><fmt:message key="${specialty}" bundle="${messages}"/></h3>
        <div class="table-container">
            <table class="specialty-table">
                <thead>
                    <tr>
                        <th><fmt:message key="student.name" bundle="${messages}"/></th>
                        <th><fmt:message key="email" bundle="${messages}"/></th>
                        <th><fmt:message key="mark.sum" bundle="${messages}"/></th>
                        <th><fmt:message key="enrollment" bundle="${messages}"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${apps}" var="app">
                        <tr>
                            <td>${app.user.name} ${app.user.surname}</td>
                            <td>${app.user.email}</td>
                            <td>${app.markSum}</td>
                            <td><fmt:message key="${app.enrollment}" bundle="${messages}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="button-container">
            <a class="page-button" href="${pageContext.request.contextPath}/api/student/rating?page=${page - 1}&locale=${sessionScope.locale}">
                <span class="symbol">←</span></a>
            <a class="page-button" href="${pageContext.request.contextPath}/api/student/rating?page=${page + 1}&locale=${sessionScope.locale}">
                <span class="symbol">→</span></a>
        </div>
    </body>
</html>