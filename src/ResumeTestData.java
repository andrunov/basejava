import com.urise.webapp.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {

        Resume resume = new Resume("ID_001", "Григорий Кислин");

        resume.setContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.setContact(ContactType.LINKEDIN, "");
        resume.setContact(ContactType.GITHUB, "");
        resume.setContact(ContactType.STACKOVERFLOW, "");
        resume.setContact(ContactType.HOMEPAGE, "");

        resume.setSection(SectionType.OBJECTIVE, new Section<>("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.setSection(PERSONAL, new Section<>("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievements = new ArrayList<>();
        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.setSection(ACHIEVEMENT, new Section<>(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualifications.add("Администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        resume.setSection(QUALIFICATIONS, new Section<>(qualifications));

        List<Company> companies = new ArrayList<>();

        Company company1 = new Company();
        company1.setName("Java Online Projects");
        company1.setWebsite("https://javaops.ru/");
        Period period1 = new Period();
        period1.setStart(new GregorianCalendar(2013, Calendar.OCTOBER, 1));
        period1.setEnd(Calendar.getInstance());
        period1.setTitle("Автор проекта.");
        period1.setDescription("Создание, организация и проведение Java онлайн проектов и стажировок.");
        company1.addPeriod(period1);
        companies.add(company1);

        Company company2 = new Company();
        company2.setName("Wrike");
        company2.setWebsite("https://www.wrike.com/");
        Period period2 = new Period();
        period2.setStart(new GregorianCalendar(2014, Calendar.OCTOBER, 1));
        period2.setEnd(new GregorianCalendar(2016, Calendar.JANUARY, 1));
        period2.setTitle("Старший разработчик (backend)");
        period1.setDescription("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        company2.addPeriod(period2);
        companies.add(company2);

        Company company3 = new Company();
        company3.setName("RIT Center");
        company3.setWebsite("");
        Period period3 = new Period();
        period3.setStart(new GregorianCalendar(2012, Calendar.APRIL, 1));
        period3.setEnd(new GregorianCalendar(2014, Calendar.NOVEMBER, 1));
        period3.setTitle("Java архитектор");
        period3.setDescription("Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        company3.addPeriod(period3);
        companies.add(company3);

        Company company4 = new Company();
        company4.setName("Luxoft (Deutsche Bank)");
        company4.setWebsite("https://luxoft.ru/");
        Period period4 = new Period();
        period4.setStart(new GregorianCalendar(2010, Calendar.JANUARY, 1));
        period4.setEnd(new GregorianCalendar(2012, Calendar.APRIL, 1));
        period4.setTitle("Ведущий программист");
        period4.setDescription("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        company4.addPeriod(period4);
        companies.add(company4);

        Company company5 = new Company();
        company5.setName("Yota");
        company5.setWebsite("https://www.yota.ru/");
        Period period5 = new Period();
        period5.setStart(new GregorianCalendar(2008, Calendar.JUNE, 1));
        period5.setEnd(new GregorianCalendar(2010, Calendar.JANUARY, 1));
        period5.setTitle("Ведущий специалист");
        period5.setDescription("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        company5.addPeriod(period5);
        companies.add(company5);

        Company company6 = new Company();
        company6.setName("Enkata");
        company6.setWebsite("http://enkata.com/");
        Period period6 = new Period();
        period6.setStart(new GregorianCalendar(2007, Calendar.MARCH, 1));
        period6.setEnd(new GregorianCalendar(2008, Calendar.JUNE, 1));
        period6.setTitle("Разработчик ПО");
        period6.setDescription("Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        company6.addPeriod(period6);
        companies.add(company6);

        Company company7 = new Company();
        company7.setName("Siemens AG");
        company7.setWebsite("https://www.siemens.com");
        Period period7 = new Period();
        period7.setStart(new GregorianCalendar(2005, Calendar.JANUARY, 1));
        period7.setEnd(new GregorianCalendar(2007, Calendar.FEBRUARY, 1));
        period7.setTitle("Разработчик ПО");
        period7.setDescription("Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        company7.addPeriod(period7);
        companies.add(company7);

        Company company8 = new Company();
        company8.setName("Alcatel");
        company8.setWebsite("http://www.alcatel.ru/");
        Period period8 = new Period();
        period8.setStart(new GregorianCalendar(1997, Calendar.SEPTEMBER, 1));
        period8.setEnd(new GregorianCalendar(2005, Calendar.JANUARY, 1));
        period8.setTitle("Инженер по аппаратному и программному тестированию");
        period8.setDescription("Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        company8.addPeriod(period8);
        companies.add(company8);

        Company company9 = new Company();
        company9.setName("Coursera");
        company9.setWebsite("https://www.coursera.org");
        Period period9 = new Period();
        period9.setStart(new GregorianCalendar(2013, Calendar.MARCH, 1));
        period9.setEnd(new GregorianCalendar(2013, Calendar.MAY, 1));
        period9.setTitle("Functional Programming Principles in Scala' by Martin Odersky");
        company9.addPeriod(period9);
        companies.add(company9);

        Company company10 = new Company();
        company10.setName("Luxoft");
        company10.setWebsite("http://www.luxoft-training.ru");
        Period period10 = new Period();
        period10.setStart(new GregorianCalendar(2011, Calendar.MARCH, 1));
        period10.setEnd(new GregorianCalendar(2011, Calendar.APRIL, 1));
        period10.setTitle("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'");
        company10.addPeriod(period10);
        companies.add(company10);

        Company company11 = new Company();
        company11.setName("Siemens AG");
        company11.setWebsite("http://www.siemens.ru/");
        Period period11 = new Period();
        period11.setStart(new GregorianCalendar(2005, Calendar.JANUARY, 1));
        period11.setEnd(new GregorianCalendar(2005, Calendar.APRIL, 1));
        period11.setTitle("3 месяца обучения мобильным IN сетям (Берлин)");
        company11.addPeriod(period11);
        companies.add(company11);

        Company company12 = new Company();
        company12.setName("Alcatel");
        company12.setWebsite("http://www.alcatel.ru/");
        Period period12 = new Period();
        period12.setStart(new GregorianCalendar(1997, Calendar.SEPTEMBER, 1));
        period12.setEnd(new GregorianCalendar(1998, Calendar.MARCH, 1));
        period12.setTitle("6 месяцев обучения цифровым телефонным сетям (Москва)");
        company12.addPeriod(period12);
        companies.add(company12);

        Company company13 = new Company();
        company13.setName("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");
        company13.setWebsite("https://itmo.ru/");
        Period period13 = new Period();
        period13.setStart(new GregorianCalendar(1993, Calendar.SEPTEMBER, 1));
        period13.setEnd(new GregorianCalendar(1996, Calendar.JULY, 1));
        period13.setTitle("Аспирантура (программист С, С++)");
        company13.addPeriod(period13);
        Period period14 = new Period();
        period14.setStart(new GregorianCalendar(1987, Calendar.SEPTEMBER, 1));
        period14.setEnd(new GregorianCalendar(1993, Calendar.JULY, 1));
        period14.setTitle("Инженер (программист Fortran, C)");
        company13.addPeriod(period14);
        companies.add(company13);

        Company company14 = new Company();
        company14.setName("Заочная физико-техническая школа при МФТИ");
        company14.setWebsite("https://mipt.ru/");
        Period period15 = new Period();
        period15.setStart(new GregorianCalendar(1984, Calendar.SEPTEMBER, 1));
        period15.setEnd(new GregorianCalendar(1987, Calendar.JUNE, 1));
        period15.setTitle("Закончил с отличием");
        company14.addPeriod(period15);
        companies.add(company14);

        resume.setSection(EXPERIENCE, new Section<>(companies));
        System.out.println(resume);

    }
}
