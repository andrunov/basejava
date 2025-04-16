package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        directory.forEach(this::doDelete);
    }

    @Override
    public int size() {
        return directory.getNameCount();
    }

    @Override
    protected Path searchKey(String uuid) {
        return directory.resolve(uuid);
    }


    @Override
    public void doUpdate(Path key, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(key.toFile().toPath())));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return path.isAbsolute();
    }

    @Override
    public void doSave(Path path, Resume resume) {
        try {
            if (path.toFile().createNewFile()) {
                doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
            } else {
                throw new StorageException("Couldn't create file from path %s ", path.toString());
            }
        } catch (IOException e) {
            throw new StorageException("Couldn't write into path ", path.toString());
        }
        doUpdate(path, resume);
    }

    @Override
    public Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString());
        }
    }

    @Override
    public void doDelete(Path path) {
        if (!path.toFile().delete()) {
            throw new StorageException("Path delete error", path.toString());
        }
    }


    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>();
        for (Path path : Objects.requireNonNull(directory)) {
            result.add(doGet(path));
        }
        result = getAllSorted(result);
        return result;
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

}
