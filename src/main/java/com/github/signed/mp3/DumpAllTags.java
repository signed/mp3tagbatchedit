package com.github.signed.mp3;

class DumpAllTags extends ExceptionTranslatingCallback<Mp3Album.Context> {
    @Override
    protected void callWithoutConstraint(Mp3Album.Context context) throws Exception {
        System.out.println("-...-...-");
        System.out.println("Path: "+context.currentTracksPath);
        context.currentTrack.dumpAllTags();
    }
}
