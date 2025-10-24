<%-- WEB-INF/jsp/error.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <a href="${pageContext.request.contextPath}/">Вернуться на главную</a>
</div>
</body>
</html>