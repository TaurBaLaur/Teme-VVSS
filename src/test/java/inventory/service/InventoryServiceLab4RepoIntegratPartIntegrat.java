package inventory.service;

import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;

public class InventoryServiceLab4RepoIntegratPartIntegrat {

    private InventoryRepository repo;
    private InventoryService service;

    @BeforeEach
    public void setUp() throws Exception {
        // Resetăm fișierul între teste
        PrintWriter writer = new PrintWriter("data/items.txt");
        writer.close();

        repo = new InventoryRepository(); // Repo real
        service = new InventoryService(repo);
    }

    @Test
    public void testAddInhousePart_withValidData_shouldBeAddedToRepo() {
        int initialSize = repo.getInventory().getAllParts().size();

        service.addInhousePart("TestInhouse", 50.0, 10, 1, 100, 999);

        ObservableList<Part> parts = repo.getInventory().getAllParts();
        assertEquals(initialSize + 1, parts.size());

        Part added = parts.get(parts.size() - 1);
        assertTrue(added instanceof InhousePart);
        assertEquals("TestInhouse", added.getName());
        assertEquals(50.0, added.getPrice());
        assertEquals(10, added.getInStock());
    }

    @Test
    public void testAddOutsourcePart_withValidData_shouldBeAddedToRepo() {
        int initialSize = repo.getInventory().getAllParts().size();

        service.addOutsourcePart("TestOutsource", 75.0, 15, 5, 50, "TestCompany");

        ObservableList<Part> parts = repo.getInventory().getAllParts();
        assertEquals(initialSize + 1, parts.size());

        Part added = parts.get(parts.size() - 1);
        assertTrue(added instanceof OutsourcedPart);
        assertEquals("TestOutsource", added.getName());
        assertEquals("TestCompany", ((OutsourcedPart) added).getCompanyName());
    }

    @AfterEach
    public void tearDown() {
        // Curăță fișierul după fiecare test
        new File("data/items.txt").delete();
    }
}
