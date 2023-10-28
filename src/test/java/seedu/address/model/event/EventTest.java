package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.volunteer.Name;
import seedu.address.testutil.EventBuilder;

public class EventTest {

    @Test
    public void eventTest() {
        // Default event
        Event defaultEvent = new EventBuilder().build();

        // Creation of event that comes after default event from event builder
        EventName firstName = new EventName("Clean home");

        Role clean = new Role("cleaning");
        Set<Role> firstRoles = new HashSet<>();
        firstRoles.add(clean);

        Set<Material> firstMaterials = new HashSet<>();
        Material cloth = new Material("cloth");
        Material soap = new Material("soap");
        firstMaterials.add(cloth);
        firstMaterials.add(soap);

        Budget firstBudget = new Budget("1000.00");
        Set<Name> firstVList = new HashSet<>();
        Event beforeCurrentEvent = new Event(
                firstName,
                firstRoles,
                new DateTime("01/01/2023 1234"),
                new DateTime(LocalDateTime.of(2024, 1, 1, 12, 34)),
                new Location("NUS"),
                new Description("No description."),
                firstMaterials,
                firstBudget,
                firstVList);

        // Creation of event that comes after default event from event builder
        EventName secondName = new EventName("Paint home");

        Role paint = new Role("painting");
        Set<Role> secondRoles = new HashSet<>();
        secondRoles.add(paint);

        Set<Material> secondMaterials = new HashSet<>();
        Material brush = new Material("paint brush");
        Material pail = new Material("pail");
        secondMaterials.add(brush);
        secondMaterials.add(pail);

        Budget secondBudget = new Budget("1000.00");
        Set<Name> secondVList = new HashSet<>();
        Event afterCurrentEvent = new Event(
                secondName,
                secondRoles,
                new DateTime("23/9/2023 1900"),
                new DateTime(LocalDateTime.of(2024, 1, 1, 12, 34)),
                new Location("NUS"),
                new Description("No description."),
                secondMaterials,
                secondBudget,
                secondVList);

        assertTrue(beforeCurrentEvent.getEventName().equals(new EventName("Clean home")));
        assertTrue(beforeCurrentEvent.getRoles().contains(clean));
        assertTrue(beforeCurrentEvent.getMaterials().contains(cloth));
        assertTrue(beforeCurrentEvent.getMaterials().contains(soap));
        assertTrue(beforeCurrentEvent.getStartDate().equals(new DateTime("01/01/2023 1234")));
        assertTrue(beforeCurrentEvent.getEndDate().equals(new DateTime("1/1/2024 1234")));
        assertTrue(beforeCurrentEvent.getLocation().equals(new Location("NUS")));
        assertTrue(beforeCurrentEvent.getDescription().equals(new Description("No description.")));
        assertTrue(beforeCurrentEvent.getBudget().equals(firstBudget));

        // Test date and time lesser than default event
        assertEquals(-1, beforeCurrentEvent.compareTo(defaultEvent));

        // Test date and time greater than default event
        assertEquals(1, afterCurrentEvent.compareTo(defaultEvent));
    }
}
