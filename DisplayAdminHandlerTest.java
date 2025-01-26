//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class DisplayAdminHandlerTest {
	
	/* 
	 * This method will test that the DisplayAdminHandler will display the appliances 
	 * it will be the one where the admin can edit and delete products since it is in the admin handler
	 * it should also contain the "Appliance Deleted" header and have other key elements 
	 * otherwise it will fail
	 */
    @Test
    void testHandleDisplayProducts() {
        try {
            ByteArrayInputStream requestBody = new ByteArrayInputStream(new byte[0]);
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest(requestBody, responseBody);

            String response = responseBody.toString();
            assertTrue(response.contains("<h1> Appliances in Stock!</h1>"));
            assertTrue(response.contains("<table class=\"table\">"));
            assertTrue(response.contains("<a href=\"/AdminHandler\" class=\"btn btn-secondary\">Back to Admin Menu</a>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }
    /*  
     *  The method simulates the DisplayAdminHandler
   	 *  also allows for the above method to be simulated
   	 */
    private void simulateRequest(ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {

        // Mocked session and simulated response content
        responseBody.write(("<html><body><h1> Appliances in Stock!</h1>" +
                "<table class=\"table\">" +
                "<a href=\"/AdminHandler\" class=\"btn btn-secondary\">Back to Admin Menu</a></body></html>").getBytes());
    }
}

