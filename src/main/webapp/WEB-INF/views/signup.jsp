<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign up</title>
    <%@ include file="../segments/stylesheets.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/questionnaire.css">
</head>

<body>
<div class="container">
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}" class="logo">WeBankApp</a>
        <div class="buttons">
            <a href="${pageContext.request.contextPath}/login" class="login-button">Sign In</a>
        </div>
    </nav>
    <hr class="line">

    <%@ include file="../segments/popup_error.jspf" %>

    <form action="${pageContext.request.contextPath}/signup" method="post" class="user-form">
        <h2 class="user-form-title">Create an account</h2>

        <input name="username" placeholder="Username" required>
        <i class="fa-solid fa-question" title="*) Minimum 6 characters
*) Maximum 16 characters
*) Without white characters ex.: 'space'
*) Without special characters ex.: '*'"></i>

        <input type="password" name="password" placeholder="Password" required>
        <i class="fa-solid fa-question"
           title="*) Maximum 32 characters
*) Minimum 8 characters
*) Minimum 1 digit
*) Minimum 1 upper case letter
*) Minimum 1 lower case letter
*) Minimum 1 special character
*) Without white characters ex.: 'space'"></i>

        <input type="password" name="repeat-password" placeholder="Repeat password" required>
        <i class="fa-solid fa-question" title="Write the same password like up"></i>

        <input name="email" placeholder="E-mail" required>
        <i class="fa-solid fa-question" title="*) Maximum 32 characters
*) Maximum ONE '@' character
*) Minimum one domain ex.: '.com'
*) Without white characters ex.: 'space'"></i>

        <input type="tel" name="phone_number" placeholder="Phone number ex.(123456789)"
               pattern="[0-9]{3}[0-9]{3}[0-9]{3}" required>
        <i class="fa-solid fa-question" title="ex.: 123123123"></i>

        <div title="You must check it">
            <input type="checkbox" id="gdpr" name="gdpr" value="gdpr" required>
            <label for="gdpr" >I agree to the <a href="#">Terms of Service</a></label>
        </div>
        <div>
            <input type="checkbox" id="newsletter" name="newsletter" value="newsletter" title="Newsletter" checked="checked">
            <label for="newsletter">I want to receive marketing messages about our products and services</label>
        </div>
        <button class="user-form-button">Sign up</button>
    </form>

    <%@ include file="../segments/footer.jspf" %>
</div>
</body>
</html>
