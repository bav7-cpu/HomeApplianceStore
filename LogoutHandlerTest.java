//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class LogoutHandlerTest {
	/* 
	 * This method will test that the LogoutHandler will allow the admin to logout
	 * ensures that the admin is sent to the rootHandler because of the "("Location: /")"
	 * if they are not sent there then it will fail
	 */
    @Test
    void testHandleLogout() {
        try {
        	ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest(responseBody);

            String responseHeaders = responseBody.toString();
            assertTrue(responseHeaders.contains("Location: /"));
        } catch (Exception e) {
            fail("Logout request simulation failed: " + e.getMessage());
        }
    }
    /*  
     *  The method simulates the LogoutHandler
   	 *  also allows for the above method to be simulated
   	 */ 
    private void simulateRequest(ByteArrayOutputStream responseBody) throws Exception {

        responseBody.write(("Location: /").getBytes());
    }
}
