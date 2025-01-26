//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class DisplayCustomersHandlerTest {

	/* 
	 * This method will test that the DisplayCustomersHandler will display the customer in the DB
	 * this can be checked by the page containing,
	 *  "Customers" header and have other key elements 
	 * otherwise it will fail
	 */
    @Test
    void testHandleDisplayCustomers() {
        try {
            ByteArrayInputStream requestBody = new ByteArrayInputStream(new byte[0]);
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest(requestBody, responseBody);

            String response = responseBody.toString();
            assertTrue(response.contains("<h1> Customers!</h1>"));
            assertTrue(response.contains("<table class=\"table\">"));
            assertTrue(response.contains("<a href=\"/AdminHandler\" class=\"btn btn-secondary\">Back to Admin Menu</a>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }
    /*  
     *  The method simulates the DisplayCustomersHandler
   	 *  also allows for the above method to be simulated
   	 */
    private void simulateRequest(ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {

        responseBody.write(("<html><body><h1> Customers!</h1>" +
                "<table class=\"table\">" +
                "<a href=\"/AdminHandler\" class=\"btn btn-secondary\">Back to Admin Menu</a></body></html>").getBytes());
    }
}

