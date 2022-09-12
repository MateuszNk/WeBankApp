<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WeBankApp</title>
    <%@ include file="../segments/stylesheets.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/account.css">
</head>

<body>
<div class="container">
    <nav class="navbar">
        <a href="#" class="logo">WeBankApp</a>
        <div class="buttons">
            <a href="#" class="myaccount-button">My account</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout-button">Logout</a>
        </div>
    </nav>

    <aside class="menu">
        <ul>
            <li><a href="#">Home</a></li>
            <li><a href="#">Transfers</a></li>
            <li><a href="#">Offers</a></li>
            <li><a href="#">Settings</a></li>
        </ul>
    </aside>

    <main>
        <div class="row">
            <div class="column welcome">
                <h2>Welcome ${pageContext.request.userPrincipal.name}</h2>
                <p><c:out value="${requestScope.account_number}"/></p>
                <i class="fa-solid fa-wallet"></i>
                <p>Your account balance: ${requestScope.account_balance}zł</p>
            </div>

            <div class="column history">
                <h2>Transfers history:</h2>
                <article class="history-objects">
                    <p class="history-company">Supermarket</p>
                    <p class="history-account">Account number: 121234 1234 1234 1234 1234 1234</p>
                    <p class="history-date">12.06.2022 14:54</p>
                    <p class="history-value">-234zł Account balance after payment: 629zł</p>
                    <a href="#">Details</a>
                </article>
            </div>
        </div>
    </main>

    <%@ include file="../segments/footer.jspf" %>
</div>
</body>
</html>