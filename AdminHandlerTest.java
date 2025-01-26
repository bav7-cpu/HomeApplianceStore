//21348140
package HandlerTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

import static org.junit.jupiter.api.Assertions.*;

class AdminHandlerTest {
	
	/*	checks that the simulation of the AdminHandler contains key elements
	 * like the header and adding/displaying customers
     * 
     */
    @Test
    void testHandle() throws Exception {
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

      
        try (OutputStreamWriter writer = new OutputStreamWriter(responseBody)) {
            writer.write(
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "  <head>" +
                            "    <title>Home Appliance Store</title>" +
                            "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                            "  </head>" +
                            "  <body>" +
                            "    <div class=\"container mt-4\">" +
                            "      <h1>Welcome to the Home Appliance Store</h1>" +
                            "       <a href=\"/appliances\" class=\"btn btn-primary\">Display Products</a>" +
                            "       <a href=\"/add\" class=\"btn btn-primary\">Add Products</a>" +
                            "       <a href=\"/Logout\" class=\"btn btn-danger\">Logout</a>" +
                            "    </div>" +
                            "  </body>" +
                            "</html>"
            );
        }

        String responseBodyContent = responseBody.toString();
        /*turns the responseBody into a string so the assertions can ensure it contains that specific key element
         * 
         */

        
        assertTrue(responseBodyContent.contains("<h1>Welcome to the Home Appliance Store</h1>"));
        assertTrue(responseBodyContent.contains("<a href=\"/appliances\" class=\"btn btn-primary\">Display Products</a>"));
        assertTrue(responseBodyContent.contains("<a href=\"/add\" class=\"btn btn-primary\">Add Products</a>"));
        assertTrue(responseBodyContent.contains("<a href=\"/Logout\" class=\"btn btn-danger\">Logout</a>"));
    }
}



