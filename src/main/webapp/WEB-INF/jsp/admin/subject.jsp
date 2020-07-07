<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><spring:message code="${subject}"/></title>
        <style><%@include file="/WEB-INF/css/admin/subject.css" %></style>
        <style><%@include file="/WEB-INF/css/right_corner.css" %></style>
        <style><%@include file="/WEB-INF/css/title.css" %></style>
        <style><%@include file="/WEB-INF/css/back_button.css" %></style>
    </head>
    <body>
        <div class="right-corner">
            <div class="language">
                <a href="${pageContext.request.contextPath}/api/admin/subject/${subject}?page=${page}&locale=ua" title="<spring:message code="choose.ukrainian"/>">UA</a>/
                <a href="${pageContext.request.contextPath}/api/admin/subject/${subject}?page=${page}&locale=en" title="choose English">EN</a>
            </div>
            <%@include file="/WEB-INF/jsp/logout.jsp" %>
        </div>
        <a class="back-button" href="${pageContext.request.contextPath}/api/home?locale=${sessionScope.locale}"
                                            title="<spring:message code="back.message"/>"><span class="symbol">&#11013;</span><spring:message code="back"/></a>
        <h3 class="title"><spring:message code="${subject}"/></h3>
        <div class="table-container">
            <form class="checkbox-form" method="post" action="${pageContext.request.contextPath}/api/admin/subject/save_marks/${subject}?page=${page}&locale=${sessionScope.locale}">
                <table class="exam-table">
                    <thead>
                        <tr>
                            <th><spring:message code="student.name"/></th>
                            <th><spring:message code="email"/></th>
                            <th><spring:message code="mark"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${exams}" var="exam">
                            <tr>
                                <td>${exam.user.name} ${exam.user.surname}</td>
                                <td>${exam.user.email}<input type="hidden" name="email" value="${exam.user.email}"/></td>
                                <td><input type="number" name="mark" value="${exam.mark}" min="0" max="200"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="form-button">
                    <input type="submit" class="btnSubmit" value="<spring:message code="save"/>" />
                </div>
            </form>
        </div>
        <div class="button-container">
            <a class="page-button" href="${pageContext.request.contextPath}/api/admin/subject/${subject}?page=${page - 1}&locale=${sessionScope.locale}">
                <span class="symbol">←</span></a>
            <a class="page-button" href="${pageContext.request.contextPath}/api/admin/subject/${subject}?page=${page + 1}&locale=${sessionScope.locale}">
                <span class="symbol">→</span></a>
        </div>
    </body>
</html>
