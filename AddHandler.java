//21348140
package Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

// this class will display the appliance properties that the admin needs to fill in to add an appliance to the system

public class AddHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {

        System.out.println("Add Handler Called");
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));


        out.write(
                "<html>" +
                        "<head> <title>Home Appliance Library</title> " +
                        "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                        "</head>" +
                        "<body>" +
                        "<div class=\"container\">" +
                        "<h1> Add Product</h1>" +
                        "<form method=\"post\" action=\"/processAddProduct\">" +
                        "<div class=\"form-group\"> " +
                        "<label for=\"ID\">ID</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"id\" id=\"id\"> " +

                        "<label for=\"sku\">SKU</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"sku\" id=\"sku\"> " +

                        "<label for=\"description\">Description</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"description\" id=\"description\"> " +

                        "<label for=\"category\">Category</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"category\" id=\"category\"> " +

                        "<label for=\"price\">Price</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"price\" id=\"price\" >" +

                        "<label for=\"warranty\">Warranty</label> " +
                        "<input type=\"text\" class=\"form-control\" name=\"warranty\" id=\"warranty\" >" +
                        
						"<label for=\"stock\">Stock</label> " +
						"<input type=\"text\" class=\"form-control\" name=\"stock\" id=\"stock\" >" +



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
