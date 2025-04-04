package inventory.service;

import inventory.model.InhousePart;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;




@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryServiceTest {

    private InventoryRepository mockRepo;
    private InventoryService service;

    @BeforeAll
    static void initAll() {
        System.out.println("Starting InventoryService tests...");
    }

    @BeforeEach
    void setUp() {
        mockRepo = mock(InventoryRepository.class);
        service = new InventoryService(mockRepo);
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
    void testAddInhousePart_Valid() {
        // Arrange
        when(mockRepo.getAutoPartId()).thenReturn(1);
        ArgumentCaptor<InhousePart> partCaptor = ArgumentCaptor.forClass(InhousePart.class);

        // Act
        service.addInhousePart("Valid Part", 10.0, 5, 1, 10, 123);

        // Assert
        verify(mockRepo).addPart(partCaptor.capture());
        InhousePart capturedPart = partCaptor.getValue();

        assertEquals(1, capturedPart.getPartId());
        assertEquals("Valid Part", capturedPart.getName());
        assertEquals(10.0, capturedPart.getPrice());
        assertEquals(5, capturedPart.getInStock());
        assertEquals(1, capturedPart.getMin());
        assertEquals(10, capturedPart.getMax());
        assertEquals(123, capturedPart.getMachineId());
    }




    @Test
    @Order(4)
    void testAddInhousePart_BVA_MinBoundary() {
        // Arrange
        when(mockRepo.getAutoPartId()).thenReturn(2);
        ArgumentCaptor<InhousePart> partCaptor = ArgumentCaptor.forClass(InhousePart.class);

        // Act
        service.addInhousePart("Boundary Min", 1.0, 1, 1, 10, 123);

        // Assert
        verify(mockRepo).addPart(partCaptor.capture());
        assertEquals(1, partCaptor.getValue().getInStock());
    }


}
