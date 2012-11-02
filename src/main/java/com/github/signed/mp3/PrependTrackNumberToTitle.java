package com.github.signed.mp3;

import static java.lang.String.format;

public class PrependTrackNumberToTitle extends ExceptionTranslatingCallback<Mp3Album.Context> {
    @Override
    protected void callWithoutConstraint(final Mp3Album.Context context) throws Exception {
        final Mp3 track = context.currentTrack;
        track.provideTitleTo(new ExceptionTranslatingCallback<String>(){

            @Override
            protected void callWithoutConstraint(String title) throws Exception {
                int requiredDigits = new NumberOfDigitsRequired().forNumber(context.totalNumberOfTracks);
                String thePattern = format("%%0%dd. ", requiredDigits);
                String thePrependValue = format(thePattern, context.trackNumber);
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
