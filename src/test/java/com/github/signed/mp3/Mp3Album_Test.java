package com.github.signed.mp3;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class Mp3Album_Test {

    @Rule
    public final Mp3Builder sample = new Mp3Builder();

    @SuppressWarnings("unchecked")
    private final ExceptionTranslatingCallback<String> callback = mock(ExceptionTranslatingCallback.class);

    @Before
    public void silenceLogger() throws Exception {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
    }

    @Test
    public void allowMultipleTagsToBeChanged() throws Exception {
        sample.build();
        Mp3Album album = Mp3Album.For(sample.root());
        album.readTracks();
        album.forEachTrack(new SetTextTo(Tag.Artist, "The artist"));
        album.forEachTrack(new SetTextTo(Tag.Album, "New album"));
        album.storeTracks();


        sample.reloaded().pass(Tag.Artist, callback);
        sample.reloaded().pass(Tag.Album, callback);
        verify(callback).call("The artist");
        verify(callback).call("New album");
    }
}