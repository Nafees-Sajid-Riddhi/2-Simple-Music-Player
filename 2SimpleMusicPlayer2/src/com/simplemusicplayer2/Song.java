package com.simplemusicplayer2;

public class Song {
    private String id;
    private String title;
    private String artist;
    private MusicGenre genre;
    private String filePath;

    public Song(String id, String title, String artist, MusicGenre genre, String filePath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public String getFilePath() {
        return filePath;
    }
}