//21348140
package Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import CookieManagement.SessionManager;
import CookieManagement.Util;
import DAO.HomeApplianceDAO;
import Model.HomeAppliance;
import Model.Session;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/*
 * When the user clicks display products on the roothandler class it will redirect to this class
 * Then they can see all the products within the system
 * This class will allow for the user to filter products and add products to the basket
 */
public class DisplayHandler implements HttpHandler {

    //Create a pop up when user clicks add to basket in display handler,Option to increase or decrease and show the price

    public void handle(HttpExchange he) throws IOException {
        // Check for session cookie
        String sessionId = Util.getSessionCookie(he.getRequestHeaders().getFirst("Cookie"));
        Session session = SessionManager.getSession(sessionId);

        if (session != null) {
            // Redirect to main.Handler.DisplayAdminHandler
            he.getResponseHeaders().set("Location", "/display");
            he.sendResponseHeaders(302, -1);
            return;
        }

        // if no user logged in, proceed with default behavior
        he.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));

        HomeApplianceDAO products = new HomeApplianceDAO();
        try {
            ArrayList<HomeAppliance> allProducts = products.findAllProducts();

            // Simplified filter parameters retrieval
            String query = he.getRequestURI().getQuery();

            String filterCategory = query != null ? getParameterValue(query, "category") : "";
            String filterDescription = query != null ? getParameterValue(query, "description") : "";
            String filterMinPrice = query != null ? getParameterValue(query, "minPrice") : "";
            String filterMaxPrice = query != null ? getParameterValue(query, "maxPrice") : "";
            String sortOrder = query != null ? getParameterValue(query, "sort") : "";
            
            //           String filterCategory = query != null && query.contains("category=") ? query.split("category=")[1].split("&")[0] : null;
//            String filterDescription = query != null && query.contains("description=") ? query.split("description=")[1].split("&")[0] : null;
//            String filterMinPrice = query != null && query.contains("minPrice=") ? query.split("minPrice=")[1].split("&")[0] : null;
//            String filterMaxPrice = query != null && query.contains("maxPrice=") ? query.split("maxPrice")[1].split("&")[0] : null;

            // Further simplified filtering
            List<HomeAppliance> filteredProducts = allProducts.stream()

                    /*initially the filterCategory etc
                    was set to != null
                    This resulted in the user needing to enter something for each filter option
                    After using stackoverflow and other websites, .isEmpty appeared to be the best solution
                     */
                    .filter(p -> filterCategory.isEmpty() || p.getCategory().equalsIgnoreCase(filterCategory))
                    .filter(p -> filterDescription.isEmpty() || p.getDescription().equalsIgnoreCase(filterDescription))
                    .filter(p -> filterMinPrice.isEmpty() || p.getPrice() >= parseIntegerOrDefault(filterMinPrice, 0))
                    .filter(p -> filterMaxPrice.isEmpty() || p.getPrice() <= parseIntegerOrDefault(filterMaxPrice, Integer.MAX_VALUE))
                    .toList();
                    /*
                    Lambda expressions
                    || = OR
                     
            */

//            he.sendResponseHeaders(200, 0);
            /*
             * The comparator import will just sort the appliances from price by using getPrice
             * this will be ascending and descending
             */
            
            if ("asc".equalsIgnoreCase(sortOrder)) {
                filteredProducts = filteredProducts.stream()
                        .sorted(Comparator.comparingInt(HomeAppliance::getPrice)) 
                        .toList();
                
            } else if ("desc".equalsIgnoreCase(sortOrder)) {
                filteredProducts = filteredProducts.stream()
                        .sorted(Comparator.comparingInt(HomeAppliance::getPrice).reversed())
                        .toList();
            }

            out.write(
                    "<html>" +
                            "<head> <title>Home Appliance Library</title>" +
                            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\">" +
                            "<style>" +
                            ".filter-container { float: left; width: 200px; margin-right: 20px; background-color: #f8f9fa; padding: 15px; border-right: 1px solid #ddd; height: 100%; box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1); }" +
                            ".content-container { margin-left: 220px; }" +
                            ".back-to-home { margin-top: 20px;  width: 100%; }" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<div class=\"container\">" +
                            "<div class=\"filter-container\">" +
                            "<h4>Filter Products</h4>" +
                            "<form method=\"GET\" action=\"/appliances\">" +
                            "<div class=\"form-group\">" +
                            "<label for=\"category\">Category:</label>" +
                            "<input type=\"text\" id=\"category\" name=\"category\" value=\"" + filterCategory + "\" class=\"form-control\" placeholder=\"Enter category\">" +
                            "</div>" +
                            "<div class=\"form-group\">" +
                            "<label for=\"description\">Description:</label>" +
                            "<input type=\"text\" id=\"description\" name=\"description\" value=\"" + filterDescription + "\" class=\"form-control\" placeholder=\"Enter description\">" +
                            "</div>" +
                            "<div class=\"form-group\">" +
                            "<label for=\"minPrice\">Min Price:</label>" +
                            "<input type=\"text\" id=\"minPrice\" name=\"minPrice\" value=\"" + filterMinPrice + "\" class=\"form-control\" placeholder=\"Enter min price\">" +
                            "</div>" +
                            "<div class=\"form-group\">" +
                            "<label for=\"maxPrice\">Max Price:</label>" +
                            "<input type=\"text\" id=\"maxPrice\" name=\"maxPrice\" value=\"" + filterMaxPrice + "\" class=\"form-control\" placeholder=\"Enter max price\">" +
                            "<select name=\"sort\" class=\"form-control\">" +
                            "<div>" +
                            "<div class=\"form-group\">"+ 
                            "<option value=\"\">Sort Price</option>" +
                            "<option value=\"asc\" " + ("asc".equalsIgnoreCase(sortOrder) ? "selected" : "") + ">Low-High</option>" +
                            "<option value=\"desc\" " + ("desc".equalsIgnoreCase(sortOrder) ? "selected" : "") + ">High-Low</option>" +
                            "</select>" +
                            "</div>" +
                            "<button type=\"submit\" class=\"btn btn-primary\">Apply Filters</button>" +
                            "<a href=\"/\" class=\"btn btn-secondary btn-block back-to-home\">Back to Home</a>" +
                            "</form>" +
                            "</div>" +
                            "<div class=\"content-container\">" +
                            "<h1> Appliances</h1>" +
                            "<table class=\"table\">" +
                            "<thead><tr><th>Id</th><th>Sku</th><th>Description</th><th>Category</th><th>Price</th><th>Warranty</th><th>Stock</th><th><a href=\"/basket\" + \"\"> Basket </a> </th></tr></thead>" +
                            "<tbody>");

            for (HomeAppliance d : filteredProducts) {
                out.write(	//	"d" object used to find appliance properties from the HomeAppliance class
                        ("<tr>\n") +
                                "    <td>" + d.getId() + "</td>" +
                                "    <td>" + d.getSku() + "</td>" +
                                "    <td>" + d.getDescription() + "</td>" +
                                "    <td>" + d.getCategory() + "</td>" +
                                "    <td>" + d.getPrice() + "</td>" +
                                "    <td>" + d.getWarranty() + "</td>" +
                                "    <td>" + d.getStock() + "</td>" +
                                "    <td> <a href=\"/basket?id=" + d.getId() + "\"> + </a> </td>" +
                                "    </tr>");
            }

        } catch (SQLException se) {
            System.out.println("SQL Error: " + se.getMessage());
        }

        out.flush();
        out.close();
    }

    
    private int parseIntegerOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String getParameterValue(String query, String parameterName) {
        try {
            String[] parts = query.split(parameterName + "=");
            return parts[1].split("&")[0];
        } catch (Exception e) {
            return "";
        }
    }
}




