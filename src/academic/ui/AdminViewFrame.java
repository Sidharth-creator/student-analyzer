package ui;

import database.DataManager;
import models.ExamSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class AdminViewFrame extends JFrame {
    private JTable adminTable;
    private DefaultTableModel tableModel;
    private DataManager dataManager;
    private StudentInfoDialog parentDialog;

    public AdminViewFrame(StudentInfoDialog parentDialog, DataManager dataManager) {
        this.dataManager = dataManager;
        this.parentDialog = parentDialog;
        
        setTitle("Admin: All Student History (Latest Session)");
        setSize(700, 500);
        setLocationRelativeTo(parentDialog);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

        initComponents();
        loadStudentData();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Table setup
        String[] columnNames = {"Name", "Student ID", "Latest Year", "Latest Semester", "Action"};
        tableModel = new DefaultTableModel(columnNames, 0);
        adminTable = new JTable(tableModel);
        
        // Add a custom button renderer/editor for the "Action" column
        adminTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        adminTable.getColumn("Action").setCellEditor(new ButtonEditor(new JTextField(), this));

        mainPanel.add(new JScrollPane(adminTable), BorderLayout.CENTER);

        // Buttons at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addSessionButton = new JButton("Add New Session");
        JButton closeButton = new JButton("Close Admin View");
        
        // Reuses the StudentInfoDialog method to open the Add form
        addSessionButton.addActionListener(e -> parentDialog.openAddExamDialog());
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(addSessionButton);
        buttonPanel.add(closeButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    private void loadStudentData() {
        // Clear previous data
        tableModel.setRowCount(0); 

        List<ExamSession> latestSessions = dataManager.getAllLatestSessions();
        
        for (ExamSession session : latestSessions) {
            tableModel.addRow(new Object[]{
                session.getName(),
                session.getId(),
                session.getYear(),
                session.getSemester(),
                "View/Analyze"
            });
        }
    }
    
    // Renders the button visually
    class ButtonRenderer extends DefaultCellEditor implements TableCellRenderer {
        private final JButton button;

        public ButtonRenderer() {
            super(new JTextField());
            setClickCountToStart(1);
            this.button = new JButton("View/Analyze");
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return button;
        }
    }

    // Handles the button click action
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private AdminViewFrame adminFrame;

        public ButtonEditor(JTextField textField, AdminViewFrame adminFrame) {
            super(textField);
            this.adminFrame = adminFrame;
            setClickCountToStart(1);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "View/Analyze" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Get the student ID from the second column
                String studentId = (String) adminTable.getModel().getValueAt(adminTable.getEditingRow(), 1);
                
                // Retrieve all sessions for that student
                List<ExamSession> sessions = dataManager.getAllSessionsForStudent(studentId);
                if (!sessions.isEmpty()) {
                    // Open the MainFrame (Analyzer)
                    MainFrame mainFrame = new MainFrame(sessions);
                    mainFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "No records found for ID: " + studentId, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
