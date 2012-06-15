package com.github.signed.mp3;

import static java.lang.String.format;

public class PrependTrackNumberToTitle extends ExceptionTranslatingCallback<Mp3Album.Context> {
    @Override
    protected void callWithoutConstraint(final Mp3Album.Context first) throws Exception {
        final Mp3 track = first.currentTrack;
        track.provideTitleTo(new ExceptionTranslatingCallback<String>(){

            @Override
            protected void callWithoutConstraint(String title) throws Exception {
                int requiredDigits = new NumberOfDigitsRequired().forNumber(first.totalNumberOfTracks);
                String thePattern = format("%%0%dd. ", requiredDigits);
                String thePrependValue = format(thePattern, first.trackNumber);
                if(title.startsWith(thePrependValue)) {
                    return;
                }
                String newTitle = format("%s%s", thePrependValue, title);
                track.setTitleTo(newTitle);
            }
        });

        track.saveChanges();
    }
}
