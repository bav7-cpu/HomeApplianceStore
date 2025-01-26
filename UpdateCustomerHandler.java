//21348140
//package Handler;
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//
//import DAO.CustomerDAO;
//import Model.Address;
//import Model.Customer;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
///* this class lets the user edit/update customers
// * 
// */
//public class UpdateCustomerHandler implements HttpHandler {
//    public void handle(HttpExchange he) throws IOException {
//
//        System.out.println("UpdateCustomerHandler Called");
//        String method = he.getRequestMethod();
//        CustomerDAO customerDAO = new CustomerDAO();
//
//        if ("GET".equalsIgnoreCase(method)) {
//            int customerid = Integer.parseInt(he.getRequestURI().getQuery().split("=")[1]);
//
//            Customer customer = null;
//            try {
//                customer = customerDAO.findCustomer(customerid);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//            if (customer != null) {
//                he.sendResponseHeaders(200, 0);
//                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
//
//                Address address = customer.getAddress();
//                out.write(
//                        "<html>" +
//                                "<head><title>Edit Customer</title></head>" +
//                                "<body>" +
//                                "<h1>Edit Customer</h1>" +
//
//                                "<form method=\"post\" action=\"/editCustomer\">" +
//                                "<input type=\"hidden\" name=\"CustomerID\" value=\"" + customer.getCustomerID() + "\" />" +
//                                "<label>Business Name:</label> <input type=\"text\" name=\"BusinessName\" value=\"" + customer.getBusinessName() + "\" /><br>" +
//                                "<label>Address Line 0:</label> <input type=\"text\" name=\"AddressLine0\" value=\"" + address.getaddressLine0()  + "\" /><br>" +
//                                "<label>Address Line 1:</label> <input type=\"text\" name=\"AddressLine1\" value=\"" + address.getaddressLine1() +"\" /><br>" +
//                                "<label>Address Line 2:</label> <input type=\"text\" name=\"AddressLine2\" value=\"" + address.getaddressLine2() + "\" /><br>" +
//                                "<label>Country:</label> <input type=\"text\" name=\"Country\" value=\"" + address.getcountry() + "\" /><br>" +
//                                "<label>Postcode:</label> <input type=\"text\" name=\"Postcode\" value=\"" +address.getpostCode()  + "\" /><br>" +
//                                "<label>Telephone Number:</label> <input type=\"text\" name=\"TelephoneNumber\" value=\"" + customer.getTelephoneNumber() + "\" /><br>" +
//                                "<button type=\"submit\">Save</button>" +
//                                "<a href=\"/DisplayCustomer\">Cancel</a>" +
//                                "</form>" +
//                                "</body>" +
//                                "</html>"
//                );
//
//                out.flush();
//                out.close();
//            } else {
//                he.sendResponseHeaders(404, 0);
//                he.getResponseBody().write("Customer not found.".getBytes());
//                he.getResponseBody().close();
//            }
//        } else if ("POST".equalsIgnoreCase(method)) {
//            // Parse the request body
//            String body = new String(he.getRequestBody().readAllBytes()); // Read the request body
//            String[] params = body.split("&"); // Split by '&' to get individual key-value pairs
//
//            // Use a Map to store key-value pairs
//            Map<String, String> paramMap = new HashMap<>();
//            for (String param : params) {
//                String[] keyValue = param.split("=");
//                /* Initial code where a "+" was printed instead
//                 * if (keyValue.length == 2) {
//                 * paramMap.put(keyValue[0], (keyValue[1]));	
//                 */
//                if (keyValue.length == 2) {
//                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
//                    /* Decodes the key
//                    initially, when I tried to use a space between words characters
//                    whilst updating/editing
//                    it would end up using a "+" sign instead
//                    So I did some research and identified (https://www.regextranslator.com/)
//                    that decoding the parameters was required
//                    the "URLDecoder.decode" basically converted the "+"
//                    into a space
//
//                    */
//
//
//
//                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
//                    paramMap.put(key, value);
//                }
//            }// Extract values from the map and convert as needed
///*		same concept as previous classes
// * 	this time with the Address address, it is calling the class Address to fill in the address details
// * 	the object address stores
// */
//            int customerID = Integer.parseInt(paramMap.get("CustomerID"));
//            String businessName = paramMap.get("BusinessName");
//            String telephoneNumber = paramMap.get("TelephoneNumber");
//            Address address = new Address(
//                    paramMap.get("AddressLine0"),
//                    paramMap.get("AddressLine1"),
//                    paramMap.get("AddressLine2"),
//                    paramMap.get("Country"),
//                    paramMap.get("Postcode")
//            );
//
//           
//            Customer updatedCustomer = new Customer(customerID, businessName, address, telephoneNumber);
//            try {
//                boolean success = customerDAO.updateCustomer(updatedCustomer);
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            he.sendResponseHeaders(200, 0);
//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
//            out.write(
//                    "<html>" +
//                            "<head><title>Customer Updated</title>" +
//                            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
//                            "</head>" +
//                            "<body>" +
//                            "<div class=\"container\">" +
//                            "<h1>Customer Successfully Updated</h1>" +
//                            "<table class=\"table\">" +
//                            "<thead>" +
//                            "<tr><th>customerID</th><th>BusinessName</th><th>Address</th><th>TelephoneNumber</th></tr>" +
//                            "</thead>" +
//                            "<tbody>" +
//                            "<tr>" +
//                            "<td>" + customerID + "</td>" +
//                            "<td>" + businessName + "</td>" +
//                            "<td>" + address + "</td>" +
//                            "<td>" + telephoneNumber + "</td>" +
//                            "</tr>" +
//                            "</tbody>" +
//                            "</table>" +
//                            "<a href=\"/AdminHandler\" class=\"btn btn-primary\">Back to Admin Menu</a>" +
//                            "</div>" +
//                            "</body>" +
//                            "</html>");
//            out.close();
//        }
//
//    }
//}



