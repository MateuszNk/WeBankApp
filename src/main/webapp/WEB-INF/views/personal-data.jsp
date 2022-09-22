<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Personal data</title>
  <%@ include file="../segments/stylesheets.jspf" %>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/questionnaire.css">
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

  <%@ include file="../segments/popup_error.jspf" %>

  <form action="${pageContext.request.contextPath}/personal-data" method="post" class="user-form">
    <h2 class="user-form-title">Edit Your personal data</h2>

    <c:if test="${requestScope.is_personal_data == true}">
      <c:forEach var="data" items="${requestScope.data}">
        <input name="name" placeholder="Name" value="${data.name}" required>
        <input name="surname" placeholder="Surname" value="${data.surname}" required>
        <input type="date" name="birth_date" placeholder="Birthday"
               value="<c:out value="${requestScope.format}"/>" required>
        <label>
          Country
          <select name="countryId" required>
            <c:forEach var="country" items="${requestScope.countries}">
              <option value="${country.country}" ${data.country == country.country ? 'selected' : ''}>
                  ${country.country}</option>
            </c:forEach>
          </select>
        </label>
        <input name="city" placeholder="City" value="${data.city}" required>
        <input name="postal_code" placeholder="Postal code" value="${data.postalCode}" required>
        <input name="address" placeholder="Address" value="${data.address}" required>
      </c:forEach>
    </c:if>

    <c:if test="${requestScope.is_personal_data == false}">
      <input name="name" placeholder="Name" required>
      <input name="surname" placeholder="Surname" required>
      <input type="date" name="birth_date" placeholder="Birthday" required>
      <label>
        Country
        <select name="countryId" required>
          <c:forEach var="country" items="${requestScope.countries}">
            <option value="${country.country}">${country.country}</option>
          </c:forEach>
        </select>
      </label>
      <input name="city" placeholder="City" required>
      <input name="postal_code" placeholder="Postal code" required>
      <input name="address" placeholder="Address" required>
    </c:if>
    <button class="user-form-button">Save</button>
  </form>

  <%@ include file="../segments/footer.jspf" %>
</div>
</body>
</html>
