package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SeatingPanel {
    private JPanel panel;
    private JCheckBox shuffleCheck;
    private JCheckBox distanceCheck;
    private JSpinner distanceSpinner;
    private JTextArea resultArea;
    private JButton generateBtn;
    
    public SeatingPanel() {
        createPanel();
    }
    
    private void createPanel() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(UniSeatView.BACKGROUND_DARK);
        
        JLabel title = new JLabel("Generate Optimized Seating Plan", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(UniSeatView.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Constraint panel
        JPanel constraintPanel = new JPanel(new FlowLayout());
        constraintPanel.setBackground(UniSeatView.SURFACE_DARK);
        constraintPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UniSeatView.SURFACE_LIGHT, 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        shuffleCheck = createCheckbox("Random Shuffle", true);
        distanceCheck = createCheckbox("Maintain Distance", false);
        distanceSpinner = createDarkSpinner(1, 1, 3);
        
        constraintPanel.add(shuffleCheck);
        constraintPanel.add(Box.createHorizontalStrut(10));
        constraintPanel.add(distanceCheck);
        constraintPanel.add(Box.createHorizontalStrut(10));
        constraintPanel.add(new JLabel("Min Seats:"));
        constraintPanel.add(distanceSpinner);

        // Result area
        resultArea = new JTextArea("Seating Plan Output will appear here...\n\nAvailable actions:\n1. Configure constraints above\n2. Click 'Generate Plan' to create seating arrangement\n3. Results will show participant assignments");
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        resultArea.setBackground(UniSeatView.SURFACE_DARK);
        resultArea.setForeground(UniSeatView.TEXT_PRIMARY);
        resultArea.setMargin(new Insets(15, 15, 15, 15));
        resultArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UniSeatView.SURFACE_LIGHT, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Generate button
        generateBtn = UniSeatView.createModernButton("Generate Seating Plan", UniSeatView.SUCCESS_COLOR);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UniSeatView.BACKGROUND_DARK);
        buttonPanel.add(generateBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(constraintPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JCheckBox createCheckbox(String text, boolean selected) {
        JCheckBox checkbox = new JCheckBox(text, selected);
        checkbox.setBackground(UniSeatView.BACKGROUND_DARK);
        checkbox.setForeground(UniSeatView.TEXT_PRIMARY);
        checkbox.setFocusPainted(false);
        return checkbox;
    }
    
    private JSpinner createDarkSpinner(int value, int min, int max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, 1));
        spinner.setBackground(UniSeatView.SURFACE_DARK);
        spinner.setForeground(UniSeatView.TEXT_PRIMARY);
        spinner.setBorder(BorderFactory.createLineBorder(UniSeatView.SURFACE_LIGHT));
        return spinner;
    }
    
    public JPanel getPanel() { return panel; }
    public JCheckBox getShuffleCheck() { return shuffleCheck; }
    public JCheckBox getDistanceCheck() { return distanceCheck; }
    public JSpinner getDistanceSpinner() { return distanceSpinner; }
    public JTextArea getResultArea() { return resultArea; }
    public JButton getGenerateBtn() { return generateBtn; }
    
    public void setGenerateListener(ActionListener listener) {
        generateBtn.addActionListener(listener);
    }
    
    public void updateResult(String result) {
        resultArea.setText(result);
    }
    
    public void setGenerateButtonEnabled(boolean enabled) {
        generateBtn.setEnabled(enabled);
    }
    
    public void setGenerateButtonText(String text) {
        generateBtn.setText(text);
    }
}