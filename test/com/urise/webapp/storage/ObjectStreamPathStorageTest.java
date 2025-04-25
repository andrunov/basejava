package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectStreamStorage;

import static com.urise.webapp.storage.TestData.*;

public class ObjectStreamPathStorageTest extends AbstractObjectStreamStorageTest{
    @Override
    public void setUp() {
        objectStreamStorage.setStrategy(ObjectStreamStorage.Strategy.PATH);
        objectStreamStorage.getStorage().clear();
        objectStreamStorage.getStorage().save(RESUME_01);
        objectStreamStorage.getStorage().save(RESUME_02);
        objectStreamStorage.getStorage().save(RESUME_03);
    }
}
