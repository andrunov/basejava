package com.urise.webapp.util;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.serializer.Container;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataSectionAdapter {

    private static final CalendarAdapter calendarAdapter = new CalendarAdapter();

    public static void write(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            forEach(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section<?>> sections = r.getSections();
            dos.writeInt(sections.size());
            forEach(dos, sections.entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        String sectionStr = (String) entry.getValue().getValue();
                        dos.writeUTF(sectionStr);
                        break;

                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        List<String> valueList = (List<String>) entry.getValue().getValue();
                        dos.writeInt(valueList.size());
                        forEach(dos, valueList, dos::writeUTF);
                        break;

                    case EXPERIENCE:
                    case EDUCATION :
                        List<Company> companyList = (List<Company>) entry.getValue().getValue();
                        dos.writeInt(companyList.size());
                        forEach(dos, companyList, company -> {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(write(company.getWebsite()));
                            dos.writeInt(company.getPeriods().size());
                            forEach(dos, company.getPeriods(), period -> {
                                try {
                                    dos.writeUTF(calendarAdapter.marshal(period.getStart()));
                                    dos.writeUTF(calendarAdapter.marshal(period.getEnd()));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                dos.writeUTF(write(period.getTitle()));
                                dos.writeUTF(write(period.getDescription()));
                            });
                        });
                }
            });
        }
    }

    public static Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactSize = dis.readInt();
            forWhile(dis, contactSize, resumeToApply -> resumeToApply.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()), resume);
            int sectionsQty = dis.readInt();
            SectionType sectionType = null;
            forWhile(dis, sectionsQty, sectionType1 -> {
                sectionType1 = SectionType.valueOf(dis.readUTF());
                switch (sectionType1) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = new TextSection();
                        textSection.setValue(dis.readUTF());
                        resume.setSection(sectionType1, textSection);
                        break;

                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        ListSection section = new ListSection();
                        int sectionSize = dis.readInt();
                        List<String> list = new ArrayList<>();
                        forWhile(dis, sectionSize, strings -> list.add(dis.readUTF()), list);
                        section.setValue(list);
                        resume.setSection(sectionType1, section);
                        break;

                    case EXPERIENCE:
                    case EDUCATION :
                        CompanySection companySection = new CompanySection(new ArrayList<>());
                        sectionSize = dis.readInt();
                        forWhile(dis, sectionSize, companySectionToApply -> {
                            Company company = new Company();
                            companySectionToApply.getValue().add(company);
                            company.setName(dis.readUTF());
                            company.setWebsite(read(dis.readUTF()));
                            int periodsQty = dis.readInt();
                            forWhile(dis, periodsQty, companyToApply -> {
                                Period period = new Period();
                                companyToApply.addPeriod(period);
                                try {
                                    period.setStart(calendarAdapter.unmarshal(dis.readUTF()));
                                    period.setEnd(calendarAdapter.unmarshal(dis.readUTF()));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                period.setTitle(read(dis.readUTF()));
                                period.setDescription(read(dis.readUTF()));
                            }, company);
                        }, companySection);
                        resume.setSection(sectionType1, companySection);
                        break;
                }
            }, sectionType);
            return resume;
        }
    }

    /**
     * Функция для реализации различных вариантов записи коллекций
     * @param collection любая коллекция
     * @param container - класс-обертка
     * @param <T> - оборачиваемый класс для записи
     * @throws IOException - выбрасываем для дальнейшей обработки
     */
    static <T> void forEach(DataOutputStream dos, Iterable<T> collection, Container<T> container) throws IOException {
        Objects.requireNonNull(collection);
        for (T element : collection) {
            Objects.requireNonNull(element);
            container.apply(element);
        }
    }

    /**
     * Функция для реализации различных вариантов чтения коллекций
     * @param counter счетчик по которому итерируемся
     * @param container - класс-обертка
     * @param applier - принимающий прочитываемые данные класс
     * @param <T> - оборачиваемый класс для чтения
     * @throws IOException - выбрасываем для дальнейшей обработки
     */
    static <T> void forWhile(DataInputStream dis, int counter, Container<T> container, T applier) throws IOException {
        for (int j = 0; j < counter; j++) {
           container.apply(applier);
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
