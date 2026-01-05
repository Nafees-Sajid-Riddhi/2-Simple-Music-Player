package com.simplemusicplayer2;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeGUI {

    private JFrame frame;

    public HomeGUI() {
        // Create the frame
        frame = new JFrame("Home Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Create the top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton playlistButton = new JButton("PLAYLIST");
        JLabel titleLabel = new JLabel("SPITOFY", SwingConstants.CENTER);
        JButton logoutButton = new JButton("LOG OUT");

        titleLabel.setForeground(Color.CYAN);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 32));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);

        styleButton(playlistButton);
        styleButton(logoutButton);

        topPanel.add(playlistButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(logoutButton, BorderLayout.EAST);

        // Create the bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.BLACK); // Set background color to black

        // Create a panel for the title
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLACK); // Set background color to black
        JLabel availableSongsLabel = new JLabel("Available Songs", SwingConstants.CENTER);
        availableSongsLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        availableSongsLabel.setForeground(Color.CYAN);
        availableSongsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding
        titlePanel.add(availableSongsLabel, BorderLayout.CENTER);

        // Add the title panel to the bottom panel
        bottomPanel.add(titlePanel);

        // Manually create panels for each image and text
        addMusicPanel(bottomPanel, "musicpicture/BOYNEXTDOOR.jpeg", "BOYNEXTDOOR - Earth, Wind & Fire");
        addMusicPanel(bottomPanel, "musicpicture/CARAVAN PALACE.jpeg", "Caravan Palace - Lone Digger");
        addMusicPanel(bottomPanel, "musicpicture/DAVE LAW OF ATTRACTION.jpeg", "Dave - Law of Attraction (Feat. Snoh Aalegra)");
        addMusicPanel(bottomPanel, "musicpicture/DAVE LOCATION.jpeg", "Dave - Location (Feat. Burna Boy)");
        addMusicPanel(bottomPanel, "musicpicture/KING KRULE.jpeg", "King Krule - Out Getting Ribs");
        addMusicPanel(bottomPanel, "musicpicture/THE SMITHS.jpeg", "The Smiths - Heaven Knows I'm Miserable Now");

        // Add scrolling capability to the bottom panel
        JScrollPane scrollPane = new JScrollPane(bottomPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(Color.BLACK); // Set viewport background color to black

        // Customize scrollbar UI
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.CYAN;
                this.trackColor = Color.BLACK;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(Color.BLACK);
                button.setForeground(Color.CYAN);
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(Color.BLACK);
                button.setForeground(Color.CYAN);
                return button;
            }
        });

        // Adjust scrolling speed
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16); // Adjust this value to change the scrolling speed
        verticalScrollBar.setBlockIncrement(50); // Adjust this value to change the block scrolling speed

        // Add panels to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners for buttons
        playlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                SwingUtilities.invokeLater(() -> {
                    Playlist playlist = new Playlist("My Playlist", new User("1", "JohnDoe", "john@example.com", "password"));
                    new PlaylistGUI(playlist);
                });
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                SwingUtilities.invokeLater(() -> new LoginGUI());
            }
        });

        frame.setVisible(true);
    }

    private void addMusicPanel(JPanel parentPanel, String imagePath, String text) {
        JPanel musicPanel = new JPanel(new BorderLayout());
        musicPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add gap between panels
        musicPanel.setBackground(Color.BLACK); // Set background color to black

        // Load and resize image
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Resize image
        JLabel musicImage = new JLabel(new ImageIcon(image));
        musicImage.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2)); // Add cyan border

        // Display the song name
        JLabel musicDetails = new JLabel(text, SwingConstants.CENTER);
        musicDetails.setFont(new Font("Verdana", Font.BOLD, 14));
        musicDetails.setForeground(Color.CYAN);

        // Add components
        musicPanel.add(musicImage, BorderLayout.WEST);
        musicPanel.add(musicDetails, BorderLayout.CENTER);

        // Add music panel to the parent panel
        parentPanel.add(musicPanel);
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeGUI());
    }
}