//21348140
package DAOTest;

import Model.HomeAppliance;
import org.junit.jupiter.api.Test;

import DAO.HomeApplianceDAO;

import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class HomeApplianceDAOTest {

    private final HomeApplianceDAO dao = new HomeApplianceDAO();
    /* 
	 *	 this class will test the HomeApplianceDAO 
	 * makes sure that product id and other details are the same as the id in the DB
	 *  and that appliance can  added from the DB
	 */
    @Test
    void testFindAllProducts() throws SQLException {
        assertNotNull(dao.findAllProducts(), "Products list should not be null");
    }

    @Test
    void testFindProduct() throws SQLException {
        assertNotNull(dao.findProduct(1), "Product with ID 1 should exist");
    }

    @Test
    void testAddAndDeleteProduct() throws SQLException {
        HomeAppliance appliance = new HomeAppliance(100, "TestSKU", "Test", "Category", 100, 1, 10);
        assertTrue(dao.addProduct(appliance), "Product has been added");
    }
}
