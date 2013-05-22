package com.github.signed.mp3;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class Mp3_Test {

    @Rule
    public final Mp3Builder sample = new Mp3Builder();
    @SuppressWarnings("unchecked")
    private final ExceptionTranslatingCallback<String> callback = mock(ExceptionTranslatingCallback.class);
    private Mp3 mp3;

    @Before
    public void setUp() throws Exception {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
        mp3 = this.sample.build();
    }

    @Test
    public void storeNewTitle() throws Exception {
        mp3.setTitleTo("the new one");
        mp3.saveChanges();

        sample.reloaded().provideTitleTo(callback);
        verify(callback).call("the new one");
    }

    @Test
    public void dropTag() throws Exception {
        mp3.drop(Tag.Title);
        mp3.saveChanges();

        sample.reloaded().provideTitleTo(callback);
        verify(callback).fallback();
    }
}