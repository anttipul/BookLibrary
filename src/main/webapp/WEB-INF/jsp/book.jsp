<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book</title>
    </head>
    <body>
        <h2>${book.title} </h2>
        <img src="http://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg" /></br>
        ISBN: ${book.isbn} <br/>    
        Publishing year: ${book.publishingYear} <br/>
        Authors:
        <ul>
            <c:forEach var="author" items="${book.authors}">
                <li>
                    ${author}
                </li>
            </c:forEach>
        </ul>
        Publishers:
        <ul>
            <c:forEach var="publisher" items="${book.publishers}">
                <li>
                    ${publisher}
                </li>
            </c:forEach>
        </ul>
        <sec:authorize access="isAuthenticated()">
            <a href="${pageContext.request.contextPath}/app/books/delete/${book.id}">Delete</a><br/>
            <a href="${pageContext.request.contextPath}/app/books/edit/${book.id}">Edit</a>
        </sec:authorize>


        <br/>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
