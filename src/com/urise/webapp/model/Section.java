package com.urise.webapp.model;

import java.util.Collection;
import java.util.List;

public class Section <V> {

    private V value;

    public Section(V value) {
        this.value = value;
    }

    public Section() {
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (value instanceof Collection<?>) {
            for (Object val : (List<?>) value) {
                sb.append(String.format("- %s\n", val));
            }
        } else {
            sb.append(String.format("- %s\n", value));
        }
        return sb.toString();
    }
}
