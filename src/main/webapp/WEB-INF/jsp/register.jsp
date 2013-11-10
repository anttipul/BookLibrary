<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <form:form commandName="user" action="${pageContext.request.contextPath}/app/register" method="POST">
            Name:<form:input path="name" /><form:errors path="name" /><br/>
            Password:<form:input type="password" path="password" /><form:errors path="password" /><br/>
            <input type="submit" value="Submit">
        </form:form>

        <br/>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
