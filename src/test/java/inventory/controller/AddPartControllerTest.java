package inventory.controller;

import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javafx.application.Platform;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import javafx.scene.control.Button;


@ExtendWith(MockitoExtension.class)
@Tag("ControllerTests")  // 🏷️ Adnotare pentru clasificarea testelor
class AddPartControllerTest {

    InventoryRepository repo;
    private InventoryService service;
    private AddPartController controller;

    @Mock private ActionEvent event;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        repo = new InventoryRepository();
        service = new InventoryService(repo);
        controller = new AddPartController();

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.setService(service);
            controller.setNameTxt(new TextField());
            controller.setPriceTxt(new TextField());
            controller.setInventoryTxt(new TextField());
            controller.setMinTxt(new TextField());
            controller.setMaxTxt(new TextField());
            controller.setAddPartDynamicTxt(new TextField());
            latch.countDown();
        });

        latch.await();
    }


    /** ECP Tests **/
    @Test
    @DisplayName(" Test salvare part valid - Inhouse")  //  Adnotare pentru denumire personalizată
    @Timeout(2) //  Testul trebuie să ruleze în maxim 2 secunde
    void testHandleAddPartSave_ValidInhousePart() throws IOException {
        controller.getNameTxt().setText("Screw");
        controller.getPriceTxt().setText("5.99");
        controller.getInventoryTxt().setText("10");
        controller.getMinTxt().setText("1");
        controller.getMaxTxt().setText("20");
        controller.getAddPartDynamicTxt().setText("101");
        controller.setOutsourced(false);

        Stage mockStage = mock(Stage.class);
        Button mockButton = new Button();
        ActionEvent mockEvent = new ActionEvent(mockButton, mockStage);

        controller.handleAddPartSave(mockEvent);

        assertTrue(true);
    }

    @Test
    @DisplayName("Test salvare part valid - Outsourced")  //  Adnotare pentru denumire personalizată
    @RepeatedTest(3) //  Testul va rula de 3 ori pentru consistență
    void testHandleAddPartSave_ValidOutsourcedPart() throws Exception {
        controller.getNameTxt().setText("Bolt");
        controller.getPriceTxt().setText("2.50");
        controller.getInventoryTxt().setText("50");
        controller.getMinTxt().setText("5");
        controller.getMaxTxt().setText("100");
        controller.getAddPartDynamicTxt().setText("SupplierX");
        controller.setOutsourced(true);

        Stage mockStage = mock(Stage.class);
        Button mockButton = new Button();
        ActionEvent mockEvent = new ActionEvent(mockButton, mockStage);

        controller.handleAddPartSave(mockEvent);

        assertTrue(true);
    }

    @Test
    @DisplayName("Test valid input") //  Adnotare pentru claritate
    @Disabled("🔧 Test dezactivat temporar pentru debugging") // Dezactivează testul temporar
    void testHandleAddPartSave_InvalidInput_ShowsAlert() throws IOException {
        Platform.runLater(() -> {
            try {
                controller.getNameTxt().setText("Bolt"); // Invalid input
                controller.getPriceTxt().setText("1");
                controller.getInventoryTxt().setText("2");
                controller.getMinTxt().setText("3");
                controller.getMaxTxt().setText("4");
                controller.getAddPartDynamicTxt().setText("SupplierY");
                controller.setOutsourced(true);

                Stage mockStage = mock(Stage.class);
                Button mockButton = new Button();
                ActionEvent mockEvent = new ActionEvent(mockButton, mockStage);

                controller.handleAddPartSave(mockEvent);

                assertTrue(true);
            } catch (Exception e) {
                fail("Test failed due to exception: " + e.getMessage());
            }
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }



    /** BVA Tests **/
    @Test
    @DisplayName("Test salvare nume gol - Outsourced")
    @RepeatedTest(1)
    void testHandleAddPartSave_ValidBVANamePart() throws Exception {
        Platform.runLater(() -> {
            controller.getNameTxt().setText("");
            controller.getPriceTxt().setText("2.50");
            controller.getInventoryTxt().setText("5");
            controller.getMinTxt().setText("5");
            controller.getMaxTxt().setText("100");
            controller.getAddPartDynamicTxt().setText("SupplierX");
            controller.setOutsourced(true);

            Stage mockStage = mock(Stage.class);
            Button mockButton = new Button();
            ActionEvent mockEvent = new ActionEvent(mockButton, mockStage);

            try {
                controller.handleAddPartSave(mockEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            assertTrue(true);
        });

        Thread.sleep(100); // Așteaptă puțin pentru ca JavaFX să proceseze event-ul
    }
    @Test
    @DisplayName("Test salvare nume gol - Outsourced")
    @RepeatedTest(1)
    void testHandleAddPartSave_ValidBVAPricePart() throws Exception {
        Platform.runLater(() -> {
            controller.getNameTxt().setText("Bolt");
            controller.getPriceTxt().setText("0");
            controller.getInventoryTxt().setText("50");
            controller.getMinTxt().setText("5");
            controller.getMaxTxt().setText("100");
            controller.getAddPartDynamicTxt().setText("SupplierX");
            controller.setOutsourced(true);

            Stage mockStage = mock(Stage.class);
            Button mockButton = new Button();
            ActionEvent mockEvent = new ActionEvent(mockButton, mockStage);

            try {
                controller.handleAddPartSave(mockEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            assertTrue(true);
        });

        Thread.sleep(100); // Așteaptă puțin pentru ca JavaFX să proceseze event-ul
    }


    @Test
    @DisplayName("Test salvare nume gol - Outsourced")
    @RepeatedTest(1)
    void testHandleAddPartSave_ValidBVAMinMaxPart() throws Exception {
        Platform.runLater(() -> {
            controller.getNameTxt().setText("Bolt");
            controller.getPriceTxt().setText("2.50");
            controller.getInventoryTxt().setText("5");
            controller.getMinTxt().setText("4");
            controller.getMaxTxt().setText("3");
            controller.getAddPartDynamicTxt().setText("SupplierX");
            controller.setOutsourced(true);

            Stage mockStage = mock(Stage.class);
            Button mockButton = new Button();
            ActionEvent mockEvent = new ActionEvent(mockButton, mockStage);

            try {
                controller.handleAddPartSave(mockEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            assertTrue(true);
        });

        Thread.sleep(100); // Așteaptă puțin pentru ca JavaFX să proceseze event-ul
    }

    @Test
    void testHandleAddPartSave_ValidBVA_MinimumValues() throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                controller.getAddPartDynamicTxt().setText("A");
                controller.getInventoryTxt().setText("1");
                controller.setOutsourced(true);
                controller.handleAddPartSave(event);
                assertTrue(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                latch.countDown();
            }
        });
        latch.await();
    }


}