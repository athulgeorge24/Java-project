package view;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class UniSeatView {
    public static final Color BACKGROUND_DARK = new Color(0, 0, 0);
    public static final Color SURFACE_DARK = new Color(20, 20, 20);
    public static final Color SURFACE_LIGHT = new Color(60, 60, 70);
    public static final Color PRIMARY_COLOR = new Color(103, 58, 183);
    public static final Color SECONDARY_COLOR = new Color(0, 150, 136);
    public static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    public static final Color ERROR_COLOR = new Color(244, 67, 54);
    public static final Color TEXT_PRIMARY = new Color(240, 240, 240);
    public static final Color TEXT_SECONDARY = new Color(180, 180, 180);
    
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel sidebar;
    
    public UniSeatView() {
        initializeFrame();
        applyDarkTheme();
        createSidebar();
        setupMainPanel();
    }
    
    private void initializeFrame() {
        frame = new JFrame("UniSeat - Universal Smart Seating Arrangement System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
    }
    
    private void applyDarkTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put("Panel.background", BACKGROUND_DARK);
            UIManager.put("Button.background", SURFACE_LIGHT);
            UIManager.put("Button.foreground", TEXT_PRIMARY);
            UIManager.put("Label.foreground", TEXT_PRIMARY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createSidebar() {
        sidebar = new JPanel();
        sidebar.setBackground(SURFACE_DARK);
        sidebar.setLayout(new GridLayout(6, 1, 5, 5));
        sidebar.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
    }
    
    private void setupMainPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BACKGROUND_DARK);
        
        // Setup header
        setupHeader();
        
        // Setup frame layout
        frame.setLayout(new BorderLayout());
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);
    }
    
    private void setupHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(SURFACE_DARK);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, SURFACE_LIGHT),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));
        
        JLabel title = new JLabel("UniSeat Admin Panel");
        title.setForeground(TEXT_PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(SURFACE_DARK);
        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel userLabel = new JLabel("Welcome, Admin");
        userLabel.setForeground(TEXT_SECONDARY);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userPanel.add(userIcon);
        userPanel.add(userLabel);
        
        header.add(title, BorderLayout.WEST);
        header.add(userPanel, BorderLayout.EAST);
        
        frame.add(header, BorderLayout.NORTH);
    }
    
    // Public methods
    public JFrame getFrame() { return frame; }
    public CardLayout getCardLayout() { return cardLayout; }
    public JPanel getMainPanel() { return mainPanel; }
    public JPanel getSidebar() { return sidebar; }
    
    public void showPanel(String panelName) { cardLayout.show(mainPanel, panelName); }
    public void setVisible(boolean visible) { frame.setVisible(visible); }
    
    // Utility methods
    public static JButton createModernButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}