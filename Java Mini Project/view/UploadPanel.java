package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.DateUtil;

import database.DatabaseHelper;

public class UploadPanel {
    private JPanel panel;
    private JButton uploadBtn;
    private JLabel statusLabel;

    public UploadPanel() {
        createPanel();
        setUploadListener(e -> handleFileUpload());
    }

    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UniSeatView.BACKGROUND_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(80, 150, 80, 150));

        JLabel title = new JLabel("Upload Venue, Event & Participants Data");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(UniSeatView.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel uploadCard = new JPanel();
        uploadCard.setLayout(new BoxLayout(uploadCard, BoxLayout.Y_AXIS));
        uploadCard.setBackground(UniSeatView.SURFACE_DARK);
        uploadCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UniSeatView.SURFACE_LIGHT, 1),
            BorderFactory.createEmptyBorder(40, 30, 40, 30)
        ));
        uploadCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadCard.setMaximumSize(new Dimension(500, 300));

        JLabel uploadIcon = new JLabel("");
        uploadIcon.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        uploadIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel uploadText = new JLabel("Click the files to upload here ");
        uploadText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        uploadText.setForeground(UniSeatView.TEXT_SECONDARY);
        uploadText.setAlignmentX(Component.CENTER_ALIGNMENT);

        uploadBtn = UniSeatView.createModernButton("Choose File", UniSeatView.PRIMARY_COLOR);
        uploadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusLabel = new JLabel("No file selected.");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(UniSeatView.TEXT_SECONDARY);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        uploadCard.add(uploadIcon);
        uploadCard.add(Box.createVerticalStrut(20));
        uploadCard.add(uploadText);
        uploadCard.add(Box.createVerticalStrut(20));
        uploadCard.add(uploadBtn);
        uploadCard.add(Box.createVerticalStrut(15));
        uploadCard.add(statusLabel);

        panel.add(title);
        panel.add(Box.createVerticalStrut(40));
        panel.add(uploadCard);
    }

    public JPanel getPanel() { return panel; }
    public JButton getUploadBtn() { return uploadBtn; }
    public JLabel getStatusLabel() { return statusLabel; }

    public void setUploadListener(ActionListener listener) {
        uploadBtn.addActionListener(listener);
    }

    public void updateStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    // Handler method for file upload - accepts .xls and .xlsx and handles common cell types
    private void handleFileUpload() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Excel Files (.xls, .xlsx)", "xls", "xlsx"));
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setDialogTitle("Select Excel file with participants");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = chooser.showOpenDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            updateStatus("Processing: " + file.getName(), Color.ORANGE);

            int rowCount = 0;
            try (FileInputStream fis = new FileInputStream(file);
                 Workbook workbook = WorkbookFactory.create(fis)) { // handles xls and xlsx
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    if (row == null) continue;
                    if (row.getRowNum() == 0) continue; // skip header row

                    String name = getCellValue(row.getCell(0)).trim();
                    String email = getCellValue(row.getCell(1)).trim();
                    String seatText = getCellValue(row.getCell(2)).trim();

                    // skip entirely empty rows
                    if (name.isEmpty() && email.isEmpty() && seatText.isEmpty()) continue;

                    int seatNumber = 0;
                    if (!seatText.isEmpty()) {
                        try {
                            // handle values like "12.0" or numeric cells converted to string
                            double d = Double.parseDouble(seatText);
                            seatNumber = (int) d;
                        } catch (NumberFormatException nfe) {
                            // invalid seat number: skip this row and log
                            System.err.println("Skipping row " + (row.getRowNum()+1) + " due to invalid seat: " + seatText);
                            continue;
                        }
                    }

                    DatabaseHelper.insertParticipant(name, email, seatNumber);
                    rowCount++;
                }
                updateStatus("Success! Imported " + rowCount + " participants.", Color.GREEN);
            } catch (Exception ex) {
                ex.printStackTrace();
                updateStatus("Error importing file: " + ex.getMessage(), Color.RED);
            }
        }
    }

    // Helper to safely get String cell values (handles STRING, NUMERIC, BOOLEAN, FORMULA, BLANK)
    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    } else {
                        double d = cell.getNumericCellValue();
                        if (d == Math.floor(d)) return String.valueOf((long) d);
                        return String.valueOf(d);
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    // evaluate formula result if possible
                    FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    CellValue value = evaluator.evaluate(cell);
                    if (value == null) return "";
                    switch (value.getCellType()) {
                        case STRING: return value.getStringValue();
                        case NUMERIC:
                            double d = value.getNumberValue();
                            if (d == Math.floor(d)) return String.valueOf((long) d);
                            return String.valueOf(d);
                        case BOOLEAN: return String.valueOf(value.getBooleanValue());
                        default: return "";
                    }
                case BLANK:
                case _NONE:
                case ERROR:
                default:
                    return "";
            }
        } catch (Exception e) {
            return cell.toString();
        }
    }
}
