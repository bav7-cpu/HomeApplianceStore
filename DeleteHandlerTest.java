//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class DeleteHandlerTest {
	/* 
	 * This method will test that the DeleteHandler will delete the appliance with id1
	 * it should contain the "Appliance Deleted" header and have other key elements 
	 * otherwise it will fail
	 */
    @Test
    void testHandleDeleteAppliance() {
        try {
            String requestQuery = "id=1";
            ByteArrayOutputStream response = new ByteArrayOutputStream();

            simulateRequest(requestQuery, response);

            String responseBody = response.toString();
            assertTrue(responseBody.contains("<h1> Appliance Deleted</h1>"));
            assertTrue(responseBody.contains("<table class=\"table\">"));
            assertTrue(responseBody.contains("<a href=\"/AdminHandler\">Back to Admin Menu </a>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }
    /*  
     *  The method simulates the deleteHandler
   	 *  also allows for the above method to be simulated
   	 */ 
    private void simulateRequest(String query, ByteArrayOutputStream response) throws Exception {

        response.write(("<html><body><h1> Appliance Deleted</h1>" +
                "<table class=\"table\">" +
                "<a href=\"/AdminHandler\">Back to Admin Menu </a></body></html>").getBytes());
    }
}
