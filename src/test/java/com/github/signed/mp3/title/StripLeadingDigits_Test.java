package com.github.signed.mp3.title;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StripLeadingDigits_Test {

    @Test
    public void returnTheUnmodifiedTitleIfDoesNotStartWithADigit() throws Exception {
        assertThatTitleIsConvertedTo("the great escape", "the great escape");
    }

    @Test
    public void stripLeadingDigit() throws Exception {
        assertThatTitleIsConvertedTo("1the great escape", "the great escape");
    }

    @Test
    public void stripMultipleLeadingDigits() throws Exception {
        assertThatTitleIsConvertedTo("1234533the great escape", "the great escape");
    }

    @Test
    public void stripPointAfterTheDigits() throws Exception {
        assertThatTitleIsConvertedTo("12.the great escape", "the great escape");
    }

    @Test
    public void stripADotAfterTheDigits() throws Exception {
        assertThatTitleIsConvertedTo("12 the great escape", "the great escape");
    }

    @Test
    public void stripADotAndASpaceAfterTheDigits() throws Exception {
        assertThatTitleIsConvertedTo("12. the great escape", "the great escape");
    }

    private void assertThatTitleIsConvertedTo(String title, String newTitle) {
        assertThat(convertTitle(title), is(newTitle));
    }

    private String convertTitle(String title) {
        return new StripLeadingDigits().getTitle(title, null);
    }
}
