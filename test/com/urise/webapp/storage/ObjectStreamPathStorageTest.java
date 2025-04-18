package com.urise.webapp.storage;

import org.junit.Test;

public class ObjectStreamPathStorageTest extends AbstractStorageTest{

    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_PATH));
    }

    @Test()
    public void saveOverflow() {}
}
