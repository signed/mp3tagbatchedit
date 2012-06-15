package com.github.signed.mp3;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetStarted {

    @Before
    public void setUp() throws Exception {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
    }

    @Test
    public void testName() throws Exception {
        FileSystem fileSystem = FileSystems.getDefault();
        Path path = fileSystem.getPath("a/folder/with/some/mp3");
        Mp3Album album = Mp3Album.For(path);
        //album.forEachMp3File(new SetTitleToFileName());
        album.forEachMp3File(new PrependTrackNumberToTitle());
        //album.forEachMp3File(new SetTrackNumber());
        //album.forEachMp3File(new DumpAllTags());
    }

    @Test
    public void forASingleFile() throws Exception {
        Path singleMp3 = Paths.get("some.mp3");
        Mp3Album.Context context = new Mp3Album.Context(1, 1, singleMp3, Mp3.From(singleMp3));
        new DumpAllTags().call(context);
        //new SetTrackNumber().call(context);
        //new SetTitleToFileName().call(context);

//        Path reloaded = Paths.get("/home/signed/tmp/18.Wolken über Ebou Dar/00. Intro.mp3");
//        context = new Mp3Album.Context(1, 1, singleMp3, Mp3.From(reloaded));
//        new DumpAllTags().call(context);
    }
}
