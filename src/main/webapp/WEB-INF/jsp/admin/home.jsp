<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><spring:message code="admin"/></title>
        <style><%@include file="/WEB-INF/css/admin/home.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <a href="${pageContext.request.contextPath}/api/admin?locale=ua" title="<spring:message code="choose.ukrainian"/>">UA</a>/
                <a href="${pageContext.request.contextPath}/api/admin?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <div class="left-corner">
            <spring:message code="admin"/>: ${name} ${surname}
        </div>
        <div class="main-container">
            <c:choose>
                <c:when test="${requestScope.subjects != null}">
                    <div class="scroll-form">
                        <table class="subject-table">
                            <tbody>
                                <c:forEach items="${subjects}" var="subject">
                                    <tr>
                                        <td><a class="sub-ref" href="${pageContext.request.contextPath}/api/admin/subject/${subject}?page=1&locale=${sessionScope.locale}"
                                            title="<spring:message code="subject.admin.message"/>"><spring:message code="${subject}"/></a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <a class="button" href="${pageContext.request.contextPath}/api/admin/set_admission?open=false&locale=${sessionScope.locale}">
                        <spring:message code="end.admission"/></a>
                </c:when>
                <c:otherwise>
                    <a class="button" href="${pageContext.request.contextPath}/api/admin/set_admission?open=true&locale=${sessionScope.locale}">
                        <spring:message code="start.admission"/></a>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
