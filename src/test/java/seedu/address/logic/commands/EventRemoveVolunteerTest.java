package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventvolunteercommands.EventRemoveVolunteerCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

public class EventRemoveVolunteerTest {
    private Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());

    @Test
    public void execute_invalidIndexes_removeUnsuccessful() {
        Index outOfBoundEventIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        Index outOfBoundVolunteerIndex = Index.fromOneBased(model.getFilteredVolunteerList().size() + 1);
        Index validEventIndex = Index.fromOneBased(model.getFilteredEventList().size());
        Index validVolunteerIndex = Index.fromOneBased(model.getFilteredVolunteerList().size());

        // invalid event id
        EventRemoveVolunteerCommand commandInvalidEventId = new EventRemoveVolunteerCommand(outOfBoundEventIndex,
                validVolunteerIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, ()
                -> commandInvalidEventId.execute(model));
        assertCommandFailure(commandInvalidEventId, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        // invalid volunteer id
        EventRemoveVolunteerCommand commandInvalidVolunteerId = new EventRemoveVolunteerCommand(validEventIndex,
                outOfBoundVolunteerIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, ()
                -> commandInvalidEventId.execute(model));

        // invalid event and volunteer id
        EventRemoveVolunteerCommand commandBothInvalidId = new EventRemoveVolunteerCommand(outOfBoundEventIndex,
                outOfBoundVolunteerIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, ()
                -> commandInvalidEventId.execute(model));
    }
    @Test
    public void execute_volunteerNotInEvent_removeUnsuccessful() {
        Index eventToRemoveFrom = Index.fromOneBased(model.getFilteredEventList().size());
        Index volunteerToRemove = Index.fromOneBased(model.getFilteredVolunteerList().size());
        EventRemoveVolunteerCommand command = new EventRemoveVolunteerCommand(eventToRemoveFrom, volunteerToRemove);
        assertThrows(CommandException.class, EventRemoveVolunteerCommand.MESSAGE_VOLUNTEER_NOT_IN_EVENT, ()
                -> command.execute(model));
    }
    @Test
    public void execute_validIndexes_removeSuccessful() {
        Model startModel = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(),
                new UserPrefs());
        Index validEventIndex = Index.fromOneBased(startModel.getFilteredEventList().size());
        Index validVolunteerIndex = Index.fromOneBased(startModel.getFilteredVolunteerList().size());
        // Add the volunteer to the event
        Event currentEvent = startModel.getEventStorage().getEventList().get(validEventIndex.getZeroBased());
        Volunteer volunteerToAdd = startModel.getVolunteerStorage().getVolunteerList()
                .get(validVolunteerIndex.getZeroBased());
        Event newEvent = currentEvent.addVolunteer(volunteerToAdd);
        startModel.setEvent(currentEvent, newEvent);
        EventRemoveVolunteerCommand command = new EventRemoveVolunteerCommand(validEventIndex, validVolunteerIndex);

        try {
            CommandResult commandResult = command.execute(startModel);
            Event eventToRemoveFrom = startModel.getEventStorage().getEventList().get(validEventIndex.getZeroBased());
            String expectedMessage = String.format(EventRemoveVolunteerCommand.MESSAGE_SUCCESS,
                    Messages.format(eventToRemoveFrom), eventToRemoveFrom.getAssignedVolunteers().size());
            assertEquals(commandResult.getFeedbackToUser(), expectedMessage);
        } catch (Exception e) {
            fail("Exception " + e + " should not be thrown here!");
        }
    }
    @Test
    public void equals() {
        EventRemoveVolunteerCommand firstCommand = new EventRemoveVolunteerCommand(INDEX_FIRST, INDEX_SECOND);
        EventRemoveVolunteerCommand secondCommand = new EventRemoveVolunteerCommand(INDEX_SECOND, INDEX_FIRST);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        EventRemoveVolunteerCommand firstCommandCopy = new EventRemoveVolunteerCommand(INDEX_FIRST, INDEX_SECOND);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different event and volunteer ids -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void toStringMethod() {
        Index eventIndex = Index.fromOneBased(1);
        Index volunteerIndex = Index.fromOneBased(1);
        EventRemoveVolunteerCommand command = new EventRemoveVolunteerCommand(eventIndex, volunteerIndex);
        String expected = EventRemoveVolunteerCommand.class.getCanonicalName() + "{assignedEventIndex=" + eventIndex
                + ", assignedVolunteerIndex=" + volunteerIndex + "}";
        assertEquals(expected, command.toString());
    }
}
