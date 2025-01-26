//21348140
package DAO;


import java.sql.*;

import Model.Admin;

/**
 * This class connects to the Admin table in the DB when the admin tries to login
 */
public class AdminDAO {
    public AdminDAO() {
    }

    private static Connection getDBConnection() {    //sets up the database connection
        Connection dbConnection = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            String dbURL = "jdbc:sqlite:HomeAppliance.sqlite"; 
            dbConnection = DriverManager.getConnection(dbURL);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
    
    // This method will find the admin in the DB based on their ID
    public Admin findUser(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                }
            }
        }
        return null;
    }
    
    //The method will allow for the admin in the webserver to login and access the admin features of the system
    public Admin findUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username); 
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Admin(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                }
            }
        }
        return null;
    }

}
   