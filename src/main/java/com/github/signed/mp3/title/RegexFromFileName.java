package com.github.signed.mp3.title;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.signed.mp3.Mp3Album;

public class RegexFromFileName implements TitleProvider {
    @Override
    public String getTitle(String currentTitle, Mp3Album.Context context) {
        String input = context.currentTracksPath.getFileName().toString();
        Matcher matcher = Pattern.compile("^.* - (?<title>.*)\\.mp3$").matcher(input);
        if(matcher.matches()) {
            return matcher.group("title");
        }else {
            throw new RuntimeException();
        }
    }
}