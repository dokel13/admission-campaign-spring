<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><spring:message code="login.page"/></title>
        <style><%@include file="/WEB-INF/css/home.css" %></style>
        <script><%@include file="/WEB-INF/js/popup.js" %></script>
    </head>
    <body>
        <div class="language">
            <a href="${pageContext.request.contextPath}/api/home?locale=ua" title="<spring:message code="choose.ukrainian"/>">UA</a>/
            <a href="${pageContext.request.contextPath}/api/home?locale=en" title="choose English">EN</a>
        </div>
        <div class="login-container">
            <div class="login-form-1">
                <h3><spring:message code="enter.admission.campaign"/></h3>
                <form class="login-form" method="post" action="${pageContext.request.contextPath}/api/login?locale=${sessionScope.locale}">
                    <div class="form-group" onclick="popup_function()">
                        <c:if test="${sessionScope.exception.message == 'Login exception! User doesn`t exist!'}">
                            <span class="popup_text" id="my_popup">
                                <spring:message code="login.exception"/>
                                <c:remove var="exception" scope="session"/>
                            </span>
                        </c:if>
                        <c:if test="${sessionScope.exception.message == 'Wrong password!'}">
                            <span class="popup_text" id="my_popup">
                                <spring:message code="wrong.password"/>
                                <c:remove var="exception" scope="session"/>
                            </span>
                        </c:if>
                        <input class="input-form" required="required" maxlength="30"
                               type="text" name="email" placeholder="<spring:message code="your.email"/> *" value=""/>
                    </div>
                    <div class="form-group">
                        <input class="input-form" required="required" maxlength="30"
                               type="password" name="password" placeholder="<spring:message code="your.password"/> *" value=""/>
                    </div>
                    <div class="form-group">
                        <input type="submit" class="btnSubmit" value="<spring:message code="login"/>"/>
                    </div>
                </form>
            </div>
            <div class="login-form-2">
                <h3><spring:message code="register.enter"/></h3>
                <form class="registration-form" method="post" action="${pageContext.request.contextPath}/api/register?locale=${sessionScope.locale}">
                    <div class="form-group" onclick="popup_function()">
                        <c:if test="${sessionScope.exception.message == 'Registration exception! User already exists!'}">
                            <span class="popup_text" id="my_popup">
                                <spring:message code="registration.exception"/>
                                <c:remove var="exception" scope="session"/>
                            </span>
                        </c:if>
                        <c:if test="${sessionScope.exception.message == 'Wrong email!'}">
                            <span class="popup_text" id="my_popup">
                                <spring:message code="wrong.email"/>
                                <c:remove var="exception" scope="session"/>
                            </span>
                        </c:if>
                        <input class="input-form" required="required" maxlength="30"
                            type="text" name="email" placeholder="<spring:message code="your.email"/> *" value=""/>
                    </div>
                    <div class="form-group">
                        <input class="input-form" pattern="[A-Za-zА-Яa-яЄєІіЇї']+" required="required" maxlength="30"
                            type="text" name="name" placeholder="<spring:message code="your.name"/> *" value=""/>
                    </div>
                    <div class="form-group">
                        <input class="input-form" pattern="[A-Za-zА-Яa-яЄєІіЇї']+" required="required" maxlength="30"
                            type="text" name="surname" placeholder="<spring:message code="your.surname"/> *" value=""/>
                    </div>
                    <div class="form-group">
                        <input class="input-form" required="required" maxlength="30"
                               type="password" name="password" placeholder="<spring:message code="new.password"/> *" value=""/>
                    </div>
                    <div class="form-group">
                        <input type="submit" class="btnSubmit" value="<spring:message code="register"/>"/>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
