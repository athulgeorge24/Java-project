package database;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/uniseat_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    private static Connection connection = null;
    
    static {
        initializeDatabase();
    }
    
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return connection;
    }
    
    public static void initializeDatabase() {
        createDatabaseIfNotExists();
        createTablesIfNotExist();
    }
    
    private static void createDatabaseIfNotExists() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS uniseat_db");
            System.out.println("Database 'uniseat_db' ready!");
            
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }
    
    private static void createTablesIfNotExist() {
        String[] createTables = {
            // Venues table
            "CREATE TABLE IF NOT EXISTS uniseat_db.venues (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL, " +
            "rows_count INT NOT NULL, " +
            "columns_count INT NOT NULL, " +
            "total_seats INT NOT NULL, " +
            "layout_data TEXT, " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            
            // Events table
            "CREATE TABLE IF NOT EXISTS uniseat_db.events (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) NOT NULL, " +
            "description TEXT, " +
            "event_date DATE, " +
            "venue_id INT, " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            
            // Participants table
            "CREATE TABLE IF NOT EXISTS uniseat_db.participants (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "event_id INT NOT NULL, " +
            "participant_id VARCHAR(100) NOT NULL, " +
            "name VARCHAR(255) NOT NULL, " +
            "email VARCHAR(255), " +
            "group_name VARCHAR(100), " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            
            // Seating plans table
            "CREATE TABLE IF NOT EXISTS uniseat_db.seating_plans (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "event_id INT NOT NULL, " +
            "plan_name VARCHAR(255) NOT NULL, " +
            "plan_data TEXT NOT NULL, " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
            
            // Users table (for future login enhancement)
            "CREATE TABLE IF NOT EXISTS uniseat_db.users (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "username VARCHAR(100) UNIQUE NOT NULL, " +
            "password_hash VARCHAR(255) NOT NULL, " +
            "full_name VARCHAR(255), " +
            "role ENUM('admin', 'organizer') DEFAULT 'organizer', " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
        };
        
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            for (String sql : createTables) {
                stmt.executeUpdate(sql);
            }
            System.out.println("All tables created successfully!");
            
            // Insert default admin user
            insertDefaultAdmin();
            
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
    
    private static void insertDefaultAdmin() {
        String checkSql = "SELECT COUNT(*) FROM uniseat_db.users WHERE username = 'admin'";
        String insertSql = "INSERT INTO uniseat_db.users (username, password_hash, full_name, role) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             Statement checkStmt = conn.createStatement();
             ResultSet rs = checkStmt.executeQuery(checkSql)) {
            
            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    pstmt.setString(1, "admin");
                    pstmt.setString(2, "123"); // In real app, use hashed password
                    pstmt.setString(3, "System Administrator");
                    pstmt.setString(4, "admin");
                    pstmt.executeUpdate();
                    System.out.println("Default admin user created!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating default admin: " + e.getMessage());
        }
    }
    
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
