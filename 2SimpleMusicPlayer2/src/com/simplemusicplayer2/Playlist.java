package com.simplemusicplayer2;

import java.util.ArrayList;
import java.util.List;

public class Playlist implements PlaylistManagement {
    private String playlistId;
    private String name;
    private String creationDate;
    private List<Song> songs;
    private User owner;

    public Playlist(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.creationDate = "2025-03-01"; // sample date
        this.songs = new ArrayList<>();
    }

    @Override
    public boolean isSongInPlaylist(String songId) {
        for (Song song : songs) {
            if (song.getFilePath().equals(songId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addSong(Song song) {
        songs.add(song);
    }

    @Override
    public void removeSong(Song song) {
        songs.remove(song);
    }

    @Override
    public List<Song> getPlaylistSongs() {
        return songs;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDate(String date) {
        this.creationDate = date;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getName() {
        return name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public User getOwner() {
        return owner;
    }
}