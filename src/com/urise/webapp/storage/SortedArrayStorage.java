package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array storage for Resumes with auto sorting
 */
public class SortedArrayStorage extends AbstractArrayStorage{

    public SortedArrayStorage() {
        super();
    }

    //for new resume only
    @Override
    public <T> void insertResume(T index, Resume r) {
        int insertionIndex = -(Integer) index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        storage[insertionIndex] = r;
    }


    @Override
    public <T> void removeResume(T index) {
        System.arraycopy(storage, (Integer)index + 1, storage, (Integer)index, size - (Integer)index - 1);
    }


    @Override
    protected Integer searchKey(String uuid) {
        Resume searchElement = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchElement);
    }

    @Override
    protected boolean isExist(Object key) {
        return (int) key >= 0;
    }
}
