package com.urise.webapp.storage;

import org.junit.Before;

import static com.urise.webapp.storage.TestData.*;

public class ObjectStreamFileStorageTest extends AbstractObjectStreamStorageTest{

    @Before
    public void setUp() {
        objectStreamStorage.setStrategy(ObjectStreamStorage.Strategy.FILE);
        objectStreamStorage.getStorage().clear();
        objectStreamStorage.getStorage().save(RESUME_01);
        objectStreamStorage.getStorage().save(RESUME_02);
        objectStreamStorage.getStorage().save(RESUME_03);
    }
}
