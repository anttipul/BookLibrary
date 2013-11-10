<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTpF-8">
        <title>Books</title>
    </head>
    <body>
        <h1>Books</h1>
        <c:forEach var="book" items="${books}">
            <h3>${book.title}</h3>
            <img src="http://covers.openlibrary.org/b/isbn/${book.isbn}-s.jpg" /> <br/>
            <a href="${pageContext.request.contextPath}/app/books/${book.id}">View</a><br/>    
            Authors:
            <c:forEach var="author" items="${book.authors}">
                ${author},
            </c:forEach>
            </br>
        </c:forEach>
        </br>
        <c:if test="${pageNumber > 1}">
            <a href="${pageContext.request.contextPath}/app/books/${query}pageNumber=${pageNumber - 1}">Prev</a>
        </c:if>
        <c:if test="${pageNumber < totalPages}">
            <a href="${pageContext.request.contextPath}/app/books/${query}pageNumber=${pageNumber + 1}">Next</a>
        </c:if>
        <br/>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
