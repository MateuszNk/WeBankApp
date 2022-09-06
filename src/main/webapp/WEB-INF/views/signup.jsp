<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign up</title>
    <%@ include file="../segments/stylesheets.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/forms.css">
</head>

<body>
    <div class="container">
        <%@ include file="../segments/header.jspf" %>

        <form action="${pageContext.request.contextPath}/signup" method="post" class="user-form">
            <h2 class="user-form-title">Create an account</h2>
            <input name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="password" name="repeat-password" placeholder="Repeat password" required>
            <input name="email" placeholder="E-mail" required>
            <input type="tel" name="phone_number" placeholder="Phone number ex.(123456789)" pattern="[0-9]{3}[0-9]{3}[0-9]{3}" required>
            <div>
                <input type="checkbox" id="gdpr" name="gdpr" value="gdpr" required>
                <label for="gdpr" >I agree to the <a href="#">Terms of Service</a></label>
            </div>
            <div>
                <input type="checkbox" id="newsletter" name="newsletter" value="newsletter" checked="checked">
                <label for="newsletter">I want to receive marketing messages about our products and services</label>
            </div>
            <button class="user-form-button">Sign up</button>
        </form>
    </div>
</body>
</html>
