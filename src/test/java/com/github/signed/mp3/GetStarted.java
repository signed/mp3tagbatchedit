package com.github.signed.mp3;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetStarted {

    @Before
    public void setUp() throws Exception {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
    }

    @Test
    @Ignore
    public void executeOnAlbum() throws Exception {
        Path path = Paths.get("/home/directory");

        Mp3Album album = Mp3Album.For(path);
        album.readTracks();
        //album.forEachMp3File(new FillUpLeadingZeros(3));
        //album.forEachMp3File(new SetTitleToFileName());
        //album.forEachMp3File(new UpdateTitle(new RegexFromFileName()));
        //album.forEachMp3File(new UpdateTitle(new StripLeadingDigits()));
        //album.forEachMp3File(new UpdateTitle(new PrependTrackNumberToTitle()));
        //album.forEachMp3File(new UpdateTitle(new RegexFromFileName()));
        //album.forEachMp3File(new SetTrackNumber());
        //album.forEachMp3File(new SetAlbum("Incredible Album"));
        //album.forEachMp3File(new CheckForMissingArtist());
        //album.forEachMp3File(new DropTag(Tag.lookup("COMM")));
        //album.forEachMp3File(new DropTag(Tag.lookup("TCOP")));
        album.storeTracks();

        Mp3Album reloaded = Mp3Album.For(path);
        reloaded.forEachTrack(new DumpAllTags());
    }


    @Test
    public void forACollectionOfFiles() throws Exception {
        Collection<Path> filePaths = new ArrayList<>();

        for (Path filePath : filePaths) {
            Mp3Album.Context context = new Mp3Album.Context(1, 1, filePath, Mp3.From(filePath));
            new DumpAllTags().call(context);
        }
    }

    @Test
    @Ignore
    public void forASingleFile() throws Exception {
        Path singleMp3 = Paths.get("some.amples");
        Mp3Album.Context context = new Mp3Album.Context(1, 1, singleMp3, Mp3.From(singleMp3));
        new DumpAllTags().call(context);
    }

}
