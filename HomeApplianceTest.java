//21348140
package ModelTest;

import Model.HomeAppliance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HomeApplianceTest {

	
    @Test
    void testHomeAppliance() {
        HomeAppliance appliance = new HomeAppliance(0, null, null, null, 0, 0, 0);

        appliance.setId(2);
        appliance.setSku("SKU456");
        appliance.setDescription("Fridge");
        appliance.setCategory("Kitchen");
        appliance.setPrice(1000);
        appliance.setWarranty(3);
        appliance.setStock(30);

        assertEquals(2, appliance.getId());
        assertEquals("SKU456", appliance.getSku());
        assertEquals("Fridge", appliance.getDescription());
        assertEquals("Kitchen", appliance.getCategory());
        assertEquals(1000, appliance.getPrice());
        assertEquals(3, appliance.getWarranty());
        assertEquals(30, appliance.getStock());
    }

}
