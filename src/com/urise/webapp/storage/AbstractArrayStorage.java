package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage;

    protected int size;

    public AbstractArrayStorage() {
        this.storage = new Resume[STORAGE_LIMIT];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    public final Resume[] getAll() {
        return Arrays.copyOf( storage, size );
    }

    @Override
    public void doSave(Integer key, Resume resume) {
        if ( size == STORAGE_LIMIT ) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertResume(key, resume );
        size++;
    }

    public final void delete(String uuid, String fullName ) {
        int index = getExistingSearchKey( uuid, fullName );
        doDelete(index);
        storage[size] = null;
        size--;
    }

    public final void clear() {
        Arrays.fill( storage, 0, size, null );
        size = 0;
    }

    @Override
    public final void doUpdate(Integer key, Resume resume) {
        storage[key] = resume;
    }

    @Override
    public final Resume doGet(Integer key) {
        return storage[key];
    }

    public abstract void insertResume( Integer index, Resume resume );


}
