<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle var="messages" basename="messages"/>
<html>
    <head>and ${sessionScope.locale} and ${locale}
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title><fmt:message key="login.page" bundle="${messages}"/></title>
        <style><%@include file="/WEB-INF/css/home.css" %></style>
        <script><%@include file="/WEB-INF/js/popup.js" %></script>
    </head>
    <body>
        <div class="language">
            <fmt:message var="choose_ukrainian" key="choose.ukrainian" bundle="${messages}"/>
            <a href="${pageContext.request.contextPath}/api/home?locale=ua" title="${choose_ukrainian}">UA</a>/
            <a href="${pageContext.request.contextPath}/api/home?locale=en" title="choose English">EN</a>
        </div>
        <div class="login-container">
            <div class="login-form-1">
                <h3><fmt:message key="enter.admission.campaign" bundle="${messages}"/></h3>
                <form class="login-form" method="post" action="${pageContext.request.contextPath}/api/login?locale=${sessionScope.locale}">
                    <div class="form-group" onclick="popup_function()">
                        <c:if test="${sessionScope.exception.message == 'Login exception! User doesn`t exist!'}">
                            <span class="popup_text" id="my_popup">
                                <fmt:message key="login.exception" bundle="${messages}"/>
                                <c:remove var="exception" scope="session"/>
                            </span>
                        </c:if>
                        <c:if test="${sessionScope.exception.message == 'Wrong password!'}">
                            <span class="popup_text" id="my_popup">
                                <fmt:message key="wrong.password" bundle="${messages}"/>
                                <c:remove var="exception" scope="session"/>
                            </span>
                        </c:if>
                        <fmt:message var="your_email" key="your.email" bundle="${messages}"/>
                        <input class="input-form" required="required" maxlength="30"
                               type="text" name="email" placeholder="${your_email} *" value=""/>
                    </div>
                    <div class="form-group">
                        <fmt:message var="your_password" key="your.password" bundle="${messages}"/>
                        <input class="input-form" required="required" maxlength="30"
                               type="password" name="password" placeholder="${your_password} *" value=""/>
                    </div>
                    <div class="form-group">
                        <fmt:message var="login" key="login" bundle="${messages}"/>
                        <input type="submit" class="btnSubmit" value="${login}"/>
                    </div>
                </form>
            </div>
            <div class="login-form-2">
                <h3><fmt:message key="register.enter" bundle="${messages}"/></h3>
                <form class="registration-form" method="post" action="${pageContext.request.contextPath}/api/register?locale=${sessionScope.locale}">
                    <div class="form-group" onclick="popup_function()">
                        <c:if test="${sessionScope.exception.message == 'Registration exception! User already exists!'}">
                            <span class="popup_text" id="my_popup">
                                <fmt:message key="registration.exception" bundle="${messages}"/>
                                <c:remove var="exception" scope="session"/>
                            </span>
                        </c:if>
                        <c:if test="${sessionScope.exception.message == 'Wrong email!'}">
                            <span class="popup_text" id="my_popup">
                                <fmt:message key="wrong.email" bundle="${messages}"/>
                                "${sessionScope.exception.wrongValue}"
                                <c:remove var="exception" scope="session"/>
                            </span>
                        </c:if>
                        <fmt:message var="your_email" key="your.email" bundle="${messages}"/>
                        <input class="input-form" required="required" maxlength="30"
                            type="text" name="email" placeholder="${your_email} *" value=""/>
                    </div>
                    <div class="form-group">
                        <fmt:message var="your_name" key="your.name" bundle="${messages}"/>
                        <input class="input-form" pattern="[A-Za-zА-Яa-яЄєІіЇї']+" required="required" maxlength="30"
                            type="text" name="name" placeholder="${your_name} *" value=""/>
                    </div>
                    <div class="form-group">
                        <fmt:message var="your_surname" key="your.surname" bundle="${messages}"/>
                        <input class="input-form" pattern="[A-Za-zА-Яa-яЄєІіЇї']+" required="required" maxlength="30"
                            type="text" name="surname" placeholder="${your_surname} *" value=""/>
                    </div>
                    <div class="form-group">
                        <fmt:message var="new_password" key="new.password" bundle="${messages}"/>
                        <input class="input-form" required="required" maxlength="30"
                               type="password" name="password" placeholder="${new_password} *" value=""/>
                    </div>
                    <div class="form-group">
                        <fmt:message var="register" key="register" bundle="${messages}"/>
                        <input type="submit" class="btnSubmit" value="${register}"/>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>