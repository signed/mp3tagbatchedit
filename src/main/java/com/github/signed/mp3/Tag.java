package com.github.signed.mp3;

import org.jaudiotagger.tag.id3.ID3v24Frames;

import static org.jaudiotagger.tag.id3.ID3v24Frames.FRAME_ID_TITLE;

public enum Tag {
    Title(FRAME_ID_TITLE),
    Copyright(ID3v24Frames.FRAME_ID_COPYRIGHTINFO),
    UserDefinedInformation(ID3v24Frames.FRAME_ID_USER_DEFINED_INFO),
    Comment(ID3v24Frames.FRAME_ID_COMMENT);

    private final String frameId;
    Tag(String frameId) {
        this.frameId = frameId;
    }

    public String frameId() {
        return frameId;
    }
}
