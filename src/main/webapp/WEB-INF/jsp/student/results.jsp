<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle var="messages" basename="messages"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><fmt:message key="results" bundle="${messages}"/></title>
        <style><%@include file="/WEB-INF/css/student/results.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
        <script><%@include file="/WEB-INF/js/popup.js" %></script>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <fmt:message var="choose_ukrainian" key="choose.ukrainian" bundle="${messages}"/>
                <a href="${pageContext.request.contextPath}/api/student/results?locale=ua" title="${choose_ukrainian}">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student/results?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <fmt:message var="back_button" key="back" bundle="${messages}"/>
        <fmt:message var="back_message" key="back.message" bundle="${messages}"/>
        <a class="back-button" href="${pageContext.request.contextPath}/api/home?locale=${sessionScope.locale}"
                                            title="${back_message}"><span class="symbol">&#11013;</span>${back_button}</a>
        <h3 class="title"><fmt:message key="results" bundle="${messages}"/></h3>
        <div class="table-container">
            <div>
                <table class="results-table-1">
                    <thead>
                        <tr>
                            <th><fmt:message key="subject" bundle="${messages}"/></th>
                            <th class="th-2"><fmt:message key="mark" bundle="${messages}"/></th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div class="scroll-form">
                <table class="results-table-2">
                    <tbody>
                        <c:forEach items="${exams}" var="exam">
                            <tr>
                                <td><fmt:message key="${exam.subject}" bundle="${messages}"/></td>
                                <td>${exam.mark}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>