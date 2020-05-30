<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle var="messages" basename="messages"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><fmt:message key="admin" bundle="${messages}"/></title>
        <style><%@include file="/WEB-INF/css/admin/home.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <fmt:message var="choose_ukrainian" key="choose.ukrainian" bundle="${messages}"/>
                <a href="${pageContext.request.contextPath}/api/admin?locale=ua" title="${choose_ukrainian}">UA</a>/
                <a href="${pageContext.request.contextPath}/api/admin?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <div class="left-corner">
            <fmt:message key="admin" bundle="${messages}"/>: ${name} ${surname}
        </div>
        <div class="main-container">
            <c:choose>
                <c:when test="${requestScope.subjects != null}">
                    <div class="scroll-form">
                        <table class="subject-table">
                            <tbody>
                                <c:forEach items="${subjects}" var="subject">
                                    <tr>
                                        <fmt:message var="subject_locale" key="${subject}" bundle="${messages}"/>
                                        <fmt:message var="subject_admin_message" key="subject.admin.message" bundle="${messages}"/>
                                        <td><a class="sub-ref" href="${pageContext.request.contextPath}/api/admin/subject?${subject}&page=1&locale=${sessionScope.locale}"
                                            title="${subject_admin_message}">${subject_locale}</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <fmt:message var="end_admission" key="end.admission" bundle="${messages}"/>
                    <a class="button" href="${pageContext.request.contextPath}/api/admin/set_admission?open=false&locale=${sessionScope.locale}">
                        ${end_admission}</a>
                </c:when>
                <c:otherwise>
                    <fmt:message var="start_admission" key="start.admission" bundle="${messages}"/>
                    <a class="button" href="${pageContext.request.contextPath}/api/admin/set_admission?open=true&locale=${sessionScope.locale}">
                        ${start_admission}</a>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>