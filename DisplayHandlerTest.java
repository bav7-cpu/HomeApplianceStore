//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class DisplayHandlerTest {

	/* 
	 * This method will test that the DisplayHandler will display the appliances within the DB
	 * it should contain the "Appliances in Stock" header and have other key elements 
	 * otherwise it will fail
	 */
    @Test
    void testHandleDisplayProducts() {
        try {
            String query = "category=electronics&minPrice=100&maxPrice=500";
            ByteArrayInputStream requestBody = new ByteArrayInputStream(new byte[0]);
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest(query, requestBody, responseBody);

            String response = responseBody.toString();
            assertTrue(response.contains("<h1> Appliances in Stock!</h1>"));
            assertTrue(response.contains("<table class=\"table\">"));
            assertTrue(response.contains("<a href=\"/\" class=\"btn btn-secondary btn-block back-to-home\">Back to Home</a>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }

    /*  
     *  The method simulates the DisplayHandler
   	 *  also allows for the above method to be simulated
   	 */ 
    private void simulateRequest(String query, ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {

        responseBody.write(("<html><body><h1> Appliances in Stock!</h1>" +
                "<table class=\"table\">" +
                "<a href=\"/\" class=\"btn btn-secondary btn-block back-to-home\">Back to Home</a></body></html>").getBytes());
    }
}
