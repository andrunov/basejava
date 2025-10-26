<%-- WEB-INF/jsp/error.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ошибка</title>
    <style>
        .error-container {
            padding: 20px;
            background: #dae6f1;
            border-radius: 10px;
            margin: 20px;
        }
    </style>
</head>
<body>
<div class="error-container">
    <h2>⚠️ Что-то пошло не так</h2>
    <p>${errorMessage}</p>
    <c:if test="${not empty stackTrace}">
        <h3>Детали ошибки:</h3>
        <pre style="background: #f5f5f5; padding: 10px; overflow: auto;">${stackTrace}</pre>
    </c:if>
    <a href="${pageContext.request.contextPath}/">Вернуться на главную</a>
</div>
</body>
</html>