package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private JLabel messageLabel;

    private final String USERNAME = "admin";
    private final String PASSWORD = "123";

    public LoginPanel(ActionListener loginListener) {
        setLayout(new BorderLayout());
        setBackground(UniSeatView.BACKGROUND_DARK);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(UniSeatView.SURFACE_DARK);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UniSeatView.SURFACE_LIGHT, 1),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Username label and field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userLabel.setForeground(UniSeatView.TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(userLabel, gbc);

        userField = new JTextField(15);
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(userField, gbc);

        // Password label and field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passLabel.setForeground(UniSeatView.TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passLabel, gbc);

        passField = new JPasswordField(15);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passField, gbc);

        // Message Label
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setForeground(UniSeatView.ERROR_COLOR);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(messageLabel, gbc);

        // Login Button
        loginButton = UniSeatView.createModernButton("Login", UniSeatView.PRIMARY_COLOR);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
                messageLabel.setText("Login successful!");
                if (loginListener != null) loginListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "LoggedIn"));
            } else {
                messageLabel.setText("Invalid username or password");
            }
        });
    }
}
