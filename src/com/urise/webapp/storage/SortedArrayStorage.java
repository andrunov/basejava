package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;


/**
 * Array storage for Resumes with auto sorting
 */
public class SortedArrayStorage extends AbstractArrayStorage{
    /*
        private static class ResumeComparator implements Comparator<Resume> {
            @Override
            public int compare(Resume o1, Resume o2) {
                return o1.getUuid().compareTo(o2.getUuid());
            }
        }
    */
    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };

    public SortedArrayStorage() {
        super();
    }

    //for new resume only
    @Override
    public void insertResume(Integer index, Resume r) {
        int insertionIndex = - (index + 1);
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        storage[insertionIndex] = r;
    }


    @Override
    public void doDelete(Integer key) {
        System.arraycopy(storage, key + 1, storage, key, size - key - 1);
    }


    @Override
    protected Integer searchKey(String uuid) {
        Resume searchElement = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchElement, RESUME_COMPARATOR);
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }
}
