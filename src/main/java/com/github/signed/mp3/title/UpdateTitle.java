package com.github.signed.mp3.title;

import static com.github.signed.mp3.Tag.Title;

import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import com.github.signed.mp3.ExceptionTranslatingCallback;
import com.github.signed.mp3.Mp3;
import com.github.signed.mp3.Mp3Album;

public class UpdateTitle extends ExceptionTranslatingCallback<Mp3Album.Context> {
    private final TitleProvider titleProvider;

    public UpdateTitle(TitleProvider titleProvider) {
        this.titleProvider = titleProvider;
    }

    @Override
    protected void callWithoutConstraint(final Mp3Album.Context context) throws Exception {
        final Mp3 track = context.currentTrack;
        track.pass(Title, new ExceptionTranslatingCallback<String>() {

            @Override
            protected void callWithoutConstraint(String title) throws Exception {
                updateTrackTitle(title);
            }

            @Override
            protected void fallbackWithoutConstraint() throws Exception {
                updateTrackTitle("");
            }

            private void updateTrackTitle(String currentTitle) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
                track.setTextFor(Title, titleProvider.getTitle(currentTitle, context));
            }
        });
    }
}
