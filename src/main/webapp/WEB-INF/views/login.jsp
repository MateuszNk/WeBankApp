<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <%@ include file="../segments/stylesheets.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/forms.css">
</head>

<body>
<div class="container">
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}" class="logo">WeBankApp</a>
        <div class="buttons">
            <a href="${pageContext.request.contextPath}/signup" class="register-button">Register</a>
        </div>
    </nav>
    <hr class="line">

    <form action="j_security_check" method="post" class="user-form">
        <h2 class="user-form-title">Login</h2>
        <input name="j_username" placeholder="Username" required>
        <input name="j_password" placeholder="Password" type="password" required>
        <button class="user-form-button">Login</button>
        <p>
            Do not You have an account?
            <a href="${pageContext.request.contextPath}/signup">Register now!</a>
        </p>
    </form>

    <%@ include file="../segments/footer.jspf" %>
</div>
</body>
</html>
