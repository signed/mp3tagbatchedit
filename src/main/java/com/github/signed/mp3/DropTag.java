package com.github.signed.mp3;

public class DropTag implements Callback<Mp3Album.Context> {
    private final Tag tag;

    public DropTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public void call(Mp3Album.Context first) {
        Mp3 track = first.currentTrack;
        track.drop(tag);
        track.saveChanges();
    }
}
