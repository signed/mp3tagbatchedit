package com.github.signed.mp3;

import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.mp3.MP3File;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mp3_Test {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
    }

    @Test
    public void testName() throws Exception {
        InputStream resourceAsStream = Mp3_Test.class.getClassLoader().getResourceAsStream("sample/whitenoise.mp3");
        File mp3File = folder.newFile();
        FileOutputStream out = new FileOutputStream(mp3File);

        IOUtils.copy(resourceAsStream, out);

        Mp3 mp3 = new Mp3(new MP3File(mp3File));
        mp3.setTitleTo("the new one");
        mp3.setTrackNumberTo(7, 7);
        mp3.saveChanges();


        Mp3 reload = new Mp3(new MP3File(mp3File));
        reload.dumpAllTags();
    }
}
