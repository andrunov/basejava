package com.urise.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    protected ArrayStorage arrayStorage;

    public ArrayStorageTest() {
        super();
        arrayStorage = (ArrayStorage)storage;
    }

    @Before
    public void setUp() {
        arrayStorage.clear();
        arrayStorage.save(RESUME_01);
        arrayStorage.save(RESUME_02);
        arrayStorage.save(RESUME_03);
    }

    @Test
    public void insertResume() {
        arrayStorage.insertResume(0, RESUME_05);
        Assert.assertEquals(RESUME_05, arrayStorage.storage[arrayStorage.size()]);
    }

    @Test
    public void removeResume() {
        arrayStorage.removeResume(0);
        Assert.assertNotEquals(RESUME_01, arrayStorage.storage[0]);
    }

    @Test
    public void getIndex() {
        Assert.assertEquals(0 , arrayStorage.getIndex(UUID_01));
    }
}