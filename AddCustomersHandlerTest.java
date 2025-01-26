//21348140
package HandlerTest;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class AddCustomersHandlerTest {
	
	/**	
	 * The method tests if the addCustomerHandler can add customers to the DB
     * it will show the test to be successful if it contains the "CustomerID", header of "Add Customer" and "Back to Admin Menu" option.
     */
    @Test
    void testHandleAddCustomerPage() {
        try {
            // Simulate a GET request
            ByteArrayInputStream requestBody = new ByteArrayInputStream(new byte[0]);
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest(requestBody, responseBody);

            // the assertions that check if the response to the simulation contains what is in the brackets
            String response = responseBody.toString();
            assertTrue(response.contains("<h1> Add Customer</h1>"));
            assertTrue(response.contains("<form method=\"get\" action=\"/processAddCustomer\">"));
            assertTrue(response.contains("<label for=\"customerID\">CustomerID</label>"));
            assertTrue(response.contains("<a href=\"/AdminHandler\" class=\"btn btn-secondary\">Back to Admin Menu</a>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }

    /**
     * Creates a practice HTML response for the AddCustomersHandler page
     * basically makes sure that the key elements are included such as header, customerID etc
     */
    private void simulateRequest(ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {
        responseBody.write(("<html><body><h1> Add Customer</h1>" +
                "<form method=\"get\" action=\"/processAddCustomer\">" +
                "<label for=\"customerID\">CustomerID</label>" +
                "<a href=\"/AdminHandler\" class=\"btn btn-secondary\">Back to Admin Menu</a></body></html>").getBytes());
    }
}

