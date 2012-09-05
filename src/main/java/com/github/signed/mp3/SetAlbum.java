package com.github.signed.mp3;

public class SetAlbum implements Callback<Mp3Album.Context> {
    private final String albumName;

    public SetAlbum(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public void call(Mp3Album.Context first) {
        Mp3 track = first.currentTrack;
        track.setAlbumName(albumName);
        track.saveChanges();
    }
}
