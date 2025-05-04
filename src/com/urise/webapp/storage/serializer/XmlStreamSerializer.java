package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.AbstractStorage;
import com.urise.webapp.storage.FileStorage;
import com.urise.webapp.storage.PathStorage;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {

    private static final String STORAGE_PATH = "E:\\PROJECTS\\Learninig\\basejava\\file_storage";

    private XmlParser xmlParser;

    private AbstractStorage<?> storage;

    public void setStrategy(ObjectStreamSerializer.Strategy strategy) {
        switch (strategy) {
            case FILE:
                File file = new File(STORAGE_PATH);
                this.storage = new FileStorage(file, this);
                return;
            case PATH:
                this.storage = new PathStorage(STORAGE_PATH, this);
        }
    }

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class, Company.class, CompanySection.class, TextSection.class, ListSection.class, Period.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }

    public AbstractStorage<?> getStorage() {
        return storage;
    }

    public enum Strategy {
        FILE,
        PATH
    }
}
