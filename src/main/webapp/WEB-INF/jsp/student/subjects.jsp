<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle var="messages" basename="messages"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><fmt:message key="subjects.page" bundle="${messages}"/></title>
        <style><%@include file="/WEB-INF/css/student/subjects.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
        <script><%@include file="/WEB-INF/js/popup.js" %></script>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <fmt:message var="choose_ukrainian" key="choose.ukrainian" bundle="${messages}"/>
                <a href="${pageContext.request.contextPath}/api/student/subjects?locale=ua" title="${choose_ukrainian}">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student/subjects?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <fmt:message var="back_button" key="back" bundle="${messages}"/>
        <fmt:message var="back_message" key="back.message" bundle="${messages}"/>
        <a class="back-button" href="${pageContext.request.contextPath}/api/home?locale=${sessionScope.locale}"
                                            title="${back_message}"><span class="symbol">&#11013;</span>${back_button}</a>
        <h3 class="title"><fmt:message key="exam.registration" bundle="${messages}"/></h3>
        <div class="table-container">
            <form class="checkbox-form" method="post" action="${pageContext.request.contextPath}/api/student/exams?locale=${sessionScope.locale}">
                <div>
                    <table class="subject-table-1">
                        <thead>
                            <tr>
                                <th><fmt:message key="subject" bundle="${messages}"/></th>
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
                                    <fmt:message var="subject_locale" key="${subject}" bundle="${messages}"/>
                                    <fmt:message var="subject_message" key="subject.message" bundle="${messages}"/>
                                    <td><label for="${subject}" title="${subject_message}">${subject_locale}</label></td>
                                    <td><input type="checkbox" name="subject" id="${subject}" value="${subject}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <c:choose>
                    <c:when test="${exception.message == 'Finding subjects exception! No user free subjects are found!'}">
                        <span class="popup_text" id="my_popup" onclick="popup_function()">
                            <fmt:message key="finding.subjects.exception" bundle="${messages}"/>
                        </span>
                    </c:when>
                    <c:when test="${exception.message == 'Admission is closed!'}">
                        <span class="popup_text" id="my_popup" onclick="popup_function()">
                            <fmt:message key="admission.closed.exception" bundle="${messages}"/>
                        </span>
                    </c:when>
                    <c:otherwise>
                        <div class="form-button">
                            <fmt:message var="register" key="register" bundle="${messages}"/>
                            <input type="submit" class="btnSubmit" value="${register}" />
                        </div>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
    </body>
</html>