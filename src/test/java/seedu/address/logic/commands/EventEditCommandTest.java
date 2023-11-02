package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HELPOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENTNAME_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventcommands.EventEditCommand;
import seedu.address.logic.commands.eventcommands.EventEditCommand.EditEventDescriptor;
import seedu.address.model.EventStorage;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VolunteerStorage;
import seedu.address.model.event.Event;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EventEditCommandTest {

    private Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Event editedEvent = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EventEditCommand eventEditCommand = new EventEditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EventEditCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new EventStorage(model.getEventStorage()),
                new VolunteerStorage(model.getVolunteerStorage()), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(eventEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EventEditCommand eventEditCommand = new EventEditCommand(INDEX_FIRST,
                new EditEventDescriptor());
        Event editedEvent = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EventEditCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new EventStorage(model.getEventStorage()),
                new VolunteerStorage(model.getVolunteerStorage()), new UserPrefs());

        assertCommandSuccess(eventEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showEventAtIndex(model, INDEX_FIRST);

        Event eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList).withEventName(VALID_EVENTNAME_CLEANUP).build();
        EventEditCommand eventEditCommand = new EventEditCommand(INDEX_FIRST,
                new EditEventDescriptorBuilder().withEventName(VALID_EVENTNAME_CLEANUP).build());

        String expectedMessage = String.format(EventEditCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new EventStorage(model.getEventStorage()),
                new VolunteerStorage(model.getVolunteerStorage()), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(eventEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEventUnfilteredList_failure() {
        Event firstEvent = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(firstEvent).build();
        EventEditCommand eventEditCommand = new EventEditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(eventEditCommand, model, EventEditCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_duplicateEventFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST);

        // edit event in filtered list into a duplicate in Event storage
        Event eventInList = model.getEventStorage().getEventList()
                .get(INDEX_SECOND.getZeroBased());
        EventEditCommand eventEditCommand = new EventEditCommand(INDEX_FIRST,
                new EditEventDescriptorBuilder(eventInList).build());

        assertCommandFailure(eventEditCommand, model, EventEditCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withEventName(VALID_EVENTNAME_CLEANUP)
                                                                            .build();
        EventEditCommand eventEditCommand = new EventEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(eventEditCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of event storage
     */
    @Test
    public void execute_invalidEventIndexFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of event storage
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventStorage().getEventList().size());

        EventEditCommand eventEditCommand = new EventEditCommand(outOfBoundIndex,
                new EditEventDescriptorBuilder().withEventName(VALID_EVENTNAME_CLEANUP).build());

        assertCommandFailure(eventEditCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EventEditCommand standardCommand = new EventEditCommand(INDEX_FIRST, DESC_CLEANUP);

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor(DESC_CLEANUP);
        EventEditCommand commandWithSameValues = new EventEditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        // assertFalse(standardCommand.equals(new EventClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EventEditCommand(INDEX_SECOND, DESC_CLEANUP)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EventEditCommand(INDEX_FIRST, DESC_HELPOUT)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        EventEditCommand eventEditCommand = new EventEditCommand(index, editEventDescriptor);
        String expected = EventEditCommand.class.getCanonicalName()
                + "{index=" + index + ", editEventDescriptor=" + editEventDescriptor + "}";
        assertEquals(expected, eventEditCommand.toString());
    }

}
