package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;
import org.junit.Test;

import static com.urise.webapp.storage.TestData.STORAGE_DIR;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }

    @Test()
    public void saveOverflow() {}
}