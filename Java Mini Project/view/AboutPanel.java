package view;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AboutPanel {
    private JPanel panel;
    
    public AboutPanel() {
        createPanel();
    }
    
    private void createPanel() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(UniSeatView.BACKGROUND_DARK);
        
        JTextArea about = new JTextArea(
            "\n"+
            "UniSeat - Universal Smart Seating Arrangement System\n\n" +
            "Developed using Java Swing, JSP, and MySQL.\n\n" +
            "Features:\n" +
            "• Automated seating management for exams, events, and meetings\n" +
            "• Flexible venue layout designer\n" +
            "• Configurable seating constraints\n" +
            "• CSV participant data import\n" +
            "• Optimized seating algorithms\n\n" +
            "Supported Scenarios:\n" +
            "• Examination Halls\n" +
            "• Conference Events\n" +
            "• Wedding Arrangements\n" +
            "• Training Sessions\n" +
            "• Theatre Booking\n" +
            "• Public Transport Seating\n" +
            "• Concert Event Booking\n\n"
        );
        about.setEditable(false);
        about.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        about.setForeground(UniSeatView.TEXT_PRIMARY);
        about.setBackground(UniSeatView.SURFACE_DARK);
        about.setMargin(new Insets(0,0,0,0));
        about.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UniSeatView.SURFACE_DARK, 1),
            BorderFactory.createEmptyBorder(0,0,0,0)
        ));
        
        JScrollPane scrollPane = new JScrollPane(about);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        scrollPane.getViewport().setBackground(UniSeatView.SURFACE_DARK);
        
        panel.add(scrollPane, BorderLayout.CENTER);
    }
    
    public JPanel getPanel() { return panel; }
}