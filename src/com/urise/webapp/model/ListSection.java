package com.urise.webapp.model;

import java.util.List;

public class ListSection implements Section<List<String>> {

    private List<String> value;

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
