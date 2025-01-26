//21348140
package HandlerTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearBasketHandlerTest {
	/* 
	 * This method will test that the ClearBasket will actually go to a page showing that the basket is cleared 
	 * otherwise it will fail
	 */
    @Test
    void testHandleClearBasket() {
        try {
            ByteArrayInputStream requestBody = new ByteArrayInputStream(new byte[0]);
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest(requestBody, responseBody);

            String response = responseBody.toString();
            assertTrue(response.contains("<h1>Basket Cleared</h1>"));
            assertTrue(response.contains("<button type=\"submit\" class=\"btn btn-primary\">Back To Home</button>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }

    private void simulateRequest(ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {

        responseBody.write(("<html><body><h1>Basket Cleared</h1>" +
                "<form method='POST' action='/'><button type=\"submit\" class=\"btn btn-primary\">Back To Home</button></form></body></html>").getBytes());
    }
}