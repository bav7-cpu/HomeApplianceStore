//21348140
package Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import CookieManagement.SessionManager;
import CookieManagement.Util;
import Model.Session;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/*
 * This class will be the first thing the admin sees when they login
 * They will be able to click on the following; display products,add products, display customers, add customers and logout
 */

public class AdminHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {

    	/*  Sessions allow for the admin to stay logged in
    	 * the session ID from the cookie is retreived 
    	 */
        String sessionId = Util.getSessionCookie(he.getRequestHeaders().getFirst("Cookie"));
        Session session = SessionManager.getSession(sessionId);

        he.sendResponseHeaders(200, 0);

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()))) {
            out.write(
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "  <head>" +
                            "    <title>Home Appliance Store</title>" +
                            "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                            "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\" " +
                            "            integrity=\"sha384-Q7yDLc3cb3BX9EOxUjKOlEuw9F4TBGrhP9t0e2FQoM0cD5StN3oUqa25MeFJ61An\" crossorigin=\"anonymous\"></script>" +
                            "  </head>" +
                            "  <body>" +
                            "    <div class=\"container mt-4\">" +
                           "      <h1>Welcome to the Home Appliance Store</h1>" +
                            "       <a href=\"/appliances\" class=\"btn btn-primary\">Display Products</a>"  +
                            "       <a href=\"/add\" class=\"btn btn-primary\">Add Products</a>" +
                            "       <a href=\"/DisplayCustomer\" class=\"btn btn-primary\">Display Customers</a>"  +
                            "       <a href=\"/addcustomers\" class=\"btn btn-primary\">Add Customers</a>" +
                            "       <a href=\"/Logout\" class=\"btn btn-danger\">Logout</a> \n" +
                            "      <br><br>" +
                            "    </div>" +
                            "  </body>" +
                            "</html>"
            );
        }
    }
}