package inventory.service;

import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryServiceTestLab4 {

    private InventoryRepository mockRepo;
    private InventoryService inventoryService;

    @BeforeEach
    public void setup() {
        mockRepo = mock(InventoryRepository.class);
        when(mockRepo.getAutoPartId()).thenReturn(1);
        inventoryService = new InventoryService(mockRepo);
    }

    // 🔹 TEST 1: InhousePart - valid add
    @Test
    public void testAddInhousePart_CreatesCorrectInhousePart() {
        inventoryService.addInhousePart("Bolt", 1.5, 10, 1, 20, 123);

        ArgumentCaptor<InhousePart> captor = ArgumentCaptor.forClass(InhousePart.class);
        verify(mockRepo).addPart(captor.capture());

        InhousePart captured = captor.getValue();
        assertEquals("Bolt", captured.getName());
        assertEquals(123, captured.getMachineId());
    }

    // 🔹 TEST 2: InhousePart - min > max (nu verificăm validare, doar că obiectul e adăugat oricum)
    @Test
    public void testAddInhousePart_MinGreaterThanMax() {
        inventoryService.addInhousePart("Screw", 2.0, 5, 10, 5, 456);
        verify(mockRepo).addPart(any(InhousePart.class));
    }

    // 🔹 TEST 3: OutsourcedPart - valid add
    @Test
    public void testAddOutsourcePart_CreatesCorrectOutsourcedPart() {
        inventoryService.addOutsourcePart("Wheel", 10.0, 7, 2, 12, "SupplierX");

        ArgumentCaptor<OutsourcedPart> captor = ArgumentCaptor.forClass(OutsourcedPart.class);
        verify(mockRepo).addPart(captor.capture());

        OutsourcedPart captured = captor.getValue();
        assertEquals("Wheel", captured.getName());
        assertEquals("SupplierX", captured.getCompanyName());
    }

    // 🔹 TEST 4: OutsourcedPart - price negative (testăm doar dacă este adăugat)
    @Test
    public void testAddOutsourcePart_NegativePrice() {
        inventoryService.addOutsourcePart("Chain", -5.0, 3, 1, 5, "CheapCo");
        verify(mockRepo).addPart(any(OutsourcedPart.class));
    }
}
