<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><spring:message code="rating.page"/></title>
        <style><%@include file="/WEB-INF/css/student/rating.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <a href="${pageContext.request.contextPath}/api/student/rating?page=${page}&locale=ua" title="<spring:message code="choose.ukrainian"/>">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student/rating?page=${page}&locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <a class="back-button" href="${pageContext.request.contextPath}/api/home?locale=${sessionScope.locale}"
            title="<spring:message code="back.message"/>"><span class="symbol">&#11013;</span><spring:message code="back"/></a>
        <h3 class="title"><spring:message code="${specialty}"/></h3>
        <div class="table-container">
            <table class="specialty-table">
                <thead>
                    <tr>
                        <th><spring:message code="student.name"/></th>
                        <th><spring:message code="email"/></th>
                        <th><spring:message code="mark.sum"/></th>
                        <th><spring:message code="enrollment"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${apps}" var="app">
                        <tr>
                            <td>${app.user.name} ${app.user.surname}</td>
                            <td>${app.user.email}</td>
                            <td>${app.markSum}</td>
                            <td><spring:message code="${app.enrollment}"/></td>
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
