package Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.HomeApplianceDAO;
import Model.HomeAppliance;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/*This class handles most things related to the basket
 * It had methods for the basket actions,adding to the basket and displaying the basket
 * 
*/
public class BasketHandler implements HttpHandler {

    private final HomeApplianceDAO homeApplianceDAO = new HomeApplianceDAO();
    public static final Map<Integer, Integer> basket = new HashMap<>(); // Appliance ID -> Quantity

    @Override
    public void handle(HttpExchange he) throws IOException {
        try {
            if ("GET".equals(he.getRequestMethod())) {
                String query = he.getRequestURI().getQuery();
                int applianceID = parseIntegerOrDefault(getParameterValue(query, "id"), -1);

                if (applianceID != -1) {
                    addToBasket(applianceID); // Add to basket if ID is present
                }

                displayBasketPage(he);
            } else if ("POST".equals(he.getRequestMethod())) {
                processBasketActions(he);
            } else {
                he.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayErrorPage(he, "An unexpected error occurred: " + e.getMessage());
        }
    }

    private void processBasketActions(HttpExchange he) throws IOException {
        String query = new String(he.getRequestBody().readAllBytes());
        Map<String, String> params = parseQuery(query);
        String action = params.get("action");

        if ("update".equals(action)) {
            int applianceID = Integer.parseInt(params.get("id"));
            int newQuantity = Integer.parseInt(params.get("quantity"));

            try {
            	/*This try catch checks the availability of the product stock
            	 * if there is not enough, it will show an error on line 65
            	 */
                HomeAppliance appliance = homeApplianceDAO.findProduct(applianceID);
                if (appliance != null) {
                    int availableStock = appliance.getStock();

                    if (newQuantity > availableStock) {
                        displayErrorPage(he, "Cannot add more than available stock. Available: " + availableStock);
                        return;
                    }

                    basket.put(applianceID, newQuantity);
                } else {
                    displayErrorPage(he, "Appliance not found.");
                }
            } catch (SQLException e) {
                displayErrorPage(he, "Error accessing the database.");
           }
            //this section of code will let the user do with the basket what they want to
        } else if ("remove".equals(action)) {
            int applianceID = Integer.parseInt(params.get("id"));
            basket.remove(applianceID);
        } else if ("clear".equals(action)) {
            basket.clear();
        } else if ("checkout".equals(action)) {
            try {
                for (Map.Entry<Integer, Integer> entry : basket.entrySet()) { //will basically just iterate through all the basket map items
                    int applianceID = entry.getKey();
                    int quantity = entry.getValue();

                    HomeAppliance appliance = homeApplianceDAO.findProduct(applianceID);

                    if (appliance != null) {
                        int currentStock = appliance.getStock();
                        if (currentStock >= quantity) {
                            // decreases the stock accordingly with what the user decides to checkout with
                            appliance.setStock(currentStock - quantity); // this will actually make the correct stock quantity change
                            boolean success = homeApplianceDAO.updateItem(appliance);
                            if (!success) {
                                displayErrorPage(he, "Failed to update stock for appliance ID: " + applianceID);
                                return;
                            }
                        } else {
                            displayErrorPage(he, "Insufficient stock for appliance ID: " + applianceID);
                            return;
                        }
                    } else {
                        displayErrorPage(he, "Appliance not found with ID: " + applianceID);
                        return;
                    }
                }
                basket.clear();

                // Redirect to checkout confirmation page
                he.getResponseHeaders().set("Location", "/checkout");
                he.sendResponseHeaders(302, -1);
            } catch (SQLException e) {
                e.printStackTrace();
                displayErrorPage(he, "Database error: " + e.getMessage());
            }
            return;

    }else if ("clear".equals(action)) {
            basket.clear();
            he.getResponseHeaders().set("Location", "/clearBasket"); // Redirect to a confirmation page
            he.sendResponseHeaders(302, -1);
            return;
        }

        // Redirect back to the basket page
        he.getResponseHeaders().set("Location", "/basket");
        he.sendResponseHeaders(302, -1);
    }

    // the method will add an item to the basket, or increment the amount
    private void addToBasket(int applianceID) {
        basket.put(applianceID, basket.getOrDefault(applianceID, 0) + 1);
    }

    // the method will display the page that the basket is on
    private void displayBasketPage(HttpExchange he) throws IOException {
        try {
            he.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            he.sendResponseHeaders(200, 0);

            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()))) {
                out.write("<!DOCTYPE html>" +
                        "<html><head><meta charset='UTF-8'><title>Basket</title>"+
                         "<script>"+
                         "function updatePrices(id, price) {"+
                         "  const quantityInput = document.getElementById('quantity-' + id);"+
                         "  const totalElement = document.getElementById('total-' + id);"+
                         "  const overallTotalElement = document.getElementById('overallTotal');"+
                         "  const quantity = parseInt(quantityInput.value, 10) || 1;"+
                         "  const productTotal = quantity * price;"+
                         "  totalElement.innerText = '£' + productTotal;"+

                         "  let overallTotal = 0;"+
                         "  document.querySelectorAll('.product-total').forEach(el => {"+
                         "    overallTotal += parseInt(el.innerText.replace('£', '')) || 0;"+
                         "  });"+
                         "  overallTotalElement.innerText = '£' + overallTotal;"+
                         "}"+
                         "function submitUpdate(id) {"+
                         "  const quantity = document.getElementById('quantity-' + id).value;"+
                         "  const form = document.getElementById('update-form-' + id);"+
                         "  form.querySelector('[name=quantity]').value = quantity;"+
                         "  form.submit();"+
                         "}"+
                         "</script>"+
                         "</head><body>"+
                         "<h1>Your Basket</h1>");

                if (basket.isEmpty()) {
                    out.write("<p>Your basket is empty.</p>"+
                            "<form method='POST' action='/'>" +
                            "<button type=\"submit\" class=\"btn btn-primary\">Back To Home</button>" +
                            "</form>" );
                } else {
                    out.write("<table border='1'><tr><th>Appliance</th><th>Price</th><th>Quantity</th><th>Total</th><th>Actions</th></tr>");

                    int overallTotal = 0;
                    for (Map.Entry<Integer, Integer> entry : basket.entrySet()) {
                        int applianceID = entry.getKey();
                        int quantity = entry.getValue();

                        HomeAppliance appliance = homeApplianceDAO.findProduct(applianceID);
                        if (appliance != null) {
                            int total = appliance.getPrice() * quantity;
                            overallTotal += total;

                            out.write("<tr>" +
                                    "<td>" + appliance.getDescription() + "</td>" +
                                    "<td>£" + appliance.getPrice() + "</td>" +
                                    "<td>" +
                                    "<form id='update-form-" + applianceID + "' method='POST'>" +
                                    "<input type='hidden' name='action' value='update'>" +
                                    "<input type='hidden' name='id' value='" + applianceID + "'>" +
                                    "<input type='hidden' name='quantity'>" +
                                    "<input type='number' id='quantity-" + applianceID + "' value='" + quantity + "' min='1' max='" + appliance.getStock() + "' oninput='updatePrices(" + applianceID + ", " + appliance.getPrice() + ")' onchange='submitUpdate(" + applianceID + ")'>" +
                                    "</form>" +
                                    "</td>" +
                                    "<td class='product-total' id='total-" + applianceID + "'>£" + total + "</td>" +
                                    "<td>" +
                                    "<form method='POST' action='/basket'>" +
                                    "<input type='hidden' name='action' value='remove'>" +
                                    "<input type='hidden' name='id' value='" + applianceID + "'>" +
                                    "<button type='submit'>Remove</button>" +
                                    "</form>" +
                                    "</td>" +
                                    "</tr>");
                        }
                    }

                    out.write(
                            "</table>" +
                                    "<b>Overall Total: </b><span id='overallTotal'>£" + overallTotal + "</span>" +
                                    "<form method='POST' action='/basket'>" +
                                    "    <input type='hidden' name='action' value='clear'>" +
                                    "    <button type='submit'>Clear Basket</button>" +
                                    "</form>" +
                                    "<form method='POST' action='/basket'> "+
                                    " <input type='hidden' name='action' value='checkout'>"+
                                    "<button type='submit'>Proceed To Checkout</button>"+
                                    "</form>"
                    );

                    out.write(
                            "<a href='/appliances'>Back to Appliances</a>" +
                                    "</body></html>"
                    );
                }
                }


        } catch (SQLException e) {
            e.printStackTrace();
            displayErrorPage(he, "Database error: " + e.getMessage());
        }
    }

    //this method returns a key-value (Map<String,String>) 
    private Map<String, String> parseQuery(String query) {
        Map<String, String> queryParams = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return queryParams;
        }

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                queryParams.put(keyValue[0], keyValue[1]);
            } else if (keyValue.length == 1) {
                queryParams.put(keyValue[0], "");
            }
        }
        return queryParams;
    }

    private int parseIntegerOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String getParameterValue(String query, String parameterName) {
        if (query == null || !query.contains(parameterName + "=")) {
            return null;
        }
        try {
            String[] parts = query.split(parameterName + "=");
            return parts[1].split("&")[0];
        } catch (Exception e) {
            return null;
        }
    }
    private void displayErrorPage(HttpExchange he, String message) throws IOException {
        String response = "<html><body>" +
                "<h1>Error</h1>" +
                "<p>" + message + "</p>" +
                "<a href='/appliances'>Back to Appliances</a>" +
                "</body></html>";

        he.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        he.sendResponseHeaders(200, response.getBytes().length);
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()))) {
            out.write(response);
        }
    }

}
