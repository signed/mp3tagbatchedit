package com.github.signed.mp3;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractTagFrameBody;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.id3.framebody.AbstractFrameBodyTextInfo;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTRCK;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.jaudiotagger.tag.FieldKey.TITLE;
import static org.jaudiotagger.tag.id3.ID3v24Frames.FRAME_ID_TITLE;

public class Mp3 {

    public static Mp3 From(Path path) {
        try {
            File file = path.toFile();
            MP3File wrapped = new MP3File(file);
            return new Mp3(wrapped);
        } catch (IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
            throw new RuntimeException(e);
        }
    }

    private MP3File mp3File;
    private final Tags tags;

    public Mp3(MP3File mp3File) {
        this.mp3File = mp3File;
        this.tags = Tags.ExtractFrom(mp3File);
    }

    public void saveChanges() {
        try {
            mp3File.save();
        } catch (IOException | TagException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTitleTo(String newTitle) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        AbstractTagFrameBody body1 = tags.getFrameFor(FRAME_ID_TITLE, TITLE);

        AbstractFrameBodyTextInfo body = (AbstractFrameBodyTextInfo) body1;
        String oldTitle = body.getText();
        System.out.println("old title: " + oldTitle);
        System.out.println("new title: " + newTitle);
        body.setText(newTitle);
    }

    public void setTrackNumberTo(Integer current, Integer total) throws FieldDataInvalidException {
        AbstractTagFrameBody box = tags.getFrameFor(ID3v24Frames.FRAME_ID_TRACK, FieldKey.TRACK);
        FrameBodyTRCK body = (FrameBodyTRCK) box;
        System.out.println("before: " + body.getUserFriendlyValue());
        body.setTrackNo(current);
        body.setTrackTotal(total);
        System.out.println("after: " + body.getUserFriendlyValue());
        System.out.println("track number set to: " + body.getUserFriendlyValue());
    }

    public void dumpAllTags() {
        tags.dumpTagsTo(System.out);
    }
}
