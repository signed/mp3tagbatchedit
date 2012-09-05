package com.github.signed.mp3;

import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractTagFrameBody;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.jaudiotagger.tag.id3.framebody.AbstractFrameBodyTextInfo;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTALB;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTRCK;

import java.io.PrintStream;
import java.util.Iterator;

public class Tags {

    public static Tags ExtractFrom(MP3File mp3File) {
        if (!mp3File.hasID3v2Tag()) {
            mp3File.setID3v2Tag(new ID3v24Tag());
        }

        if (!(mp3File.getID3v2Tag() instanceof ID3v24Tag)) {
            ID3v24Tag tags = mp3File.getID3v2TagAsv24();
            mp3File.setID3v2Tag(tags);
        }
        ID3v24Tag id3v2Tag = (ID3v24Tag) mp3File.getID3v2Tag();
        return new Tags(id3v2Tag);
    }

    private final ID3v24Tag tags;

    public Tags(ID3v24Tag tags) {
        this.tags = tags;
    }

    public boolean hasFrameFor(String id3v24FrameKey) {
        return tags.hasFrameAndBody(id3v24FrameKey);
    }

    public AbstractTagFrameBody createFrameFor(String id3v24FrameKey, FieldKey genericFieldKey){
        try {
            AbstractID3v2Frame titleFrame = tags.getFirstField(id3v24FrameKey);
            if (null == titleFrame) {
                AbstractID3v2Frame frame = (AbstractID3v2Frame) tags.createField(genericFieldKey, "0");
                tags.setFrame(frame);
            }
            titleFrame = tags.getFirstField(id3v24FrameKey);
            return titleFrame.getBody();
        } catch (KeyNotFoundException | FieldDataInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    public void dumpTagsTo(PrintStream out) {
        Iterator<TagField> iterator = tags.getFields();
        while (iterator.hasNext()) {
            TagField next = iterator.next();

            if (next instanceof AbstractID3v2Frame) {
                AbstractID3v2Frame frame = (AbstractID3v2Frame) next;
                String value;
                if (frame.getBody() instanceof FrameBodyTALB) {
                    FrameBodyTALB body = (FrameBodyTALB)frame.getBody();
                    value = body.getUserFriendlyValue();
                } else if (frame.getBody() instanceof AbstractFrameBodyTextInfo) {
                    AbstractFrameBodyTextInfo textBody = (AbstractFrameBodyTextInfo) frame.getBody();
                    value = textBody.getText();
                } else if (frame.getBody() instanceof FrameBodyTRCK) {
                    FrameBodyTRCK body = (FrameBodyTRCK) frame.getBody();
                    value = body.getUserFriendlyValue();
                } else if (frame.getBody() instanceof FrameBodyCOMM) {
                    FrameBodyCOMM body = (FrameBodyCOMM) frame.getBody();
                    value = "\n\tdescription: " + body.getDescription();
                    value += "\n\tlanguage   :" + body.getLanguage();
                    value += "\n\ttext       :" + body.getText();
                    value += "\n\tfriendly   :" + body.getUserFriendlyValue();
                } else if (frame.getBody() instanceof FrameBodyAPIC) {
                    FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
                    value = body.getUserFriendlyValue();
                } else {
                    value = frame.toString();
                }
                String id = frame.getId();
                out.println(id + " : " + value);
            } else {
                throw new RuntimeException();
            }
        }
    }
}
