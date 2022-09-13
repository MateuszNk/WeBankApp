<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Personal data</title>
  <%@ include file="../segments/stylesheets.jspf" %>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/forms.css">
</head>

<body>
<div class="container">
  <nav class="navbar">
    <a href="${pageContext.request.contextPath}" class="logo">WeBankApp</a>
    <div class="buttons">
      <a href="${pageContext.request.contextPath}/account" class="back-button">Back</a>
    </div>
  </nav>
  <hr class="line">

  <form action="${pageContext.request.contextPath}/personal-data" method="post" class="user-form">
    <h2 class="user-form-title">Edit Your personal data</h2>
    <input name="name" placeholder="Name" required>
    <input name="surname" placeholder="Surname" required>
    <input type="date" name="birth_date" placeholder="Birthday" required>
    <label>
      Country
      <select name="country">
        <c:forEach var="country" items="${requestScope.countries}">
          <option value="${country.id}">${country.country}</option>
        </c:forEach>
      </select>
    </label>
    <input name="city" placeholder="City" required>
    <input name="postal_code" placeholder="Postal code">
    <input name="address" placeholder="Address">
    <button class="user-form-button">Save</button>
  </form>

  <%@ include file="../segments/footer.jspf" %>
</div>
</body>
</html>
