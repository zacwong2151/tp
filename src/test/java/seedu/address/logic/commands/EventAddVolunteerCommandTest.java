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
import seedu.address.testutil.EventBuilder;

public class EventAddVolunteerCommandTest {
    @Test
    public void execute_invalidIndexes_throwsCommandException() {
        Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());
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
        Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());
        ObservableList<Event> events = model.getEventStorage().getEventList();
        ObservableList<Volunteer> volunteers = model.getVolunteerStorage().getVolunteerList();

        // Assign a volunteer to an existing event
        events.get(events.size() - 1).addVolunteer(volunteers.get(volunteers.size() - 1));

        Index validEventIndex = Index.fromOneBased(model.getFilteredEventList().size());
        Index validVolunteerIndex = Index.fromOneBased(model.getFilteredVolunteerList().size());
        EventAddVolunteerCommand command = new EventAddVolunteerCommand(validEventIndex, validVolunteerIndex);
        assertThrows(CommandException.class, EventAddVolunteerCommand.MESSAGE_DUPLICATE_VOLUNTEER, ()
                -> command.execute(model));
    }

    @Test
    public void execute_validIndexes_addSuccessful() {
        Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(),
                new UserPrefs());
        Index validEventIndex = Index.fromOneBased(model.getFilteredEventList().size());
        Index validVolunteerIndex = Index.fromOneBased(2);
        EventAddVolunteerCommand command = new EventAddVolunteerCommand(validEventIndex, validVolunteerIndex);

        try {
            CommandResult commandResult = command.execute(model);
            Event eventToAddTo = model.getEventStorage().getEventList().get(validEventIndex.getZeroBased());
            String expectedMessage = String.format(EventAddVolunteerCommand.MESSAGE_SUCCESS,
                    Messages.format(eventToAddTo), eventToAddTo.getAssignedVolunteers().size());
            assertEquals(commandResult.getFeedbackToUser(), expectedMessage);
        } catch (Exception e) {
            fail("Exception " + e + " should not be thrown!");
        }
    }

    @Test
    public void hasClashingEvents_clashingEvents_addFailure() {
        Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());
        ObservableList<Event> events = model.getEventStorage().getEventList();
        ObservableList<Volunteer> volunteers = model.getVolunteerStorage().getVolunteerList();

        // Assign a volunteer to an existing event
        Volunteer volunteerToAssign = volunteers.get(volunteers.size() - 1);
        Event eventToAssign = events.get(events.size() - 1);
        Volunteer updatedVolunteer = volunteerToAssign.addEvent(eventToAssign);
        Event updatedEvent = eventToAssign.addVolunteer(volunteerToAssign);
        model.setVolunteer(volunteerToAssign, updatedVolunteer);
        model.setEvent(eventToAssign, updatedEvent);

        // Start date clashes
        Event startDateClashingEvent = new EventBuilder().withEventName("Event 1")
                .withStartDate("23/10/2023 1900").withEndDate("23/10/2023 2200").build();
        model.addEvent(startDateClashingEvent);
        EventAddVolunteerCommand startDateClashCommand = new EventAddVolunteerCommand(
                Index.fromOneBased(model.getFilteredEventList().size()),
                Index.fromOneBased(model.getFilteredVolunteerList().size()));
        assertThrows(CommandException.class, EventAddVolunteerCommand.MESSAGE_CLASHING_EVENTS, ()
                -> startDateClashCommand.execute(model));

        // End date clashes
        Event endDateClashingEvent = new EventBuilder().withEventName("Event 2")
                .withStartDate("23/10/2023 1700").withEndDate("23/10/2023 2000").build();
        model.addEvent(endDateClashingEvent);
        EventAddVolunteerCommand endDateClashCommand = new EventAddVolunteerCommand(
                Index.fromOneBased(model.getFilteredEventList().size()),
                Index.fromOneBased(model.getFilteredVolunteerList().size()));
        assertThrows(CommandException.class, EventAddVolunteerCommand.MESSAGE_CLASHING_EVENTS, ()
                -> endDateClashCommand.execute(model));

        // Duration encompasses event
        Event durationEncompassesEvent = new EventBuilder().withEventName("Event 3")
                .withStartDate("23/10/2023 1700").withEndDate("23/10/2023 2200").build();
        model.addEvent(durationEncompassesEvent);
        EventAddVolunteerCommand durationEncompassesEventCommand = new EventAddVolunteerCommand(
                Index.fromOneBased(model.getFilteredEventList().size()),
                Index.fromOneBased(model.getFilteredVolunteerList().size()));
        assertThrows(CommandException.class, EventAddVolunteerCommand.MESSAGE_CLASHING_EVENTS, ()
                -> durationEncompassesEventCommand.execute(model));
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
