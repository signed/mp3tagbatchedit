package com.github.signed.mp3;

class SetTrackNumber extends ExceptionTranslatingCallback<Mp3Album.Context> {
    @Override
    protected void callWithoutConstraint(Mp3Album.Context context) throws Exception {
        Mp3 track = context.currentTrack;
        track.setTrackNumberTo(context.trackNumber, context.totalNumberOfTracks);
        track.saveChanges();
    }
}
