package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENTNAME_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATERIAL_HANDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BRAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_HELPOUT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventcommands.EventEditCommand.EditEventDescriptor;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_CLEANUP);
        assertTrue(DESC_CLEANUP.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CLEANUP.equals(DESC_CLEANUP));

        // null -> returns false
        assertFalse(DESC_CLEANUP.equals(null));

        // different types -> returns false
        assertFalse(DESC_CLEANUP.equals(5));

        // different values -> returns false
        assertFalse(DESC_CLEANUP.equals(DESC_HELPOUT));

        // different name -> returns false
        EditEventDescriptor editedCleanUp = new EditEventDescriptorBuilder(DESC_CLEANUP)
                                                    .withEventName(VALID_EVENTNAME_HELPOUT).build();
        assertFalse(DESC_CLEANUP.equals(editedCleanUp));

        // different start Date -> returns false
        editedCleanUp = new EditEventDescriptorBuilder(DESC_CLEANUP).withStartDate(VALID_START_DATETIME_HELPOUT)
                                                                    .build();
        assertFalse(DESC_CLEANUP.equals(editedCleanUp));

        // different end date -> returns false
        editedCleanUp = new EditEventDescriptorBuilder(DESC_CLEANUP).withEndDate(VALID_END_DATETIME_HELPOUT).build();
        assertFalse(DESC_CLEANUP.equals(editedCleanUp));

        // different roles -> returns false
        editedCleanUp = new EditEventDescriptorBuilder(DESC_CLEANUP).withRoles(VALID_ROLE_BRAIN).build();
        assertFalse(DESC_CLEANUP.equals(editedCleanUp));

        // different location -> returns false
        editedCleanUp = new EditEventDescriptorBuilder(DESC_CLEANUP).withLocation(VALID_LOCATION_HELPOUT).build();
        assertFalse(DESC_CLEANUP.equals(editedCleanUp));

        // different description -> returns false
        editedCleanUp = new EditEventDescriptorBuilder(DESC_CLEANUP).withDescription(VALID_DESCRIPTION_HELPOUT).build();
        assertFalse(DESC_CLEANUP.equals(editedCleanUp));

        // different materials -> returns false
        editedCleanUp = new EditEventDescriptorBuilder(DESC_CLEANUP).withMaterials(VALID_MATERIAL_HANDS).build();
        assertFalse(DESC_CLEANUP.equals(editedCleanUp));

        // different budget -> returns false
        editedCleanUp = new EditEventDescriptorBuilder(DESC_CLEANUP).withBudget(VALID_BUDGET_HELPOUT).build();
        assertFalse(DESC_CLEANUP.equals(editedCleanUp));
    }

    @Test
    public void toStringMethod() {
        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        String expected = EditEventDescriptor.class.getCanonicalName() + "{name="
                + editEventDescriptor.getEventName().orElse(null) + ", roles="
                + editEventDescriptor.getRoles().orElse(null) + ", start date="
                + editEventDescriptor.getStartDate().orElse(null) + ", end date="
                + editEventDescriptor.getEndDate().orElse(null) + ", location="
                + editEventDescriptor.getLocation().orElse(null) + ", description="
                + editEventDescriptor.getDescription().orElse(null) + ", materials="
                + editEventDescriptor.getMaterials().orElse(null) + ", budget="
                + editEventDescriptor.getBudget().orElse(null) + "}";
        assertEquals(expected, editEventDescriptor.toString());
    }
}
