package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.eventcommands.EventAddMaterialCommand.MESSAGE_MATERIAL_NOT_FOUND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventcommands.EventAddMaterialCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class EventAddMaterialCommandTest {

    // pancakes is not a material name included within the TypicalEventStorage
    public static final String INVALID_MATERIAL_NAME = "pancakes";
    public static final String VALID_MATERIAL_NAME_TRASHBAG = "trash bags";

    private Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());

    @Test
    public void constructor_nullEventIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAddMaterialCommand(
                null, 0, ""));
    }

    @Test
    public void constructor_nullMaterialName_throwsNullPointerException() {
        Index eventIndex = Index.fromOneBased(model.getFilteredEventList().size() - 1);

        // null material name
        assertThrows(NullPointerException.class, () -> new EventAddMaterialCommand(
                eventIndex, 0, null));

        // null event index and material name
        assertThrows(NullPointerException.class, () -> new EventAddMaterialCommand(
                null, 0, null));
    }

    @Test
    public void execute_invalidIndexes_throwsCommandException() {
        Index outOfBoundEventIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);

        // invalid event id
        EventAddMaterialCommand commandInvalidEventId = new EventAddMaterialCommand(outOfBoundEventIndex,
                0, "none");
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, ()
                -> commandInvalidEventId.execute(model));
        assertCommandFailure(commandInvalidEventId, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        EventAddMaterialCommand commandNullModel = new EventAddMaterialCommand(INDEX_FIRST,
                0, "none");
        assertThrows(NullPointerException.class, () -> commandNullModel.execute(null));
    }

    @Test
    public void execute_invalidMaterialName_throwsCommandException() {
        EventAddMaterialCommand commandInvalidMaterialName = new EventAddMaterialCommand(INDEX_FIRST,
                0, INVALID_MATERIAL_NAME);
        assertThrows(CommandException.class, String.format(MESSAGE_MATERIAL_NOT_FOUND, INVALID_MATERIAL_NAME), ()
                -> commandInvalidMaterialName.execute(model));
        assertCommandFailure(commandInvalidMaterialName, model,
                String.format(MESSAGE_MATERIAL_NOT_FOUND, INVALID_MATERIAL_NAME));
    }

    @Test
    public void equals() {
        EventAddMaterialCommand firstCommand = new EventAddMaterialCommand(INDEX_FIRST, 0, "");
        EventAddMaterialCommand secondCommand =
                new EventAddMaterialCommand(INDEX_SECOND, 0, "");
        EventAddMaterialCommand thirdCommand =
                new EventAddMaterialCommand(INDEX_FIRST, 3, "");
        EventAddMaterialCommand fourthCommand =
                new EventAddMaterialCommand(INDEX_SECOND, 0, "apples");

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        EventAddMaterialCommand firstCommandCopy =
                new EventAddMaterialCommand(INDEX_FIRST, 0, "");
        assertTrue(firstCommand.equals(firstCommandCopy));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different event id -> returns false
        assertFalse(firstCommand.equals(secondCommand));

        // different quantity -> returns false
        assertFalse(firstCommand.equals(thirdCommand));

        // different material name -> returns false
        assertFalse(secondCommand.equals(fourthCommand));
    }

    @Test
    public void toStringMethod() {
        Index eventIndex = Index.fromOneBased(1);
        EventAddMaterialCommand command = new EventAddMaterialCommand(eventIndex, 20, "apples");
        String expected = EventAddMaterialCommand.class.getCanonicalName() + "{eventIndex=" + eventIndex
                + ", quantityToAdd=" + 20 + ", materialName=apples}";
        assertEquals(expected, command.toString());
    }
}
