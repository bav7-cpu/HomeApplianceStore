//21348140
package ModelTest;

import Model.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

	/* the method tests that the address getters and setters work properly 
	 * 
	 */
    @Test
    void testAddressProperties() {
        Address address = new Address(null, null, null, null, null);

        address.setaddressLine0("456 St");
        address.setaddressLine1("Suite 1");
        address.setaddressLine2("Building");
        address.setcountry("Canada");
        address.setpostCode("ABC");

        assertEquals("456 St", address.getaddressLine0());
        assertEquals("Suite 1", address.getaddressLine1());
        assertEquals("Building", address.getaddressLine2());
        assertEquals("Canada", address.getcountry());
        assertEquals("ABC", address.getpostCode());
    }
}
