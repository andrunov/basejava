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
    public Integer getKeyForSave(Resume resume) {
        if ( size == STORAGE_LIMIT ) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        return getNotExistingSearchKey( resume.getUuid() );
    }

    @Override
    public void doSave(Integer index, Resume resume) {
        insertResume( index, resume );
        size++;
    }

    public final void delete(String uuid ) {
        int index = getExistingSearchKey( uuid );
        removeResume(index);
        storage[size] = null;
        size--;
    }

    public final void clear() {
        Arrays.fill( storage, 0, size, null );
        size = 0;
    }

    @Override
    public final void updateResume(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    public final Resume getResume(Integer index) {
        return storage[index];
    }

}
