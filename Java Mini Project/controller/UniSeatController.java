package controller;
import view.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.UniSeatModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UniSeatController {
    private UniSeatModel model;
    private UniSeatView view;
    private DashboardPanel dashboardPanel;
    private UploadPanel uploadPanel;
    private VenuePanel venuePanel;
    private SeatingPanel seatingPanel;
    private AboutPanel aboutPanel;
    
    public UniSeatController(UniSeatModel model, UniSeatView view) {
        this.model = model;
        this.view = view;
        initializePanels();
        setupSidebar();
        setupEventHandlers();
        showDashboard();
    }
    
    private void initializePanels() {
        // Create all panels
        dashboardPanel = new DashboardPanel();
        uploadPanel = new UploadPanel();
        venuePanel = new VenuePanel();
        seatingPanel = new SeatingPanel();
        aboutPanel = new AboutPanel();
        
        // Add panels to main view
        view.getMainPanel().add(dashboardPanel.getPanel(), "Dashboard");
        view.getMainPanel().add(uploadPanel.getPanel(), "Upload");
        view.getMainPanel().add(venuePanel.getPanel(), "Venue");
        view.getMainPanel().add(seatingPanel.getPanel(), "Seating");
        view.getMainPanel().add(aboutPanel.getPanel(), "About");
    }
    
    private void setupSidebar() {
        String[] names = {" Dashboard", " Upload Participants", " Venue Layout", " Generate Seating Plan", " About", " Exit"};
        
        for (int i = 0; i < names.length; i++) {
            JButton button = new JButton(names[i]);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setFocusPainted(false);
            button.setBackground(UniSeatView.SURFACE_DARK);
            button.setForeground(UniSeatView.TEXT_SECONDARY);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Hover effect
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    button.setBackground(UniSeatView.SURFACE_LIGHT);
                    button.setForeground(UniSeatView.TEXT_PRIMARY);
                }
                public void mouseExited(java.awt.event.MouseEvent e) {
                    button.setBackground(UniSeatView.SURFACE_DARK);
                    button.setForeground(UniSeatView.TEXT_SECONDARY);
                }
            });

            final int index = i;
            button.addActionListener(e -> {
                String[] panelNames = {"Dashboard", "Upload", "Venue", "Seating", "About"};
                if (index < panelNames.length) {
                    view.showPanel(panelNames[index]);
                } else {
                    System.exit(0);
                }
            });

            view.getSidebar().add(button);
        }
    }
    
    private void setupEventHandlers() {
        // Dashboard quick actions
        JButton[] actionButtons = dashboardPanel.getActionButtons();
        for (int i = 0; i < actionButtons.length; i++) {
            final int index = i;
            actionButtons[i].addActionListener(e -> {
                String[] panelNames = {"Upload", "Venue", "Seating", "About"};
                view.showPanel(panelNames[index]);
            });
        }
        
        // Upload panel events
        uploadPanel.setUploadListener(e -> handleFileUpload());
        
        // Venue panel events
        venuePanel.setGenerateGridListener(e -> generateSeatGrid());
        
        // Seating panel events
        seatingPanel.setGenerateListener(e -> generateSeatingPlan());
    }
    
    private void handleFileUpload() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("CSV and Excel Files", "csv", "xlsx"));
    
    int result = fileChooser.showOpenDialog(uploadPanel.getPanel());
    if (result == JFileChooser.APPROVE_OPTION) {
        String fileName = fileChooser.getSelectedFile().getName();
        
        // Simulate saving to database
        boolean saved = model.saveVenueToDatabase("Uploaded Venue", 10, 15);
        
        if (saved) {
            uploadPanel.updateStatus("File uploaded and saved to database: " + fileName, UniSeatView.SUCCESS_COLOR);
            
            int recordCount = model.simulateCSVProcessing();
            JOptionPane.showMessageDialog(uploadPanel.getPanel(), 
                "Successfully processed " + recordCount + " participants!\nData saved to database.", 
                "Upload Complete", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            uploadPanel.updateStatus("Upload failed - database error", UniSeatView.ERROR_COLOR);
        }
    }
}
    
    private void generateSeatGrid() {
        int rows = (Integer) venuePanel.getRowsSpinner().getValue();
        int cols = (Integer) venuePanel.getColsSpinner().getValue();
        
        model.generateSeatLayout(rows, cols);
        
        // Create visual grid
        JPanel gridPanel = createVisualGrid(rows, cols);
        venuePanel.updateGridLayout(gridPanel);
        
        // Update dashboard statistics
        updateDashboardStatistics();
    }
    
    private JPanel createVisualGrid(int rows, int cols) {
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols, 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        gridPanel.setBackground(UniSeatView.BACKGROUND_DARK);

        for (model.Seat seat : model.getSeatList()) {
            JButton seatButton = createSeatButton(seat);
            gridPanel.add(seatButton);
        }

        return gridPanel;
    }
    
    private JButton createSeatButton(model.Seat seat) {
        JButton button = new JButton("S" + seat.getNumber());
        button.setBackground(seat.isAvailable() ? 
            new Color(52, 152, 219) : new Color(231, 76, 60));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(70, 40));
        
        button.addActionListener(e -> {
            model.toggleSeatAvailability(seat.getNumber());
            button.setBackground(seat.isAvailable() ? 
                new Color(52, 152, 219) : new Color(231, 76, 60));
            button.setText(seat.isAvailable() ? "S" + seat.getNumber() : "X");
            updateDashboardStatistics();
        });
        
        return button;
    }
    
    private void generateSeatingPlan() {
        seatingPanel.setGenerateButtonEnabled(false);
        seatingPanel.setGenerateButtonText("🔄 Generating...");
        
        Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean shuffle = seatingPanel.getShuffleCheck().isSelected();
                boolean maintainDistance = seatingPanel.getDistanceCheck().isSelected();
                int minDistance = (Integer) seatingPanel.getDistanceSpinner().getValue();
                
                String plan = createSeatingPlan(shuffle, maintainDistance, minDistance);
                seatingPanel.updateResult(plan);
                
                seatingPanel.setGenerateButtonEnabled(true);
                seatingPanel.setGenerateButtonText("Generate Seating Plan");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private String createSeatingPlan(boolean shuffle, boolean maintainDistance, int minDistance) {
        StringBuilder plan = new StringBuilder();
        plan.append("=== GENERATED SEATING PLAN ===\n\n");
        plan.append("Constraints Applied:\n");
        plan.append("- Random Shuffle: ").append(shuffle ? "Yes" : "No").append("\n");
        plan.append("- Maintain Distance: ").append(maintainDistance ? 
            "Yes (Min: " + minDistance + " seat(s))" : "No").append("\n\n");
        
        plan.append("Seat Assignments:\n");
        plan.append("-----------------\n");
        
        String[] participants = {"Sarju S", "Angelina", "Aswathi", "Athul", "Kritthik", 
                                "Dawn", "Johns", "Noel", "Cyril", "Parvathy"};
        
        for (int i = 0; i < Math.min(10, participants.length); i++) {
            plan.append(String.format("S%d - %s\n", i + 1, participants[i]));
        }
        
        plan.append("\n... and ").append(model.simulateCSVProcessing() - 10).append(" more participants");
        plan.append("\n\nTotal Available Seats: ").append(model.getAvailableSeats());
        plan.append("\nTotal Participants: ").append(participants.length);
        
        return plan.toString();
    }
    
    private void updateDashboardStatistics() {
        dashboardPanel.updateStatistics(
            model.getTotalSeats(),
            model.getAvailableSeats(),
            model.getOccupiedSeats()
        );
    }
    
    private void showDashboard() {
        view.showPanel("Dashboard");
        updateDashboardStatistics();
    }
    
    public void showApplication() {
        view.setVisible(true);
    }

    private void testDatabaseConnection() {
    if (model.testDatabaseConnection()) {
        JOptionPane.showMessageDialog(view.getFrame(), 
            "✅ Database connection successful!", 
            "Database Status", 
            JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(view.getFrame(), 
            "❌ Database connection failed!\nPlease check XAMPP MySQL is running.", 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
}


