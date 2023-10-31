package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class EventNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidEventName = "";
        assertThrows(IllegalArgumentException.class, () -> new EventName(invalidEventName));
    }

    @Test
    public void isValidEventName() {
        // null name
        assertThrows(NullPointerException.class, () -> EventName.isValidEventName(null));

        // invalid name
        assertFalse(EventName.isValidEventName("")); // empty string
        assertFalse(EventName.isValidEventName(" ")); // spaces only
        assertFalse(EventName.isValidEventName("^")); // only non-alphanumeric characters
        assertFalse(EventName.isValidEventName("/")); // only non-alphanumeric characters
        assertFalse(EventName.isValidEventName("donation*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(EventName.isValidEventName("food distribution")); // alphabets only
        assertTrue(EventName.isValidEventName("12345")); // numbers only
        assertTrue(EventName.isValidEventName("foodie number 123")); // alphanumeric characters
        assertTrue(EventName.isValidEventName("donAting ClotheS")); // with capital letters
        assertTrue(EventName.isValidEventName("donating clothes at ECP with friends")); // long names
    }

    @Test
    public void equals() {
        EventName eventName = new EventName("Valid Name");

        // same values -> returns true
        assertTrue(eventName.equals(new EventName("Valid Name")));

        // same object -> returns true
        assertTrue(eventName.equals(eventName));

        // null -> returns false
        assertFalse(eventName.equals(null));

        // different types -> returns false
        assertFalse(eventName.equals(5.0f));

        // different values -> returns false
        assertFalse(eventName.equals(new EventName("Other Valid Name")));
    }
}
