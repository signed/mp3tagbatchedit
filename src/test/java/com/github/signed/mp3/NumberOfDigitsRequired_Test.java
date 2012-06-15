package com.github.signed.mp3;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NumberOfDigitsRequired_Test {

    @Test
    public void returnOneForNumberZeroTo9() throws Exception {
        assertThat(numberOfDigitsRequiredToRepresent(0), is(1));
    }

    @Test
    public void returnTwoForNumberTenToNinetyNine() throws Exception {
        assertThat(numberOfDigitsRequiredToRepresent(12), is(2));
    }

    @Test
    public void returnThreeForNumberOneHundredToNineHundredNinetyNine() throws Exception {
        assertThat(numberOfDigitsRequiredToRepresent(344), is(3));
    }

    private int numberOfDigitsRequiredToRepresent(int input) {
        NumberOfDigitsRequired numberOfDigitsRequired = new NumberOfDigitsRequired();
        return numberOfDigitsRequired.forNumber(input);
    }
}
