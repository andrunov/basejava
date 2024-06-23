package com.urise.webapp.storage;

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
            System.out.printf( "Resume %s not exist", uuid );
            return null;
        }
        return storage[index];
    }

    public final void update( Resume resume ) {
        int index = getIndex( resume.getUuid() );
        if ( index >= 0 ) {
            storage[index] = resume;
        } else {
            System.out.printf( "Element with ID=%s not present, updating impossible%n", resume.getUuid() );
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
            System.out.println( "Storage is full" );
        } else if ( index >= 0 ) {
            System.out.printf( "Element with ID=%s is already present, please use update method%n", resume.getUuid() );
        } else {
            insertResume( index, resume );
        }
        size++;
    }


    //template method
    public final void delete( String uuid ) {
        int index = getIndex( uuid );
        if ( index < 0 ) {
            System.out.printf( "Element with ID=%s not present%n", uuid );
            return;
        }
        removeResume( index );
        storage[size] = null;
        size--;
    }

    public abstract void insertResume( int index, Resume resume );

    public abstract void removeResume( int index );

    protected abstract int getIndex( String uuid );
}
