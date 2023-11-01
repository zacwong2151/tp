package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventvolunteercommands.EventListVolunteerCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

public class EventListVolunteerCommandTest {
    private Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ObservableList<Event> events = model.getFilteredEventList();
        ObservableList<Volunteer> volunteers = model.getFilteredVolunteerList();
        // Assign a volunteer to an event in the model
        events.get(events.size() - 1).addVolunteer(volunteers.get(volunteers.size() - 1));

        Index invalidIndex = Index.fromOneBased(events.size() + 1);
        EventListVolunteerCommand command = new EventListVolunteerCommand(invalidIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, ()
                -> command.execute(model));
    }
    @Test
    public void execute_validIndexes_listSuccessful() {
        ObservableList<Event> events = model.getFilteredEventList();
        ObservableList<Volunteer> volunteers = model.getFilteredVolunteerList();

        // Assign a volunteer to an event in the model
        events.get(events.size() - 1).addVolunteer(volunteers.get(volunteers.size() - 1));

        Index validIndex = Index.fromOneBased(events.size());
        EventListVolunteerCommand command = new EventListVolunteerCommand(validIndex);
        Event eventToAssign = model.getEventStorage().getEventList().get(validIndex.getZeroBased());


        String expectedMessage = String.format(EventListVolunteerCommand.MESSAGE_SUCCESS,
            eventToAssign.getEventName(), eventToAssign.getAssignedVolunteers().size());

        try {
            CommandResult commandResult = command.execute(model);
            assertEquals(commandResult.getFeedbackToUser(), expectedMessage);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    @Test
    public void equals() {
        EventListVolunteerCommand firstCommand = new EventListVolunteerCommand(INDEX_FIRST);
        EventListVolunteerCommand secondCommand = new EventListVolunteerCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        EventListVolunteerCommand firstCommandCopy = new EventListVolunteerCommand(INDEX_FIRST);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different event and volunteer ids -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void toStringMethod() {
        Index eventIndex = Index.fromOneBased(1);
        EventListVolunteerCommand command = new EventListVolunteerCommand(eventIndex);
        String expected = EventListVolunteerCommand.class.getCanonicalName() + "{eventIndex=" + eventIndex + "}";
        assertEquals(expected, command.toString());
    }
}
