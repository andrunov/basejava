package com.urise.webapp.model;

import java.io.Serializable;

public enum SectionType implements Serializable {
    PERSONAL("Личные качества", 1),
    OBJECTIVE("Позиция", 2),
    ACHIEVEMENT("Достижения", 3),
    QUALIFICATIONS("Квалификация", 4),
    EXPERIENCE("Опыт работы", 5),
    EDUCATION("Образование", 6);

    private static final long serialVersionUID = 1L;
    private final String title;
    private final int order;
    SectionType(String title, int order) {
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
