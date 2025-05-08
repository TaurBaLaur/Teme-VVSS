package inventory.repository;

import inventory.model.InhousePart;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryRepositoryTest {

    @Test
    public void testAddPart_AddsPartToInventory() {
        InventoryRepository repo = new InventoryRepository() {
            @Override
            public void writeAll() {
                // suprascriem pentru a evita scrierea în fișier
            }
        };

        InhousePart part = new InhousePart(100, "Test Part", 10.0, 5, 1, 10, 999);
        int initialSize = repo.getInventory().getAllParts().size();

        repo.addPart(part);

        int finalSize = repo.getInventory().getAllParts().size();
        assertEquals(initialSize + 1, finalSize);
        assertEquals("Test Part", repo.getInventory().getAllParts().get(finalSize - 1).getName());
    }
    @Test
    public void testAddPart_CallsWriteAll() {
        final boolean[] wasWriteCalled = {false};

        InventoryRepository repo = new InventoryRepository() {
            @Override
            public void writeAll() {
                wasWriteCalled[0] = true;
            }
        };

        InhousePart part = new InhousePart(101, "WriteCheck", 15.0, 10, 2, 20, 111);
        repo.addPart(part);

        assertTrue(wasWriteCalled[0], "writeAll should have been called");
    }
}

