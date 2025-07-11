package com.urise.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.TestData.*;

public class ArrayStorageTest extends AbstractStorageTest {

    private ArrayStorage arrayStorage;

    public ArrayStorageTest() {
        super(new ArrayStorage());
        this.arrayStorage = (ArrayStorage) super.storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_01);
        storage.save(RESUME_02);
        RESUME_03.setUuid(UUID_03);
        storage.save(RESUME_03);
    }

    @Test
    public void insertResume() {
        arrayStorage.insertResume(0, RESUME_05);
        Assert.assertEquals(RESUME_05, arrayStorage.storage[storage.size()]);
    }

    @Test
    public void removeResume() {
        arrayStorage.doDelete(0);
        Assert.assertNotEquals(RESUME_01, arrayStorage.storage[0]);
    }

    @Test
    public void getIndex() {
        Assert.assertEquals(Integer.valueOf(0), arrayStorage.searchKey(UUID_01));
    }
}