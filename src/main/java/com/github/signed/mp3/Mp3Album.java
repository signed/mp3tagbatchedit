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

    private final List<Mp3> tracks = new ArrayList<>();
    private final Path path;

    public Mp3Album(Path path) {
        this.path = path;
    }

    public void readTracks() {
        final List<Path> allPath = readTracksInternal();
        sortTracks(allPath);

        for (Path path1 : allPath) {
            tracks.add(Mp3.From(path1));
        }
    }

    public void forEachTrack(Callback<Context> callback) {
        final int totalNumberOfTracks = tracks.size();
        int currentTrackNumber = 1;


        for (Mp3 mp3 : tracks) {
            Context context = new Context(totalNumberOfTracks, currentTrackNumber, path, mp3);
            callback.call(context);
            ++currentTrackNumber;
        }
    }

    private void sortTracks(List<Path> allPath) {
        Collections.sort(allPath, new Comparator<Path>() {
            @Override
            public int compare(Path o1, Path o2) {
                return o1.toAbsolutePath().toString().compareTo(o2.toAbsolutePath().toString());
            }
        });
    }

    private List<Path> readTracksInternal() {
        System.out.println("process album at '" + path + "'");
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

    public void storeTracks() {
        for (Mp3 track : tracks) {
            track.saveChanges();
        }
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
