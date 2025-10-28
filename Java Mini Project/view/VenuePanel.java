package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VenuePanel {
    private JPanel panel;
    private JSpinner rowsSpinner;
    private JSpinner colsSpinner;
    private JButton generateGridBtn;
    private JPanel gridContainer;
    
    public VenuePanel() {
        createPanel();
    }
    
    private void createPanel() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(UniSeatView.BACKGROUND_DARK);

        JLabel title = new JLabel("Venue Layout Designer", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(UniSeatView.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(UniSeatView.BACKGROUND_DARK);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        rowsSpinner = createDarkSpinner(5, 1, 20);
        colsSpinner = createDarkSpinner(8, 1, 50);
        generateGridBtn = UniSeatView.createModernButton("Generate Grid", UniSeatView.SUCCESS_COLOR);

        controlPanel.add(new JLabel("Rows:"));
        controlPanel.add(rowsSpinner);
        controlPanel.add(new JLabel("Columns:"));
        controlPanel.add(colsSpinner);
        controlPanel.add(generateGridBtn);

        // Grid container with scroll
        gridContainer = new JPanel(new BorderLayout());
        gridContainer.setBackground(UniSeatView.BACKGROUND_DARK);

        panel.add(title, BorderLayout.NORTH);
        panel.add(controlPanel, BorderLayout.SOUTH);
        panel.add(gridContainer, BorderLayout.CENTER);
    }
    
    private JSpinner createDarkSpinner(int value, int min, int max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, 1));
        spinner.setBackground(UniSeatView.SURFACE_DARK);
        spinner.setForeground(UniSeatView.TEXT_PRIMARY);
        spinner.setBorder(BorderFactory.createLineBorder(UniSeatView.SURFACE_LIGHT));
        return spinner;
    }
    
    public JPanel getPanel() { return panel; }
    public JSpinner getRowsSpinner() { return rowsSpinner; }
    public JSpinner getColsSpinner() { return colsSpinner; }
    public JButton getGenerateGridBtn() { return generateGridBtn; }
    public JPanel getGridContainer() { return gridContainer; }
    
    public void setGenerateGridListener(ActionListener listener) {
        generateGridBtn.addActionListener(listener);
    }
    
    public void updateGridLayout(JPanel gridPanel) {
        gridContainer.removeAll();
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        gridContainer.add(scrollPane, BorderLayout.CENTER);
        gridContainer.revalidate();
        gridContainer.repaint();
    }
}