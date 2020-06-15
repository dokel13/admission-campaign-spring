<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle var="messages" basename="messages"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><fmt:message key="${specialty.name}" bundle="${messages}"/></title>
        <style><%@include file="/WEB-INF/css/student/specialty.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
        <script><%@include file="/WEB-INF/js/popup.js" %></script>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <fmt:message var="choose_ukrainian" key="choose.ukrainian" bundle="${messages}"/>
                <a href="${pageContext.request.contextPath}/api/student/specialty/${specialty.name}?locale=ua" title="${choose_ukrainian}">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student/specialty/${specialty.name}?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <fmt:message var="back_button" key="back" bundle="${messages}"/>
        <fmt:message var="back_message" key="back.specialty.message" bundle="${messages}"/>
        <a class="back-button" href="${pageContext.request.contextPath}/api/student/specialties?locale=${sessionScope.locale}"
            title="${back_message}"><span class="symbol">&#11013;</span>${back_button}</a>
        <h3 class="title"><fmt:message key="${specialty.name}" bundle="${messages}"/></h3>
        <div class="table-container">
            <div class="table-form">
                <fmt:message key="admission.places.amount" bundle="${messages}"/>: ${specialty.maxStudentAmount}<br>
                <hr>
                <fmt:message key="requirements" bundle="${messages}"/>:<br>
                <div class="reqs">
                    <c:forEach items="${specialty.requirements}" var="requirement">
                        <fmt:message key="${requirement.subject}" bundle="${messages}"/>: ${requirement.mark}<br>
                    </c:forEach>
                </div>
            </div>
            <div class="form-button" onclick="popup_function()">
                <c:if test="${sessionScope.exception.message == 'Admission is closed!'}">
                    <span class="popup_text" id="my_popup">
                        <fmt:message key="admission.closed.exception" bundle="${messages}"/>
                        <c:remove var="exception" scope="session" />
                    </span>
                </c:if>
                <c:if test="${sessionScope.exception.message == 'User already has application!'}">
                    <span class="popup_text" id="my_popup">
                        <fmt:message key="application.exists.exception" bundle="${messages}"/>
                        <c:remove var="exception" scope="session" />
                    </span>
                </c:if>
                <c:if test="${sessionScope.exception.message == 'Insufficient marks for specialty admission!'}">
                    <span class="popup_text" id="my_popup">
                        <fmt:message key="insufficient.marks.exception" bundle="${messages}"/>
                        <c:remove var="exception" scope="session" />
                    </span>
                </c:if>
                <a class="btnSubmit" href="${pageContext.request.contextPath}/api/student/specialty/apply/${specialty.name}?locale=${sessionScope.locale}">
                    <fmt:message key="apply" bundle="${messages}"/>
                </a>
            </div>
        </div>
    </body>
</html>