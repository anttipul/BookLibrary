<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WepaHT</title>
    </head>
    <body>
        <h1>BookLibrary</h1>

        <p>Search for a book from Openlibrary:</p>
        <form:form action="${pageContext.request.contextPath}/app/openlibrary/search/" method="GET">
            <input id="olisbn" type="radio" value="isbn" name="isbnortitle" checked>ISBN<br/>
            <input id="oltitle" type="radio" value="title" name="isbnortitle">Title<br/>
            <input id="olquery" type="text" name="query"/>
            <input type="submit" value="Search">
        </form:form>
        <br/>

        <p>Search for a book from own database:</p>
        <form:form action="${pageContext.request.contextPath}/app/books/search/" method="GET">
            <input id="odauthor" type="radio" value="author" name="where" checked>Author<br/>
            <input id="odtitle" type="radio" value="title" name="where">Title<br/>
            <input id="odquery" type="text" name="query"/>
            <input type="submit" value="Search">
        </form:form>
        <br/>
        <jsp:include page="footer.jsp"/>

    </body>
</html>
