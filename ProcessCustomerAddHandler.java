//21348140
package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import CookieManagement.Util;
import DAO.CustomerDAO;
import Model.Address;
import Model.Customer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.Map;

/*The class lets the admin add a customer to the DB
*/ 
public class ProcessCustomerAddHandler implements HttpHandler {

    public void handle(HttpExchange he) throws IOException {
        System.out.println("ProcessAddCustomerHandler Called");

        // Parse query parameters
        Map<String, String> params = Util.requestStringToMap(he.getRequestURI().getQuery());
        System.out.println("Params received: " + params);

        CustomerDAO customers = new CustomerDAO();

        try {
            // Validate and parse input
            int customerID = validateInteger(params.get("customerID"), "Customer ID");
            String businessName = validateString(params.get("businessName"), "Business Name");
            String telephoneNumber = validateString(params.get("telephoneNumber"), "Telephone Number");

            Address address = new Address(
                    validateString(params.get("addressLine0"), "Address Line 0"),
                    validateString(params.get("addressLine1"), "Address Line 1"),
                    validateString(params.get("addressLine2"), "Address Line 2"),
                    validateString(params.get("country"), "Country"),
                    validateString(params.get("postCode"), "Postcode")
            );

            Customer customer = new Customer(customerID, businessName, address, telephoneNumber);
            customers.addCustomer(customer);

            he.sendResponseHeaders(200, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
            out.write(
                    "<html>" +
                            "<head><title>Customer Added</title>" +
                            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\">" +
                            "</head>" +
                            "<body>" +
                            "<div class=\"container\">" +
                            "<h1>Customer Added Successfully</h1>" +
                            "<table class=\"table\">" +
                            "<thead>" +
                            "<tr><th>Customer ID</th><th>Business Name</th><th>Address</th><th>Telephone Number</th></tr>" +
                            "</thead>" +
                            "<tbody>" +
                            "<tr>" +
                            "<td>" + customerID + "</td>" +
                            "<td>" + businessName + "</td>" +
                            "<td>" + address + "</td>" +
                            "<td>" + telephoneNumber + "</td>" +
                            "</tr>" +
                            "</tbody>" +
                            "</table>" +
                            "<a href=\"/AdminHandler\" class=\"btn btn-primary\">Back to Admin Menu</a>" +
                            "</div>" +
                            "</body>" +
                            "</html>");
            out.close();

        } catch (IllegalArgumentException | SQLException e) {
            sendErrorResponse(he, e.getMessage());
        }
    }

    private int validateInteger(String value, String fieldName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid number. Please re-enter.");
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
                        "<a href=\"/AdminHandler\">Back to Admin Menu</a>" +
                        "</body>" +
                        "</html>");
        out.close();
    }
}

