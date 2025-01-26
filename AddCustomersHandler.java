//21348140
package Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

// this class will display the customer properties that the admin needs to fill in to add a customer

public class AddCustomersHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {

        System.out.println("Add Customers Handler Called");
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));


        out.write(
                "<html>" +
                        "<head> <title>Home Appliance Library</title> " +
                        "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                        "</head>" +
                        "<body>" +
                        "<div class=\"container\">" +
                        "<h1> Add Customer</h1>" +
                        "<form method=\"get\" action=\"/processAddCustomer\">" +
                        "<div class=\"form-group\"> " +
                        "<label for=\"customerID\">Customer ID</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"customerID\" customerID=\"customerID\"> " +

                        "<label for=\"businessName\">Business Name</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"businessName\" customerID=\"businessName\"> " +

                        "<label for=\"address\">Address Line 0</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"addressLine0\" customerID=\"addressLine0\"> " +

                        "<label for=\"address\">Address Line 1</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"addressLine1\" customerID=\"addressLine1\"> " +

                        "<label for=\"address\">Address Line 2</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"addressLine2\" customerID=\"addressLine2\"> " +

                        "<label for=\"address\">Country</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"country\" customerID=\"country\"> " +

                        "<label for=\"address\">Post Code</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"postCode\" customerID=\"postCode\"> " +

                        "<label for=\"telephoneNumber\">Telephone Number</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"telephoneNumber\" customerID=\"telephoneNumber\"> " +


                        "<button type=\"submit\" class=\"btn btn-primary\">Submit</button> " +
                        "</div>" +
                        "</form>" +
                        "<a href=\"/AdminHandler\" class=\"btn btn-secondary\">Back to Admin Menu</a>" +
                        "</div>" +
                        "</body>" +
                        "</html>");

        out.close();

    }
}
