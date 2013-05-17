package com.github.signed.mp3.title;

import com.github.signed.mp3.Mp3Album;
import org.junit.Test;

import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegexFromFileName_Test {
        RegexFromFileName provider = new RegexFromFileName();

    @Test
    public void extractChapter() throws Exception {
        assertThat(titleFromFileName("027 - unimportant - Kapitel 1.mp3"), is("Kapitel 1"));
    }

    @Test
    public void extractProlog() throws Exception {
        assertThat(titleFromFileName("027 - unimportant - Prolog.mp3"), is("Prolog"));
    }

    private String titleFromFileName(String filename) {
        return this.provider.getTitle(null, new Mp3Album.Context(0, 0, Paths.get("/home/user/" + filename), null));
    }
}
