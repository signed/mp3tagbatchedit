package com.github.signed.mp3.title;

import com.github.signed.mp3.Mp3Album;

public interface TitleProvider {
    String getTitle(String currentTitle, Mp3Album.Context context);
}
