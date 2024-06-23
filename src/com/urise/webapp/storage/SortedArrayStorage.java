package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array storage for Resumes with auto sorting
 */
public class SortedArrayStorage extends AbstractArrayStorage{

    public SortedArrayStorage() {
        storage = new Resume[STORAGE_LIMIT];
    }

    @Override
    public void insertResume(int index, Resume r) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        storage[insertionIndex] = r;
    }


    @Override
    public void removeResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }


    @Override
    protected int getIndex(String uuid) {
        Resume searchElement = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchElement);
    }
}
