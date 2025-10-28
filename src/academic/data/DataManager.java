package database;

import models.ExamSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/college";
    private static final String BASE_DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "Sidbal@123";
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("FATAL: Failed to load MySQL JDBC driver.", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
    
    private Connection getBaseConnection() throws SQLException {
        return DriverManager.getConnection(BASE_DB_URL, USER, PASSWORD);
    }

    public void initializeDatabase() {
        try {
            createDatabaseIfNotExists();
            createMarksTableIfNotExists();
        } catch (SQLException e) {
            System.err.println("Database initialization failed. Check server status/credentials.");
            e.printStackTrace();
            throw new RuntimeException("Database initialization failed.", e);
        }
    }

    private void createDatabaseIfNotExists() throws SQLException {
        String dbName = "college";
        try (Connection conn = getBaseConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
        }
    }

    private void createMarksTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS marks (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY," +
                     "name VARCHAR(255) NOT NULL," +
                     "student_id VARCHAR(50) NOT NULL," +
                     "year INT NOT NULL," +
                     "semester INT NOT NULL," +
                     "subject1 VARCHAR(100)," +
                     "sub1_internal1 INT," +
                     "sub1_internal2 INT," +
                     "sub1_sem INT," +
                     "subject2 VARCHAR(100)," +
                     "sub2_internal1 INT," +
                     "sub2_internal2 INT," +
                     "sub2_sem INT," +
                     "subject3 VARCHAR(100)," +
                     "sub3_internal1 INT," +
                     "sub3_internal2 INT," +
                     "sub3_sem INT," +
                     "UNIQUE KEY unique_session (student_id, year, semester)" +
                     ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Table 'marks' verified/created successfully.");
        }
    }
    public ExamSession getLatestSessionForStudent(String studentId) {
        ExamSession student = null;
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
            e.printStackTrace();
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
            // Student Info 
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getId());
            pstmt.setInt(3, student.getYear());
            pstmt.setInt(4, student.getSemester()); 

            // Subject 1 
            pstmt.setString(5, student.getSubject1());
            pstmt.setInt(6, student.getSub1Internal1());
            pstmt.setInt(7, student.getSub1Internal2());
            pstmt.setInt(8, student.getSub1Sem());

            // Subject 2 
            pstmt.setString(9, student.getSubject2());
            pstmt.setInt(10, student.getSub2Internal1());
            pstmt.setInt(11, student.getSub2Internal2());
            pstmt.setInt(12, student.getSub2Sem());

            // Subject 3 
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
