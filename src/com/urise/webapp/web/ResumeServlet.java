package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        final boolean isCreate = (uuid == null || uuid.length() == 0);

        Resume r;
        if (isCreate) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type.name()) {
                case "PERSONAL":
                case "OBJECTIVE": {
                    String value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        TextSection section = new TextSection();
                        section.setValue(value);
                        r.setSection(type, section);
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                }
                case "ACHIEVEMENT":
                case "QUALIFICATIONS": {
                    String[] value = request.getParameterValues(type.name() + "[]");
                    if (value != null && value.length != 0) {
                        ListSection section = new ListSection();
                        section.setValue(Arrays.asList(value));
                        r.setSection(type, section);
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                }
                case "EXPERIENCE":
                case "EDUCATION":
                    List<Company> companies = parseCompanies(request, type.name());
                    if (companies.size() != 0) {
                        CompanySection section = new CompanySection();
                        section.setValue(companies);
                        r.setSection(type, section);
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
            }
        }

        if (isCreate) {
            storage.save(r);
        } else {
            storage.update(r);
        }

        response.sendRedirect("resume");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "add":
                r = Resume.EMPTY;
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section<?> section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            CompanySection companySection = (CompanySection) section;
                            if (companySection == null) {
                                companySection = new CompanySection();
                                List<Period> emptyPeriods = new ArrayList<>();
                                emptyPeriods.add(Period.EMPTY);
                                Company.EMPTY.setPeriods(emptyPeriods);
                                List<Company> emptyCompanies = new ArrayList<>();
                                emptyCompanies.add(Company.EMPTY);
                                companySection.setValue(emptyCompanies);
                            }
                            break;
                    }
                    r.setSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }


    private List<Company> parseCompanies(HttpServletRequest request, String typeName) {
        List<Company> companies = new ArrayList<>();
        int index = 0;

        while (true) {
            // Проверяем, есть ли параметры для текущего индекса
            String companyName = request.getParameter(typeName + "[" + index + "].name");
            if (companyName == null) break;

            Company company = new Company();
            company.setName(companyName);
            company.setWebsite(request.getParameter(typeName + "[" + index + "].website"));

            List<Period> periods = parsePeriods(request, typeName, index);
            company.setPeriods(periods);

            companies.add(company);
            index++;
        }

        return companies;
    }

    private List<Period> parsePeriods(HttpServletRequest request, String typeName, int companyIndex) {
        List<Period> periods = new ArrayList<>();
        int periodIndex = 0;

        while (true) {
            String periodStart = request.getParameter(
                    typeName + "[" + companyIndex + "].periods[" + periodIndex + "].start");
            if (periodStart == null) break;

            Period period = new Period();

            // Обрабатываем даты (нужна проверка на null)
            if (!periodStart.isEmpty()) {
                period.setStart(parseCalendarFromString(periodStart));
            }

            String periodEnd = request.getParameter(
                    typeName + "[" + companyIndex + "].periods[" + periodIndex + "].end");
            if (periodEnd != null && !periodEnd.isEmpty()) {
                period.setEnd(parseCalendarFromString(periodEnd));
            }

            period.setTitle(request.getParameter(
                    typeName + "[" + companyIndex + "].periods[" + periodIndex + "].title"));
            period.setDescription(request.getParameter(
                    typeName + "[" + companyIndex + "].periods[" + periodIndex + "].description"));

            periods.add(period);
            periodIndex++;
        }

        return periods;
    }

    private Calendar parseCalendarFromString(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString, e);
        }
    }
}
