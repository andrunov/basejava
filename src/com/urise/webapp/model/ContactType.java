package com.urise.webapp.model;

import java.io.Serializable;

public enum ContactType implements Serializable {
    PHONE("Телефон",1),
    SKYPE("Skype",2),
    EMAIL("Почта",3),
    LINKEDIN("Профиль LinkedIn",4),
    GITHUB("Профиль GitHub",5),
    STACKOVERFLOW("Профиль Stackoverflow",6),
    HOMEPAGE("Домашняя страница", 7);

    private static final long serialVersionUID = 1L;
    private final String title;
    private final int order;

    ContactType(String title, int order) {
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
