package seedu.address.storage.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.event.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.FIRST;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Budget;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;

public class JsonAdaptedEventTest {
    private static final String INVALID_EVENT_NAME = "C@ean beach";
    private static final String INVALID_START_DATE = "30-09-2023 1800";
    private static final String INVALID_END_DATE = "30-09-2023 2100";
    private static final String INVALID_LOCATION = "Sing@pore";
    private static final String INVALID_DESCRIPTION = "Cle&ning the beach.";
    private static final String INVALID_BUDGET = "One thousand";
    private static final String INVALID_MATERIAL = "no@hing";
    private static final String INVALID_ROLE = "cle&ner";

    private static final String VALID_EVENT_NAME = FIRST.getEventName().toString();
    private static final String VALID_START_DATE = FIRST.getStartDate().toString();
    private static final String VALID_END_DATE = FIRST.getEndDate().toString();
    private static final String VALID_LOCATION = FIRST.getLocation().toString();
    private static final String VALID_DESCRIPTION = FIRST.getDescription().toString();
    private static final String VALID_BUDGET = FIRST.getBudget().toString();
    private static final List<JsonAdaptedName> VALID_ASSIGNED_VOLUNTEERS = FIRST.getAssignedVolunteers().stream()
            .map(JsonAdaptedName::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedRole> VALID_ROLES = FIRST.getRoles().stream()
            .map(JsonAdaptedRole::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedMaterial> VALID_MATERIALS = FIRST.getMaterials().stream()
            .map(JsonAdaptedMaterial::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(FIRST);
        assertEquals(FIRST, event.toModelType());
    }

    @Test
    public void toModelType_invalidEventName_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(INVALID_EVENT_NAME, VALID_ROLES, VALID_START_DATE, VALID_END_DATE,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_MATERIALS, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = EventName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventName_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(null, VALID_ROLES, VALID_START_DATE, VALID_END_DATE,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_MATERIALS, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_ROLES, INVALID_START_DATE, VALID_END_DATE,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_MATERIALS, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_ROLES, null, VALID_END_DATE,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_MATERIALS, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndDate_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_ROLES, VALID_START_DATE, INVALID_END_DATE,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_MATERIALS, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_ROLES, VALID_START_DATE, null,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_MATERIALS, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_ROLES, VALID_START_DATE, VALID_END_DATE,
                        INVALID_LOCATION, VALID_DESCRIPTION, VALID_MATERIALS, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_ROLES, VALID_START_DATE, VALID_END_DATE,
                        null, VALID_DESCRIPTION, VALID_MATERIALS, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_ROLES, VALID_START_DATE, VALID_END_DATE,
                        VALID_LOCATION, INVALID_DESCRIPTION, VALID_MATERIALS, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_ROLES, VALID_START_DATE, VALID_END_DATE,
                        VALID_LOCATION, null, VALID_MATERIALS, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidBudget_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_ROLES, VALID_START_DATE, VALID_END_DATE,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_MATERIALS, INVALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = Budget.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullBudget_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_ROLES, VALID_START_DATE, VALID_END_DATE,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_MATERIALS, null, VALID_ASSIGNED_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Budget.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidRoles_throwsIllegalValueException() {
        List<JsonAdaptedRole> invalidRoles = new ArrayList<>(VALID_ROLES);
        invalidRoles.add(new JsonAdaptedRole(INVALID_ROLE));
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, invalidRoles, VALID_START_DATE, VALID_END_DATE,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_MATERIALS, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        assertThrows(IllegalValueException.class, event::toModelType);
    }

    @Test
    public void toModelType_invalidMaterials_throwsIllegalValueException() {
        List<JsonAdaptedMaterial> invalidMaterials = new ArrayList<>(VALID_MATERIALS);
        invalidMaterials.add(new JsonAdaptedMaterial(INVALID_MATERIAL));
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_ROLES, VALID_START_DATE, VALID_END_DATE,
                        VALID_LOCATION, VALID_DESCRIPTION, invalidMaterials, VALID_BUDGET, VALID_ASSIGNED_VOLUNTEERS);
        assertThrows(IllegalValueException.class, event::toModelType);
    }
}
