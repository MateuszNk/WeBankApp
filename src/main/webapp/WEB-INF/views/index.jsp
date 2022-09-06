<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WeBankApp</title>
    <%@ include file="../segments/stylesheets.jspf" %>
</head>

<body>
<div class="container">
    <%@ include file="../segments/header.jspf" %>

    <div class="usage">
        <p>Number of people using our bank: 123456</p>
        <label>
            <i class="fa-solid fa-magnifying-glass" style="position: absolute; padding: 8px;"></i>
            <input type="text" name="search" class="search" placeholder="Search:">
        </label>
    </div>

    <main>
        <c:set var="numCols" value="2"/>
        <c:forEach var="news" items="${requestScope.news}" varStatus="status">
            <c:if test="${status.index % numCols == 0}">
                <div class="row">
            </c:if>
            <div class="column">
                <article class="discovery">
                    <h2 class="discovery-header"><c:out value="${news.title}"/></h2>
                    <p class="discovery-date">Created ${news.dateAdded.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"))}</p>
                    <p>${news.description}</p>
                </article>
            </div>
            <c:if test="${status.count % numCols == 0 or status.last}"></div></c:if>
        </c:forEach>
    </main>

    <%@ include file="../segments/footer.jspf" %>
</div>
</body>
</html>