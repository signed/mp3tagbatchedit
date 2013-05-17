package com.github.signed.mp3.title;

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
        track.provideTitleTo(new ExceptionTranslatingCallback<String>(){

            @Override
            protected void callWithoutConstraint(String title) throws Exception {
                track.setTitleTo(titleProvider.getTitle(title, context));
            }

            @Override
            protected void fallbackWithoutConstraint() throws Exception {
                track.setTitleTo(titleProvider.getTitle("", context));
            }
        });
        track.saveChanges();
    }
}
