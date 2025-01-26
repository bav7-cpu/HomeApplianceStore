//21348140
package CookieManagementTest;

import org.junit.jupiter.api.Test;

import CookieManagement.SessionManager;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the SessionManager class in the CookieManagement package
 */
class SessionManagerTest {

    @Test
    /**
     * This method creates a session, that will take 3 parameters in id,string,string
     * The session is then removed using the "SessionManager.removeSession(sessionId);"
     * This will complete the check of a session life cycle
     */
    void testSessionFromStartToFinish() {
        String sessionId = SessionManager.createSession(1, "user", "password");
        assertTrue(SessionManager.isSessionValid(sessionId));
        assertEquals("user", SessionManager.getSession(sessionId).getUserName());

        SessionManager.removeSession(sessionId);
        assertFalse(SessionManager.isSessionValid(sessionId));
        assertNull(SessionManager.getSession(sessionId));
    }

    @Test
    
    /**
     * This method verifies the sessionmanager's "isAdmin" method function
     * this is based on the user/admins session ID
     */
    void testAdminCheck() {
        String adminSessionId = SessionManager.createSession(2, "admin", "adminPassword");
        String userSessionId = SessionManager.createSession(3, "user", "password");

        assertTrue(SessionManager.isAdmin(adminSessionId));
        assertFalse(SessionManager.isAdmin(userSessionId));
    }
}
