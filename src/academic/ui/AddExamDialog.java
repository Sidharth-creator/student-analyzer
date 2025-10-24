package academic.ui;

import academic.data.DataManager;
import academic.models.ExamSession;

import javax.swing.*;
import java.awt.*;

public class AddExamDialog extends JDialog {

    private JTextField nameField, idField, yearField, semesterField;
    private JTextField subject1Field, sub1Int1Field, sub1Int2Field, sub1SemField;
    private JTextField subject2Field, sub2Int1Field, sub2Int2Field, sub2SemField;
    private JTextField subject3Field, sub3Int1Field, sub3Int2Field, sub3SemField;
    
    private DataManager dataManager;

    public AddExamDialog(Frame owner) {
        super(owner, "Add New Exam Session", true);
        setSize(500, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        dataManager = new DataManager();

        // Main form panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Student Info
        formPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        formPanel.add(yearField);

        formPanel.add(new JLabel("Semester:"));
        semesterField = new JTextField();
        formPanel.add(semesterField);

        // Separator
        formPanel.add(new JSeparator());
        formPanel.add(new JSeparator());

        // Subject 1
        formPanel.add(new JLabel("Subject 1 Name:"));
        subject1Field = new JTextField();
        formPanel.add(subject1Field);
        formPanel.add(new JLabel("Internal 1 Marks:"));
        sub1Int1Field = new JTextField();
        formPanel.add(sub1Int1Field);
        formPanel.add(new JLabel("Internal 2 Marks:"));
        sub1Int2Field = new JTextField();
        formPanel.add(sub1Int2Field);
        formPanel.add(new JLabel("Semester Marks:"));
        sub1SemField = new JTextField();
        formPanel.add(sub1SemField);

        // Separator
        formPanel.add(new JSeparator());
        formPanel.add(new JSeparator());
        
        // Subject 2
        formPanel.add(new JLabel("Subject 2 Name:"));
        subject2Field = new JTextField();
        formPanel.add(subject2Field);
        formPanel.add(new JLabel("Internal 1 Marks:"));
        sub2Int1Field = new JTextField();
        formPanel.add(sub2Int1Field);
        formPanel.add(new JLabel("Internal 2 Marks:"));
        sub2Int2Field = new JTextField();
        formPanel.add(sub2Int2Field);
        formPanel.add(new JLabel("Semester Marks:"));
        sub2SemField = new JTextField();
        formPanel.add(sub2SemField);

        // Separator
        formPanel.add(new JSeparator());
        formPanel.add(new JSeparator());

        // Subject 3
        formPanel.add(new JLabel("Subject 3 Name:"));
        subject3Field = new JTextField();
        formPanel.add(subject3Field);
        formPanel.add(new JLabel("Internal 1 Marks:"));
        sub3Int1Field = new JTextField();
        formPanel.add(sub3Int1Field);
        formPanel.add(new JLabel("Internal 2 Marks:"));
        sub3Int2Field = new JTextField();
        formPanel.add(sub3Int2Field);
        formPanel.add(new JLabel("Semester Marks:"));
        sub3SemField = new JTextField();
        formPanel.add(sub3SemField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveStudent());

        add(new JScrollPane(formPanel), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveStudent() {
        // Basic validation
        if (nameField.getText().trim().isEmpty() || idField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student Name and ID cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ExamSession student = new ExamSession(
                nameField.getText().trim(),
                idField.getText().trim(),
                Integer.parseInt(yearField.getText().trim()),
                Integer.parseInt(semesterField.getText().trim()),
                subject1Field.getText().trim(),
                Integer.parseInt(sub1Int1Field.getText().trim()),
                Integer.parseInt(sub1Int2Field.getText().trim()),
                Integer.parseInt(sub1SemField.getText().trim()),
                subject2Field.getText().trim(),
                Integer.parseInt(sub2Int1Field.getText().trim()),
                Integer.parseInt(sub2Int2Field.getText().trim()),
                Integer.parseInt(sub2SemField.getText().trim()),
                subject3Field.getText().trim(),
                Integer.parseInt(sub3Int1Field.getText().trim()),
                Integer.parseInt(sub3Int2Field.getText().trim()),
                Integer.parseInt(sub3SemField.getText().trim())
            );

            if (dataManager.addExamSession(student)) {
                JOptionPane.showMessageDialog(this, "Exam Session added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the dialog
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student. Check console for errors.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for year, semester, and marks.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
