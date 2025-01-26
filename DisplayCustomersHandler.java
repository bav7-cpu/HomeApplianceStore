//21348140
package Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import CookieManagement.SessionManager;
import CookieManagement.Util;
import DAO.CustomerDAO;
import Model.Customer;
import Model.Session;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;


/* when admin clicks displaycustomers they will be redirected to this class
 * this class allows the user to see all of the customers in the system
 */

public class DisplayCustomersHandler implements HttpHandler {

    public void handle(HttpExchange he) throws IOException {

        String sessionId = Util.getSessionCookie(he.getRequestHeaders().getFirst("Cookie"));
        Session session = SessionManager.getSession(sessionId);

        if (session == null) {
            he.getResponseHeaders().set("Location", "/DisplayCustomer");
            he.sendResponseHeaders(302, -1);
        }
        he.sendResponseHeaders(200, 0);

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        CustomerDAO customer = new CustomerDAO();
        try {
            ArrayList<Customer> allCustomers = customer.findAllCustomers();


            out.write(
                    "<html>" +
                            "<head> <title>Home Appliance Library</title> " +
                            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                            "</head>" +
                            "<body>" +
                            "<div class=\"container\">" +
                            "<h1> Customers!</h1>" +
                            "<table class=\"table\">" +
                            "<thead>" +
                            "  <tr>" +
                            "    <th>Customer Id</th>" +
                            "    <th>Business Name</th>" +
                            "    <th>Address</th>" +
                            "    <th>Telephone Number</th>" +
                            "    <th>Delete Customer</th>" + 	
                            //delete and edit options for admin
                            "    <th>Edit Customer Details</th>" +
                            "  </tr>" +
                            "</thead>" +
                            "<tbody>");

            for (Customer d : allCustomers) {
                System.out.println("Rendering products: " + d);
                out.write(
                        "  <tr>" +
                                "    <td>" + d.getCustomerID() + "</td>" +
                                "    <td>" + d.getBusinessName() + "</td>" +
                                "    <td>" + d.getAddress() + "</td>" +
                                "    <td>" + d.getTelephoneNumber() + "</td>" +
                                "    <td> <a href=\"/deleteCustomer?customerID=" + d.getCustomerID() + "\"> delete </a> </td>" + 
                                "    <td> <a href=\"/editCustomer?customerID=" + d.getCustomerID() + "\"> edit </a> </td>" +
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

