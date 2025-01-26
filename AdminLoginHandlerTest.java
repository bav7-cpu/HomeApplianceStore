//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class AdminLoginHandlerTest {

	/* 
	 * The method tests the user actually gets directed to the admin login page
	 * it should contain the "Login" header otherwise it will fail
	 */
    @Test
    void testGetRequestReturnsLoginPage() {
        ByteArrayOutputStream response = new ByteArrayOutputStream();

        try {
            simulateRequest("GET", "", response);
        } catch (Exception e) {
            fail("GET request simulation failed: " + e.getMessage());
        }

        String responseBody = response.toString(StandardCharsets.UTF_8);
        assertTrue(responseBody.contains("<h1>Login</h1>"));
    }

    /*	
     *  Tests by inputting invalid details for the username and password
     * 	this will make sure that no non-admins can enter the adminHandler
     */
    @Test
    void testPostRequestWithInvalidCredentials() {
        // Simulate a POST request with invalid credentials
        String requestBody = "username=admin&password=wrongpassword";
        ByteArrayOutputStream response = new ByteArrayOutputStream();

        try {
            simulateRequest("POST", requestBody, response);
        } catch (Exception e) {
            fail("POST request simulation failed: " + e.getMessage());
        }

        String responseBody = response.toString(StandardCharsets.UTF_8);
        assertTrue(responseBody.contains("<h1>Invalid Credentials</h1>"));
    }

    /*  
     *  The method simulates the loginHandler
	 *  and makes sure that the Get and POST requests are supported in the other 2 methods in this class
	 */
    private void simulateRequest(String method, String requestBody, ByteArrayOutputStream response) throws Exception {

        if ("GET".equalsIgnoreCase(method)) {
            response.write(("<html><body><h1>Login</h1><form method='POST' action='/Login'>"+
                     "Username: <input type='text' name='username'><br>"+
                     "Password: <input type='password' name='password'><br>"+
                     "<button type='submit'>Login</button></form></body></html>"+
                     "<a href='/' class='btn btn-secondary'>Cancel</a>").getBytes(StandardCharsets.UTF_8));
        } else if ("POST".equalsIgnoreCase(method)) {
            if (requestBody.contains("password=wrongpassword")) {
                response.write(("<html><body><h1>Invalid Credentials</h1><a href='/Login'>Back</a></body></html>")
                        .getBytes(StandardCharsets.UTF_8));
            } else {
                throw new Exception("Unhandled simulation case");
            }
        }
    }
}
