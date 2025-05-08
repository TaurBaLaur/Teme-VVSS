package inventory.model;

import inventory.model.Part;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PartTest {

    @Test
    public void testIsValidPart_ValidInput() {
        String error = Part.isValidPart("Motor", 10.0, 5, 1, 10, "");
        assertEquals("", error, "Expected no error message for valid input.");
    }

    @Test
    public void testIsValidPart_InvalidInput() {
        String error = Part.isValidPart("", -5.0, 0, 10, 5, "");

        assertTrue(error.contains("A name has not been entered."));
        assertTrue(error.contains("The price must be greater than 0."));
        assertTrue(error.contains("Inventory level must be greater than 0."));
        assertTrue(error.contains("The Min value must be less than the Max value."));
    }
}

