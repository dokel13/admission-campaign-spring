<fmt:message var="logout" key="logout" bundle="${messages}"/>
<a class="logout" href="${pageContext.request.contextPath}/api/logout?locale=${sessionScope.locale}"
    title="${logout}">${logout}</a>