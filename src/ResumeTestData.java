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

        List<Experience> experiences = new ArrayList<>();

        Experience experience1 = new Experience("Java Online Projects", Experience.ExperienceType.JOB);
        experience1.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("https://javaops.ru/"));
        Period period1 = new Period();
        period1.setStart(new GregorianCalendar(2013, Calendar.OCTOBER, 1));
        period1.setEnd(Calendar.getInstance());
        experience1.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period1));
        experience1.setSection(Experience.ExperienceSectionType.POSITION, new Section<>("Автор проекта."));
        experience1.setSection(Experience.ExperienceSectionType.DUTIES,
                new Section<>("Создание, организация и проведение Java онлайн проектов и стажировок."));
        experiences.add(experience1);

        Experience experience2 = new Experience("Wrike", Experience.ExperienceType.JOB);
        experience2.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("https://www.wrike.com/"));
        Period period2 = new Period();
        period2.setStart(new GregorianCalendar(2014, Calendar.OCTOBER, 1));
        period2.setEnd(new GregorianCalendar(2016, Calendar.JANUARY, 1));
        experience2.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period2));
        experience2.setSection(Experience.ExperienceSectionType.POSITION, new Section<>("Старший разработчик (backend)"));
        experience2.setSection(Experience.ExperienceSectionType.DUTIES,
                new Section<>("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        experiences.add(experience2);

        Experience experience3 = new Experience("RIT Center", Experience.ExperienceType.JOB);
        Period period3 = new Period();
        period3.setStart(new GregorianCalendar(2012, Calendar.APRIL, 1));
        period3.setEnd(new GregorianCalendar(2014, Calendar.NOVEMBER, 1));
        experience3.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period3));
        experience3.setSection(Experience.ExperienceSectionType.POSITION, new Section<>("Java архитектор"));
        experience3.setSection(Experience.ExperienceSectionType.DUTIES,
                new Section<>("Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        experiences.add(experience3);

        Experience experience4 = new Experience("Luxoft (Deutsche Bank)", Experience.ExperienceType.JOB);
        experience4.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("https://luxoft.ru/"));
        Period period4 = new Period();
        period4.setStart(new GregorianCalendar(2010, Calendar.JANUARY, 1));
        period4.setEnd(new GregorianCalendar(2012, Calendar.APRIL, 1));
        experience4.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period4));
        experience4.setSection(Experience.ExperienceSectionType.POSITION, new Section<>("Ведущий программист"));
        experience4.setSection(Experience.ExperienceSectionType.DUTIES,
                new Section<>("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        experiences.add(experience4);

        Experience experience5 = new Experience("Yota", Experience.ExperienceType.JOB);
        experience5.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("https://www.yota.ru/"));
        Period period5 = new Period();
        period5.setStart(new GregorianCalendar(2008, Calendar.JUNE, 1));
        period5.setEnd(new GregorianCalendar(2010, Calendar.JANUARY, 1));
        experience5.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period5));
        experience5.setSection(Experience.ExperienceSectionType.POSITION, new Section<>("Ведущий специалист"));
        experience5.setSection(Experience.ExperienceSectionType.DUTIES,
                new Section<>("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        experiences.add(experience5);

        Experience experience6 = new Experience("Enkata", Experience.ExperienceType.JOB);
        experience6.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("http://enkata.com/"));
        Period period6 = new Period();
        period6.setStart(new GregorianCalendar(2007, Calendar.MARCH, 1));
        period6.setEnd(new GregorianCalendar(2008, Calendar.JUNE, 1));
        experience6.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period6));
        experience6.setSection(Experience.ExperienceSectionType.POSITION, new Section<>("Разработчик ПО"));
        experience6.setSection(Experience.ExperienceSectionType.DUTIES,
                new Section<>("Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        experiences.add(experience6);

        Experience experience7 = new Experience("Siemens AG", Experience.ExperienceType.JOB);
        experience7.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("https://www.siemens.com"));
        Period period7 = new Period();
        period7.setStart(new GregorianCalendar(2005, Calendar.JANUARY, 1));
        period7.setEnd(new GregorianCalendar(2007, Calendar.FEBRUARY, 1));
        experience7.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period7));
        experience7.setSection(Experience.ExperienceSectionType.POSITION, new Section<>("Разработчик ПО"));
        experience7.setSection(Experience.ExperienceSectionType.DUTIES,
                new Section<>("Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        experiences.add(experience7);

        Experience experience8 = new Experience("Alcatel", Experience.ExperienceType.JOB);
        experience8.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("http://www.alcatel.ru/"));
        Period period8 = new Period();
        period8.setStart(new GregorianCalendar(1997, Calendar.SEPTEMBER, 1));
        period8.setEnd(new GregorianCalendar(2005, Calendar.JANUARY, 1));
        experience8.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period8));
        experience8.setSection(Experience.ExperienceSectionType.POSITION, new Section<>("Инженер по аппаратному и программному тестированию"));
        experience8.setSection(Experience.ExperienceSectionType.POSITION,
                new Section<>("Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        experiences.add(experience8);

        Experience experience9 = new Experience("Coursera", Experience.ExperienceType.EDUCATION);
        experience9.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("https://www.coursera.org"));
        Period period9 = new Period();
        period9.setStart(new GregorianCalendar(2013, Calendar.MARCH, 1));
        period9.setEnd(new GregorianCalendar(2013, Calendar.MAY, 1));
        experience9.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period9));
        experience9.setSection(Experience.ExperienceSectionType.DUTIES, new Section<>("Functional Programming Principles in Scala' by Martin Odersky"));
        experiences.add(experience9);

        Experience experience10 = new Experience("Luxoft", Experience.ExperienceType.EDUCATION);
        experience10.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("http://www.luxoft-training.ru"));
        Period period10 = new Period();
        period10.setStart(new GregorianCalendar(2011, Calendar.MARCH, 1));
        period10.setEnd(new GregorianCalendar(2011, Calendar.APRIL, 1));
        experience10.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period10));
        experience10.setSection(Experience.ExperienceSectionType.DUTIES,
                new Section<>("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'"));
        experiences.add(experience10);

        Experience experience11 = new Experience("Siemens AG", Experience.ExperienceType.EDUCATION);
        experience11.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("http://www.siemens.ru/"));
        Period period11 = new Period();
        period11.setStart(new GregorianCalendar(2005, Calendar.JANUARY, 1));
        period11.setEnd(new GregorianCalendar(2005, Calendar.APRIL, 1));
        experience11.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period11));
        experience11.setSection(Experience.ExperienceSectionType.DUTIES, new Section<>("3 месяца обучения мобильным IN сетям (Берлин)"));
        experiences.add(experience11);

        Experience experience12 = new Experience("Alcatel", Experience.ExperienceType.EDUCATION);
        experience12.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("http://www.alcatel.ru/"));
        Period period12 = new Period();
        period12.setStart(new GregorianCalendar(1997, Calendar.SEPTEMBER, 1));
        period12.setEnd(new GregorianCalendar(1998, Calendar.MARCH, 1));
        experience12.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period12));
        experience12.setSection(Experience.ExperienceSectionType.DUTIES, new Section<>("6 месяцев обучения цифровым телефонным сетям (Москва)"));
        experiences.add(experience12);

        Experience experience13 = new Experience("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                Experience.ExperienceType.EDUCATION);
        experience13.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("https://itmo.ru/"));
        Period period13 = new Period();
        period13.setStart(new GregorianCalendar(1993, Calendar.SEPTEMBER, 1));
        period13.setEnd(new GregorianCalendar(1996, Calendar.JULY, 1));
        experience13.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period13));
        experience13.setSection(Experience.ExperienceSectionType.DUTIES, new Section<>("Аспирантура (программист С, С++)"));
        experiences.add(experience13);

        Experience experience14 = new Experience("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                Experience.ExperienceType.EDUCATION);
        experience14.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("https://itmo.ru/"));
        Period period14 = new Period();
        period14.setStart(new GregorianCalendar(1987, Calendar.SEPTEMBER, 1));
        period14.setEnd(new GregorianCalendar(1993, Calendar.JULY, 1));
        experience14.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period14));
        experience14.setSection(Experience.ExperienceSectionType.DUTIES, new Section<>("Инженер (программист Fortran, C)"));
        experiences.add(experience14);

        Experience experience15 = new Experience("Заочная физико-техническая школа при МФТИ", Experience.ExperienceType.EDUCATION);
        experience15.setSection(Experience.ExperienceSectionType.WEBSITE, new Section<>("https://mipt.ru/"));
        Period period15 = new Period();
        period15.setStart(new GregorianCalendar(1984, Calendar.SEPTEMBER, 1));
        period15.setEnd(new GregorianCalendar(1987, Calendar.JUNE, 1));
        experience15.setSection(Experience.ExperienceSectionType.PERIOD, new Section<>(period15));
        experience15.setSection(Experience.ExperienceSectionType.DUTIES, new Section<>("Закончил с отличием"));
        experiences.add(experience15);

        resume.setSection(EXPERIENCE, new Section<>(experiences));
        System.out.println(resume);

    }
}
