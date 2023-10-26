package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MaterialTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Material(null));
        assertThrows(NullPointerException.class, () -> new Material(null, 3));
        assertThrows(NullPointerException.class, () -> new Material(null, 1, 2));
    }

    @Test
    public void constructor_invalid_throwsIllegalArgumentException() {
        String invalidMaterial = "";
        assertThrows(IllegalArgumentException.class, () -> new Material(invalidMaterial));
    }

    @Test
    public void isValidMaterialName() {
        // null name
        assertThrows(NullPointerException.class, () -> Material.isValidMaterialName(null));

        // invalid name
        assertFalse(Material.isValidMaterialName("")); // empty string
        assertFalse(Material.isValidMaterialName(" ")); // spaces only
        assertFalse(Material.isValidMaterialName("^")); // only non-alphanumeric characters
        assertFalse(Material.isValidMaterialName("food yummy*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Material.isValidMaterialName("potato chips")); // alphabets only
        assertTrue(Material.isValidMaterialName("2349")); // numbers only
        assertTrue(Material.isValidMaterialName("sack of 1kg potatoes")); // alphanumeric characters
        assertTrue(Material.isValidMaterialName("A Lot Of Flour")); // with capital letters
        assertTrue(Material.isValidMaterialName("2 kilogram bags of flour")); // long names
    }

    @Test
    public void isValidQuantity() {
        // invalid numbers (negative numbers)
        assertFalse(Material.isValidQuantity(-3));
        assertFalse(Material.isValidQuantity(Integer.MIN_VALUE));

        // valid numbers   (0 and positive numbers
        assertTrue(Material.isValidQuantity(0)); // zero
        assertTrue(Material.isValidQuantity(1)); // positive numbers
        assertTrue(Material.isValidQuantity(Integer.MAX_VALUE)); // positive numbers
    }

    @Test
    public void isValidMaterial() {
        // null material with quantity
        assertThrows(NullPointerException.class, () -> Material.isValidMaterial(null));

        // invalid materials
        assertFalse(Material.isValidMaterial("")); // empty string
        assertFalse(Material.isValidMaterial(" ")); // spaces only
        assertFalse(Material.isValidMaterial("^")); // only non-alphanumeric characters
        assertFalse(Material.isValidMaterial("food yummy*")); // contains non-alphanumeric characters

        // materials without quantity
        assertFalse(Material.isValidMaterial("potato chips")); // alphabets only
        assertFalse(Material.isValidMaterial("2349")); // numbers only
        assertFalse(Material.isValidMaterial("sacks of 1kg potatoes")); // alphanumeric characters
        assertFalse(Material.isValidMaterial("Lot Of Flour")); // with capital letters
        assertFalse(Material.isValidMaterial("Two-kilogram bags of flour")); // long names

        // invalid materials with quantity
        assertFalse(Material.isValidMaterial("42 ")); // spaces only
        assertFalse(Material.isValidMaterial("53 ^")); // only non-alphanumeric characters
        assertFalse(Material.isValidMaterial("12 food yummy*")); // contains non-alphanumeric characters

        // invalid numbers (negative numbers)
        assertFalse(Material.isValidMaterial("-23 potato chips")); // alphabets only
        assertFalse(Material.isValidMaterial("-50 2349")); // numbers only
        assertFalse(Material.isValidMaterial("-34 sacks of 1kg potatoes")); // alphanumeric characters
        assertFalse(Material.isValidMaterial("-1 Lot Of Flour")); // with capital letters
        assertFalse(Material.isValidMaterial("-20 2 kilogram bags of flour")); // long names

        // valid materials
        assertTrue(Material.isValidMaterial("23 potato chips")); // alphabets only
        assertTrue(Material.isValidMaterial("50 2349")); // numbers only
        assertTrue(Material.isValidMaterial("34 sacks of 1kg potatoes")); // alphanumeric characters
        assertTrue(Material.isValidMaterial("1 Lot Of Flour")); // with capital letters
        assertTrue(Material.isValidMaterial("20 2 kilogram bags of flour")); // long names

    }

    @Test
    public void equals() {
        // same name and quantity - return true
        Material material1a = new Material("50 potatoes");
        Material material1b = new Material("potatoes", 50);
        Material material1c = new Material("potatoes", 0, 50);
        assertTrue(material1a.equals(material1b));
        assertTrue(material1b.equals(material1c));
        assertTrue(material1a.equals(material1c));

        // same name and different current quantity - return false
        Material material2a = new Material("50 potatoes");
        Material material2b = new Material("potatoes", 50);
        Material material2c = new Material("potatoes", 10, 50);
        Material material2d = new Material("potatoes", 40, 50);
        assertFalse(material2a.equals(material2c));
        assertFalse(material2b.equals(material2c));
        assertFalse(material2c.equals(material2d));

        // different name and same current and required quantity - return false
        Material material3a = new Material("50 potatoes");
        Material material3b = new Material("50 carrots");
        Material material3c = new Material("potatoes", 50);
        Material material3d = new Material("carrots", 50);
        assertFalse(material3a.equals(material3b));
        assertFalse(material3c.equals(material3d));

        // additional checks
        assertTrue(material3a.equals(material3c));
        assertTrue(material3b.equals(material3d));

        // same name and different required quantity - return false
        Material material4a = new Material("50 potatoes");
        Material material4b = new Material("potatoes", 13);
        Material material4c = new Material("potatoes", 40, 60);
        Material material4d = new Material("potatoes", 40, 50);
        assertFalse(material4a.equals(material4b));
        assertFalse(material4c.equals(material4d));
    }
}
