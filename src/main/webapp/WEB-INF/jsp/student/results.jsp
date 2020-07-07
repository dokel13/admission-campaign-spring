<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><spring:message code="results"/></title>
        <style><%@include file="/WEB-INF/css/student/results.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
        <script><%@include file="/WEB-INF/js/popup.js" %></script>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <a href="${pageContext.request.contextPath}/api/student/results?locale=ua" title="<spring:message code="choose.ukrainian"/>">UA</a>/
                <a href="${pageContext.request.contextPath}/api/student/results?locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <a class="back-button" href="${pageContext.request.contextPath}/api/home?locale=${sessionScope.locale}"
                                            title="<spring:message code="back.message"/>"><span class="symbol">&#11013;</span><spring:message code="back"/></a>
        <h3 class="title"><spring:message code="results"/></h3>
        <div class="table-container">
            <div>
                <table class="results-table-1">
                    <thead>
                        <tr>
                            <th><spring:message code="subject"/></th>
                            <th class="th-2"><spring:message code="mark"/></th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div class="scroll-form">
                <table class="results-table-2">
                    <tbody>
                        <c:forEach items="${exams}" var="exam">
                            <tr>
                                <td><spring:message code="${exam.subject}"/></td>
                                <td>${exam.mark}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
