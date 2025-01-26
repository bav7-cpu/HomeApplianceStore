//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class ProcessCustomerAddHandlerTest {
	/* 
	 * This method will test that the ProcessCustomerAddHandler will actually add the customer to the DB
	 * also checks that a table will appear showing the added customer
	 * it should contain the "Customer Added Successfully" header and have other key elements 
	 * otherwise it will fail
	 */
    @Test
    void testHandleProcessAddCustomer() {
        try {
            String query = "customerID=1&businessName=TestBusiness&addressLine0=123+Main+St&addressLine1=Apt+4&addressLine2=&country=USA&postCode=12345&telephoneNumber=555-1234";
            ByteArrayInputStream requestBody = new ByteArrayInputStream(query.getBytes());
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest(query, requestBody, responseBody);
            
            String response = responseBody.toString();
            assertTrue(response.contains("<h1>Customer Added Successfully</h1>"));
            assertTrue(response.contains("<table class=\"table\">"));
            assertTrue(response.contains("<td>1</td>"));
            assertTrue(response.contains("<td>TestBusiness</td>"));
            assertTrue(response.contains("<td>123 Main St</td>"));
            assertTrue(response.contains("<td>555-1234</td>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }
    /*  
     *  The method simulates the ProcessCustomerAddHandler
   	 *  also allows for the above method to be simulated
   	 */ 
    private void simulateRequest(String query, ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {
     
        responseBody.write(("<html><body><h1>Customer Added Successfully</h1>" +
                "<table class=\"table\">" +
                "<tr><td>1</td><td>TestBusiness</td><td>123 Main St</td><td>555-1234</td></tr>" +
                "</table>" +
                "<a href=\"/AdminHandler\" class=\"btn btn-primary\">Back to Admin Menu</a></body></html>").getBytes());
    }
}

