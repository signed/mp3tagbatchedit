package com.github.signed.mp3;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FillUpLeadingZeros_Test {

    private final FillUpLeadingZeros fillUp = new FillUpLeadingZeros(3);

    @Test
    public void prependMissingZeros() throws Exception {

        assertThat(fillUp.fillUp("1 - Name"), is("001 - Name"));

    }

    @Test
    public void doNothingIfRequiredNumberOfDigitsArePresent() throws Exception {

        assertThat(fillUp.fillUp("123 - Name"), is("123 - Name"));
    }

    @Test(expected = IllegalStateException.class)
    public void onMissingDigitsAtStartOfString() throws Exception {
        fillUp.fillUp(" - Name");
    }

}
