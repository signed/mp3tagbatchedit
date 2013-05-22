package com.github.signed.mp3;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractTagFrameBody;
import org.jaudiotagger.tag.id3.framebody.AbstractFrameBodyTextInfo;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTALB;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTRCK;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

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

    private void dumpNotExistingTag(String tagId){
        boolean has = tags.hasFrameFor(tagId);
        if(!has){
            System.out.println(mp3File.getFile().getAbsolutePath());
            System.out.println(tagId + ": n.a.");
        }
    }

    public void setTitleTo(final String newTitle) {
        textBodyFor(Tag.Title, new Callback<AbstractFrameBodyTextInfo>() {
            @Override
            public void call(AbstractFrameBodyTextInfo first) {
                first.setText(newTitle);
            }
        });
    }

    public void pass(Tag tag, final CallbackWithFallback<String> callback) {
        if(tags.hasFrameFor(tag)){
            textBodyFor(Tag.Title, new Callback<AbstractFrameBodyTextInfo>() {

                @Override
                public void call(AbstractFrameBodyTextInfo first) {
                    callback.call(first.getText());
                }
            });
        }else{
            callback.fallback();
        }
    }

    private void textBodyFor(Tag tag, Callback<AbstractFrameBodyTextInfo> callback) {
        AbstractTagFrameBody body1 = tags.createFrameFor(tag);
        callback.call((AbstractFrameBodyTextInfo) body1);
    }

    public void setTrackNumberTo(Integer current, Integer total) throws FieldDataInvalidException {
        FrameBodyTRCK body = getTrackTag();
        System.out.println("before: " + body.getUserFriendlyValue());
        body.setTrackNo(current);
        body.setTrackTotal(total);
        System.out.println("after: " + body.getUserFriendlyValue());
        System.out.println("track number set to: " + body.getUserFriendlyValue());
    }

    private FrameBodyTRCK getTrackTag() {
        AbstractTagFrameBody box = tags.createFrameFor(Tag.Track);
        return (FrameBodyTRCK) box;
    }

    public void dumpAllTags() {
        tags.dumpTagsTo(System.out);
    }

    public void setAlbumName(String albumName) {
        FrameBodyTALB frameBodyTALB = getAlbumFrame();
        frameBodyTALB.setText(albumName);
    }

    private FrameBodyTALB getAlbumFrame() {
        AbstractTagFrameBody body = tags.createFrameFor(Tag.Album);
        return (FrameBodyTALB) body;
    }

    public void drop(Tag tag){
        tags.dropFrameFor(tag);
    }
}