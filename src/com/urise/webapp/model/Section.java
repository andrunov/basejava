package com.urise.webapp.model;

import java.io.Serializable;

public interface Section <V> extends Serializable {

    public V getValue();

    public void setValue(V value);

}
