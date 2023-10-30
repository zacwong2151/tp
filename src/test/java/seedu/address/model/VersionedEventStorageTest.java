package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

class VersionedEventStorageTest {
    private VersionedEventStorage versionedEventStorage;
    private EventStorage eventStorage = new EventStorage();
    @BeforeEach
    public void setUp() {
        List<Event> events = new ArrayList<>();
        Event event = new EventBuilder().build();
        events.add(event);
        eventStorage.setEvents(events);
        versionedEventStorage = new VersionedEventStorage(eventStorage);
    }

    @Test
    public void shiftPointerForward() {
        versionedEventStorage.shiftPointerForward();
        assertEquals(1, versionedEventStorage.getCurrentStatePointer());
    }

    @Test
    public void shiftPointerBackward() {
        versionedEventStorage.shiftPointerForward();
        versionedEventStorage.shiftPointerForward();
        versionedEventStorage.shiftPointerBackwards();
        assertEquals(1, versionedEventStorage.getCurrentStatePointer());
    }

    @Test
    public void constructor_validParams_success() {
        versionedEventStorage = new VersionedEventStorage(eventStorage);
    }

    @Test
    public void constructor_null_throwNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                versionedEventStorage = new VersionedEventStorage(null));
    }

    @Test
    public void saveNewState_addNewVersionedEvents_success() throws CommandException {
        int initialSize = versionedEventStorage.getVersionedEventsSize();
        commitToVersionedEventStorage();
        int updatedSize = versionedEventStorage.getVersionedEventsSize();
        // a versionedEvents is added to the ArrayList of versionedEvents
        assertEquals(initialSize + 1, updatedSize);

        commitToVersionedEventStorage();
        int updatedSizeAgain = versionedEventStorage.getVersionedEventsSize();
        // at this point, size = 3, currentStatePointer = 2
        assertEquals(updatedSize + 1, updatedSizeAgain);

        // at this point, size = 3, currentStatePointer = 1
        versionedEventStorage.undo();

        /*
         currentStatePointer is incremented to 2. VersionedEventStorage#saveNewState will resize versionedEvents to
          contain only the first 2 elements. Then a versionedEvent will be added to the list of versionedEvents.
           Thus, size of versionedEvents becomes 3.
         */
        commitToVersionedEventStorage();
        assertEquals(versionedEventStorage.getCurrentStatePointer(), 2);
        assertEquals(versionedEventStorage.getVersionedEventsSize(), 3);
    }

    @Test
    public void saveNewState_null_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> versionedEventStorage.saveNewState(null));
    }

    @Test
    public void undo() throws CommandException {
        // First command user executes is undo
        assertThrows(CommandException.class, () -> versionedEventStorage.undo());

        // A new state is saved, and it is undone, so the pointers will point at the same state
        int initialPointer = versionedEventStorage.getCurrentStatePointer();
        commitToVersionedEventStorage();
        versionedEventStorage.undo();
        int updatedPointer = versionedEventStorage.getCurrentStatePointer();
        assertEquals(initialPointer, updatedPointer);

        // user tries to call the undo function again
        assertThrows(CommandException.class, () -> versionedEventStorage.undo());

        // user saves 3 new states, and undos all of them
        commitToVersionedEventStorage();
        commitToVersionedEventStorage();
        commitToVersionedEventStorage();
        versionedEventStorage.undo();
        versionedEventStorage.undo();
        versionedEventStorage.undo();

        // total of 4 events in the event history
        assertEquals(versionedEventStorage.getVersionedEventsSize(), 4);

        // pointer now points at the initial state of events
        assertEquals(versionedEventStorage.getCurrentStatePointer(), 0);

        // user tries to call the undo function again
        assertThrows(CommandException.class, () -> versionedEventStorage.undo());
    }

    @Test
    public void redo() throws CommandException {
        // First command user executes is redo
        assertThrows(CommandException.class, () -> versionedEventStorage.redo());

        // A new state is saved, it is undone, then redone. The updatedPointer will point at the
        // next state compared to the initialPointer
        int initialPointer = versionedEventStorage.getCurrentStatePointer();
        commitToVersionedEventStorage();
        versionedEventStorage.undo();
        versionedEventStorage.redo();
        int updatedPointer = versionedEventStorage.getCurrentStatePointer();
        assertEquals(initialPointer + 1, updatedPointer);

        // user tries to call the redo function again
        assertThrows(CommandException.class, () -> versionedEventStorage.redo());

        // user saves 3 new states, undos all of them, and redos all
        commitToVersionedEventStorage();
        commitToVersionedEventStorage();
        commitToVersionedEventStorage();
        versionedEventStorage.undo();
        versionedEventStorage.undo();
        versionedEventStorage.undo();
        versionedEventStorage.redo();
        versionedEventStorage.redo();
        versionedEventStorage.redo();

        // total of 5 versionedEvents in the event history, because 4 new versionedEvents were saved
        assertEquals(versionedEventStorage.getVersionedEventsSize(), 5);

        // pointer now points at the latest state of events
        assertEquals(versionedEventStorage.getCurrentStatePointer(), 4);

        // user tries to call the redo function again
        assertThrows(CommandException.class, () -> versionedEventStorage.redo());
    }

    private void commitToVersionedEventStorage() {
        versionedEventStorage.shiftPointerForward();
        versionedEventStorage.saveNewState(eventStorage);
    }
}
