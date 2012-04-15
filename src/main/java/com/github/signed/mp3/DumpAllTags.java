package com.github.signed.mp3;

class DumpAllTags extends ExceptionTranslatingCallback<Mp3Album.Context> {
    @Override
    protected void callWithoutConstraint(Mp3Album.Context context) throws Exception {
        System.out.println("-...-...-");
        context.currentTrack.dumpAllTags();
    }
}
