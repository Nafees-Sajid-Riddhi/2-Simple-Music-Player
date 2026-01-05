package com.simplemusicplayer2;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MusicPlayer {
    private List<Song> playlist;
    private int currentSongIndex;
    private Clip clip;
    private boolean isPaused;
    private String currentSongTitle;

    public MusicPlayer(List<Song> playlist) {
        this.playlist = playlist;
        this.currentSongIndex = 0;
        this.isPaused = false;
    }

    public void playSong(Song song) {
        stopSong(); // Stop any currently playing song
        try {
            File audioFile = new File(song.getFilePath());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            currentSongTitle = song.getTitle();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void pauseSong() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPaused = true;
        }
    }

    public void resumeSong() {
        if (clip != null && isPaused) {
            clip.start();
            isPaused = false;
        }
    }

    public void stopSong() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    public void nextSong() {
        if (playlist != null && !playlist.isEmpty()) {
            currentSongIndex = (currentSongIndex + 1) % playlist.size();
            playSong(playlist.get(currentSongIndex));
        }
    }

    public void previousSong() {
        if (playlist != null && !playlist.isEmpty()) {
            currentSongIndex = (currentSongIndex - 1 + playlist.size()) % playlist.size();
            playSong(playlist.get(currentSongIndex));
        }
    }

    public void repeatSong() {
        if (playlist != null && !playlist.isEmpty()) {
            playSong(playlist.get(currentSongIndex));
        }
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public String getCurrentSongTitle() {
        return currentSongTitle;
    }

    public Song getCurrentSong() {
        return playlist.get(currentSongIndex);
    }
}