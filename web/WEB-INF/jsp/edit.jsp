<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.Company" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="${ContactType.values()}">
            <dl>
                <dt>${type.title}</dt>
                <dd><label>
                    <input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}">
                </label></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3
        <c:forEach var="type" items="${SectionType.values()}">
            <dl>
                <dt>${type.title}</dt>
                <c:choose>
                    <c:when test="${type.name().equals('PERSONAL') || type.name().equals('OBJECTIVE')}">
                        <dd><label>
                            <input type="text" name="${type.name()}" size=30 value="${resume.getSection(type).value}">
                        </label></dd>
                    </c:when>
                    <c:when test="${type.name().equals('ACHIEVEMENT') || type.name().equals('QUALIFICATIONS')}">
                        <c:forEach var="listSection" items="${resume.getSection(type).value}">
                            <dl>
                                <dd><label>
                                    <input type="text" name="${type.name()}[]" size=30 value="${listSection}">
                                </label></dd>
                            </dl>
                        </c:forEach>
                    </c:when>
                    <c:when test="${type.name().equals('EXPERIENCE') || type.name().equals('EDUCATION')}">
                        <c:forEach var="company" items="${resume.getSection(type).value}" varStatus="companyStatus">
                            <dl>
                                <dd><label>Компания:
                                        <input type="text" name="${type.name()}[${companyStatus.index}].name"  size=20 value="${company.getName()}">
                                        <input type="text" name="${type.name()}[${companyStatus.index}].website" size=30 value="${company.getWebsite()}">
                                        <c:forEach var="period" items="${company.getPeriods()}" varStatus="periodStatus">
                                            <dl>
                                                <dd><label>Период:
                                                    <input type="date"
                                                           name="${type.name()}[${companyStatus.index}].periods[${periodStatus.index}].start"
                                                           value="<fmt:formatDate value='${period.getStart().time}' pattern='yyyy-MM-dd' />" />
                                                    <input type="date"
                                                           name="${type.name()}[${companyStatus.index}].periods[${periodStatus.index}].end"
                                                           value="<fmt:formatDate value='${period.getEnd().time}' pattern='yyyy-MM-dd' />" />
                                                    <input type="text" size=20
                                                           name="${type.name()}[${companyStatus.index}].periods[${periodStatus.index}].title"
                                                           value="${period.getTitle()}">
                                                    <input type="text" size=20
                                                           name="${type.name()}[${companyStatus.index}].periods[${periodStatus.index}].description"
                                                           value="${period.getDescription()}">
                                                </label></dd>
                                            </dl>
                                        </c:forEach>
                                </label></dd>
                            </dl>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
