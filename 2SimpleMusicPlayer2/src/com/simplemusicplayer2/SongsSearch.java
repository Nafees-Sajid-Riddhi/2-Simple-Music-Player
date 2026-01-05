package com.simplemusicplayer2;

import java.util.List;
import java.util.ArrayList;

public class SongsSearch {

    private List<Song> allSongs;

    // Constructor to initialize with a list of all songs
    public SongsSearch(List<Song> allSongs) {
        this.allSongs = allSongs;
    }

    // Search for songs by title (partial or exact match)
    public List<Song> searchByTitle(String keyword) {
        List<Song> foundSongs = new ArrayList<>();

        // Iterate through all songs and check if title matches the search keyword
        for (Song song : allSongs) {
            if (song.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                foundSongs.add(song);
            }
        }

        return foundSongs;
    }
}