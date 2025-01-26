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


/* this class is very similar to the displayHandler
 * but it has the options to delete and edit products
 * 
 */
public class DisplayAdminHandler implements HttpHandler {

    public void handle(HttpExchange he) throws IOException {

        String sessionId = Util.getSessionCookie(he.getRequestHeaders().getFirst("Cookie"));
        Session session = SessionManager.getSession(sessionId);

        if (session == null) {
            he.getResponseHeaders().set("Location", "/Login");
            he.sendResponseHeaders(302, -1);

        }
        he.sendResponseHeaders(200, 0);

        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(he.getResponseBody()));
                HomeApplianceDAO products = new HomeApplianceDAO();
                try {
                    ArrayList<HomeAppliance> allProducts = products.findAllProducts();


                out.write(
                        "<html>" +
                                "<head> <title>Home Appliance Library</title> " +
                                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                                "</head>" +
                                "<body>" +
                                "<div class=\"container\">" +
                                "<h1> Appliances in Stock!</h1>" +
                                "<table class=\"table\">" +
                                "<thead>" +
                                "  <tr>" +
                                "    <th>Id</th>" +
                                "    <th>Sku</th>" +
                                "    <th>Description</th>" +
                                "    <th>Category</th>" +
                                "    <th>Price</th>" +
                                "    <th>Warranty</th>" +
                                "    <th>Stock</th>" +
                                "    <th>Delete</th>" +
                                "    <th>Edit</th>" +
                                "  </tr>" +
                                "</thead>" +
                                "<tbody>");

                for (HomeAppliance d : allProducts) {
                    System.out.println("Rendering products: " + d);
                    out.write(
                            "  <tr>" +
                                    "    <td>" + d.getId() + "</td>" +
                                    "    <td>" + d.getSku() + "</td>" +
                                    "    <td>" + d.getDescription() + "</td>" +
                                    "    <td>" + d.getCategory() + "</td>" +
                                    "    <td>" + d.getPrice() + "</td>" +
                                    "    <td>" + d.getWarranty() + "</td>" +
                                    "    <td>" + d.getStock() + "</td>" +
                                    "    <td> <a href=\"/delete?id=" + d.getId() + "\"> delete </a> </td>" +	//redirect admin to the deleteHandler
                                    "    <td> <a href=\"/edit?id=" + d.getId() + "\"> edit </a> </td>" + //redirect admin to the updateHandler
                                    "  </tr>"
                    );
                }
                out.write(
                        "</tbody>" +
                                "</table>" +
                                "<a href=\"/AdminHandler\" class=\"btn btn-secondary\">Back to Admin Menu</a>" + // Adjusted href to point to /display
                                "</div>" +
                                "</body>" +
                                "</html>");

                out.flush();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
    }
        
