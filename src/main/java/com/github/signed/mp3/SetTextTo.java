package com.github.signed.mp3;

public class SetTextTo extends ExceptionTranslatingCallback<Mp3Album.Context> {

    private final Tag tag;
    private final String title;

    public SetTextTo(Tag tag, String title) {
        this.title = title;
        this.tag = tag;
    }

    @Override
    protected void callWithoutConstraint(Mp3Album.Context context) throws Exception {
        Mp3 mp3 = context.currentTrack;
        mp3.setTextFor(tag, title);
    }
}
