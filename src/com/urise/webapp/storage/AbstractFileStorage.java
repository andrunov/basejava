package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;
    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        Iterator<File> iterator = Arrays.stream(Objects.requireNonNull(directory.listFiles())).iterator();
        while (iterator.hasNext()) {
            File file = iterator.next();
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.list()).length;
    }

    @Override
    protected File searchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void doUpdate(File file, Resume r) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void doSave(File file, Resume r) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;
    protected abstract void doRead(File file) throws IOException;


    @Override
    public Resume doGet(File file) {
        Resume resume = null;
        if (isExist(file)) {
            try {
                doRead(file);
            } catch (IOException e) {
                throw new StorageException("IO error", file.getName(), e);
            }
        }
        return resume;
    }

    @Override
    public void doDelete(File file) {
        if (isExist(file) && file.isFile()) {
            file.delete();
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            result.add(doGet(file));
        }
        result = getAllSorted(result);
        return result;
    }
}