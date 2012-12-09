package com.github.signed.mp3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FillUpLeadingZeros extends ExceptionTranslatingCallback<Mp3Album.Context> {

    private int numberOfDigitsToFill;

    public FillUpLeadingZeros(int numberOfDigitsToFill) {
        this.numberOfDigitsToFill = numberOfDigitsToFill;
    }

    @Override
    protected void callWithoutConstraint(Mp3Album.Context first) throws Exception {
        Path path = first.currentTracksPath;
        Path name = path.getFileName();
        String newName = fillUp(name.toString());
        Path destination = path.getParent().resolve(newName);
        Files.move(path, destination);
    }

    public String fillUp(String originalName) {
        Pattern digitDetector = Pattern.compile("^(\\d+)");
        Matcher matcher = digitDetector.matcher(originalName);
        matcher.find();

        String digits = matcher.group(1);
        int trackNumber = Integer.parseInt(digits);
        String numbers = String.format("%0" + this.numberOfDigitsToFill + "d", trackNumber);

        String rest = originalName.substring(digits.length());
        return numbers + rest;
    }
}
