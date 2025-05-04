package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListSection extends Section<List<String>> {

    private static final long serialVersionUID = 1L;

    private List<String> value;

    public ListSection() {
    }

    public ListSection(List<String> value) {
        this.value = value;
    }

    @Override
    public List<String> getValue() {
        return value;
    }

    @Override
    public void setValue(List<String> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object val : value) {
            sb.append(String.format("- %s\n", val));
        }
        return sb.toString();
    }
}
