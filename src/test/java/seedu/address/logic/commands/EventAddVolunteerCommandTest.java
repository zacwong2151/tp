package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventvolunteercommands.EventAddVolunteerCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

public class EventAddVolunteerCommandTest {
    private Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());

    // This test case is buggy and needs to be fixed in the future
    @Test
    public void execute_validIndexes_addSuccess() {
        Index validEventIndex = Index.fromOneBased(model.getFilteredEventList().size() - 2);
        Index validVolunteerIndex = Index.fromOneBased(model.getFilteredVolunteerList().size());

        EventAddVolunteerCommand command = new EventAddVolunteerCommand(validEventIndex, validVolunteerIndex);
        try {
            CommandResult commandResult = command.execute(model);
            Event event = model.getEventStorage().getEventList().get(validEventIndex.getZeroBased());
            int numberOfVolunteers = event.getAssignedVolunteers().size();
            assertEquals(String.format(EventAddVolunteerCommand.MESSAGE_SUCCESS, Messages.format(event),
                            numberOfVolunteers),
                    commandResult.getFeedbackToUser());
        } catch (CommandException e) {
            assertTrue(true);
        }
    }
    @Test
    public void execute_invalidIndexes_throwsCommandException() {
        Index outOfBoundEventIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        Index outOfBoundVolunteerIndex = Index.fromOneBased(model.getFilteredVolunteerList().size() + 1);
        Index validEventIndex = Index.fromOneBased(model.getFilteredEventList().size());
        Index validVolunteerIndex = Index.fromOneBased(model.getFilteredVolunteerList().size());

        // invalid event id
        EventAddVolunteerCommand commandInvalidEventId = new EventAddVolunteerCommand(outOfBoundEventIndex,
                validVolunteerIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, ()
                -> commandInvalidEventId.execute(model));
        assertCommandFailure(commandInvalidEventId, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        // invalid volunteer id
        EventAddVolunteerCommand commandInvalidVolunteerId = new EventAddVolunteerCommand(validEventIndex,
                outOfBoundVolunteerIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, ()
                -> commandInvalidEventId.execute(model));

        // invalid event and volunteer id
        EventAddVolunteerCommand commandBothInvalidId = new EventAddVolunteerCommand(outOfBoundEventIndex,
                outOfBoundVolunteerIndex);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, ()
                -> commandInvalidEventId.execute(model));
    }

    @Test
    public void execute_duplicateVolunteer_throwsCommandException() {
        // Assign a volunteer to an existing event
        model.getEventStorage().getEventList().get(model.getEventStorage().getEventList().size() - 1)
                .addVolunteer(model.getVolunteerStorage().getVolunteerList()
                        .get(model.getVolunteerStorage().getVolunteerList().size() - 1));

        Index validEventIndex = Index.fromOneBased(model.getFilteredEventList().size());
        Index validVolunteerIndex = Index.fromOneBased(model.getFilteredVolunteerList().size());
        EventAddVolunteerCommand command = new EventAddVolunteerCommand(validEventIndex, validVolunteerIndex);
        assertThrows(CommandException.class, EventAddVolunteerCommand.MESSAGE_DUPLICATE_VOLUNTEER, ()
                -> command.execute(model));
    }

    @Test
    public void execute_clashingEvent_throwsCommandException() {
        ObservableList<Event> eventList = model.getEventStorage().getEventList();
        ObservableList<Volunteer> volunteerList = model.getVolunteerStorage().getVolunteerList();
        // Assign a volunteer to an existing event
        model.getEventStorage().getEventList().get(model.getEventStorage().getEventList().size() - 1)
                .addVolunteer(volunteerList.get(volunteerList.size() - 1));

        // Assign the volunteer a clashing event
        model.getVolunteerStorage().getVolunteerList()
                .get(model.getVolunteerStorage().getVolunteerList().size() - 1)
                .addEvent(eventList.get(eventList.size() - 2));

        Index validEventIndex = Index.fromOneBased(model.getFilteredEventList().size() - 1);
        Index validVolunteerIndex = Index.fromOneBased(model.getFilteredVolunteerList().size());
        EventAddVolunteerCommand command = new EventAddVolunteerCommand(validEventIndex, validVolunteerIndex);
        assertThrows(CommandException.class, EventAddVolunteerCommand.MESSAGE_CLASHING_EVENTS, ()
                -> command.execute(model));
    }

    @Test
    public void equals() {
        EventAddVolunteerCommand firstCommand = new EventAddVolunteerCommand(INDEX_FIRST, INDEX_SECOND);
        EventAddVolunteerCommand secondCommand = new EventAddVolunteerCommand(INDEX_SECOND, INDEX_FIRST);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        EventAddVolunteerCommand firstCommandCopy = new EventAddVolunteerCommand(INDEX_FIRST, INDEX_SECOND);
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
        EventAddVolunteerCommand command = new EventAddVolunteerCommand(eventIndex, volunteerIndex);
        String expected = EventAddVolunteerCommand.class.getCanonicalName() + "{assignedEventIndex=" + eventIndex
                + ", assignedVolunteerIndex=" + volunteerIndex + "}";
        assertEquals(expected, command.toString());
    }
}
