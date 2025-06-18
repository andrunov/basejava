package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSectionAdapter {

    private static final CalendarAdapter calendarAdapter = new CalendarAdapter();

    public static void write(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section<?>> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section<?>> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                List<Section> section = null;
                switch (sectionType) {
                    case EXPERIENCE:
                        section = (List<Section>) entry.getValue().getValue();
                        dos.writeInt(section.size());
                        for (Object element : section) {
                            Company company = (Company) element;
                            dos.writeUTF(company.getName());
                            dos.writeUTF(write(company.getWebsite()));
                            dos.writeInt(company.getPeriods().size());
                            for (Period period : company.getPeriods()) {
                                try {
                                    dos.writeUTF(calendarAdapter.marshal(period.getStart()));
                                    dos.writeUTF(calendarAdapter.marshal(period.getEnd()));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                dos.writeUTF(write(period.getTitle()));
                                dos.writeUTF(write(period.getDescription()));
                            }
                        }
                        break;

                    case EDUCATION :
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        section = (List<Section>) entry.getValue().getValue();
                        dos.writeInt(section.size());
                        for (Object string : section) {
                            dos.writeUTF((String) string);
                        }
                        break;

                    case PERSONAL:
                    case OBJECTIVE:
                        String sectionStr = (String) entry.getValue().getValue();
                        dos.writeUTF(sectionStr);

                }
            }
        }
    }

    public static Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactSize = dis.readInt();
            for (int i = 0; i < contactSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsQty = dis.readInt();
            for (int i = 0; i < sectionsQty; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                if (sectionType == SectionType.EXPERIENCE) {
                    CompanySection section = new CompanySection(new ArrayList<>());
                    int sectionSize = dis.readInt();
                    for (int j = 0; j < sectionSize; j++) {
                        Company company = new Company();
                        section.getValue().add(company);
                        company.setName(dis.readUTF());
                        company.setWebsite(read(dis.readUTF()));
                        int periodsQty = dis.readInt();
                        for (int n = 0; n < periodsQty; n++) {
                            Period period = new Period();
                            company.addPeriod(period);
                            try {
                                period.setStart(calendarAdapter.unmarshal(dis.readUTF()));
                                period.setEnd(calendarAdapter.unmarshal(dis.readUTF()));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            period.setTitle(read(dis.readUTF()));
                            period.setDescription(read(dis.readUTF()));
                        }
                    }
                    resume.setSection(sectionType, section);

                } else if (sectionType == SectionType.EDUCATION || sectionType == SectionType.QUALIFICATIONS || sectionType == SectionType.ACHIEVEMENT) {
                    ListSection section = new ListSection();
                    int sectionSize = dis.readInt();
                    List<String> list = new ArrayList<>();
                    for (int j = 0; j < sectionSize; j++) {
                        list.add(dis.readUTF());
                    }
                    section.setValue(list);
                    resume.setSection(sectionType, section);

                } else if (sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE) {
                    TextSection section = new TextSection();
                    section.setValue(dis.readUTF());
                    resume.setSection(sectionType, section);
                }
            }
            return resume;
        }
    }


    /**
     * Используется для сериализации где недопуcтимо передавать пустую строку
     * возвращает строку "null" если строка равна null
     * иначе возвращает строку без изменений
     * @param string - входная строка
     * @return - возвращаемое значение
     */
    public static String write(String string) {
        return string == null ? "null" : string;
    }

    /**
     * Используется для десериализации где необхрдимо явно указать пустое значение
     * возвращает  null если строка равна "null"
     * иначе возвращает строку без изменений
     * @param string - входная строка
     * @return - возвращаемое значение
     */
    public static String read(String string) {
        return string.equals("null") ? null : string;
    }
}
