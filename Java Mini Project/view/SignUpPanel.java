package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import database.DatabaseHelper;

public class SignUpPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signUpButton;
    private JLabel messageLabel;

    public SignUpPanel(ActionListener signUpSuccessListener) {
        setLayout(new GridBagLayout());
        setBackground(UniSeatView.BACKGROUND_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(UniSeatView.TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        userLabel.setForeground(UniSeatView.TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 1;
        add(userLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        passLabel.setForeground(UniSeatView.TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 2;
        add(passLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(messageLabel, gbc);

        signUpButton = UniSeatView.createModernButton("Sign Up", UniSeatView.SUCCESS_COLOR);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(signUpButton, gbc);

        signUpButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if(username.isEmpty() || password.isEmpty()){
                messageLabel.setText("Please fill all the fields.");
                return;
            }
            if(DatabaseHelper.userExists(username)){
                messageLabel.setText("Username already exists!");
            } else {
                DatabaseHelper.insertUser(username, password);
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Sign up successful! You may now log in.");
                if (signUpSuccessListener != null)
                    signUpSuccessListener.actionPerformed(null);
            }
        });
    }
}
