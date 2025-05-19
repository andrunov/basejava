package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.CalendarAdapter;
import com.urise.webapp.util.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    private final CalendarAdapter calendarAdapter = new CalendarAdapter();

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
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
                if (sectionType == SectionType.EXPERIENCE) {
                    List<?> section = (List<?>) entry.getValue().getValue();
                    dos.writeInt(section.size());
                    for (Object element : section) {

                        //System.out.println("### " + element);
                        Company company = (Company) element;
                        //System.out.println(company.getName());
                        //System.out.println(company.getWebsite());
                        dos.writeUTF(company.getName());
                        dos.writeUTF(StringUtil.write(company.getWebsite()));

                        dos.writeInt(company.getPeriods().size());
                        for (Period period : company.getPeriods()) {
                            try {
                                //System.out.println(calendarAdapter.marshal(period.getStart()));
                                //System.out.println(calendarAdapter.marshal(period.getEnd()));

                                dos.writeUTF(calendarAdapter.marshal(period.getStart()));
                                dos.writeUTF(calendarAdapter.marshal(period.getEnd()));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            //System.out.println(period.getTitle());
                            //System.out.println(period.getDescription());
                            dos.writeUTF(StringUtil.write(period.getTitle()));
                            dos.writeUTF(StringUtil.write(period.getDescription()));
                        }
                    }
                } else if (sectionType == SectionType.EDUCATION || sectionType == SectionType.QUALIFICATIONS || sectionType == SectionType.ACHIEVEMENT) {
                    List<?> section = (List<?>) entry.getValue().getValue();
                    dos.writeInt(section.size());
                    for (Object string : section) {
                        dos.writeUTF((String) string);
                    }
                } else if (sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE) {
                    String section = (String) entry.getValue().getValue();
                    dos.writeUTF(section);
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
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
                String sectionstr = dis.readUTF();
                System.out.println("### " + sectionstr);
                SectionType sectionType = SectionType.valueOf(sectionstr);
                if (sectionType == SectionType.EXPERIENCE) {
                    CompanySection section = new CompanySection(new ArrayList<>());
                    int sectionSize = dis.readInt();
                    for (int j = 0; j < sectionSize; j++) {
                        Company company = new Company();
                        section.getValue().add(company);
                        company.setName(dis.readUTF());
                        company.setWebsite(StringUtil.read(dis.readUTF()));
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
                            period.setTitle(StringUtil.read(dis.readUTF()));
                            period.setDescription(StringUtil.read(dis.readUTF()));
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
}
