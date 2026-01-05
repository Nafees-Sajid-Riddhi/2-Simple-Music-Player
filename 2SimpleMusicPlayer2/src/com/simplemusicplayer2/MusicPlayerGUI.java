package com.simplemusicplayer2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class MusicPlayerGUI {
    private JFrame frame;
    private JPanel homePanel;
    private JButton playButton, pauseButton, nextButton, prevButton, repeatButton, stopButton, backButton;
    private JLabel songLabel;
    private JList<String> songList;
    private DefaultListModel<String> songListModel;
    private MusicPlayer musicPlayer;
    private List<Song> allSongs;
    private JPanel leftEqualizerPanel, rightEqualizerPanel;  // Panels for equalizer bars
    private Timer equalizerTimer;
    private int[] leftBarHeights = {50, 40, 60, 30, 70};  // Initial heights of left bars
    private int[] rightBarHeights = {50, 40, 60, 30, 70};  // Initial heights of right bars

    public MusicPlayerGUI(MusicPlayer musicPlayer, List<Song> allSongs) {
        this.musicPlayer = musicPlayer;
        this.allSongs = allSongs;

        frame = new JFrame("Music Player");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new CardLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        createHomePage();
        frame.add(homePanel, "Home");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        enableKeyboardShortcuts();
    }

    private void createHomePage() {
        homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(Color.BLACK);

        // Now Playing label with Matrix-like font and smaller size
        songLabel = new JLabel("Now Playing: No Song", SwingConstants.CENTER);
        songLabel.setForeground(new Color(0, 255, 255));  // Aqua color for "Now Playing"
        songLabel.setFont(new Font("OCR A Extended", Font.PLAIN, 14));  // Matrix-style font (can be any monospaced digital font)

        // Equalizer panels for both sides
        leftEqualizerPanel = createEqualizerPanel();
        rightEqualizerPanel = createEqualizerPanel();

        // Centered panel for "Now Playing"
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(leftEqualizerPanel, BorderLayout.WEST);
        centerPanel.add(songLabel, BorderLayout.CENTER);
        centerPanel.add(rightEqualizerPanel, BorderLayout.EAST);

        JPanel controls = new JPanel(new FlowLayout());
        controls.setBackground(Color.BLACK);

        // Changed to uppercase labels for buttons
        playButton = new JButton("▶ PLAY");
        pauseButton = new JButton("⏸ PAUSE");
        nextButton = new JButton("⏭ NEXT");
        prevButton = new JButton("⏮ PREVIOUS");
        repeatButton = new JButton("🔁 REPEAT");
        stopButton = new JButton("🛑 STOP");
        backButton = new JButton("⬅ BACK");

        // Style buttons with black background and blue borders for neon glow effect
        styleButton(playButton, new Color(0, 0, 255));  // Blue border and text
        styleButton(pauseButton, new Color(0, 191, 255));  // Light Blue
        styleButton(nextButton, new Color(30, 144, 255));  // Dodger blue
        styleButton(prevButton, new Color(30, 144, 255));  // Dodger blue
        styleButton(repeatButton, new Color(0, 255, 255));  // Cyan
        styleButton(stopButton, new Color(0, 0, 255));  // Blue border and text
        styleButton(backButton, new Color(0, 255, 255));  // Cyan

        controls.add(backButton);
        controls.add(prevButton);
        controls.add(playButton);
        controls.add(pauseButton);
        controls.add(nextButton);
        controls.add(repeatButton);
        controls.add(stopButton);

        songListModel = new DefaultListModel<>();
        for (Song song : allSongs) {
            songListModel.addElement(song.getTitle());
        }
        songList = new JList<>(songListModel);
        songList.setBackground(Color.BLACK);
        songList.setForeground(new Color(0, 255, 255));  // Aqua text for song list
        songList.setFont(new Font("OCR A Extended", Font.PLAIN, 16));  // Matrix-like font for song titles

        // Customize highlighted song color and opacity
        songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        songList.setSelectionBackground(new Color(169, 169, 169, 100)); // Light grey with low opacity
        songList.setSelectionForeground(new Color(255, 255, 255));  // White text when highlighted

        JScrollPane songScrollPane = new JScrollPane(songList);

        homePanel.add(centerPanel, BorderLayout.NORTH);
        homePanel.add(songScrollPane, BorderLayout.CENTER);
        homePanel.add(controls, BorderLayout.SOUTH);

        // Add action listeners for buttons
        playButton.addActionListener(e -> playMusic());
        pauseButton.addActionListener(e -> togglePause());
        nextButton.addActionListener(e -> nextSong());
        prevButton.addActionListener(e -> previousSong());
        repeatButton.addActionListener(e -> repeatSong());
        stopButton.addActionListener(e -> stopMusic());
        backButton.addActionListener(e -> goBackToPlaylist());
    }

    private JPanel createEqualizerPanel() {
        JPanel equalizerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 255, 255));  // Neon blue color for bars
                for (int i = 0; i < leftBarHeights.length; i++) {
                    g.fillRect(i * 10 + 5, 50 - leftBarHeights[i], 6, leftBarHeights[i]);
                }
            }
        };
        equalizerPanel.setPreferredSize(new Dimension(70, 70));  // Small size for equalizer
        equalizerPanel.setOpaque(false);
        return equalizerPanel;
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(Color.BLACK);
        button.setForeground(color);  // Text color is blue
        button.setFocusPainted(false);
        button.setFont(new Font("Verdana", Font.BOLD, 16));  // Futuristic font
        button.setBorder(BorderFactory.createLineBorder(color, 2));  // Blue border

        // Add glowing neon effect on hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 255), 3));  // Blue glow on hover
                button.setForeground(new Color(173, 216, 230));  // Lighter blue for the glow effect
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBorder(BorderFactory.createLineBorder(color, 2));  // Remove glow
                button.setForeground(color);  // Reset text color to blue
            }
        });
    }

    private void playMusic() {
        int selectedIndex = songList.getSelectedIndex();
        if (selectedIndex != -1) {
            Song selectedSong = allSongs.get(selectedIndex);
            musicPlayer.playSong(selectedSong);
            songLabel.setText("Now Playing: " + selectedSong.getTitle());
            startEqualizerAnimation();
        } else if (!allSongs.isEmpty()) {
            Song firstSong = allSongs.get(0);
            musicPlayer.playSong(firstSong);
            songLabel.setText("Now Playing: " + firstSong.getTitle());
            startEqualizerAnimation();
        } else {
            JOptionPane.showMessageDialog(frame, "No songs available to play.");
        }
    }

    private void togglePause() {
        if (musicPlayer != null) {
            if (musicPlayer.getIsPaused()) {
                musicPlayer.resumeSong();
                songLabel.setText("Resumed: " + musicPlayer.getCurrentSongTitle());
                startEqualizerAnimation();
            } else {
                musicPlayer.pauseSong();
                songLabel.setText("Paused: " + musicPlayer.getCurrentSongTitle());
                stopEqualizerAnimation();
            }
        }
    }

    private void nextSong() {
        musicPlayer.nextSong();
        songLabel.setText("Now Playing: " + musicPlayer.getCurrentSongTitle());
        songList.setSelectedIndex(allSongs.indexOf(musicPlayer.getCurrentSong()));  // Highlight the current song
    }

    private void previousSong() {
        musicPlayer.previousSong();
        songLabel.setText("Now Playing: " + musicPlayer.getCurrentSongTitle());
        songList.setSelectedIndex(allSongs.indexOf(musicPlayer.getCurrentSong()));  // Highlight the current song
    }

    private void repeatSong() {
        musicPlayer.repeatSong();
        songLabel.setText("Repeating: " + musicPlayer.getCurrentSongTitle());
    }

    private void stopMusic() {
        musicPlayer.stopSong();
        songLabel.setText("No song playing");
        stopEqualizerAnimation();
        JOptionPane.showMessageDialog(frame, "Music stopped. Exiting...");
        System.exit(0);
    }

    private void goBackToPlaylist() {
        frame.dispose(); // Close the MusicPlayerGUI window
        SwingUtilities.invokeLater(() -> new PlaylistGUI(new Playlist("My Playlist", new User("1", "JohnDoe", "john@example.com", "password")))); // Open PlaylistGUI
    }

    // Method to start equalizer animation
    private void startEqualizerAnimation() {
        if (equalizerTimer == null) {
            equalizerTimer = new Timer(100, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Randomize the height of each bar to simulate the equalizer effect
                    for (int i = 0; i < leftBarHeights.length; i++) {
                        leftBarHeights[i] = (int) (Math.random() * 60) + 20;  // Random height between 20 and 80
                        rightBarHeights[i] = (int) (Math.random() * 60) + 20;  // Random height between 20 and 80
                    }
                    leftEqualizerPanel.repaint();
                    rightEqualizerPanel.repaint();
                }
            });
        }
        equalizerTimer.start();
    }

    // Method to stop equalizer animation
    private void stopEqualizerAnimation() {
        if (equalizerTimer != null) {
            equalizerTimer.stop();
        }
    }

    private void enableKeyboardShortcuts() {
        songList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "playSelectedSong");
        songList.getActionMap().put("playSelectedSong", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playMusic();
            }
        });

        songList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pauseMusic");
        songList.getActionMap().put("pauseMusic", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });

        songList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "nextSong");
        songList.getActionMap().put("nextSong", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextSong();
            }
        });

        songList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "previousSong");
        songList.getActionMap().put("previousSong", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousSong();
            }
        });

        songList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "repeatSong");
        songList.getActionMap().put("repeatSong", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repeatSong();
            }
        });

        songList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "stopMusic");
        songList.getActionMap().put("stopMusic", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopMusic();
            }
        });
    }
}