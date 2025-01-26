//21348140
package CookieManagement;
import com.sun.net.httpserver.HttpExchange;

import Model.Session;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Util {

	//converts queries into strings
    public static HashMap<String, String> requestStringToMap(String request) {
        HashMap<String, String> map = new HashMap<String, String>();
        String[] pairs = request.split("&");
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i];

            try {
                String key = pair.split("=")[0];
                key = URLDecoder.decode(key, "UTF-8");

                String value = pair.split("=")[1];
                value = URLDecoder.decode(value, "UTF-8");

                map.put(key, value);
            } catch (UnsupportedEncodingException e) {
                System.err.println(e.getMessage());
            }

        }
        return map;
    }
    public static String getSessionCookie(String cookieHeader) {
        if (cookieHeader == null || cookieHeader.isEmpty()) return null;
        String[] cookies = cookieHeader.split("; ");
        for (String cookie : cookies) {
            if (cookie.startsWith("session=")) {
                return cookie.substring("session=".length());
            }
        }
        return null;
    }

    public static Map<String, String> passInAllUserDetails(HttpExchange he) {
        String sessionId = getSessionCookie(he.getRequestHeaders().getFirst("Cookie"));
        Session session = SessionManager.getSession(sessionId);

        if (session == null) return Map.of(); // Return empty if no session exists

        return Map.of(
                "userId", String.valueOf(session.getUserId()),
                "userName", session.getUserName()
        );
    }

}

