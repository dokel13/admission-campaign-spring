<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle var="messages" basename="messages"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><fmt:message key="profile.page" bundle="${messages}"/></title>
        <style><%@include file="/WEB-INF/css/student/home.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <fmt:message var="choose_ukrainian" key="choose.ukrainian" bundle="${messages}"/>
                <a href="${pageContext.request.contextPath}/api/student?locale=ua" title="${choose_ukrainian}">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <h3 class="title"><fmt:message key="your.profile" bundle="${messages}"/></h3>
        <div class="buttons-container">
            <div class="profile">
                <h3><fmt:message key="name" bundle="${messages}"/>: <span>${name}</span></h3>
                <h3><fmt:message key="surname" bundle="${messages}"/>: <span>${surname}</span></h3>
                <c:choose>
                    <c:when test="${enrollment == true}">
                        <fmt:message var="status_value" key="status.student" bundle="${messages}"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message var="status_value" key="status.entrant" bundle="${messages}"/>
                    </c:otherwise>
                </c:choose>
                <h3><fmt:message key="status" bundle="${messages}"/>: <span>${status_value}</span></h3>
            </div>
            <table class="button-group">
                <tr>
                    <fmt:message var="subjects" key="subjects" bundle="${messages}"/>
                    <fmt:message var="subjects_message" key="subjects.message" bundle="${messages}"/>
                    <td><a class="button-1" href="${pageContext.request.contextPath}/api/student/subjects?locale=${sessionScope.locale}"
                        title="${subjects_message}">${subjects}<br><span class="symbol">&#10000;</span></a></td>
                    <fmt:message var="specialties" key="specialties" bundle="${messages}"/>
                    <fmt:message var="specialties_message" key="specialties.message" bundle="${messages}"/>
                    <td><a class="button-1" href="${pageContext.request.contextPath}/api/student/specialties?locale=${sessionScope.locale}" title="${specialties_message}">${specialties}
                        <br><span class="symbol">&#128214;</span></a></td>
                </tr>
                <tr>
                    <fmt:message var="rating" key="rating" bundle="${messages}"/>
                    <fmt:message var="rating_message" key="rating.message" bundle="${messages}"/>
                    <td colspan = "2"><a class="button-2" href="${pageContext.request.contextPath}/api/student/rating?page=1&locale=${sessionScope.locale}"
                        title="${rating_message}">${rating}<br><span class="symbol">&#10532;</span></a></td>
                </tr>
            </table>
        </div>
        <fmt:message var="results_message" key="results.message" bundle="${messages}"/>
        <a class="results" href="${pageContext.request.contextPath}/api/student/results?locale=${sessionScope.locale}" title="${results_message}">
            <fmt:message key="results" bundle="${messages}"/></a>
    </body>
</html>