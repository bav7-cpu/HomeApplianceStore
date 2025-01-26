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

public class RootHandler implements HttpHandler {

	/* 	this class is what the user sees when they first click on the webserver
	 * 	there are options to login, for the admin
	 * display products
	 * and check the basket
	 */

    public void handle(HttpExchange he) throws IOException {

        // String cookies = he.getRequestHeaders().getFirst("Cookie");
        String sessionId = Util.getSessionCookie(he.getRequestHeaders().getFirst("Cookie"));
        Session session = SessionManager.getSession(sessionId);

        boolean isAdminLoggedIn = SessionManager.isSessionValid(sessionId);

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
                            getNavbar(isAdminLoggedIn) + // Navbar at the top
                            "    <div class=\"container mt-4\">" +
                            "      <h1>Welcome to the Home Appliance Store</h1>" +
                            "      <a href=\"/appliances\" class=\"btn btn-primary\">Display Products</a>");

            out.write("</body>" +
                    "</html>");
        }
    }

    /*  
     *  The method simulates the RootdHandler
   	 *  also allows for the above method to be simulated
   	 */ 
    private String getNavbar(boolean isAdminLoggedIn) {

        return "<nav class=\"navbar navbar-expand-lg bg-body-tertiary\">" +
                "  <div class=\"container-fluid\">" +
                (isAdminLoggedIn
                        ? "<li class=\"nav-item\"><a class=\"nav-link\" href=\"/logout\">Logout</a></li>"
                        : "<li class=\"nav-item\"><a class=\"nav-link\" href=\"/Login\">Login</a></li>" +
                          "<li class=\"nav-item\"><a class=\"nav-link\" href=\"/basket\">Basket</a></li>") +
                "      </ul>" +
                "    </div>" +
                "  </div>" +
                "</nav>";
    }
}