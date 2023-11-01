package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalid_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRoleName() {
        // valid role
        assertTrue(Role.isValidRoleName("chef"));

        // empty string
        assertFalse(Role.isValidRoleName(""));
    }

    @Test
    public void equals() {
        Role role = new Role("chef");

        // same values -> returns true
        assertTrue(role.equals(new Role("chef")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different values -> returns false
        assertFalse(role.equals(new Role("packer")));
    }
}
