package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectStreamStorage;

import static com.urise.webapp.storage.TestData.*;

public class ObjectStreamFileStorageTest extends AbstractObjectStreamStorageTest{

    @Override
    public void setUp() {
        objectStreamStorage.setStrategy(ObjectStreamStorage.Strategy.FILE);
        objectStreamStorage.getStorage().clear();
        objectStreamStorage.getStorage().save(RESUME_01);
        objectStreamStorage.getStorage().save(RESUME_02);
        objectStreamStorage.getStorage().save(RESUME_03);
    }
}
