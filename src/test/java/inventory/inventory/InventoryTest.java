package inventory.inventory;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InventoryTest {

    private Inventory inventory;


    @BeforeAll
    static void initAll() {
        System.out.println("Starting InventoryService tests...");
    }

    @BeforeEach
    void setUp() {
        inventory = new Inventory();

        Part part1 = new OutsourcedPart(1,"Motor",2.3,1,1,3,"company");
        Part part2 = new OutsourcedPart(2,"Baterie",2.3,1,1,3,"company");
        inventory.addPart(part1);
        inventory.addPart(part2);

    }

    @AfterEach
    void tearDown() {
        System.out.println("Test finished.");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("All tests finished.");
    }

    @Test
    @Order(1)
    void testLookupPart_Valid() {


        Part rezultPart = inventory.lookupPart("Motor");

        assertEquals(rezultPart.getPartId(),1);


    }

    @Test
    @Order(1)
    void testLookupPart_NonValid() {



        Part rezultPart = inventory.lookupPart("Roata");

        assertEquals(rezultPart,null);


    }


}
