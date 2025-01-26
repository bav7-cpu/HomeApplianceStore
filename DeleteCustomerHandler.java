//21348140
package Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import CookieManagement.Util;
import DAO.CustomerDAO;
import Model.Customer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.Map;

// when the admin clicks delete on the displaycustomers class it will redirect to this class
//This class will allow for the user in the menu console or admin in the web server to delete a customer from the system
// The class also shows the properties of the customer that is being deleted

public class DeleteCustomerHandler implements HttpHandler{
    public void handle(HttpExchange he) throws IOException {

        System.out.println("deleteCustomer Called");
        he.sendResponseHeaders(200,0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody() ));

        // Get param from URL
        Map <String,String> parms = Util.requestStringToMap
                (he.getRequestURI().getQuery());

        // print the params for debugging
        System.out.println(parms);

        //get customerID number
        int customerID = Integer.parseInt(parms.get("customerID"));

       CustomerDAO customers = new CustomerDAO();



        try{
            // get the product details before we delete from the Database
            Customer deletedCustomer = customers.findCustomer(customerID);
            // actually delete from database;
            customers.deleteCustomer(customerID);


            out.write(	
                    "<html>" +
                            "<head> <title>Customers</title> "+
                            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                            "</head>" +
                            "<body>" +
                            "<h1> Customer Deleted</h1>"+
                            "<table class=\"table\">" +
                            "<thead>" +
                            "  <tr>" +
                            "    <th>CustomerID</th>" +
                            "    <th>Business Name</th>" +
                            "    <th>addressLine0</th>" +
                            "    <th>addressLine1</th>" +
                            "    <th>addressLine2</th>" +
                            "    <th>country</th>" +
                            "    <th>Post Code</th>" +
                            "    <th>Telephone Number</th>" +

                            "  </tr>" +
                            "</thead>" +
                            "<tbody>");


            out.write( // just displays the deleted customer
                    "  <tr>"       +
                            "    <td>"+ deletedCustomer.getCustomerID() + "</td>" +
                            "    <td>"+ deletedCustomer.getBusinessName() + "</td>" +
                            "    <td>"+ deletedCustomer.getAddress().getaddressLine0() + "</td>" +
                            "    <td>"+ deletedCustomer.getAddress().getaddressLine1() + "</td>" +
                            "    <td>"+ deletedCustomer.getAddress().getaddressLine2() + "</td>" +
                            "    <td>"+ deletedCustomer.getAddress().getcountry() + "</td>" +
                            "    <td>"+ deletedCustomer.getAddress().getpostCode() + "</td>" +
                            "    <td>"+ deletedCustomer.getTelephoneNumber() + "</td>" +
                            "  </tr>"
            );

            out.write(
                    "</tbody>" +
                            "</table>" +
                            "<a href=\"/AdminHandler\">Back to Admin Menu </a>"+	//redirects to AdminHandler class if they click "back to admin menu"
                            "</body>" +
                            "</html>");
        }catch(SQLException se){
            System.out.println(se.getMessage());
        }
        out.close();


    }
}