package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

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
        try (Stream<Path> list = Files.list(directory)) {
            Iterator<Path> iterator = list.iterator();
            while (iterator.hasNext()) {
                Path path = iterator.next();
                doDelete(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> list = Files.list(directory))  {
            return (int) list.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Path searchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
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
        return Files.exists(path);
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
        try {
            if (!Files.deleteIfExists(path)) {
                throw new StorageException("Path delete error", path.toString());
            }
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.toString());
        }
    }


    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>();
        try (Stream<Path> list = Files.list(directory)) {
           Iterator<Path> iterator = list.iterator();
           while (iterator.hasNext()) {
               result.add(doGet(iterator.next()));
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result = getAllSorted(result);
        return result;
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

}
