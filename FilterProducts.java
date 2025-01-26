//21348140
package Filter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import CookieManagement.SessionManager;
import CookieManagement.Util;
import Model.Session;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FilterProducts implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {

        String sessionId = Util.getSessionCookie(he.getRequestHeaders().getFirst("Cookie"));
        Session session = SessionManager.getSession(sessionId);

//        if (session == null) {
//            // Redirect to login if no session
//            he.getResponseHeaders().set("Location", "/Login");
//            he.sendResponseHeaders(302, -1);
//            return;
//        }
        he.sendResponseHeaders(200, 0);

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()))) {
            out.write(
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "  <head>" +
                            "    <title>Filtering Products</title>" +
                            "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                            "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\" " +
                            "            integrity=\"sha384-Q7yDLc3cb3BX9EOxUjKOlEuw9F4TBGrhP9t0e2FQoM0cD5StN3oUqa25MeFJ61An\" crossorigin=\"anonymous\"></script>" +
                            "  </head>" +
                            "  <body>" +
//                            getNavbar() + // Navbar at the top
                            "    <div class=\"container mt-4\">" +
                            "      <h1>Filtering Products</h1>" +
                            //  "<h1>Welcome, " + session.getUserName() + "</h1>" +
                            "       <a href=\"/FilterByCategory\" class=\"btn btn-primary\">Filter By Category </a>"  +
                            "       <a href=\"/FilterByDescription\" class=\"btn btn-primary \">Filter By Description</a> \n" +
                            "       <a href=\"/FilterByPrice\" class=\"btn btn-primary\">Filter By Price</a>" +
                            "       <a href=\"/\" class=\"btn btn-secondary\">Back to Menu</a>" +
                            "      <br><br>" +
                            "    </div>" +
                            "  </body>" +
                            "</html>"
            );
        }
    }
}
