package com.simplemusicplayer2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginGUI {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginGUI() {
        frame = new JFrame("Spitofy - Login");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null); // Center the frame

        JLabel titleLabel = new JLabel("Welcome to Spitofy, stream your music freely", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.CYAN);
        titleLabel.setBounds(150, 50, 500, 30);
        frame.add(titleLabel);

        // Username Field
        JLabel usernameLabel = new JLabel("USERNAME:");
        usernameLabel.setForeground(Color.CYAN);
        usernameLabel.setBounds(200, 150, 100, 25);
        frame.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(300, 150, 250, 30);
        usernameField.setBackground(Color.DARK_GRAY);
        usernameField.setForeground(Color.WHITE);
        frame.add(usernameField);

        // Password Field
        JLabel passwordLabel = new JLabel("PASSWORD:");
        passwordLabel.setForeground(Color.CYAN);
        passwordLabel.setBounds(200, 200, 100, 25);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 200, 250, 30);
        passwordField.setBackground(Color.DARK_GRAY);
        passwordField.setForeground(Color.WHITE);
        frame.add(passwordField);

        // Login Button
        loginButton = new JButton("LOGIN");
        loginButton.setBounds(300, 270, 100, 40);
        styleButton(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.dispose(); // Close login window
                    new HomeGUI(); // Open home page
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.add(loginButton);

        // Register Button
        registerButton = new JButton("REGISTER");
        registerButton.setBounds(450, 270, 100, 40);
        styleButton(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close login window
                new RegisterGUI(); // Open register page
            }
        });
        frame.add(registerButton);

        frame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.CYAN);
        button.setFocusPainted(false);
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

    // Method to validate login credentials against the saved file
    private boolean validateLogin(String username, String password) {
        String filePath = "users.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(","); // Split the line into username, email, and password
                if (userDetails[0].equals(username) && userDetails[2].equals(password)) {
                    return true; // Username and password match
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // If no match is found
    }

    // Main method to run the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI());
    }
}