/* this class lets the user edit/update customers
 * 
 */
package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.CustomerDAO;
import Model.Address;
import Model.Customer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/* This class lets the user edit/update customers 
 * 
 */
public class UpdateCustomerHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {

        System.out.println("UpdateCustomerHandler Called");
        String method = he.getRequestMethod();
        CustomerDAO customerDAO = new CustomerDAO();

        if ("GET".equalsIgnoreCase(method)) {
            int customerid = Integer.parseInt(he.getRequestURI().getQuery().split("=")[1]);

            Customer customer = null;
            try {
                customer = customerDAO.findCustomer(customerid);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (customer != null) {
                he.sendResponseHeaders(200, 0);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

                Address address = customer.getAddress();
                out.write(
                        "<html>" +
                                "<head><title>Edit Customer</title></head>" +
                                "<body>" +
                                "<h1>Edit Customer</h1>" +
                                "<form method=\"post\" action=\"/editCustomer\">" +
                                "<input type=\"hidden\" name=\"CustomerID\" value=\"" + customer.getCustomerID() + "\" />" +
                                "<label>Business Name:</label> <input type=\"text\" name=\"BusinessName\" value=\"" + customer.getBusinessName() + "\" /><br>" +
                                "<label>Address Line 0:</label> <input type=\"text\" name=\"AddressLine0\" value=\"" + address.getaddressLine0()  + "\" /><br>" +
                                "<label>Address Line 1:</label> <input type=\"text\" name=\"AddressLine1\" value=\"" + address.getaddressLine1() +"\" /><br>" +
                                "<label>Address Line 2:</label> <input type=\"text\" name=\"AddressLine2\" value=\"" + address.getaddressLine2() + "\" /><br>" +
                                "<label>Country:</label> <input type=\"text\" name=\"Country\" value=\"" + address.getcountry() + "\" /><br>" +
                                "<label>Postcode:</label> <input type=\"text\" name=\"Postcode\" value=\"" +address.getpostCode()  + "\" /><br>" +
                                "<label>Telephone Number:</label> <input type=\"text\" name=\"TelephoneNumber\" value=\"" + customer.getTelephoneNumber() + "\" /><br>" +
                                "<button type=\"submit\">Save</button>" +
                                "<a href=\"/DisplayCustomer\">Cancel</a>" +
                                "</form>" +
                                "</body>" +
                                "</html>"
                );

                out.flush();
                out.close();
            } else {
                he.sendResponseHeaders(404, 0);
                he.getResponseBody().write("Customer not found.".getBytes());
                he.getResponseBody().close();
            }
        } else if ("POST".equalsIgnoreCase(method)) {
            String body = new String(he.getRequestBody().readAllBytes());
            String[] params = body.split("&");

            Map<String, String> paramMap = new HashMap<>();
            for (String param : params) {
                String[] keyValue = param.split("=");
                /* Initial code where a "+" was printed instead
                 * if (keyValue.length == 2) {
                 * paramMap.put(keyValue[0], (keyValue[1]));	
                 */
                if (keyValue.length == 2) {
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                    /* Decodes the key
                    initially, when I tried to use a space between words characters
                    whilst updating/editing
                    it would end up using a "+" sign instead
                    So I did some research and identified (https://www.regextranslator.com/)
                    that decoding the parameters was required
                    the "URLDecoder.decode" basically converted the "+"
                    into a space

                    */
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                    paramMap.put(key, value);
                }
            }// Extract values from the map and convert as needed
            /*		same concept as previous classes
             * 	this time with the Address address, it is calling the class Address to fill in the address details
             * 	the object address stores
             */

            try {
                int customerID = validateInteger(paramMap.get("CustomerID"), "Customer ID");
                String businessName = validateString(paramMap.get("BusinessName"), "Business Name");
                String telephoneNumber = validateString(paramMap.get("TelephoneNumber"), "Telephone Number");

                Address address = new Address(
                        validateString(paramMap.get("AddressLine0"), "Address Line 0"),
                        validateString(paramMap.get("AddressLine1"), "Address Line 1"),
                        validateString(paramMap.get("AddressLine2"), "Address Line 2"),
                        validateString(paramMap.get("Country"), "Country"),
                        validateString(paramMap.get("Postcode"), "Postcode")
                );

                Customer updatedCustomer = new Customer(customerID, businessName, address, telephoneNumber);

                boolean success = customerDAO.updateCustomer(updatedCustomer);
                if (!success) {
                    sendErrorResponse(he, "Failed to update customer.");
                } else {
                    he.sendResponseHeaders(200, 0);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
                    out.write(
                         "<html>" +
                                  "<head><title>Customer Updated</title>" +
                                  "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                                  "</head>" +
                                  "<body>" +
                                  "<div class=\"container\">" +
                                  "<h1>Customer Successfully Updated</h1>" +
                                  "<table class=\"table\">" +
                                  "<thead>" +
                                  "<tr><th>customerID</th><th>BusinessName</th><th>Address</th><th>TelephoneNumber</th></tr>" +
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
            throw new IllegalArgumentException(fieldName + " must be an number. Please re-enter.");
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
