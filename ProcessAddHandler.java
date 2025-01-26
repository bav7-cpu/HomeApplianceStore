//21348140
package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import CookieManagement.Util;
import DAO.HomeApplianceDAO;
import Model.HomeAppliance;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.Map;

/*  this class lets the admin add an appliance to the DB
* 
*/
public class ProcessAddHandler implements HttpHandler {

    public void handle(HttpExchange he) throws IOException {
        System.out.println("ProcessAddProduct Called");

        String body = new String(he.getRequestBody().readAllBytes());
        Map<String, String> params = Util.requestStringToMap(body);

        System.out.println("Params received: " + params);

        HomeApplianceDAO appliances = new HomeApplianceDAO();

        try {
            //  validates the admin input
            int id = validateInteger(params.get("id"), "ID");
            String sku = validateString(params.get("sku"), "SKU");
            String description = validateString(params.get("description"), "Description");
            String category = validateString(params.get("category"), "Category");
            int price = validateInteger(params.get("price"), "Price");
            int warranty = validateInteger(params.get("warranty"), "Warranty");
            int stock = validateInteger(params.get("stock"), "Stock");

            HomeAppliance product = new HomeAppliance(id, sku, description, category, price, warranty, stock);
            appliances.addProduct(product);

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
// initial plan, but decided to go with the approach of making the user aware that the appliance has actually been added instead
//  send user back to menu, initially I had an issue where the page would just be loading and the user wouldn't get sent anywhere
// unless they refreshed the page or went and clicked back to menu manually
//          he.getResponseHeaders().add("Location", "/");
//          he.sendResponseHeaders(302, -1); // 302 Found: Redirect to the menu
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

//package Handler;
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//
//import CookieManagement.Util;
//import DAO.HomeApplianceDAO;
//import Model.HomeAppliance;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.sql.SQLException;
//import java.util.Map;
//
//
///*  this class lets the admin add an appliance to the DB
// * 
// */
//
//public class ProcessAddHandler implements HttpHandler {
//    public void handle(HttpExchange he) throws IOException {
//        System.out.println("ProcessAddProduct Called");
//       
//        
//        /*key-value pair again this time for all the details of the product
//         * in this case the id is the key and the exact id in the DB will be the value
//         * same for the rest of the product details
//         */
//        String body = new String(he.getRequestBody().readAllBytes());
//        Map<String, String> params = Util.requestStringToMap(body);
//
//// Map<String, String> params = Util.requestStringToMap(he.getRequestURI().getQuery());
//        System.out.println("Params received: " + params);
//
//        HomeApplianceDAO appliances = new HomeApplianceDAO();
//
//        try {
//            // 	parses and validate the product details
//            int id = Integer.parseInt(params.get("id"));
//            String sku = params.get("sku");
//            String description = params.get("description");
//            String category = params.get("category");
//            int price = Integer.parseInt(params.get("price"));
//            int warranty = Integer.parseInt(params.get("warranty"));
//            int stock = Integer.parseInt(params.get("stock"));
//
//            HomeAppliance product = new HomeAppliance(id, sku, description, category, price, warranty, stock);
//            appliances.addProduct(product);
//            he.sendResponseHeaders(200, 0);
//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
//            out.write(
//                    "<html>" +
//                            "<head><title>Product Added</title>" +
//                            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
//                            "</head>" +
//                            "<body>" +
//                            "<div class=\"container\">" +
//                            "<h1>Appliance Added Successfully</h1>" +
//                            "<table class=\"table\">" +
//                            "<thead>" +
//                            "<tr><th>ID</th><th>SKU</th><th>Description</th><th>Category</th><th>Price</th><th>Warranty</th><th>Stock</th></tr>" +
//                            "</thead>" +
//                            "<tbody>" +
//                            "<tr>" +
//                            "<td>" + id + "</td>" +
//                            "<td>" + sku + "</td>" +
//                            "<td>" + description + "</td>" +
//                            "<td>" + category + "</td>" +
//                            "<td>" + price + "</td>" +
//                            "<td>" + warranty + "</td>" +
//                            "<td>" + stock + "</td>" +
//                            "</tr>" +
//                            "</tbody>" +
//                            "</table>" +
//                            "<a href=\"/AdminHandler\" class=\"btn btn-primary\">Back to Admin Menu</a>" +
//                            "</div>" +
//                            "</body>" +
//                            "</html>");
//            out.close();
//            // initial plan, but decided to go with the approach of making the user aware that the appliance has actually been added instead
//            // send user back to menu, initially I had an issue where the page would just be loading and the user wouldn't get sent anywhere
//            //unless they refreshed the page or went and clicked back to menu manually
////            he.getResponseHeaders().add("Location", "/");
////            he.sendResponseHeaders(302, -1); // 302 Found: Redirect to the menu
//        } catch (SQLException e) {
//            // Handle SQL errors and send an appropriate error response
//            he.sendResponseHeaders(500, 0);
//        }
//
//    }
//}