package com.urise.webapp.model;

import java.util.*;

public class Experience {

    private String name;

    private ExperienceType experienceType;
    private final Map<ExperienceSectionType, Section<?>> sections;

    public Experience(String name, ExperienceType experienceType) {
        this.name = name;
        this.experienceType = experienceType;
        sections = new EnumMap<>(ExperienceSectionType.class);
    }

    public Experience() {
        sections = new EnumMap<>(ExperienceSectionType.class);
    }

    public void setSection(ExperienceSectionType key, Section<?> value) {
        sections.put(key, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExperienceType getExperienceType() {
        return experienceType;
    }

    public void setExperienceType(ExperienceType experienceType) {
        this.experienceType = experienceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience experience = (Experience) o;
        return Objects.equals(name, experience.name) && Objects.equals(sections, experience.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sections);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Компания       - %s\n", name));
        sb.append(String.format("  Тип занятости  - %s\n", experienceType.title));
        for (Map.Entry<ExperienceSectionType, Section<?>> entry : sections.entrySet()) {
            sb.append(String.format("  %-12s   %s",entry.getKey().getTitle(), entry.getValue()));
        }
        return sb.toString();
    }

    public enum ExperienceType {

        EDUCATION("Обучение",1),
        JOB("Работа по найму",2);

        private final String title;
        private final int order;

        ExperienceType(String title, int order) {
            this.title = title;
            this.order = order;
        }

        public String getTitle() {
            return title;
        }

        public int getOrder() {
            return order;
        }
    }

    public enum ExperienceSectionType {

        NAME("Компания",1),
        WEBSITE("Сайт",2),
        PERIOD("Период",3),
        POSITION("Позиция",4),
        DUTIES("Обязанности",5);

        private final String title;
        private final int order;

        ExperienceSectionType(String title, int order) {
            this.title = title;
            this.order = order;
        }

        public String getTitle() {
            return title;
        }

        public int getOrder() {
            return order;
        }
    }
}
