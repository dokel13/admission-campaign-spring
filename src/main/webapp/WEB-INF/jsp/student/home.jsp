<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><spring:message code="profile.page"/></title>
        <style><%@include file="/WEB-INF/css/student/home.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <a href="${pageContext.request.contextPath}/api/student?locale=ua" title="<spring:message code="choose.ukrainian"/>">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <h3 class="title"><spring:message code="your.profile"/></h3>
        <div class="buttons-container">
            <div class="profile">
                <h3><spring:message code="name"/>: <span>${name}</span></h3>
                <h3><spring:message code="surname"/>: <span>${surname}</span></h3>
                <c:choose>
                    <c:when test="${enrollment == true}">
                        <h3><spring:message code="status"/>: <span><spring:message code="status.student"/></span></h3>
                    </c:when>
                    <c:otherwise>
                        <h3><spring:message code="status"/>: <span><spring:message code="status.entrant"/></span></h3>
                    </c:otherwise>
                </c:choose>
            </div>
            <table class="button-group">
                <tr>
                    <td><a class="button-1" href="${pageContext.request.contextPath}/api/student/subjects?locale=${sessionScope.locale}"
                        title="<spring:message code="subject.message"/>"><spring:message code="subjects"/><br><span class="symbol">&#10000;</span></a></td>
                    <td><a class="button-1" href="${pageContext.request.contextPath}/api/student/specialties?locale=${sessionScope.locale}"
                           title="<spring:message code="specialties.message"/>"><spring:message code="specialties"/><br>
                        <span class="symbol">&#128214;</span></a></td>
                </tr>
                <tr>
                    <td colspan = "2"><a class="button-2" href="${pageContext.request.contextPath}/api/student/rating?page=1&locale=${sessionScope.locale}"
                        title="<spring:message code="rating.message"/>"><spring:message code="rating"/><br><span class="symbol">&#10532;</span></a></td>
                </tr>
            </table>
        </div>
        <a class="results" href="${pageContext.request.contextPath}/api/student/results?locale=${sessionScope.locale}"
           title="<spring:message code="results.message"/>">
            <spring:message code="results"/></a>
    </body>
</html>
