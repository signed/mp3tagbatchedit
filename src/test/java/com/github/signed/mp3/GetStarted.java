package com.github.signed.mp3;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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
        Path path = fileSystem.getPath("mp3");
        Mp3Album album = Mp3Album.For(path);
        //album.forEachMp3File(new SetTitleToFileName());
        //album.forEachMp3File(new DumpAllTags());
        album.forEachMp3File(new SetTrackNumber());
    }


    @Test
    public void forTheFirstFile() throws Exception {
        FileSystem fileSystem = FileSystems.getDefault();
        Path path = fileSystem.getPath("an.mp3");
        Mp3 intro = Mp3.From(path);
        intro.setTrackNumberTo( 2, 37);
        Mp3Album.Context context = new Mp3Album.Context(1, 1, path, intro);
        new DumpAllTags().call(context);
        new SetTitleToFileName().call(context);
    }

    private static class SetTitleToFileName extends ExceptionTranslatingCallback<Mp3Album.Context> {
        @Override
        protected void callWithoutConstraint(Mp3Album.Context context) throws Exception {
            Mp3 mp3 = context.currentTrack;
            Path path = context.currentTracksPath;
            mp3.setTitleTo(path.getFileName().toString());
        }
    }

    private static class DumpAllTags extends ExceptionTranslatingCallback<Mp3Album.Context> {
        @Override
        protected void callWithoutConstraint(Mp3Album.Context context) throws Exception {
            System.out.println("-...-...-");
            context.currentTrack.dumpAllTags();
        }
    }

    private static class SetTrackNumber extends ExceptionTranslatingCallback<Mp3Album.Context> {
        @Override
        protected void callWithoutConstraint(Mp3Album.Context context) throws Exception {
            Mp3 track = context.currentTrack;
            track.setTrackNumberTo(context.trackNumber, context.totalNumberOfTracks);
            track.saveChanges();
        }
    }
}
