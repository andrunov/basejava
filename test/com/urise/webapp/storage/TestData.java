package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.urise.webapp.model.SectionType.*;

public class TestData {
    //static final String STORAGE_PATH = Config.get().getStorageDir();
    static final File STORAGE_DIR = Config.get().getStorageDir();
    static final String UUID_01 = "uuid1";
    static final String FIO_01 = "First Middle Last 1";
    static final String UUID_02 = "uuid2";
    static final String FIO_02 = "First Middle Last 2";

    static final String UUID_03 = "uuid3";
    static final String FIO_03 = "First Middle Last 3";

    static final String UUID_04 = "uuid4";
    static final String FIO_04 = "First Middle Last 4";

    static final String UUID_05 = "uuid5";
    static final String FIO_05 = "First Middle Last 5";

    static final Resume RESUME_01;
    static final Resume RESUME_02;
    static final Resume RESUME_03;
    static final Resume RESUME_04;
    static final Resume RESUME_05;

    static {
        Company company1 = new Company();
        company1.setName("Company 1");
        company1.setWebsite("https://somecompany1.ru/");
        Period period1 = new Period();
        period1.setStart(DateUtil.of(2015, Calendar.OCTOBER));
        period1.setEnd(DateUtil.of(2020, Calendar.MAY));
        period1.setTitle("Title 1");
        period1.setDescription("Description 1");
        company1.addPeriod(period1);

        Company company2 = new Company();
        company2.setName("Company 2");
        company2.setWebsite("https://somecompany2.ru/");
        Period period2 = new Period();
        period2.setStart(DateUtil.of(2012, Calendar.OCTOBER));
        period2.setEnd(DateUtil.of(2017, Calendar.JANUARY));
        period2.setTitle("Title 2");
        period1.setDescription("Description 2");
        company2.addPeriod(period2);

        Company company3 = new Company();
        company3.setName("Company 3");
        company3.setWebsite("https://somecompany3.ru/");
        Period period3 = new Period();
        period3.setStart(DateUtil.of(2012, Calendar.APRIL));
        period3.setEnd(DateUtil.of(2014, Calendar.NOVEMBER));
        period3.setTitle("Title 3");
        period3.setDescription("Description 3");
        company3.addPeriod(period3);

        List<Company> companies1 = new ArrayList<>();
        List<Company> companies2 = new ArrayList<>();
        List<Company> companies3 = new ArrayList<>();

        companies1.add(company1);
        companies1.add(company2);

        companies2.add(company2);
        companies2.add(company3);

        companies3.add(company1);
        companies3.add(company3);

        RESUME_01 = new Resume(UUID_01, FIO_01);
        RESUME_01.setContact(ContactType.PHONE, "+7(111) 111-1111");
        RESUME_01.setContact(ContactType.SKYPE, "skype:skype1");
        RESUME_01.setContact(ContactType.EMAIL, "some1@yandex.ru");
        RESUME_01.setSection(SectionType.OBJECTIVE, new TextSection("Разработчик 1"));
        RESUME_01.setSection(PERSONAL, new TextSection("Some personal 1"));
        List<String> achievements1 = new ArrayList<>();
        achievements1.add("Достижение 1");
        achievements1.add("Достижение 2");
        achievements1.add("Достижение 3");
        RESUME_01.setSection(ACHIEVEMENT, new ListSection(achievements1));
        List<String> qualifications1 = new ArrayList<>();
        qualifications1.add("Квалификация 1");
        qualifications1.add("Квалификация 2");
        qualifications1.add("Квалификация 3");
        RESUME_01.setSection(QUALIFICATIONS, new ListSection(qualifications1));
        RESUME_01.setSection(EXPERIENCE, new CompanySection(companies1));

        RESUME_02 = new Resume(UUID_02, FIO_02);
        RESUME_02.setContact(ContactType.PHONE, "+7(222) 222-2222");
        RESUME_02.setContact(ContactType.SKYPE, "skype:skype2");
        RESUME_02.setContact(ContactType.EMAIL, "some2@yandex.ru");
        RESUME_02.setSection(SectionType.OBJECTIVE, new TextSection("Разработчик 2"));
        RESUME_02.setSection(PERSONAL, new TextSection("Some personal 2"));
        List<String> achievements2 = new ArrayList<>();
        achievements2.add("Достижение 123");
        achievements2.add("Достижение 223");
        achievements2.add("Достижение 312");
        RESUME_02.setSection(ACHIEVEMENT, new ListSection(achievements2));
        List<String> qualifications2 = new ArrayList<>();
        qualifications2.add("Квалификация 145");
        qualifications2.add("Квалификация 221");
        qualifications2.add("Квалификация 378");
        RESUME_02.setSection(QUALIFICATIONS, new ListSection(qualifications2));
        RESUME_02.setSection(EXPERIENCE, new CompanySection(companies2));

        RESUME_03 = new Resume(UUID_03, FIO_03);
        RESUME_03.setContact(ContactType.PHONE, "+7(333) 333-3333");
        RESUME_03.setContact(ContactType.SKYPE, "skype:skype3");
        RESUME_03.setContact(ContactType.EMAIL, "some3@yandex.ru");
        RESUME_03.setSection(SectionType.OBJECTIVE, new TextSection("Разработчик 3"));
        RESUME_03.setSection(PERSONAL, new TextSection("Some personal 3"));
        List<String> achievements3 = new ArrayList<>();
        achievements3.add("Достижение 4561");
        achievements3.add("Достижение 4562");
        achievements3.add("Достижение 4563");
        RESUME_03.setSection(ACHIEVEMENT, new ListSection(achievements3));
        List<String> qualifications3= new ArrayList<>();
        qualifications3.add("Квалификация 1752");
        qualifications3.add("Квалификация 2452");
        qualifications3.add("Квалификация 34525");
        RESUME_03.setSection(QUALIFICATIONS, new ListSection(qualifications3));
        RESUME_03.setSection(EXPERIENCE, new CompanySection(companies3));

        RESUME_04 = new Resume(UUID_04, FIO_04);
        RESUME_04.setContact(ContactType.PHONE, "+7(444) 444-4444");
        RESUME_04.setContact(ContactType.SKYPE, "skype:skype4");
        RESUME_04.setContact(ContactType.EMAIL, "some4@yandex.ru");
        RESUME_04.setSection(SectionType.OBJECTIVE, new TextSection("Разработчик 4"));
        RESUME_04.setSection(PERSONAL, new TextSection("Some personal 4"));
        List<String> achievements4 = new ArrayList<>();
        achievements4.add("Достижение 1758");
        achievements4.add("Достижение 245");
        achievements4.add("Достижение 345");
        RESUME_04.setSection(ACHIEVEMENT, new ListSection(achievements4));
        List<String> qualifications4 = new ArrayList<>();
        qualifications4.add("Квалификация 145");
        qualifications4.add("Квалификация 452");
        qualifications4.add("Квалификация 453");
        RESUME_04.setSection(QUALIFICATIONS, new ListSection(qualifications4));
        RESUME_04.setSection(EXPERIENCE, new CompanySection(companies1));

        RESUME_05 = new Resume(UUID_05, FIO_05);
        RESUME_05.setContact(ContactType.PHONE, "+7(555) 555-5555");
        RESUME_05.setContact(ContactType.SKYPE, "skype:skype5");
        RESUME_05.setContact(ContactType.EMAIL, "some5@yandex.ru");
        RESUME_05.setSection(SectionType.OBJECTIVE, new TextSection("Разработчик 5"));
        RESUME_05.setSection(PERSONAL, new TextSection("Some personal 5"));
        RESUME_05.setSection(PERSONAL, new TextSection("Some personal 1"));
        List<String> achievements5 = new ArrayList<>();
        achievements5.add("Достижение 178");
        achievements5.add("Достижение 245");
        achievements5.add("Достижение 378");
        RESUME_05.setSection(ACHIEVEMENT, new ListSection(achievements5));
        List<String> qualifications5 = new ArrayList<>();
        qualifications5.add("Квалификация 78581");
        qualifications5.add("Квалификация 278");
        qualifications5.add("Квалификация 386");
        RESUME_05.setSection(QUALIFICATIONS, new ListSection(qualifications5));
        RESUME_05.setSection(EXPERIENCE, new CompanySection(companies2));


    }
}
