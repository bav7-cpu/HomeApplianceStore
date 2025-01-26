package Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class CheckoutHandler implements HttpHandler {
        public void handle(HttpExchange he) throws IOException {

            System.out.println("Checkout Called");
            he.sendResponseHeaders(200, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

                out.write(
                        "<html>" +
                                "<head> <title>Home Appliances</title> " +
                                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                                "</head>" +
                                "<body>" +
                                "<h1>Checkout Successful</h1>" +
                                "<h2>Thank You For Shopping At The Home Appliance Store</h2>" +
                                "<table class=\"table\">" +
                                "<thead>" +
                                "</div>" +
                                "</form>" +
                                "<form method='POST' action='/'>" +
                                "<button type=\"submit\" class=\"btn btn-primary\">Back To Home</button>" +
                                "</form>" +
                                "</div>" +
                                "</thead>" +
                                "<tbody>");
            out.close();
        }

}
