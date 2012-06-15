# How to use the the code
```java
        FileSystem fileSystem = FileSystems.getDefault();
        Path path = fileSystem.getPath("a/folder/with/some/mp3");
        Mp3Album album = Mp3Album.For(path);
        album.forEachMp3File( new <command>);
```

The following commands are available

## DumpAllTags
Writes the Tags for each file to System.out
## SetTrackNumber
Lists all mp3 files in the passed folder an sorts them by name. The first file in the list gets track number 1, the second track number 2, ...
## SetTitleToFileName
Takes the file name of the mp3 file and sets it as the title
## PrependTrackNumberToTitle
You have a mp3 track in a file called *'some.mp3 '* with a title of *'Incredible Tunes'*. After you run the command on this file the title will be *'1. Incredible Tunes'*

