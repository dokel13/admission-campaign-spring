<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><spring:message code="${specialty.name}"/></title>
        <style><%@include file="/WEB-INF/css/student/specialty.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
        <script><%@include file="/WEB-INF/js/popup.js" %></script>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <a href="${pageContext.request.contextPath}/api/student/specialty/${specialty.name}?locale=ua" title="<spring:message code="choose.ukrainian"/>">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student/specialty/${specialty.name}?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <a class="back-button" href="${pageContext.request.contextPath}/api/student/specialties?locale=${sessionScope.locale}"
            title="<spring:message code="back.message"/>"><span class="symbol">&#11013;</span><spring:message code="back"/></a>
        <h3 class="title"><spring:message code="${specialty.name}"/></h3>
        <div class="table-container">
            <div class="table-form">
                <spring:message code="admission.places.amount"/>: ${specialty.maxStudentAmount}<br>
                <hr>
                <spring:message code="requirements"/>:<br>
                <div class="reqs">
                    <c:forEach items="${specialty.requirements}" var="requirement">
                        <spring:message code="${requirement.subject}"/>: ${requirement.mark}<br>
                    </c:forEach>
                </div>
            </div>
            <div class="form-button" onclick="popup_function()">
                <c:if test="${sessionScope.exception.message == 'Admission is closed!'}">
                    <span class="popup_text" id="my_popup">
                        <spring:message code="admission.closed.exception"/>
                        <c:remove var="exception" scope="session" />
                    </span>
                </c:if>
                <c:if test="${sessionScope.exception.message == 'User already has application!'}">
                    <span class="popup_text" id="my_popup">
                        <spring:message code="application.exists.exception"/>
                        <c:remove var="exception" scope="session" />
                    </span>
                </c:if>
                <c:if test="${sessionScope.exception.message == 'Insufficient marks for specialty admission!'}">
                    <span class="popup_text" id="my_popup">
                        <spring:message code="insufficient.marks.exception"/>
                        <c:remove var="exception" scope="session" />
                    </span>
                </c:if>
                <a class="btnSubmit" href="${pageContext.request.contextPath}/api/student/specialty/apply/${specialty.name}?locale=${sessionScope.locale}">
                    <spring:message code="apply"/>
                </a>
            </div>
        </div>
    </body>
</html>
