package com.github.signed.mp3;

public class SetTextTo extends ExceptionTranslatingCallback<Mp3Album.Context> {

    public static ExceptionTranslatingCallback<Mp3Album.Context> set(Tag tag, String value) {
        return new SetTextTo(tag, value);
    }

    private final Tag tag;
    private final String value;

    public SetTextTo(Tag tag, String value) {
        this.value = value;
        this.tag = tag;
    }

    @Override
    protected void callWithoutConstraint(Mp3Album.Context context) throws Exception {
        Mp3 mp3 = context.currentTrack;
        mp3.setTextFor(tag, value);
    }
}