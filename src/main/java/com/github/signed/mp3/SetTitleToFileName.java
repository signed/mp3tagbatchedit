package com.github.signed.mp3;

import java.nio.file.Path;

class SetTitleToFileName extends ExceptionTranslatingCallback<Mp3Album.Context> {
    @Override
    protected void callWithoutConstraint(Mp3Album.Context context) throws Exception {
        Mp3 mp3 = context.currentTrack;
        Path path = context.currentTracksPath;
        mp3.setTextFor(Tag.Title, path.getFileName().toString());
        mp3.saveChanges();
    }
}
