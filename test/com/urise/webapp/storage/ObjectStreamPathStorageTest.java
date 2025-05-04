package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

import static com.urise.webapp.storage.TestData.*;

public class ObjectStreamPathStorageTest extends AbstractObjectStreamSerializerTest {

    @Override
    public void setUp() {
        objectStreamSerializer.setStrategy(ObjectStreamSerializer.Strategy.PATH);
        objectStreamSerializer.getStorage().clear();
        objectStreamSerializer.getStorage().save(RESUME_01);
        objectStreamSerializer.getStorage().save(RESUME_02);
        objectStreamSerializer.getStorage().save(RESUME_03);
    }
}
