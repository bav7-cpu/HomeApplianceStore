//21348140
package DAOTest;

import Model.Address;
import Model.Customer;
import org.junit.jupiter.api.Test;

import DAO.CustomerDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOTest {
	/* 
	 *	 this class will test the CustomerDAO 
	 * makes sure that customerID's and other details are the same as the id in the DB
	 *  and that customers can be added and deleted from the DB
	 */
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Test
    void testFindAllCustomers() throws SQLException {
        ArrayList<Customer> customers = customerDAO.findAllCustomers();
        assertNotNull(customers, "Customers list should not be null");
    }

    @Test
    void testFindCustomerById() throws SQLException {
        Customer customer = customerDAO.findCustomer(1);
        assertEquals(1, customer.getCustomerID(), "Customer ID should match");
    }

    @Test
    void testAddCustomer() throws SQLException {
        Address address = new Address("Line 1", "Line 2", "Line 3", "Country", "12345");
        Customer newCustomer = new Customer(100, "Test Business", address, "123-456-7890");
        boolean result = customerDAO.addCustomer(newCustomer);
        assertTrue(result, "Customer should be added successfully");
    }

    @Test
    void testDeleteCustomer() throws SQLException {
        boolean result = customerDAO.deleteCustomer(100);
        assertTrue(result, "Customer should have been deleted successfully");
    }
}
