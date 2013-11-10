<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit</title>
    </head>
    <body>

        <form:form commandName="book" action="${pageContext.request.contextPath}/app/books/update/${id}" method="POST">
            Title:<form:input path="title" /><form:errors path="title" /><br/>
            ISBN:<form:input path="isbn" /><form:errors path="isbn" /><br/>
            Authors:<form:input path="authors" /><form:errors path="authors" /><br/>
            Publishers:<form:input path="publishers" /><form:errors path="publishers" /><br/>
            Publishing year:<form:input path="publishingYear" /><form:errors path="publishingYear" /><br/>
            <sec:authorize access="isAuthenticated()">
                <input type="submit" value="Submit">
            </sec:authorize>
        </form:form>
        <br/>
        <jsp:include page="footer.jsp"/>
    </body>
</html>