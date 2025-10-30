package ui;

import models.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private GraphPanel graphPanel;
    private MarksTablePanel marksTablePanel;

    public MainFrame(List<ExamSession> sessions) {
        setTitle("Student Performance Analyzer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed from EXIT_ON_CLOSE
        setLocationRelativeTo(null);

        // Set Nimbus L&F
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        initComponents(sessions);
    }

    private void initComponents(List<ExamSession> sessions) {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // Header
        String studentName = sessions.isEmpty() ? "Student" : sessions.get(0).getName();
        JLabel headerLabel = new JLabel(studentName + "'s Performance Analysis", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        graphPanel = new GraphPanel();
        marksTablePanel = new MarksTablePanel();
        // Add Detailed Marks tab first
        tabbedPane.addTab("Detailed Marks", marksTablePanel);
        tabbedPane.addTab("Performance Graph", graphPanel);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Populate data
        graphPanel.updateData(sessions);
        marksTablePanel.updateData(sessions);
        
        // Bottom button panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Logout");
        bottomPanel.add(closeButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(e -> {
            this.dispose();
            // Re-open the initial dialog (The main entry point)
            StudentInfoDialog.main(null);
        });
    }
}
