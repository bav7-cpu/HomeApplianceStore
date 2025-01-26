package DAOTest;

import DAO.AdminDAO;
import Model.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class AdminDAOTest {
	/* 
	 * This method will test the AdminDAO methods
	 * it will have different tests ensuring that the admin is matched to the DB
	 * used getters to compare if invalid details have been inputted to prevent unauthorised access
	 */
    private static final String TEST_DB_URL = "jdbc:sqlite:TestHomeAppliance.sqlite";

    @BeforeEach
    void setUp() throws SQLException {
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT, password TEXT);");
            stmt.execute("INSERT INTO users (id, username, password) VALUES (1, 'admin', 'hashedpassword123');");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS users;");
        }
    }

    private AdminDAO getTestDAO() {
        return new AdminDAO() {
            protected Connection getDBConnection() {
                try {
                    return DriverManager.getConnection(TEST_DB_URL);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
    @Test
    void testFindUserByUsername() throws SQLException {
        Admin admin = getTestDAO().findUserByUsername("admin");

        assertNotNull(admin);
        assertEquals(1, admin.getuserID());
        assertEquals("admin", admin.getusername());
        assertEquals("hashedpassword123", admin.getpassword());
    }

    @Test
    void testFindUserByInvalidUsername() throws SQLException {
        assertNull(getTestDAO().findUserByUsername("invalidUser"));
    }
}
