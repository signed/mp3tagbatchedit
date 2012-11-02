package com.github.signed.mp3.title;

import com.github.signed.mp3.Mp3Album;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StripLeadingDigits implements TitleProvider {

    @Override
    public String getTitle(String currentTitle, Mp3Album.Context context) {

        Pattern compile = Pattern.compile("^\\d+\\.?\\s?(.*)");
        Matcher matcher = compile.matcher(currentTitle);
        if(matcher.matches()) {
            return matcher.group(1);
        }
        return currentTitle;
    }
}
