//21348140
package CookieManagementTest;

import CookieManagement.Util;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void testRequestStringToMap() {
        assertEquals(
                new HashMap<>() {{
                    put("key1", "value1");
                    put("key2", "value2");
                }},
                Util.requestStringToMap("key1=value1&key2=value2")
        );
    }

  
    @Test
    void testGetSessionCookie() {
        assertEquals("abc123", Util.getSessionCookie("session=abc123; user=JohnDoe"));
    }

    @Test
    void testGetSessionCookieNoSession() {
        assertNull(Util.getSessionCookie("user=JohnDoe; theme=dark"));
    }

    @Test
    void testGetSessionCookieEmptyHeader() {
        assertNull(Util.getSessionCookie(""));
    }

    @Test
    void testGetSessionCookieNullHeader() {
        assertNull(Util.getSessionCookie(null));
    }
}
