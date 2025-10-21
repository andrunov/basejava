package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class TextSection extends Section<String> {

    private static final long serialVersionUID = 1L;

    public static final TextSection EMPTY = new TextSection("");


    private String value;

    public TextSection() {
    }

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
