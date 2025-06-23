package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.Resume;
import com.urise.webapp.util.DataSectionAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        DataSectionAdapter.write(r, os);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return DataSectionAdapter.read(is);
    }
}
