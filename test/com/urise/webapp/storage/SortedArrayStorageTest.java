package com.urise.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    protected SortedArrayStorage sortedArrayStorage;

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
        sortedArrayStorage = (SortedArrayStorage) super.storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        RESUME_02.setUuid(UUID_02);
        RESUME_03.setUuid(UUID_03);
        storage.save(RESUME_01);
        storage.save(RESUME_02);
        storage.save(RESUME_03);
    }

    @Test
    public void insertResume() {
        sortedArrayStorage.insertResume(-4, RESUME_05);
        Assert.assertEquals(RESUME_05, sortedArrayStorage.storage[sortedArrayStorage.size()]);
    }

    @Test
    public void removeResume() {
        sortedArrayStorage.removeResume(0);
        Assert.assertNotEquals(RESUME_01, sortedArrayStorage.storage[0]);
    }

    @Test
    public void getIndex() {
        Assert.assertEquals(0 , sortedArrayStorage.getIndex(UUID_01));
    }
}