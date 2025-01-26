//21348140
package ModelTest;

import Model.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
	/* the method tests that the session getters and setters work properly 
	 * 
	 */
    @Test
    void testSession() {
        Session session = new Session(1, "user1", "password123");

        
        assertEquals(1, session.getUserId());
        assertEquals("user1", session.getUserName());
        assertEquals("password123", session.getPassword());
    }
}
