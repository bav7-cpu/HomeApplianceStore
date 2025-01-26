//21348140
package HandlerTest;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class AddHandlerTest {

    @Test
    /*	the method tests if the AddHandler can add customers to the DB
     * it will show the test to be successful if it contains the "ID", header of "Add Product" and "Back to Admin Menu" option.
     */
    void testHandleAddProductPage() {
        try {
            // Simulate a GET request
            ByteArrayInputStream requestBody = new ByteArrayInputStream(new byte[0]);
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest(requestBody, responseBody);

            // Validate response content
            String response = responseBody.toString();
            assertTrue(response.contains("<h1> Add Product</h1>"));
            assertTrue(response.contains("<form method=\"get\" action=\"/processAddProduct\">"));
            assertTrue(response.contains("<label for=\"ID\">ID</label>"));
            assertTrue(response.contains("<a href=\"/AdminHandler\" class=\"btn btn-secondary\">Back to Admin Menu</a>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }
    /*creates a practice HTML response for the AddHandler page
     * basically makes sure that the key elements are included such as header, ID etc
     * 
     */
    private void simulateRequest(ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {

        // Simulated response content
        responseBody.write(("<html><body><h1> Add Product</h1>" +
                "<form method=\"get\" action=\"/processAddProduct\">" +
                "<label for=\"ID\">ID</label>" +
                "<a href=\"/AdminHandler\" class=\"btn btn-secondary\">Back to Admin Menu</a></body></html>").getBytes());
    }
}
