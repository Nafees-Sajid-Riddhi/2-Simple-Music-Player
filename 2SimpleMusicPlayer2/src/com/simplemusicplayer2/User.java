package com.simplemusicplayer2;

import java.util.List;
import java.util.ArrayList;

public class User {
    private String userId;
    private String username;
    private String email;
    private String password;
    private UserSubscription subscription;
    private List<Playlist> userPlaylists;

    // Constructor with initialization of playlists
    public User(String userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userPlaylists = new ArrayList<>(); // Initialize the playlists list
        this.subscription = new UserSubscription("default", 0); // Default subscription
    }

    // Getter methods
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserSubscription getSubscription() {
        return subscription;
    }

    // Method to upgrade subscription
    public void upgradeSubscription(UserSubscription subscription) {
        this.subscription = subscription;
    }

    // Method to add a new playlist
    public void addPlaylist(Playlist playlist) {
        userPlaylists.add(playlist);
    }

    // Method to get the user's playlists
    public List<Playlist> getPlaylists() {
        return userPlaylists;
    }
}