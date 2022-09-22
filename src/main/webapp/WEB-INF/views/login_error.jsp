<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/error.css">
<div class="popup" id="popup">
    <h2>Error</h2>
    <p>Wrong login and/or password</p>
    <button type="button" onclick="closePopup()">OK</button>
</div>
<script>
    let popup = document.getElementById("popup");
    function closePopup() {
        popup.classList.add("close-popup");
        window.location = '${pageContext.request.contextPath}/login';
    }
</script>
