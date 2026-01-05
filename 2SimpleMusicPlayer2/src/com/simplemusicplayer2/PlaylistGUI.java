package com.simplemusicplayer2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlaylistGUI {
    private JFrame frame;
    private DefaultListModel<String> songListModel;
    private JList<String> songList;
    private Playlist playlist;
    private JTextField songField;
    private JPopupMenu suggestionPopup;
    private List<String> songNames;

    public PlaylistGUI(Playlist playlist) {
        this.playlist = playlist;
        frame = new JFrame("Playlist Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Create the top panel to hold both title and controls
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.BLACK);

        // Title label
        JLabel titleLabel = new JLabel("PLAYLIST", SwingConstants.CENTER);
        titleLabel.setForeground(Color.CYAN);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 32));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Control panel (song field + buttons)
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.BLACK);

        songField = new JTextField(20);
        JButton addButton = new JButton("ADD SONG");
        JButton removeButton = new JButton("REMOVE SONG");

        // Style components
        styleButton(addButton);
        styleButton(removeButton);
        styleTextField(songField);

        controlPanel.add(songField);
        controlPanel.add(addButton);
        controlPanel.add(removeButton);

        // Add title and control panel to top panel
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(controlPanel, BorderLayout.SOUTH);

        // Add the top panel to frame
        frame.add(topPanel, BorderLayout.NORTH);

        // Song list
        songListModel = new DefaultListModel<>();
        songList = new JList<>(songListModel);
        updateSongList();

        songList.setBackground(Color.BLACK);
        songList.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        songList.setForeground(Color.CYAN);
        songList.setFont(new Font("Verdana", Font.BOLD, 16));
        songList.setSelectionBackground(new Color(0, 150, 150));
        songList.setSelectionForeground(Color.CYAN);

        JScrollPane scrollPane = new JScrollPane(songList);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Done and Back buttons at the bottom
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.BLACK);
        JButton doneButton = new JButton("DONE");
        JButton backButton = new JButton("BACK");
        styleButton(doneButton);
        styleButton(backButton);

        // Add some padding around the buttons
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.add(backButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(doneButton);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Load song names from "2music" folder for autocomplete suggestions
        songNames = loadSongNames("D:\\Downloads\\2SimpleMusicPlayer2\\2music");

        // Autocomplete feature
        suggestionPopup = new JPopupMenu();
        songField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                showSuggestions();
            }
        });

        // Add song button action
        addButton.addActionListener(e -> {
            String songName = songField.getText().trim();
            if (!songName.isEmpty()) {
                String filePath = "D:\\Downloads\\2SimpleMusicPlayer2\\2music\\" + songName + ".wav";
                Song newSong = new Song("id" + System.currentTimeMillis(), songName, "Unknown Artist", MusicGenre.POP, filePath);
                playlist.addSong(newSong);
                updateSongList();
                songField.setText("");
            }
        });

        // Remove song button action
        removeButton.addActionListener(e -> {
            int selectedIndex = songList.getSelectedIndex();
            if (selectedIndex != -1) {
                playlist.getPlaylistSongs().remove(selectedIndex);
                updateSongList();
            }
        });

        // Done button action
        doneButton.addActionListener(e -> {
            frame.dispose(); // Close the PlaylistGUI window
            List<Song> selectedSongs = playlist.getPlaylistSongs();
            Main.startMusicPlayer(selectedSongs); // Open Main with selected songs
        });

        // Back button action
        backButton.addActionListener(e -> {
            frame.dispose(); // Close the PlaylistGUI window
            SwingUtilities.invokeLater(() -> new HomeGUI()); // Open HomeGUI
        });

        frame.setVisible(true);
    }

    // Load song names from "2music" folder
    private List<String> loadSongNames(String folderPath) {
        List<String> names = new ArrayList<>();
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".wav")) {
                    names.add(file.getName().replace(".wav", ""));
                }
            }
        }
        return names;
    }

    // Show song name suggestions
    private void showSuggestions() {
        String input = songField.getText().trim().toLowerCase();
        suggestionPopup.removeAll();

        if (input.isEmpty()) {
            suggestionPopup.setVisible(false);
            return;
        }

        for (String song : songNames) {
            if (song.toLowerCase().startsWith(input)) {
                JMenuItem item = new JMenuItem(song);
                item.addActionListener(e -> {
                    songField.setText(song);
                    suggestionPopup.setVisible(false);
                });
                suggestionPopup.add(item);
            }
        }

        if (suggestionPopup.getComponentCount() > 0) {
            suggestionPopup.show(songField, 0, songField.getHeight());
        } else {
            suggestionPopup.setVisible(false);
        }
    }

    // Update song list
    private void updateSongList() {
        songListModel.clear();
        for (Song song : playlist.getPlaylistSongs()) {
            songListModel.addElement(song.getTitle());
        }
    }

    // Style buttons
    private void styleButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.CYAN);
        button.setFocusPainted(false);
        button.setFont(new Font("Verdana", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK);
            }
        });
    }

    // Style text field
    private void styleTextField(JTextField field) {
        field.setForeground(Color.CYAN);
        field.setFont(new Font("Verdana", Font.BOLD, 16));
        field.setBackground(Color.GRAY);
        field.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
    }

    public static void main(String[] args) {
        User user = new User("1", "JohnDoe", "john@example.com", "password");
        Playlist playlist = new Playlist("My Playlist", user);
        new PlaylistGUI(playlist);
    }
}