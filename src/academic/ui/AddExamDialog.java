package ui;

import database.DataManager;
import models.*;

import javax.swing.*;
import java.awt.*;

public class AddExamDialog extends JDialog {

    private JTextField nameField, idField, yearField, semesterField;
    private JTextField subject1Field, sub1Int1Field, sub1Int2Field, sub1SemField;
    private JTextField subject2Field, sub2Int1Field, sub2Int2Field, sub2SemField;
    private JTextField subject3Field, sub3Int1Field, sub3Int2Field, sub3SemField;

    private DataManager dataManager;

    public AddExamDialog(StudentInfoDialog studentInfoDialog) {
        super(studentInfoDialog, "Add New Exam Session", true);
        setSize(500, 400);
        setLocationRelativeTo(studentInfoDialog);
        setLayout(new BorderLayout(10, 10));

        dataManager = new DataManager();

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
        try {
            // Step 1: Validate Name
            String name = nameField.getText().trim();
            if (name.isEmpty()) throw new ValidationException("Student Name cannot be empty.");
            if (!ExamSessionValidation.isValidName(name))
                throw new ValidationException("Invalid Name: only letters and spaces allowed.");

            // Step 2: Validate ID
            String id = idField.getText().trim();
            if (id.isEmpty()) throw new ValidationException("Student ID cannot be empty.");

            // Step 3: Parse numeric fields individually and validate ranges
            int year = parseInteger(yearField.getText(), "Year");
            if (!ExamSessionValidation.isValidYear(year))
                throw new ValidationException("Invalid Year: must be between 1 and 4.");

            int semester = parseInteger(semesterField.getText(), "Semester");
            if (!ExamSessionValidation.isValidSemester(semester))
                throw new ValidationException("Invalid Semester: must be between 1 and 8.");

            int sub1Int1 = parseInteger(sub1Int1Field.getText(), "Subject 1 Internal 1");
            if (!ExamSessionValidation.isValidInternalMark(sub1Int1))
                throw new ValidationException("Subject 1 Internal 1 must be between 0 and 40.");

            int sub1Int2 = parseInteger(sub1Int2Field.getText(), "Subject 1 Internal 2");
            if (!ExamSessionValidation.isValidInternalMark(sub1Int2))
                throw new ValidationException("Subject 1 Internal 2 must be between 0 and 40.");

            int sub1Sem = parseInteger(sub1SemField.getText(), "Subject 1 Semester");
            if (!ExamSessionValidation.isValidSemMark(sub1Sem))
                throw new ValidationException("Subject 1 Semester marks must be between 0 and 60.");

            int sub2Int1 = parseInteger(sub2Int1Field.getText(), "Subject 2 Internal 1");
            if (!ExamSessionValidation.isValidInternalMark(sub2Int1))
                throw new ValidationException("Subject 2 Internal 1 must be between 0 and 40.");

            int sub2Int2 = parseInteger(sub2Int2Field.getText(), "Subject 2 Internal 2");
            if (!ExamSessionValidation.isValidInternalMark(sub2Int2))
                throw new ValidationException("Subject 2 Internal 2 must be between 0 and 40.");

            int sub2Sem = parseInteger(sub2SemField.getText(), "Subject 2 Semester");
            if (!ExamSessionValidation.isValidSemMark(sub2Sem))
                throw new ValidationException("Subject 2 Semester marks must be between 0 and 60.");

            int sub3Int1 = parseInteger(sub3Int1Field.getText(), "Subject 3 Internal 1");
            if (!ExamSessionValidation.isValidInternalMark(sub3Int1))
                throw new ValidationException("Subject 3 Internal 1 must be between 0 and 40.");

            int sub3Int2 = parseInteger(sub3Int2Field.getText(), "Subject 3 Internal 2");
            if (!ExamSessionValidation.isValidInternalMark(sub3Int2))
                throw new ValidationException("Subject 3 Internal 2 must be between 0 and 40.");

            int sub3Sem = parseInteger(sub3SemField.getText(), "Subject 3 Semester");
            if (!ExamSessionValidation.isValidSemMark(sub3Sem))
                throw new ValidationException("Subject 3 Semester marks must be between 0 and 60.");

            // Step 4: Build ExamSession object
            ExamSession exam = new ExamSession(
                    name, id, year, semester,
                    subject1Field.getText().trim(), sub1Int1, sub1Int2, sub1Sem,
                    subject2Field.getText().trim(), sub2Int1, sub2Int2, sub2Sem,
                    subject3Field.getText().trim(), sub3Int1, sub3Int2, sub3Sem
            );

            // Step 5: Centralized validation for safety
            String validationMessage = ExamSessionValidation.getValidationMessage(exam);
            if (validationMessage != null)
                throw new ValidationException(validationMessage);

            // Step 6: Save to DB
            if (dataManager.addExamSession(exam)) {
                JOptionPane.showMessageDialog(this, "Exam Session added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add exam session. Check console for details.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (ValidationException ve) {
            JOptionPane.showMessageDialog(this, ve.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper — throws ValidationException to stop immediately
    private int parseInteger(String text, String fieldName) {
        if (text.trim().isEmpty())
            throw new ValidationException(fieldName + " cannot be empty.");
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " must be a valid number.");
        }
    }

    // Custom runtime exception for immediate validation stops
    private static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
}
