package com.github.signed.mp3.title;

import static java.lang.String.format;

import com.github.signed.mp3.Mp3Album;
import com.github.signed.mp3.NumberOfDigitsRequired;

public class PrependTrackNumberToTitle implements TitleProvider {
    public String getTitle(String title, Mp3Album.Context context) {
        int requiredDigits = new NumberOfDigitsRequired().forNumber(context.totalNumberOfTracks);
        String thePattern = format("%%0%dd. ", requiredDigits);
        String thePrependValue = format(thePattern, context.trackNumber);
        String newTitle = title;
        if(!title.startsWith(thePrependValue)) {
            newTitle = format("%s%s", thePrependValue, title);
        }
        return newTitle;
    }
}
