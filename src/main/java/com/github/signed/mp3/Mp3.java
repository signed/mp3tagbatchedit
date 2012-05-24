package com.github.signed.mp3;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.jaudiotagger.tag.id3.framebody.AbstractFrameBodyTextInfo;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTRCK;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

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

    public Mp3(MP3File mp3File) {
        this.mp3File = mp3File;
    }

    public void saveChanges() {
        try {
            mp3File.save();
        } catch (IOException | TagException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTitleTo(String newTitle) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        ID3v24Tag tags = getTags();

        AbstractID3v2Frame titleFrame = tags.getFirstField(ID3v24Frames.FRAME_ID_TITLE);
        if(null == titleFrame) {
            AbstractID3v2Frame frame = (AbstractID3v2Frame)tags.createField(FieldKey.TITLE, "initial");
            tags.setFrame(frame);
        }
        titleFrame = tags.getFirstField(ID3v24Frames.FRAME_ID_TITLE);
        AbstractFrameBodyTextInfo body = (AbstractFrameBodyTextInfo) titleFrame.getBody();
        String oldTitle = body.getText();
        System.out.println("old title: " + oldTitle);
        System.out.println("new title: " + newTitle);
        body.setText(newTitle);
    }

    public void setTrackNumberTo(Integer current, Integer total) {
        ID3v24Tag tags = getTags();
        try {
            AbstractID3v2Frame frame = (AbstractID3v2Frame) tags.createField(FieldKey.TRACK, "-1");
            FrameBodyTRCK body = (FrameBodyTRCK) frame.getBody();
            System.out.println("before: " + body.getUserFriendlyValue());
            body.setTrackNo(current);
            body.setTrackTotal(total);
            tags.setFrame(frame);
            System.out.println("after: " + body.getUserFriendlyValue());
            System.out.println("track number set to: " + body.getUserFriendlyValue());
        } catch (FieldDataInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    public void dumpAllTags() {
        AbstractID3v2Tag tags = getTags();
        Iterator<TagField> iterator = tags.getFields();
        while (iterator.hasNext()) {
            TagField next = iterator.next();

            if (next instanceof AbstractID3v2Frame) {
                AbstractID3v2Frame frame = (AbstractID3v2Frame) next;
                String value;
                if (frame.getBody() instanceof AbstractFrameBodyTextInfo) {
                    AbstractFrameBodyTextInfo textBody = (AbstractFrameBodyTextInfo) frame.getBody();
                    value = textBody.getText();
                } else if (frame.getBody() instanceof FrameBodyTRCK) {
                    FrameBodyTRCK body = (FrameBodyTRCK) frame.getBody();
                    value = body.getUserFriendlyValue();
                } else {
                    value = frame.toString();
                }
                String id = frame.getId();
                System.out.println(id + " : " + value);
            } else {
                throw new RuntimeException();
            }
        }
    }

    private ID3v24Tag getTags() {
        if (!mp3File.hasID3v2Tag()) {
            mp3File.setID3v2Tag(new ID3v24Tag());
        }
        return (ID3v24Tag) mp3File.getID3v2Tag();
    }
}
