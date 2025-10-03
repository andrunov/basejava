package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Arrays;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Section <V> implements Serializable {

    public abstract V getValue();

    public abstract void setValue(V value);

    public static Section<?> of(SectionType key, String value) {
        if (!SectionType.EXPERIENCE.equals(key)) {
            //TEXT section
            if (SectionType.OBJECTIVE.equals(key) || SectionType.PERSONAL.equals(key)) {
                TextSection textSection = new TextSection();
                textSection.setValue(value);
                return textSection;
                //LIST section
            } else {
                ListSection listSection = new ListSection();
                listSection.setValue(Arrays.asList(value.split("\n")));
                return listSection;
            }
        }
        return null;
    }

}
