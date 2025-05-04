package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.JsonStreamSerializer;
import org.junit.Test;

import static com.urise.webapp.storage.TestData.STORAGE_DIR;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }

    @Test()
    public void saveOverflow() {}
}