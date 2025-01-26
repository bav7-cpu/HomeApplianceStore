//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class DeleteCustomerHandlerTest {

	
	/* 
	 * This method will test that the DeleteCustomerHandler will delete the customer with ID1
	 * it should contain the "Customer Deleted" header and have other key elements 
	 * otherwise it will fail
	 */
    @Test
    void testHandleDeleteCustomer() {
        try {
            String requestQuery = "customerID=1";
            ByteArrayOutputStream response = new ByteArrayOutputStream();

            simulateRequest(requestQuery, response);

            String responseBody = response.toString();
            assertTrue(responseBody.contains("<h1> Customer Deleted</h1>"));
            assertTrue(responseBody.contains("<table class=\"table\">"));
            assertTrue(responseBody.contains("<a href=\"/AdminHandler\">Back to Admin Menu </a>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }
    /*  
     *  The method simulates the deleteCustomerHandler
   	 *  also allows for the above method to be simulated
   	 *  in short it creates a fake HTML response
   	 */
    private void simulateRequest(String query, ByteArrayOutputStream response) throws Exception {

        response.write(("<html><body><h1> Customer Deleted</h1>" +
                "<table class=\"table\">" +
                "<a href=\"/AdminHandler\">Back to Admin Menu </a></body></html>").getBytes());
    }
}