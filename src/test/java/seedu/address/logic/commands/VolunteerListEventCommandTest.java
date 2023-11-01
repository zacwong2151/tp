package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventvolunteercommands.VolunteerListEventCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

public class VolunteerListEventCommandTest {
    private Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Assign a volunteer to an event in the model
        Volunteer volunteer = model
                .getVolunteerStorage()
                .getVolunteerList()
                .get(model.getFilteredVolunteerList().size() - 1);
        Event eventToAssign = model.getEventStorage().getEventList().get(model.getFilteredEventList().size() - 1);

        eventToAssign.addVolunteer(volunteer);

        Index invalidIndex = Index.fromOneBased(model.getFilteredVolunteerList().size() + 1);
        VolunteerListEventCommand command = new VolunteerListEventCommand(invalidIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, ()
                -> command.execute(model));
    }
    @Test
    public void execute_validIndexes_listSuccessful() {
        // Assign a volunteer to an event in the model
        Volunteer volunteer = model
                .getVolunteerStorage()
                .getVolunteerList()
                .get(model.getFilteredVolunteerList().size() - 1);
        Event eventToAssign = model.getEventStorage().getEventList().get(model.getFilteredEventList().size() - 1);

        eventToAssign.addVolunteer(volunteer);

        Index validIndex = Index.fromOneBased(model.getFilteredVolunteerList().size());
        VolunteerListEventCommand command = new VolunteerListEventCommand(validIndex);
        Volunteer volunteerToList = model.getVolunteerStorage().getVolunteerList().get(validIndex.getZeroBased());

        String expectedMessage = String.format(VolunteerListEventCommand.MESSAGE_SUCCESS,
                volunteerToList.getName().fullName, volunteerToList.getAssignedEvents().size());

        try {
            CommandResult commandResult = command.execute(model);
            assertEquals(commandResult.getFeedbackToUser(), expectedMessage);
        } catch (Exception e) {
            fail("Command should not throw an exception.");
        }
    }
    @Test
    public void equals() {
        VolunteerListEventCommand firstCommand = new VolunteerListEventCommand(INDEX_FIRST);
        VolunteerListEventCommand secondCommand = new VolunteerListEventCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        VolunteerListEventCommand firstCommandCopy = new VolunteerListEventCommand(INDEX_FIRST);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different event and volunteer ids -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void toStringMethod() {
        Index volunteerIndex = Index.fromOneBased(1);
        VolunteerListEventCommand command = new VolunteerListEventCommand(volunteerIndex);
        String expected = VolunteerListEventCommand.class.getCanonicalName()
                + "{volunteerIndex=" + volunteerIndex + "}";
        assertEquals(expected, command.toString());
    }
}
