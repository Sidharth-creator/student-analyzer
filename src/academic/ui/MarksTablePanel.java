package ui;

import models.ExamSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class MarksTablePanel extends JPanel {
    private JTable marksTable;
    private JComboBox<String> filterComboBox;
    private DefaultTableModel tableModel;
    private List<ExamSession> currentSessions;

    public MarksTablePanel() {
        setLayout(new BorderLayout(10, 10));

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Show Marks For:"));
        String[] filters = {"All", "Internal 1", "Internal 2", "Semester Exam", "Total Marks"};
        filterComboBox = new JComboBox<>(filters);
        filterPanel.add(filterComboBox);

        // Table
        tableModel = new DefaultTableModel();
        marksTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(marksTable);

        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        filterComboBox.addActionListener(e -> filterTable());
    }

    public void updateData(List<ExamSession> sessions) {
        this.currentSessions = sessions;
        filterTable();
    }

    private void filterTable() {
        if (currentSessions == null) return;

        String selectedFilter = (String) filterComboBox.getSelectedItem();

        // Define columns
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Year");
        columnNames.add("Semester");
        columnNames.add("Subject");
        columnNames.add("Marks");
        
        Vector<Vector<Object>> data = new Vector<>();

        for (ExamSession session : currentSessions) {
            addSubjectData(data, session, session.getSubject1(), 
                           session.getSub1Internal1(), session.getSub1Internal2(), session.getSub1Sem(), 
                           selectedFilter);
            addSubjectData(data, session, session.getSubject2(), 
                           session.getSub2Internal1(), session.getSub2Internal2(), session.getSub2Sem(), 
                           selectedFilter);
            addSubjectData(data, session, session.getSubject3(), 
                           session.getSub3Internal1(), session.getSub3Internal2(), session.getSub3Sem(), 
                           selectedFilter);
        }

        tableModel.setDataVector(data, columnNames);
    }
    
    private void addSubjectData(Vector<Vector<Object>> data, ExamSession s, String subName, 
                                int int1, int int2, int sem, String filter) {
        int marks = 0;
        boolean all = "All".equals(filter);
        if (all || "Internal 1".equals(filter)) data.add(createRow(s, subName, "Internal 1", int1));
        if (all || "Internal 2".equals(filter)) data.add(createRow(s, subName, "Internal 2", int2));
        if (all || "Semester Exam".equals(filter)) data.add(createRow(s, subName, "Semester Exam", sem));
        if (all || "Total Marks".equals(filter)) data.add(createRow(s, subName, "Total", int1+int2+sem));
    }
    
    private Vector<Object> createRow(ExamSession s, String subName, String examType, int marks) {
        Vector<Object> row = new Vector<>();
        row.add(s.getYear());
        row.add(s.getSemester());
        row.add(subName + " (" + examType + ")");
        row.add(marks);
        return row;
    }
}
