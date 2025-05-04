package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;
import org.junit.Test;

import static com.urise.webapp.storage.TestData.STORAGE_DIR;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }

    @Test()
    public void saveOverflow() {}
}