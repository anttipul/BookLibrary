<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form name="f" action="<c:url value='/j_spring_security_check' />" method="post">
            username:
            <input type="text" name="j_username" value="${username}" />
            password:
            <input type="password" name="j_password" />
            <input type="submit" />
        </form>
        <br/>
        <jsp:include page="footer.jsp"/>
    </body>
</html>