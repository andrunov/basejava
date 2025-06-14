package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private final Path directory;

    private final StreamSerializer streamSerializer;

    public PathStorage(String dir, StreamSerializer streamSerializer) {
        directory = Paths.get(dir);
        this.streamSerializer = streamSerializer;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> stream = Files.list(directory)) {
            stream.forEach(this::doDelete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> stream = Files.list(directory))  {
            return (int) stream.count();
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
            streamSerializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(key.toFile().toPath())));
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
                streamSerializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
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
            return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
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
        final List<Resume> result = new ArrayList<>();
        try (Stream<Path> stream = Files.list(directory)) {
            stream.forEach(path -> result.add(doGet(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getAllSorted(result);
    }
}
