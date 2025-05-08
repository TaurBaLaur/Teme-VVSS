package inventory.service;

import inventory.model.Part;
import inventory.repository.InventoryRepository;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.File;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryServiceWithTestLab4RepoIntegratMockPart {

    private InventoryRepository repo;
    private InventoryService service;

    @BeforeEach
    public void setUp() throws Exception {
        // Resetăm fișierul înainte de fiecare test
        PrintWriter writer = new PrintWriter("data/items.txt");
        writer.close();

        repo = new InventoryRepository(); // repo real
        service = new InventoryService(repo);
    }

    @Test
    public void testAddMockedInhousePart_shouldBeAddedToRepo() {
        // Cream un mock de Part (care ar reprezenta un InhousePart)
        Part mockedPart = mock(Part.class);
        when(mockedPart.getName()).thenReturn("MockedInhouse");
        when(mockedPart.getPrice()).thenReturn(10.0);
        when(mockedPart.getInStock()).thenReturn(5);

        int initialSize = repo.getInventory().getAllParts().size();

        // Simulăm adăugarea unui part prin metoda din repo
        repo.addPart(mockedPart);

        ObservableList<Part> parts = repo.getInventory().getAllParts();
        assertEquals(initialSize + 1, parts.size());

        Part added = parts.get(parts.size() - 1);
        assertEquals("MockedInhouse", added.getName());
    }

    @Test
    public void testAddMockedOutsourcePart_shouldBeAddedToRepo() {
        Part mockedPart = mock(Part.class);
        when(mockedPart.getName()).thenReturn("MockedOutsource");
        when(mockedPart.getPrice()).thenReturn(99.9);
        when(mockedPart.getInStock()).thenReturn(3);

        int initialSize = repo.getInventory().getAllParts().size();

        repo.addPart(mockedPart);

        ObservableList<Part> parts = repo.getInventory().getAllParts();
        assertEquals(initialSize + 1, parts.size());

        Part added = parts.get(parts.size() - 1);
        assertEquals("MockedOutsource", added.getName());
        assertEquals(3, added.getInStock());
    }

    @AfterEach
    public void tearDown() {
        new File("data/items.txt").delete();
    }
}
