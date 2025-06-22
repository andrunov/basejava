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
            forEach(contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section<?>> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section<?>> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case EXPERIENCE:
                        List<Section> section = (List<Section>) entry.getValue().getValue();
                        dos.writeInt(section.size());
                        forEach(section, (Container) element -> {
                            Company company = (Company) element;
                            dos.writeUTF(company.getName());
                            dos.writeUTF(write(company.getWebsite()));
                            dos.writeInt(company.getPeriods().size());
                            forEach(company.getPeriods(), period -> {
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
                        break;

                    case EDUCATION :
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        section = (List<Section>) entry.getValue().getValue();
                        dos.writeInt(section.size());
                        forEach(section, (Container) element -> dos.writeUTF((String) element));
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
            forWhile(contactSize, resumeToApply -> resumeToApply.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()), resume);
            int sectionsQty = dis.readInt();
            for (int i = 0; i < sectionsQty; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case EXPERIENCE:
                        CompanySection companySection = new CompanySection(new ArrayList<>());
                        int sectionSize = dis.readInt();
                        forWhile(sectionSize, companySectionToApply -> {
                            Company company = new Company();
                            companySectionToApply.getValue().add(company);
                            company.setName(dis.readUTF());
                            company.setWebsite(read(dis.readUTF()));
                            int periodsQty = dis.readInt();
                            forWhile(periodsQty, companyToApply -> {
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
                        resume.setSection(sectionType, companySection);
                        break;

                    case EDUCATION :
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        ListSection section = new ListSection();
                        sectionSize = dis.readInt();
                        List<String> list = new ArrayList<>();
                        forWhile(sectionSize, strings -> list.add(dis.readUTF()), list);
                        section.setValue(list);
                        resume.setSection(sectionType, section);
                        break;

                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = new TextSection();
                        textSection.setValue(dis.readUTF());
                        resume.setSection(sectionType, textSection);
                }
            }
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
    static <T> void forEach(Iterable<T> collection, Container<T> container) throws IOException {
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
    static <T> void forWhile(int counter, Container<T> container, T applier) throws IOException {
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
