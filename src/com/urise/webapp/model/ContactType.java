package com.urise.webapp.model;

import java.io.Serializable;

public enum ContactType implements Serializable {
    PHONE("Телефон",1),
    SKYPE("Skype",2) {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("skype:", value);
        }
    },
    EMAIL("Почта",3) {
        @Override
        public String toHtml0(String value) {
            return toLink("mailto:", value);
        }
    },
    LINKEDIN("Профиль LinkedIn",4) {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("Профиль GitHub",5) {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow",6) {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOMEPAGE("Домашняя страница", 7) {
        @Override
        public String toHtml0(String value) {
            return toLink(value);
        }
    };

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

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toEmail(String email) {
        return String.format("<a href='mailto:%s' title='Написать письмо на %s'>%s</a>", email, email, email);
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}
