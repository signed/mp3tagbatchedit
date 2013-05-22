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
    private File mp3File;

    @Override
    protected void before() throws Throwable {
        folder.create();
        mp3File = folder.newFile();
    }

    @Override
    protected void after() {
        folder.delete();
    }

    public Mp3 reloaded() {
        return Mp3.From(mp3File.toPath());
    }

    public Mp3 build() throws Exception{
        InputStream resourceAsStream = Mp3_Test.class.getClassLoader().getResourceAsStream("sample/whitenoise.mp3");
        FileOutputStream out = new FileOutputStream(mp3File);
        IOUtils.copy(resourceAsStream, out);
        return new Mp3(new MP3File(mp3File));
    }
}
