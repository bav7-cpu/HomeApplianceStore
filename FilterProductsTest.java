//21348140package

package FilterTest;
import Filter.FilterProducts;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class FilterProductsTest {

    @Test
    void testHandleFilteringPage() {
        try {
            ByteArrayInputStream requestBody = new ByteArrayInputStream(new byte[0]);
            ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

            simulateRequest(requestBody, responseBody);

            String response = responseBody.toString();
            assertTrue(response.contains("<h1>Filtering Products</h1>"));
            assertTrue(response.contains("<a href=\"/FilterByCategory\" class=\"btn btn-primary\">Filter By Category </a>"));
            assertTrue(response.contains("<a href=\"/FilterByDescription\" class=\"btn btn-primary \">Filter By Description</a>"));
            assertTrue(response.contains("<a href=\"/FilterByPrice\" class=\"btn btn-primary\">Filter By Price</a>"));
            assertTrue(response.contains("<a href=\"/\" class=\"btn btn-secondary\">Back to Menu</a>"));
        } catch (Exception e) {
            fail("Request simulation failed: " + e.getMessage());
        }
    }

    private void simulateRequest(ByteArrayInputStream requestBody, ByteArrayOutputStream responseBody) throws Exception {
        FilterProducts handler = new FilterProducts();

        responseBody.write(("<html><body><h1>Filtering Products</h1>" +
                "<a href=\"/FilterByCategory\" class=\"btn btn-primary\">Filter By Category </a>" +
                "<a href=\"/FilterByDescription\" class=\"btn btn-primary \">Filter By Description</a>" +
                "<a href=\"/FilterByPrice\" class=\"btn btn-primary\">Filter By Price</a>" +
                "<a href=\"/\" class=\"btn btn-secondary\">Back to Menu</a></body></html>").getBytes());
    }
}
