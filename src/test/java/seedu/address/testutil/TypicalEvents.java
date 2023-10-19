package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EventStorage;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Volunteer} objects to be used in tests.
 */
public class TypicalEvents {
    public static final Event FIRST = new EventBuilder().withEventName("Clean 1")
            .withRoles("cleaner")
            .withDateAndTime("23/10/2023 1800")
            .withLocation("Sembawang")
            .withDescription("Clean up la")
            .withMaterials("trash bag", "gloves")
            .withBudget("50.00").build();

    public static final Event SECOND = new EventBuilder().withEventName("Clean 2")
            .withRoles("cleaner")
            .withDateAndTime("23/10/2023 1800")
            .withLocation("Sembawang")
            .withDescription("Clean up la")
            .withMaterials("trash bag", "gloves")
            .withBudget("50.00").build();

    public static final Event THIRD = new EventBuilder().withEventName("Clean 3")
            .withRoles("cleaner")
            .withDateAndTime("23/10/2023 1800")
            .withLocation("Sembawang")
            .withDescription("Clean up la")
            .withMaterials("trash bag", "gloves")
            .withBudget("50.00").build();

    public static final Event FOURTH = new EventBuilder().withEventName("Clean 4")
            .withRoles("cleaner")
            .withDateAndTime("23/10/2023 1800")
            .withLocation("Sembawang")
            .withDescription("Clean up la")
            .withMaterials("trash bag", "gloves")
            .withBudget("50.00").build();

    public static final Event FIFTH = new EventBuilder().withEventName("Clean 5")
            .withRoles("cleaner")
            .withDateAndTime("23/10/2023 1800")
            .withLocation("Sembawang")
            .withDescription("Clean up la")
            .withMaterials("trash bag", "gloves")
            .withBudget("50.00").build();

    public static final Event SIXTH = new EventBuilder().withEventName("Clean 6")
            .withRoles("cleaner")
            .withDateAndTime("23/10/2023 1800")
            .withLocation("Sembawang")
            .withDescription("Clean up la")
            .withMaterials("trash bag", "gloves")
            .withBudget("50.00").build();

    public static final Event SEVENTH = new EventBuilder().withEventName("Clean 7")
            .withRoles("cleaner")
            .withDateAndTime("23/10/2023 1800")
            .withLocation("Sembawang")
            .withDescription("Clean up la")
            .withMaterials("trash bag", "gloves")
            .withBudget("50.00").build();
    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code VolunteerStorage} with all the typical volunteers.
     */
    public static EventStorage getTypicalEventStorage() {
        EventStorage ab = new EventStorage();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {

        return new ArrayList<>(Arrays.asList(FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH));
    }
}
