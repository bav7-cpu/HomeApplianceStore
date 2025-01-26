//21348140
package ModelTest;

import Model.Admin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
	/* the method tests that the admin getters and setters work properly
	 * even though there isn't an area in the webserver/menu console for a new admin to be add
	 * it will be useful to check incase that changes when the code is rechecked 
	 * 
	 */
    @Test
    void testAdminProperties() {
        Admin admin = new Admin(0, null, null);

        admin.setuserID(2);
        admin.setusername("newAdmin");
        admin.setpassword("newPassword");

        assertEquals(2, admin.getuserID());
        assertEquals("newAdmin", admin.getusername());
        assertEquals("newPassword", admin.getpassword());
    }

}