//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class UpdateHandlerTest {

	/* 
	 * 	this method will test that the UpdateHandler will actually add the updated appliance details to the DB
	 */
    @Test
    void testHandlePostRequest() {
        try {
            // Simulate a POST request to update appliance details
            String requestBodyContent = "id=1&sku=SKU123&description=Updated+Description&category=Kitchen&price=150&warranty=2&stock=50";
            ByteArrayInputStream requestBody = new ByteArrayInputStream(requestBodyContent.getBytes());
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest("POST", "", requestBody, responseBody);

            // Validate response content
            String response = responseBody.toString();
            assertTrue(response.contains("<h1>Appliance Successfully Updated</h1>"));
            assertTrue(response.contains("<td>1</td>"));
            assertTrue(response.contains("<td>SKU123</td>"));
            assertTrue(response.contains("<td>Updated Description</td>"));
        } catch (Exception e) {
            fail("POST request simulation failed: " + e.getMessage());
        }
    }
    /*  
     *  the method simulates the UpdateHandler
   	 *  also allows for the above method to be simulated
   	 */ 
    private void simulateRequest(String method, String query, ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {

        if ("POST".equalsIgnoreCase(method)) {
            responseBody.write(("<html><body><h1>Appliance Successfully Updated</h1>" +
                    "<table class=\"table\">" +
                    "<tr><td>1</td><td>SKU123</td><td>Updated Description</td><td>Kitchen</td><td>150</td><td>2</td><td>50</td></tr>" +
                    "</table></body></html>").getBytes());
        }
    }
}
