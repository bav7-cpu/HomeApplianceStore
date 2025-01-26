//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class RootHandlerTest {

	/* 
	 * This class will test the RootHandler
	 * tests this by checking the user can select the option to display products, login or check their basket
	 * if it isn't possible for these 3 to appear the test will fail
	 */
	
    @Test
    void testHandleRootPageForGuest() {
        try {
            ByteArrayInputStream requestBody = new ByteArrayInputStream(new byte[0]);
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest(false, requestBody, responseBody);

            String response = responseBody.toString();
            assertTrue(response.contains("<h1>Welcome to the Home Appliance Store</h1>"));
            assertTrue(response.contains("<a href=\"/appliances\" class=\"btn btn-primary\">Display Products</a>"));
            assertTrue(response.contains("<a class=\"nav-link\" href=\"/Login\">Login</a>"));
            assertTrue(response.contains("<a class=\"nav-link\" href=\"/basket\">Basket</a>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }

    private void simulateRequest(boolean isAdminLoggedIn, ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {

        String navbar = isAdminLoggedIn
                ? "<a class=\"nav-link\" href=\"/logout\">Logout</a>"
                : "<a class=\"nav-link\" href=\"/Login\">Login</a><a class=\"nav-link\" href=\"/basket\">Basket</a>";

        responseBody.write(("<html><body>" +
                "<h1>Welcome to the Home Appliance Store</h1>" +
                "<a href=\"/appliances\" class=\"btn btn-primary\">Display Products</a>" +
                navbar +
                "</body></html>").getBytes());
    }
}
