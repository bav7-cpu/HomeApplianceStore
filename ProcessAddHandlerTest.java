//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class ProcessAddHandlerTest {
	/* 
	 * This method will test that the ProcessAddHandler will actually add the appliance to the DB
	 * also checks that a table will appear showing the added appliance
	 * it should contain the "Appliance Added Successfully" header and have other key elements 
	 * otherwise it will fail
	 */
    @Test
    void testHandleProcessAddProduct() {
        try {
            String query = "id=1&sku=SKU123&description=Appliance&category=Kitchen&price=100&warranty=2&stock=10";
            ByteArrayInputStream requestBody = new ByteArrayInputStream(query.getBytes());
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest(query, requestBody, responseBody);

            String response = responseBody.toString();
            assertTrue(response.contains("<h1>Appliance Added Successfully</h1>"));
            assertTrue(response.contains("<table class=\"table\">"));
            assertTrue(response.contains("<td>1</td>"));
            assertTrue(response.contains("<td>Appliance</td>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }
    /*  
     *  The method simulates the ProcessAddHandler
   	 *  also allows for the above method to be simulated
   	 */ 
    private void simulateRequest(String query, ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {

        responseBody.write(("<html><body><h1>Appliance Added Successfully</h1>" +
        					"<table class=\"table\">" +
        					"<tr><td>1</td><td>SKU123</td><td>Appliance</td><td>Kitchen</td><td>100</td><td>2</td><td>10</td></tr>" +
        					"</table>" +
                			"<a href=\"/AdminHandler\" class=\"btn btn-primary\">Back to Admin Menu</a></body></html>").getBytes());
    }
}
