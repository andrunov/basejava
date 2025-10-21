<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/resume-list-styles.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="scrollable-panel">
    <div class="table-wrapper">
        <div class="add-resume">
            <a class="no-underline-anchor" href="resume?action=add">
                <img src="img/add-person.svg" alt="">
            </a>
            <a class="text-anchor" href="resume?action=add">
                <p class="add-resume-title">Добавить резюме</p>
            </a>
        </div>
        <div class="resumes-list">
            <table>
                <tr class="t-header">
                    <th class="name-column">Имя</th>
                    <th class="info-column">Контакты</th>
                    <th class="img-column">Редактировать</th>
                    <th class="img-column">Удалить</th>
                </tr>
                <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
                <c:forEach items="${resumes}" var="resume">
                    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
                    <tr class="t-body">
                        <td class="name-column">
                            <a class="contact-link"
                               href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a>
                        </td>
                        <td class="info-column">
                            <%=ContactType.EMAIL.toLink(resume.getContact(ContactType.EMAIL))%>
                        </td>
                        <td class="img-column">
                            <a class="no-underline-anchor" href="resume?uuid=${resume.uuid}&action=edit">
                                <img src="img/edit.svg" alt="">
                            </a>
                        </td>
                        <td class="img-column">
                            <a class="no-underline-anchor" href="resume?uuid=${resume.uuid}&action=delete">
                                <img src="img/remove.svg" alt="">
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
