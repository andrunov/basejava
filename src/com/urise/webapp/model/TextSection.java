package com.urise.webapp.model;

public class TextSection implements Section<String> {

    private static final long serialVersionUID = 1L;

    private String value;

    public TextSection(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("- %s\n", value);
    }
}
