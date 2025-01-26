//21348140
package Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import CookieManagement.SessionManager;
import CookieManagement.Util;

import java.io.IOException;

/*   this class allows for the admin to logout
 *  	when they choose to do this they are redirected to the roothandler class
 */

public class LogoutHandler implements HttpHandler {
    @Override
    /* Method retrieves the session ID from the cookie
     */
    public void handle(HttpExchange he) throws IOException {
        String sessionId = Util.getSessionCookie(he.getRequestHeaders().getFirst("Cookie"));

        //   removes session if it exists
        if (sessionId != null) {
            SessionManager.removeSession(sessionId);
        }

        // clears the cookie
        he.getResponseHeaders().add("Set-Cookie", "session=; Path=/; HttpOnly; Max-Age=0");

     
        he.getResponseHeaders().set("Location", "/");
        he.sendResponseHeaders(302, -1);

    }
}
