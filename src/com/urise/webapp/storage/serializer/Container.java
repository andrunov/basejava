package com.urise.webapp.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface Container<T> {

    void apply(T t) throws IOException;
}
