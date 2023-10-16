package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

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

        Budget b = new Budget("$1000");
        Event e = new Event(
                name,
                roles,
                new DateTime("10/10/2023 1234"),
                new Location("NUS"),
                new Description("No description."),
                materials,
                b);

        assertTrue(e.getEventName().equals(new EventName("Clean home")));
        assertTrue(e.getRoles().contains(clean));
        assertTrue(e.getMaterials().contains(cloth));
        assertTrue(e.getMaterials().contains(soap));
        assertTrue(e.getDateAndTime().equals(new DateTime("10/10/2023 1234")));
        assertTrue(e.getLocation().equals(new Location("NUS")));
        assertTrue(e.getDescription().equals(new Description("No description.")));
        assertTrue(e.getBudget().equals(b));
    }
}
