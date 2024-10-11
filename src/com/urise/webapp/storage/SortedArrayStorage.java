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
    public void insertResume(Object index, Resume r) {
        int insertionIndex = -(int)index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        storage[insertionIndex] = r;
    }


    @Override
    public void removeResume(Object index) {
        System.arraycopy(storage, (int)index + 1, storage, (int)index, size - (int)index - 1);
    }


    @Override
    protected Integer searchKey(String uuid) {
        Resume searchElement = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchElement);
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }
}
