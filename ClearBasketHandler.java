//21348140
package Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class ClearBasketHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        System.out.println("clearBasket Called");
        he.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        he.sendResponseHeaders(200, 0);

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()))) {
            out.write(
                    "<html>" +
                            "<head> <title>Basket Cleared</title> " +
                            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\">" +
                            "</head>" +
                            "<body>" +
                            "<div class=\"container\">" +
                            "<h1>Basket Cleared</h1>" +
                            "<form method='POST' action='/'>" +
                            "<button type=\"submit\" class=\"btn btn-primary\">Back To Home</button>" +
                            "</form>" +
                            "</div>" +
                            "</body></html>"
            );
        }
    }
}
