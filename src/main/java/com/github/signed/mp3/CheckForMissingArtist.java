package com.github.signed.mp3;

class CheckForMissingArtist implements Callback<Mp3Album.Context> {
    @Override
    public void call(Mp3Album.Context first) {
        Mp3 track = first.currentTrack;
        track.isAuthorSet();
    }
}
