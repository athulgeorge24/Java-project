package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DashboardPanel {
    private JPanel panel;
    private JButton[] actionButtons;
    
    public DashboardPanel() {
        createPanel();
    }
    
    private void createPanel() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(UniSeatView.BACKGROUND_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(UniSeatView.BACKGROUND_DARK);

        // Title
        JLabel title = new JLabel("Welcome to UniSeat");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(UniSeatView.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Universal Smart Seating Arrangement System");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(UniSeatView.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(subtitle);
        contentPanel.add(Box.createVerticalStrut(40));

        // Quick actions
        JPanel quickActions = createQuickActionsPanel();
        contentPanel.add(quickActions);

        panel.add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createQuickActionsPanel() {
        JPanel quickActions = new JPanel(new GridLayout(2, 2, 15, 15));
        quickActions.setBackground(UniSeatView.BACKGROUND_DARK);
        quickActions.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        quickActions.setMaximumSize(new Dimension(800, 200));
        
        String[] actionNames = {"Upload New Participants", "Design Venue", "Generate Plan", "About Section"};
        Color[] colors = {
            UniSeatView.PRIMARY_COLOR, 
            UniSeatView.SECONDARY_COLOR, 
            UniSeatView.SUCCESS_COLOR, 
            new Color(255, 152, 0)
        };
        
        actionButtons = new JButton[actionNames.length];
        
        for (int i = 0; i < actionNames.length; i++) {
            actionButtons[i] = UniSeatView.createModernButton(actionNames[i], colors[i]);
            quickActions.add(actionButtons[i]);
        }
        
        return quickActions;
    }
    
    public JPanel getPanel() { return panel; }
    public JButton[] getActionButtons() { return actionButtons; }
    
    public void updateStatistics(int totalSeats, int availableSeats, int occupiedSeats) {
        // Remove existing stats panel if any
        Component[] components = panel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel contentPanel = (JPanel) comp;
                Component[] contentComps = contentPanel.getComponents();
                for (int i = 0; i < contentComps.length; i++) {
                    if (contentComps[i] instanceof JPanel && i == 3) { // Stats panel is at index 3
                        contentPanel.remove(i);
                        break;
                    }
                }
            }
        }
        
        // Add updated stats panel at position 3
        JPanel statsPanel = createStatsPanel(totalSeats, availableSeats, occupiedSeats);
        ((JPanel) panel.getComponent(0)).add(statsPanel, 3);
        panel.revalidate();
        panel.repaint();
    }
    
    private JPanel createStatsPanel(int totalSeats, int availableSeats, int occupiedSeats) {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        statsPanel.setBackground(UniSeatView.BACKGROUND_DARK);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        statsPanel.setMaximumSize(new Dimension(800, 120));

        String[] stats = {"Total Seats", "Available", "Occupied"};
        String[] values = {String.valueOf(totalSeats), String.valueOf(availableSeats), String.valueOf(occupiedSeats)};
        Color[] statColors = {UniSeatView.PRIMARY_COLOR, UniSeatView.SUCCESS_COLOR, UniSeatView.ERROR_COLOR};

        for (int i = 0; i < stats.length; i++) {
            JPanel statCard = createStatCard(stats[i], values[i], statColors[i]);
            statsPanel.add(statCard);
        }
        
        return statsPanel;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UniSeatView.SURFACE_DARK);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UniSeatView.SURFACE_LIGHT, 1),
            BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(UniSeatView.TEXT_SECONDARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(titleLabel);

        return card;
    }
}

