package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Section <V> implements Serializable {

    public abstract V getValue();

    public abstract void setValue(V value);

}
