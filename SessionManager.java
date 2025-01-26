//21348140
package CookieManagement;


import java.util.HashMap;
import java.util.UUID;

import Model.Session;

/**
 * The entire point of this class is to create the base for storing users activity (session)
 * once this class is set up each class that should track a user with getSession
 */


public class SessionManager {
    // In-memory map to store session details
    private static final HashMap<String, Session> sessions = new HashMap<>();

    /**
     * Creates a new session for a user.
     *
     * @param userId   The user's unique ID.
     * @param userName The user's username.
     * @param password The user's password (hashed or plain text depending on your design).
     * @return The session ID.
     */
    public static String createSession(int userId, String userName, String password) {
        String sessionId = UUID.randomUUID().toString(); // Generate a unique session ID
        sessions.put(sessionId, new Session(userId, userName, password)); // Store session details
        return sessionId;
    }

    /**
     * Retrieves a session based on the session ID.
     *
     * @param sessionId The session ID.
     * @return The main.CookieManagement.Session object if found, otherwise null.
     */
    public static Session getSession(String sessionId) {
        return sessionId != null ? sessions.get(sessionId) : null;
    }

    /**
     * Removes a session.
     *
     * @param sessionId The session ID to remove.
     */
    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    /**
     * Validates if a session exists and is associated with a logged-in user.
     *
     * @param sessionId The session ID to validate.
     * @return True if the session is valid, false otherwise.
     */
    public static boolean isSessionValid(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    /**
     * Destroys a session, effectively logging out the user.
     *
     * @param sessionId The session ID to destroy.
     */
    public static void destroySession(String sessionId) {
        sessions.remove(sessionId);
    }

    /**
     * Utility method to check if the session is associated with an admin user.
     * Adjust the logic based on your admin identification mechanism.
     *
     * @param sessionId The session ID.
     * @return True if the session belongs to an admin, false otherwise.
     */
    public static boolean isAdmin(String sessionId) {
        Session session = getSession(sessionId);
        // Assuming username "admin" or some other criteria identifies admin users
        return session != null && "admin".equalsIgnoreCase(session.getUserName());
    }
}
