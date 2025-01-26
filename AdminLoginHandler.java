//21348140
package Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import CookieManagement.SessionManager;
import CookieManagement.Util;
import DAO.AdminDAO;
import Model.Admin;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

// This class will allow for the admin to login to access admin features
public class AdminLoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        try {
            String method = he.getRequestMethod();
            if ("GET".equalsIgnoreCase(method)) {
                // Serve login page
                // it will have a username and password where they can submit this information or cancel
                // if they cancel they will be redirected to the root handler
                he.sendResponseHeaders(200, 0);
                try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()))) {
                    out.write("<html><body><h1>Login</h1><form method='POST' action='/Login'>"
                            + "Username: <input type='text' name='username'><br>"
                            + "Password: <input type='password' name='password'><br>"
                            + "<button type='submit'>Login</button></form></body></html>"
                            + "  <a href='/' class='btn btn-secondary'>Cancel</a>");
                }
            } else if ("POST".equalsIgnoreCase(method)) {
                // Handle login
                String body = new String(he.getRequestBody().readAllBytes());
                Map<String, String> params = Util.requestStringToMap(body);

                String username = params.get("username");
                String password = params.get("password");

                /*Run through DB by using the "finduserByUsername" method
                 * if the password they enter is the same as the one in the DB, it will redirect them to the "AdminHandler"
                 * otherwise and error will occur
                 */
                AdminDAO dao = new AdminDAO();
                Admin user = dao.findUserByUsername(username);
                if (user != null && user.getpassword().equals(password)) {
                    // if the login is successful
                    String sessionId = SessionManager.createSession(user.getuserID(), username, password);
                    he.getResponseHeaders().add("Set-Cookie", "session=" + sessionId + "; Path=/; HttpOnly");
                    he.getResponseHeaders().set("Location", "/AdminHandler");
                    he.sendResponseHeaders(302, -1); // Redirect
                } else {
                    // if the login is unsuccessful
                    he.sendResponseHeaders(401, 0);
                    try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()))) {
                        out.write("<html><body><h1>Invalid Credentials</h1><a href='/Login'>Back</a></body></html>");
                    }
                }
            }
        } catch (Exception e) {
            he.sendResponseHeaders(500, 0);
            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()))) {
                out.write("<html><body><h1>Server Error</h1><p>" + e.getMessage() + "</p></body></html>");
            }
            e.printStackTrace(); 
        }
    }
}