<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><spring:message code="subjects.page"/></title>
        <style><%@include file="/WEB-INF/css/student/subjects.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
        <script><%@include file="/WEB-INF/js/popup.js" %></script>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <a href="${pageContext.request.contextPath}/api/student/subjects?locale=ua" title="<spring:message code="choose.ukrainian"/>">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student/subjects?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <a class="back-button" href="${pageContext.request.contextPath}/api/home?locale=${sessionScope.locale}"
            title="<spring:message code="back.message"/>"><span class="symbol">&#11013;</span><spring:message code="back"/></a>
        <h3 class="title"><spring:message code="exam.registration"/></h3>
        <div class="table-container">
            <form class="checkbox-form" method="post" action="${pageContext.request.contextPath}/api/student/exams?locale=${sessionScope.locale}">
                <div>
                    <table class="subject-table-1">
                        <thead>
                            <tr>
                                <th><spring:message code="subject"/></th>
                                <th class="th-2">&#10004;</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div class="scroll-form">
                    <table class="subject-table-2">
                        <tbody>
                            <c:forEach items="${subjects}" var="subject">
                                <tr>
                                    <td><label for="${subject}" title="<spring:message code="subject.message"/>"><spring:message code="${subject}"/></label></td>
                                    <td><input type="checkbox" name="subject" id="${subject}" value="${subject}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <c:choose>
                    <c:when test="${exception.message == 'Finding subjects exception! No user free subjects are found!'}">
                        <span class="popup_text" id="my_popup" onclick="popup_function()">
                            <spring:message code="finding.subjects.exception"/>
                        </span>
                    </c:when>
                    <c:when test="${exception.message == 'Admission is closed!'}">
                        <span class="popup_text" id="my_popup" onclick="popup_function()">
                            <spring:message code="admission.closed.exception"/>
                        </span>
                    </c:when>
                    <c:otherwise>
                        <div class="form-button">
                            <input type="submit" class="btnSubmit" value="<spring:message code="register"/>" />
                        </div>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
    </body>
</html>
