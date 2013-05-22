package com.github.signed.mp3;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Mp3Album {

    public static Mp3Album For(Path path) {
        return new Mp3Album(path);
    }

    private Path path;

    public Mp3Album(Path path) {
        this.path = path;
    }

    public void forEachMp3File(Callback<Context> callback) {
        System.out.println("process album at '" + path + "'");

        final List<Path> allPath = getMp3s();

        Collections.sort(allPath, new Comparator<Path>() {
            @Override
            public int compare(Path o1, Path o2) {
                return o1.getFileName().compareTo(o2.getFileName());
            }
        });

        final int totalNumberOfTracks = allPath.size();
        int currentTrackNumber = 1;

        for (Path path : allPath) {
            Mp3 currentMp3 = Mp3.From(path);
            Context context = new Context(totalNumberOfTracks, currentTrackNumber, path, currentMp3);
            callback.call(context);
            ++currentTrackNumber;
        }
    }

    private List<Path> getMp3s() {
        final List<Path> allPath = new ArrayList<>();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.getFileName().toString().endsWith(".mp3")) {
                        allPath.add(file);
                    }
                    return super.visitFile(file, attrs);    //To change body of overridden methods use File | Settings | File Templates.
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allPath;
    }

    public static class Context {
        public final int totalNumberOfTracks;
        public final int trackNumber;
        public final Path currentTracksPath;
        public final Mp3 currentTrack;

        public Context(int totalNumberOfTracks, int trackNumber, Path currentTracksPath, Mp3 currentTrack) {
            this.totalNumberOfTracks = totalNumberOfTracks;
            this.trackNumber = trackNumber;
            this.currentTracksPath = currentTracksPath;
            this.currentTrack = currentTrack;
        }
    }
}
