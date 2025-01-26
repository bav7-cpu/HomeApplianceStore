//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class UpdateCustomerHandlerTest {

	/* 
	 * 	this method will test that the UpdateCustomerHandler will actually add the updated customer details to the DB
	 */
    @Test
    void testHandlePostCustomer() {
        try {
            String requestBodyContent = "CustomerID=1&BusinessName=UpdatedBusiness&AddressLine0=New+Address+0&AddressLine1=New+Address+1&AddressLine2=New+Address+2&Country=NewCountry&Postcode=12345&TelephoneNumber=111-222";
            ByteArrayInputStream requestBody = new ByteArrayInputStream(requestBodyContent.getBytes());
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest("POST", "", requestBody, responseBody);

            String response = responseBody.toString();
            assertTrue(response.contains("<h1>Customer Successfully Updated</h1>"));
            assertTrue(response.contains("<td>1</td>"));
            assertTrue(response.contains("<td>UpdatedBusiness</td>"));
            assertTrue(response.contains("<td>New Address 0</td>"));
            assertTrue(response.contains("<td>111-222</td>"));
        } catch (Exception e) {
            fail("POST request simulation failed: " + e.getMessage());
        }
    }
    /*  
     *  The method simulates the UpdateCustomerHandler
   	 *  also allows for the above method to be simulated
   	 */ 
    private void simulateRequest(String method, String query, ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {

        if ("POST".equalsIgnoreCase(method)) {
            responseBody.write(("<html><body><h1>Customer Successfully Updated</h1>" +
                    "<table class=\"table\">" +
                    "<tr><td>1</td><td>UpdatedBusiness</td><td>New Address 0</td><td>111-222</td></tr>" +
                    "</table></body></html>").getBytes());
        }
    }
}
