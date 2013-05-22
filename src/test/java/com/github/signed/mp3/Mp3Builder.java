package com.github.signed.mp3;

import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.mp3.MP3File;
import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Mp3Builder extends ExternalResource {
    private final TemporaryFolder folder = new TemporaryFolder();

    @Override
    protected void before() throws Throwable {
        folder.create();
    }

    public Mp3 whiteNoise() throws Exception{
        InputStream resourceAsStream = Mp3_Test.class.getClassLoader().getResourceAsStream("sample/whitenoise.mp3");
        File mp3File = folder.newFile();
        FileOutputStream out = new FileOutputStream(mp3File);

        IOUtils.copy(resourceAsStream, out);

        return new Mp3(new MP3File(mp3File));
    }

    @Override
    protected void after() {
        folder.delete();
    }
}
