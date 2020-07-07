<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><spring:message code="specialties.page"/></title>
        <style><%@include file="/WEB-INF/css/student/specialties.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <a href="${pageContext.request.contextPath}/api/student/specialties?locale=ua" title="<spring:message code="choose.ukrainian"/>">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student/specialties?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <a class="back-button" href="${pageContext.request.contextPath}/api/home?locale=${sessionScope.locale}"
            title="<spring:message code="back.message"/>"><span class="symbol">&#11013;</span><spring:message code="back"/></a>
        <h3 class="title"><spring:message code="rating.page"/></h3>
        <div class="table-container">
            <div class="scroll-form">
                <table class="specialty-table">
                    <tbody>
                        <c:forEach items="${specialties}" var="specialty">
                            <tr>
                                <td><a class="spec-ref" href="${pageContext.request.contextPath}/api/student/specialty/${specialty}?locale=${sessionScope.locale}"
                                    title="<spring:message code="specialty.message"/>"><spring:message code="${specialty}"/></a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
