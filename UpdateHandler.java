//21348140
package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import DAO.HomeApplianceDAO;
import Model.HomeAppliance;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/* this class lets the user edit/update products
* 
*/
public class UpdateHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {

        System.out.println("main.Handler.UpdateHandler Called");
        String method = he.getRequestMethod();
        HomeApplianceDAO homeApplianceDAO = new HomeApplianceDAO();

        if ("GET".equalsIgnoreCase(method)) {
            int id = Integer.parseInt(he.getRequestURI().getQuery().split("=")[1]);

            HomeAppliance appliance = null;
            try {
                appliance = homeApplianceDAO.findProduct(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (appliance != null) {
                he.sendResponseHeaders(200, 0);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

                out.write(
                        "<html>" +
                                "<head><title>Edit Appliance</title></head>" +
                                "<body>" +
                                "<h1>Edit Appliance</h1>" +
                                "<form method=\"post\" action=\"/edit\">" +
                                "<input type=\"hidden\" name=\"id\" value=\"" + appliance.getId() + "\" />" +
                                "<label>SKU:</label> <input type=\"text\" name=\"sku\" value=\"" + appliance.getSku() + "\" /><br>" +
                                "<label>Description:</label> <input type=\"text\" name=\"description\" value=\"" + appliance.getDescription() + "\" /><br>" +
                                "<label>Category:</label> <input type=\"text\" name=\"category\" value=\"" + appliance.getCategory() + "\" /><br>" +
                                "<label>Price:</label> <input type=\"number\" step=\"1\" name=\"price\" value=\"" + appliance.getPrice() + "\" /><br>" +
                                "<label>Warranty:</label> <input type=\"text\" name=\"warranty\" value=\"" + appliance.getWarranty() + "\" /><br>" +
                                "<label>Stock:</label> <input type=\"text\" name=\"stock\" value=\"" + appliance.getStock() + "\" /><br>" +
                                "<button type=\"submit\">Save</button>" +
                                "<a href=\"/display\">Cancel</a>" +
                                "</form>" +
                                "</body>" +
                                "</html>"
                );

                out.flush();
                out.close();
            } else {
                he.sendResponseHeaders(404, 0);
                he.getResponseBody().write("Appliance not found.".getBytes());
                he.getResponseBody().close();
            }
        } else if ("POST".equalsIgnoreCase(method)) {
            // Parse the request body
            String body = new String(he.getRequestBody().readAllBytes()); // Read the request body
            String[] params = body.split("&"); // Split by '&' to get individual key-value pairs

            // Use a Map to store key-value pairs
            Map<String, String> paramMap = new HashMap<>();
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                    paramMap.put(key, value);
                }
            }

            try {
                //  if it is a valid input by the admin
                int id = validateInteger(paramMap.get("id"), "ID");
                String sku = validateString(paramMap.get("sku"), "SKU");
                String description = validateString(paramMap.get("description"), "Description");
                String category = validateString(paramMap.get("category"), "Category");
                int price = validateInteger(paramMap.get("price"), "Price");
                int warranty = validateInteger(paramMap.get("warranty"), "Warranty");
                int stock = validateInteger(paramMap.get("stock"), "Stock");

                HomeAppliance updatedAppliance = new HomeAppliance(id, sku, description, category, price, warranty, stock);

              
                boolean success = homeApplianceDAO.updateItem(updatedAppliance);
                if (success) {
                    he.sendResponseHeaders(200, 0);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
                    out.write(
                            "<html>" +
                                    "<head><title>Product Added</title>" +
                                    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\">" +
                                    "</head>" +
                                    "<body>" +
                                    "<div class=\"container\">" +
                                    "<h1>Appliance Added Successfully</h1>" +
                                    "<table class=\"table\">" +
                                    "<thead>" +
                                    "<tr><th>ID</th><th>SKU</th><th>Description</th><th>Category</th><th>Price</th><th>Warranty</th><th>Stock</th></tr>" +
                                    "</thead>" +
                                    "<tbody>" +
                                    "<tr>" +
                                    "<td>" + id + "</td>" +
                                    "<td>" + sku + "</td>" +
                                    "<td>" + description + "</td>" +
                                    "<td>" + category + "</td>" +
                                    "<td>" + price + "</td>" +
                                    "<td>" + warranty + "</td>" +
                                    "<td>" + stock + "</td>" +
                                    "</tr>" +
                                    "</tbody>" +
                                    "</table>" +
                                    "<a href=\"/AdminHandler\" class=\"btn btn-primary\">Back to Admin Menu</a>" +
                                    "</div>" +
                                    "</body>" +
                                    "</html>");
                    out.close();
                } else {
                    sendErrorResponse(he, "Failed to update appliance.");
                }
            } catch (IllegalArgumentException | SQLException e) {
                sendErrorResponse(he, e.getMessage());
            }
        }
    }

    private int validateInteger(String value, String fieldName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a number. Please re-enter.");
        }
    }

    private String validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty. Please re-enter.");
        }
        return value.trim();
    }

    private void sendErrorResponse(HttpExchange he, String message) throws IOException {
        he.sendResponseHeaders(400, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        out.write(
                "<html>" +
                        "<head><title>Error</title></head>" +
                        "<body>" +
                        "<h1>Error</h1>" +
                        "<p>" + message + "</p>" +
                        "<a href=\"/AdminHandler\">Back To Admin Menu</a>" +
                        "</body>" +
                        "</html>");
        out.close();
    }
}
