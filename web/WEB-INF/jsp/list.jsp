<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/list.css">
    <title>Список резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <div class="resumes-container">
        <!-- Блок добавления резюме -->
        <div class="add-resume">
            <a href="resume?action=add">
                <img src="img/add-person.svg" alt="Добавить" width="20" height="20">
                Добавить резюме
            </a>
        </div>

        <!-- Таблица резюме -->
        <table class="resumes-table">
            <thead>
            <tr>
                <th class="name-column">Имя</th>
                <th class="info-column">Контакты</th>
                <th class="action-column">Просмотр</th>
                <th class="action-column">Редактировать</th>
                <th class="action-column">Удалить</th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
            <c:forEach items="${resumes}" var="resume">
                <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
                <tr>
                    <td class="name-column">
                        <a class="contact-link" href="resume?uuid=${resume.uuid}&action=view">
                                ${resume.fullName}
                        </a>
                    </td>
                    <td class="info-column">
                        <%=ContactType.EMAIL.toLink(resume.getContact(ContactType.EMAIL))%>
                    </td>
                    <td class="action-column">
                        <a class="action-link" href="resume?uuid=${resume.uuid}&action=view" title="Просмотр">
                            <img src="img/view.svg" alt="Просмотр" width="18" height="18">
                        </a>
                    </td>
                    <td class="action-column">
                        <a class="action-link" href="resume?uuid=${resume.uuid}&action=edit" title="Редактировать">
                            <img src="img/edit.svg" alt="Редактировать" width="18" height="18">
                        </a>
                    </td>
                    <td class="action-column">
                        <a class="action-link" href="resume?uuid=${resume.uuid}&action=delete" title="Удалить" onclick="return confirm('Удалить резюме ${resume.fullName}?')">
                            <img src="img/remove.svg" alt="Удалить" width="18" height="18">
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Сообщение если список пуст -->
        <c:if test="${empty resumes}">
            <div style="text-align: center; padding: 40px; color: #6c757d;">
                <p>Резюме не найдены. Добавьте первое резюме!</p>
            </div>
        </c:if>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>