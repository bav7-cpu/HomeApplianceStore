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


// when the admin clicks delete on the displayadminhandler class it will redirect to this class
//This class will allow for the user in the menu console or admin in the web server to delete an applaince from the system
// The class also shows the properties of the appliance that is being deleted

public class DeleteHandler implements HttpHandler{
    public void handle(HttpExchange he) throws IOException {

        System.out.println("DeleteHandler Called");
        he.sendResponseHeaders(200,0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody() ));

        /* takes the query string from the URL and turns it into a key-value pair
         * allows for id to be used and retreived later in the code
         * the id is the key-value pair, 
         * the id is passed through  HomeAppliance deletedProd = products.findProduct(id).
         * if the map wasn't used this would not be possible, at least i think
         * 
         * the 	Util.requestStringToMap in the util class will convert the query string into a map
         */
        Map <String,String> parms = Util.requestStringToMap
                (he.getRequestURI().getQuery());

        System.out.println(parms);

        
        int id = Integer.parseInt(parms.get("id"));

        HomeApplianceDAO products = new HomeApplianceDAO();



        try{
            // get the product details before we delete from the Database
            HomeAppliance deletedProd = products.findProduct(id);
            // actually delete from database;
            products.deleteProduct(id);


            out.write(
                    "<html>" +
                            "<head> <title>Home Appliances</title> "+
                            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                            "</head>" +
                            "<body>" +
                            "<h1> Appliance Deleted</h1>"+
                            "<table class=\"table\">" +
                            "<thead>" +
                            "  <tr>" +
                            "    <th>ID</th>" +
                            "    <th>SKU</th>" +
                            "    <th>Description</th>" +
                            "    <th>Category</th>" +
                            "    <th>Price</th>" +
                            "    <th>Warranty</th>" +

                            "  </tr>" +
                            "</thead>" +
                            "<tbody>");


            out.write(
                    "  <tr>"       +
                            "    <td>"+ deletedProd.getId() + "</td>" +
                            "    <td>"+ deletedProd.getSku() + "</td>" +
                            "    <td>"+ deletedProd.getDescription() + "</td>" +
                            "    <td>"+ deletedProd.getCategory() + "</td>" +
                            "    <td>"+ deletedProd.getPrice() + "</td>" +
                            "    <td>"+ deletedProd.getWarranty() + "</td>" +
                            "  </tr>"
            );

            out.write(
                    "</tbody>" +
                            "</table>" +
                            "<a href=\"/AdminHandler\" class=\"btn btn-secondary\">Back to Admin Menu</a>" +
                            "</body>" +
                            "</html>");
        }catch(SQLException se){
            System.out.println(se.getMessage());
        }
        out.close();


    }
}