//21348140
package ModelTest;
import Model.HomeAppliance;
import Model.Basket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {

	/* the method tests that the basket getters and setters work properly 
	 * 
	 */
    @Test
    void testBasketProperties() {
        HomeAppliance appliance = new HomeAppliance(0, null, null, null, 0, 0, 0);
        Basket basket = new Basket(1, appliance, 5);

        basket.setBasketID(2);
        basket.setId(new HomeAppliance(8, "Bye", "H", "C", 25, 1, 10));
        basket.setQuantity(10);

        assertEquals(2, basket.getBasketID());
        assertNotEquals(appliance, basket.getId());
        assertEquals(10, basket.getQuantity());
    }

}
