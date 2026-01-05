package com.simplemusicplayer2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterGUI {
    private JFrame frame;
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    public RegisterGUI() {
        frame = new JFrame("Spitofy - Register");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Create your Spitofy account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.CYAN);
        titleLabel.setBounds(150, 50, 500, 30);
        frame.add(titleLabel);

        // Username Field
        JLabel usernameLabel = new JLabel("USERNAME:");
        usernameLabel.setForeground(Color.CYAN);
        usernameLabel.setBounds(200, 120, 100, 25);
        frame.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(300, 120, 250, 30);
        usernameField.setBackground(Color.DARK_GRAY);
        usernameField.setForeground(Color.WHITE);
        frame.add(usernameField);

        // Email Field
        JLabel emailLabel = new JLabel("EMAIL:");
        emailLabel.setForeground(Color.CYAN);
        emailLabel.setBounds(200, 180, 100, 25);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(300, 180, 250, 30);
        emailField.setBackground(Color.DARK_GRAY);
        emailField.setForeground(Color.WHITE);
        frame.add(emailField);

        // Password Field
        JLabel passwordLabel = new JLabel("PASSWORD:");
        passwordLabel.setForeground(Color.CYAN);
        passwordLabel.setBounds(200, 240, 100, 25);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 240, 250, 30);
        passwordField.setBackground(Color.DARK_GRAY);
        passwordField.setForeground(Color.WHITE);
        frame.add(passwordField);

        // Register Button
        registerButton = new JButton("REGISTER");
        registerButton.setBounds(300, 310, 100, 40);
        styleButton(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    saveUserToFile(username, email, password); // Save user details to file
                    JOptionPane.showMessageDialog(frame, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose(); // Close register page
                    new LoginGUI(); // Go to login page
                }
            }
        });
        frame.add(registerButton);

        // Back Button
        backButton = new JButton("BACK");
        backButton.setBounds(450, 310, 100, 40);
        styleButton(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close register page
                new LoginGUI(); // Go back to login page
            }
        });
        frame.add(backButton);

        frame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.CYAN);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
    }

    // Method to save user registration data to a file
    private void saveUserToFile(String username, String email, String password) {
        String filePath = "users.txt";
        String userData = username + "," + email + "," + password;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(userData);
            writer.newLine(); // Save the data on a new line
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}