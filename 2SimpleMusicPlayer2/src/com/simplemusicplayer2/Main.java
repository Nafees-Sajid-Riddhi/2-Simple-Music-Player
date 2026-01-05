package com.simplemusicplayer2;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Start the LoginGUI
        SwingUtilities.invokeLater(() -> new LoginGUI());
    }

    // Method to start the MusicPlayerGUI with the selected songs
    public static void startMusicPlayer(List<Song> selectedSongs) {
        // Create a MusicPlayer object with the selected songs
        MusicPlayer musicPlayer = new MusicPlayer(selectedSongs);

        // Start the MusicPlayerGUI
        SwingUtilities.invokeLater(() -> new MusicPlayerGUI(musicPlayer, selectedSongs));
    }

    // Method to load songs from the specified directory
    public static List<Song> loadSongsFromDirectory(String folderPath) {
        List<Song> songs = new ArrayList<>();

        File musicFolder = new File(folderPath);
        File[] musicFiles = musicFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));

        if (musicFiles != null) {
            for (File musicFile : musicFiles) {
                String title = musicFile.getName().replace(".wav", "");  // Remove ".wav" extension
                String artist = "Unknown Artist";  // Default artist, you can modify this as needed

                // Create Song objects and add them to the list
                songs.add(new Song(String.valueOf(songs.size() + 1), title, artist, MusicGenre.POP, musicFile.getAbsolutePath()));
            }
        }
        return songs;
    }
}