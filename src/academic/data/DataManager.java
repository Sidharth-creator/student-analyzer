package academic.data;

import academic.models.ExamSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/college";
    private static final String USER = "root";
    private static String PASSWORD = ""; // IMPORTANT: Set your MySQL password here

    // Static block to load the driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver.", e);
        }
    }

    private Connection getConnection() throws SQLException {
        // Update this line with your actual password
        PASSWORD = "1234"; // Password updated from your CLI version
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public ExamSession getLatestSessionForStudent(String studentId) {
        ExamSession student = null;
        // Order by year and semester descending to get the latest one
        String sql = "SELECT * FROM marks WHERE student_id = ? ORDER BY year DESC, semester DESC LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                student = new ExamSession(
                    rs.getString("name"),
                    rs.getString("student_id"),
                    rs.getInt("year"),
                    rs.getInt("semester"),
                    rs.getString("subject1"),
                    rs.getInt("sub1_internal1"),
                    rs.getInt("sub1_internal2"),
                    rs.getInt("sub1_sem"),
                    rs.getString("subject2"),
                    rs.getInt("sub2_internal1"),
                    rs.getInt("sub2_internal2"),
                    rs.getInt("sub2_sem"),
                    rs.getString("subject3"),
                    rs.getInt("sub3_internal1"),
                    rs.getInt("sub3_internal2"),
                    rs.getInt("sub3_sem")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling should be implemented
        }
        return student;
    }

    public List<ExamSession> getAllSessionsForStudent(String studentId) {
        List<ExamSession> sessions = new ArrayList<>();
        String sql = "SELECT * FROM marks WHERE student_id = ? ORDER BY year, semester";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                sessions.add(new ExamSession(
                    rs.getString("name"),
                    rs.getString("student_id"),
                    rs.getInt("year"),
                    rs.getInt("semester"),
                    rs.getString("subject1"),
                    rs.getInt("sub1_internal1"),
                    rs.getInt("sub1_internal2"),
                    rs.getInt("sub1_sem"),
                    rs.getString("subject2"),
                    rs.getInt("sub2_internal1"),
                    rs.getInt("sub2_internal2"),
                    rs.getInt("sub2_sem"),
                    rs.getString("subject3"),
                    rs.getInt("sub3_internal1"),
                    rs.getInt("sub3_internal2"),
                    rs.getInt("sub3_sem")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    public boolean addExamSession(ExamSession student) {
        String sql = "INSERT INTO marks (name, student_id, year, semester, " +
                     "subject1, sub1_internal1, sub1_internal2, sub1_sem, " +
                     "subject2, sub2_internal1, sub2_internal2, sub2_sem, " +
                     "subject3, sub3_internal1, sub3_internal2, sub3_sem) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getId());
            pstmt.setInt(3, student.getYear());
            pstmt.setInt(4, student.getSemester());
            pstmt.setString(5, student.getSubject1());
            pstmt.setInt(6, student.getSub1Internal1());
            pstmt.setInt(7, student.getSub1Internal2());
            pstmt.setInt(8, student.getSub1Sem());
            pstmt.setString(9, student.getSubject2());
            pstmt.setInt(10, student.getSub2Internal1());
            pstmt.setInt(11, student.getSub2Internal2());
            pstmt.setInt(12, student.getSub2Sem());
            pstmt.setString(13, student.getSubject3());
            pstmt.setInt(14, student.getSub3Internal1());
            pstmt.setInt(15, student.getSub3Internal2());
            pstmt.setInt(16, student.getSub3Sem());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
