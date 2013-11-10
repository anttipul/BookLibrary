<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<a href="${pageContext.request.contextPath}/app/menu" >Menu</a>
<a href="${pageContext.request.contextPath}/app/books" >List</a>

<sec:authorize access="isAuthenticated()">
    <a href="${pageContext.request.contextPath}/app/add" >Add</a>
</sec:authorize>

<sec:authorize access="!isAuthenticated()">
    <a href="${pageContext.request.contextPath}/app/login">Login</a>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <a href="${pageContext.request.contextPath}/app/logout">Logout</a>
</sec:authorize>   

<sec:authorize access="!isAuthenticated()">
    <a href="${pageContext.request.contextPath}/app/register" >Register</a>
</sec:authorize>   
