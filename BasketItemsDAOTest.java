//21348140
package DAOTest;

import Model.HomeAppliance;
import org.junit.jupiter.api.Test;

import DAO.BasketItemsDAO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BasketItemsDAOTest {

    private final BasketItemsDAO basketItemsDAO = new BasketItemsDAO();
    
    /*
     *  the method tests that an appliance within the basket is actually there
     *  the appliance will have these properties for the (1, "SKU123", "Test Appliance", "Category", 100, 2, 10)
     *  
     */ 

    @Test
    void testAddItemToBasket() {
        HomeAppliance appliance = new HomeAppliance(1, "SKU123", "Test Appliance", "Category", 100, 2, 10);
        boolean result = basketItemsDAO.addItemToBasket(1, appliance, 2);

        assertTrue(result, "Item should be added to the basket");
    }
}
