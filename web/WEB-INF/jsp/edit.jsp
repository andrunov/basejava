<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
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
                        <div id="${type.name()}-container">
                            <c:forEach var="listSection" items="${resume.getSection(type).value}">
                                <dl>
                                    <dd><label>
                                        <input type="text" name="${type.name()}[]" size=30 value="${listSection}">
                                        <button type="button" class="remove-btn">× Удалить</button>
                                    </label></dd>
                                </dl>
                            </c:forEach>
                        </div>
                        <button type="button" onclick="addField('${type.name()}')">+ Добавить пункт</button>
                    </c:when>
                    <c:when test="${type.name().equals('EXPERIENCE') || type.name().equals('EDUCATION')}">
                        <div id="${type.name()}-container">
                            <c:forEach var="company" items="${resume.getSection(type).value}" varStatus="companyStatus">
                                <div class="company-block">
                                    <dl>
                                        <dd><label>Компания:
                                                <input type="text" name="${type.name()}[${companyStatus.index}].name"  size=20 value="${company.getName()}">
                                                <input type="text" name="${type.name()}[${companyStatus.index}].website" size=30 value="${company.getWebsite()}">
                                                <button type="button" class="remove-btn" onclick="this.closest('.company-block').remove()">× Удалить компанию</button>
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
                                </div>
                            </c:forEach>
                        </div>
                        <button type="button" onclick="addCompany('${type.name()}')">+ Добавить компанию</button>
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
<script>
    function addField(type) {
        const container = document.getElementById(type + '-container');
        const newField = document.createElement('dl');
        const newDD = document.createElement('dd');
        const newLabel = document.createElement('label');

        // Создаем input через DOM
        const newInput = document.createElement('input');
        newInput.type = 'text';
        newInput.name = type + '[]';  // Вот так правильно!
        newInput.size = 30;
        newInput.value = '';
        newInput.placeholder = 'Введите новый пункт';

        // Создаем кнопку удаления
        const removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.className = 'remove-btn';
        removeBtn.textContent = '× Удалить';

        // Собираем структуру
        newLabel.appendChild(newInput);
        newLabel.appendChild(removeBtn);
        newDD.appendChild(newLabel);
        newField.appendChild(newDD);
        container.appendChild(newField);

        // Обработчик для кнопки удаления
        removeBtn.addEventListener('click', function() {
            newField.remove();
        });
    }

    function addCompany(type) {
        const container = document.getElementById(type + '-container');

        // Считаем сколько компаний уже есть
        const existingCompanies = container.querySelectorAll('.company-block');
        const newIndex = existingCompanies.length;

        // Создаем основные элементы
        const companyBlock = document.createElement('div');
        companyBlock.className = 'company-block';

        const dl = document.createElement('dl');
        const dd = document.createElement('dd');
        const label = document.createElement('label');

        // Текст "Компания:"
        label.appendChild(document.createTextNode('Компания: '));

        // Поле для названия компании
        const nameInput = document.createElement('input');
        nameInput.type = 'text';
        nameInput.name = type + '[' + newIndex + '].name';
        nameInput.size = 20;
        nameInput.placeholder = 'Название компании';

        // Поле для сайта
        const websiteInput = document.createElement('input');
        websiteInput.type = 'text';
        websiteInput.name = type + '[' + newIndex + '].website';
        websiteInput.size = 30;
        websiteInput.placeholder = 'Веб-сайт';

        // Кнопка удаления
        const removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.className = 'remove-btn';
        removeBtn.textContent = '× Удалить компанию';

        // Собираем структуру
        label.appendChild(nameInput);
        label.appendChild(websiteInput);
        label.appendChild(removeBtn);

        dd.appendChild(label);
        dl.appendChild(dd);
        companyBlock.appendChild(dl);
        container.appendChild(companyBlock);

        // Обработчик для кнопки удаления
        removeBtn.addEventListener('click', function() {
            companyBlock.remove();
        });

        // Добавляем один пустой период по умолчанию
        addPeriodToCompany(companyBlock, type, newIndex, 0);
    }

    // Вспомогательная функция для добавления периода к компании
    function addPeriodToCompany(companyBlock, type, companyIndex, periodIndex) {
        const dl = companyBlock.querySelector('dl');
        const periodsDl = document.createElement('dl');
        const periodsDd = document.createElement('dd');
        const periodsLabel = document.createElement('label');

        periodsLabel.appendChild(document.createTextNode('Период: '));

        // Поле начала периода
        const startInput = document.createElement('input');
        startInput.type = 'date';
        startInput.name = type + '[' + companyIndex + '].periods[' + periodIndex + '].start';

        // Поле окончания периода
        const endInput = document.createElement('input');
        endInput.type = 'date';
        endInput.name = type + '[' + companyIndex + '].periods[' + periodIndex + '].end';

        // Поле должности
        const titleInput = document.createElement('input');
        titleInput.type = 'text';
        titleInput.name = type + '[' + companyIndex + '].periods[' + periodIndex + '].title';
        titleInput.size = 20;
        titleInput.placeholder = 'Должность';

        // Поле описания
        const descInput = document.createElement('input');
        descInput.type = 'text';
        descInput.name = type + '[' + companyIndex + '].periods[' + periodIndex + '].description';
        descInput.size = 20;
        descInput.placeholder = 'Описание';

        // Собираем период
        periodsLabel.appendChild(startInput);
        periodsLabel.appendChild(endInput);
        periodsLabel.appendChild(titleInput);
        periodsLabel.appendChild(descInput);

        periodsDd.appendChild(periodsLabel);
        periodsDl.appendChild(periodsDd);
        dl.appendChild(periodsDl);
    }
    // Отдельная функция для инициализации существующих кнопок при загрузке страницы
    function initRemoveButtons() {
        document.querySelectorAll('.remove-btn').forEach(button => {
            button.addEventListener('click', function() {
                this.closest('dl').remove();
            });
        });
    }

    // Инициализация при загрузке страницы
    document.addEventListener('DOMContentLoaded', function() {
        initRemoveButtons();
    });
</script>
</body>
</html>
