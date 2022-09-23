<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Payment</title>
  <%@ include file="../segments/stylesheets.jspf" %>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/payment.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}" class="logo">WeBankApp</a>
  <div class="buttons">
    <a href="${pageContext.request.contextPath}/account" class="back-button">Back</a>
  </div>
</nav>
<hr class="line">

<div class="container">
  <form action="${pageContext.request.contextPath}/payment" method="post">
  <h2>Payment</h2>
  <div class="row">
    <div class="column">
      <label for="account">Your account number and balance</label><br>
      <input type="text" class="account" id="account" name="account"
             value="${requestScope.account_number}" readonly>
    </div>
  </div>

  <div class="row">
    <div class="column">
      <label for="receiver-account">Receiver account number*</label><br>
      <input type="text" name="receiver-account" id="receiver-account" placeholder="12 1234 1234 1234 1234 1234 1234" required>
    </div>

    <div class="column">
      <label for="name-of-receiver">Name of receiver*</label><br>
      <input type="text" name="name-of-receiver" id="name-of-receiver" placeholder="Name" required>
    </div>
  </div>

  <div class="row">
    <div class="column">
      <label for="additional-data">Localization of receiver</label><br>
      <input type="text" name="additional-data" id="additional-data" placeholder="Localization">
    </div>

    <div class="column">
      <label for="title">Title*</label><br>
      <input type="text" name="title" id="title" value="Title" required>
    </div>
  </div>

  <div class="row">
    <div class="column">
      <label for="amount">Amount*</label><br>
      <input type="number" name="amount" id="amount" placeholder="123" required>
    </div>

    <div class="column">
      <button type="submit" class="send-button">Send</button>
    </div>
  </div>

  <p>All fields with * are required</p>
  <p>Amount can not be higher than your account balance</p>
  </form>
</div>

<%@ include file="../segments/footer.jspf" %>
</body>
</html>
