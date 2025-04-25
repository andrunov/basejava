package com.urise.webapp.storage.strategy;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractStorage;
import com.urise.webapp.storage.FileStorage;
import com.urise.webapp.storage.PathStorage;

import java.io.*;

public class ObjectStreamStorage {

    private static final String STORAGE_PATH = "E:\\PROJECTS\\Learninig\\basejava\\file_storage";

    private AbstractStorage<?> storage;

    public void setStrategy(Strategy strategy) {
        switch (strategy) {
            case FILE:
                File file = new File(STORAGE_PATH);
                this.storage = new FileStorage(file, this);
                return;
            case PATH:
                this.storage = new PathStorage(STORAGE_PATH, this);
        }
    }


    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
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
