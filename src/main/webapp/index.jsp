<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WeBankApp</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./styles/styles.css">
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/5c6b760006.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <nav class="navbar">
        <a href="#" class="logo">WeBankApp</a>
        <div class="buttons">
            <a href="#" class="login-button">Sign In</a>
            <a href="#" class="register-button">Register</a>
        </div>
    </nav>

    <aside class="menu">
        <ul>
            <li><a href="#">Home</a></li>
            <li><a href="#">Individuals</a></li>
            <li><a href="#">Companies</a></li>
            <li><a href="#">Contact</a></li>
        </ul>
        <div class="usage">
            <p>Number of people using our bank: 123456</p>
            <label>
                <i class="fa-solid fa-magnifying-glass" style="position: absolute; padding: 8px;"></i>
                <input type="text" name="search" class="search" placeholder="Search:">
            </label>
        </div>
    </aside>

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

    <section class="big-footer">
        <hr class="line">
        <a href="#">
            <i class="fa-solid fa-newspaper"></i>
            WeBankApp
        </a>
    </section>

    <footer>WeBankApp ®, created and developed by Mateusz Nowak</footer>
</div>
</body>
</html>