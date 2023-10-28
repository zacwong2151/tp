package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.volunteer.Name;

public class EventTest {

    @Test
    public void eventTest() {
        // temporary implementation until EventBuilder is implemented
        EventName name = new EventName("Clean home");

        Role clean = new Role("cleaning");
        Set<Role> roles = new HashSet<>();
        roles.add(clean);

        Set<Material> materials = new HashSet<>();
        Material cloth = new Material("cloth");
        Material soap = new Material("soap");
        materials.add(cloth);
        materials.add(soap);

        Budget b = new Budget("1000.00");
        Set<Name> vList = new HashSet<>();
        Event e = new Event(
                name,
                roles,
                new DateTime("10/10/2023 1234"),
                new DateTime(LocalDateTime.of(2024, 1, 1, 12, 34)),
                new Location("NUS"),
                new Description("No description."),
                materials,
                b,
                vList);

        assertTrue(e.getEventName().equals(new EventName("Clean home")));
        assertTrue(e.getRoles().contains(clean));
        assertTrue(e.getMaterials().contains(cloth));
        assertTrue(e.getMaterials().contains(soap));
        assertTrue(e.getStartDate().equals(new DateTime("10/10/2023 1234")));
        assertTrue(e.getEndDate().equals(new DateTime("1/1/2024 1234")));
        assertTrue(e.getLocation().equals(new Location("NUS")));
        assertTrue(e.getDescription().equals(new Description("No description.")));
        assertTrue(e.getBudget().equals(b));
    }
}
