//21348140
package ModelTest;

import Model.Customer;
import Model.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
	
	/* 
	 * This method will test that the customer getters and setters
	 */
    @Test
    void testCustomerProperties() {
        Address address = new Address(null, null, null, null, null);
        Customer customer = new Customer(0, null, address, null);
        
        customer.setCustomerID(2);
        customer.setBusinessName("NewBusiness");
        customer.setAddress(new Address("456 Fake St", "Suite 1", "Building", "Canada", "321"));
        customer.setTelephoneNumbery("098-765-4321");

        assertEquals(2, customer.getCustomerID());
        assertEquals("NewBusiness", customer.getBusinessName());
        assertNotEquals(address, customer.getAddress());  
        assertEquals("098-765-4321", customer.getTelephoneNumber());
    }

}
