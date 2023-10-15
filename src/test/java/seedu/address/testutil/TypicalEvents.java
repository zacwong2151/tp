package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.EventStorage;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalEvents {
    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static EventStorage getTypicalEventStorage() {
        EventStorage ab = new EventStorage();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>();
    }
}
