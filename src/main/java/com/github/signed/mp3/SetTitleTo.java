package com.github.signed.mp3;

public class SetTitleTo extends ExceptionTranslatingCallback<Mp3Album.Context> {

    private String title;

    public SetTitleTo(String title) {
        this.title = title;
    }

    @Override
    protected void callWithoutConstraint(Mp3Album.Context context) throws Exception {
        Mp3 mp3 = context.currentTrack;
        mp3.setTitleTo(title);
        mp3.saveChanges();
    }
}
