package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
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

    public final Resume get( String uuid ) {
        int index = getIndex( uuid );
        if ( index < 0 ) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    public final void update( Resume resume ) {
        int index = getIndex( resume.getUuid() );
        if ( index >= 0 ) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public final Resume[] getAll() {
        return Arrays.copyOf( storage, size );
    }

    public final void clear() {
        Arrays.fill( storage, 0, size, null );
        size = 0;
    }

    //template method
    public final void save( Resume resume ) {
        int index = getIndex( resume.getUuid() );
        if ( size == STORAGE_LIMIT ) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else if ( index >= 0 ) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertResume( index, resume );
        }
        size++;
    }


    //template method
    public final void delete( String uuid ) {
        int index = getIndex( uuid );
        if ( index < 0 ) {
            throw new NotExistStorageException(uuid);
        }
        removeResume( index );
        storage[size] = null;
        size--;
    }

    public abstract void insertResume( int index, Resume resume );

    public abstract void removeResume( int index );

    protected abstract int getIndex( String uuid );
}
