package com.github.signed.mp3;

public class NumberOfDigitsRequired {


    public int forNumber(int numberToPrint) {
        int result = 0;
        int work = numberToPrint;
        while(work != 0) {
            work = work / 10;
            ++result;
        }
        return result;
    }
}
