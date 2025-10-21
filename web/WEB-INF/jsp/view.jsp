<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/view.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table class="resume-table">
        <!-- Полное имя -->
        <tr>
            <td colspan="2" class="section-header">
                <h2>${resume.fullName}</h2>
            </td>
        </tr>

        <!-- Контакты -->
        <c:if test="${not empty resume.contacts}">
            <tr>
                <td class="label-cell"><h3>Контакты:</h3></td>
                <td class="content-cell">
                    <c:forEach var="type" items="${ContactType.values()}">
                        <c:if test="${not empty resume.getContact(type)}">
                            <div><strong>${type.title}:</strong> ${resume.getContact(type)}</div>
                        </c:if>
                    </c:forEach>
                </td>
            </tr>
        </c:if>

        <!-- Секции -->
        <c:forEach var="type" items="${SectionType.values()}">
            <c:if test="${not empty resume.getSection(type)}">
                <tr>
                    <td class="label-cell"><h3>${type.title}</h3></td>
                    <td class="content-cell">
                        <c:choose>
                            <c:when test="${type.name().equals('PERSONAL') || type.name().equals('OBJECTIVE')}">
                                ${resume.getSection(type).value}
                            </c:when>

                            <c:when test="${type.name().equals('ACHIEVEMENT') || type.name().equals('QUALIFICATIONS')}">
                                <ul>
                                    <c:forEach var="item" items="${resume.getSection(type).value}">
                                        <li>${item}</li>
                                    </c:forEach>
                                </ul>
                            </c:when>

                            <c:when test="${type.name().equals('EXPERIENCE') || type.name().equals('EDUCATION')}">
                                <c:forEach var="company" items="${resume.getSection(type).value}">
                                    <div class="company-block">
                                        <h4>
                                            <c:choose>
                                                <c:when test="${not empty company.website}">
                                                    <a href="${company.website}" target="_blank">${company.name}</a>
                                                </c:when>
                                                <c:otherwise>
                                                    ${company.name}
                                                </c:otherwise>
                                            </c:choose>
                                        </h4>

                                        <div class="periods-container">
                                            <c:forEach var="period" items="${company.periods}">
                                                <div class="period-block">
                                                    <strong>
                                                        <fmt:formatDate value="${period.start.time}" pattern="MM/yyyy"/> -
                                                        <fmt:formatDate value="${period.end.time}" pattern="MM/yyyy"/>
                                                    </strong>
                                                    <br/>
                                                    <em>${period.title}</em>
                                                    <c:if test="${not empty period.description}">
                                                        <br/>${period.description}
                                                    </c:if>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>

    <div style="text-align: center; margin-top: 20px;">
        <button type="button" class="btn btn-primary" onclick="location.href='resume?uuid=${resume.uuid}&action=edit'">Редактировать</button>
        <button type="button" class="btn btn-secondary" onclick="window.history.back()">Назад</button>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>