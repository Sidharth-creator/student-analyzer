package ui;

import database.DataManager;
import models.ExamSession;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentInfoDialog extends JDialog {
    private JTextField studentIdField;
    private DataManager dataManager;

    public StudentInfoDialog(Frame owner) {
        super(owner, "Student Analyzer", true);
        dataManager = new DataManager();
        
        // **CRITICAL UPDATE: Initialize the database immediately**
        try {
            dataManager.initializeDatabase();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "FATAL ERROR: Could not initialize database. Check if MySQL server is running and credentials are correct in DataManager.", 
                "Database Connection Error", JOptionPane.ERROR_MESSAGE);
            // Exit the application if the database cannot be initialized
            System.exit(1);
        }

        // Set Nimbus L&F (Kept for consistency)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Fallback
        }

        setTitle("Student Performance Analyzer");
        setSize(400, 150);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        initComponent();
    }

    private void initComponent() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Enter Student ID to Analyze or Add New Session:"), gbc);

        // Text Field
        gbc.gridy = 1;
        studentIdField = new JTextField(20);
        panel.add(studentIdField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton analyzeButton = new JButton("Analyze");
        JButton addButton = new JButton("Add Exam Session");
        JButton quitButton = new JButton("Quit");
        buttonPanel.add(analyzeButton);
        buttonPanel.add(addButton);
        buttonPanel.add(quitButton);
        
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel, BorderLayout.CENTER);

        // --- Action Listeners ---
        analyzeButton.addActionListener(e -> analyzeStudent());
        addButton.addActionListener(e -> openAddExamDialog());
        quitButton.addActionListener(e -> System.exit(0));
    }

    private void analyzeStudent() {
        String studentId = studentIdField.getText().trim();
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Student ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<ExamSession> sessions = dataManager.getAllSessionsForStudent(studentId);
        if (sessions.isEmpty()) {
            // Updated error message to hint at potential database issues
            JOptionPane.showMessageDialog(this, 
                "No records found for Student ID: " + studentId + 
                "\n\n(If the student should exist, check the console for a database connection error.)", 
                "No Data/Database Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Open the MainFrame with the data
            MainFrame mainFrame = new MainFrame(sessions);
            mainFrame.setVisible(true);
            this.dispose(); // Close this dialog
        }
    }

    private void openAddExamDialog() {
        // **FIX: Pass 'this' as the owner to correctly establish the dialog hierarchy**
        AddExamDialog addExamDialog = new AddExamDialog(this); 
        addExamDialog.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentInfoDialog dialog = new StudentInfoDialog(null);
            dialog.setVisible(true);
        });
    }
}
