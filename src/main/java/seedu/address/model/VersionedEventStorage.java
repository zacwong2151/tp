package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;

/**
 * Stores the history of Events.
 */
public class VersionedEventStorage extends EventStorage {
    private final ArrayList<UniqueEventList> versionedEvents = new ArrayList<>();
    private int currentStatePointer;
    /**
     * Upon running the app, initialises the history of Events.
     */
    public VersionedEventStorage(ReadOnlyEventStorage toBeCopied) {
        initState(toBeCopied);
        currentStatePointer = 0;
    }

    /**
     * Stores the initial state of Events in versionedEvents.
     */
    public void initState(ReadOnlyEventStorage initialState) {
        requireNonNull(initialState);
        UniqueEventList uniqueEventList = generateUniqueEventList(initialState);
        versionedEvents.add(uniqueEventList);
    }

    /**
     * Shift the currentStatePointer to the next state of Events.
     */
    public void shiftPointerForward() {
        currentStatePointer += 1;
    }

    /**
     * Shift the currentStatePointer to point to the previous state of Events.
     */
    public void shiftPointerBackwards() {
        currentStatePointer -= 1;
    }

    public int getCurrentStatePointer() { return currentStatePointer; }

    public int getVersionedEventsSize() { return versionedEvents.size(); }

    /**
     * When commands that change the current state of Events are executed, the new state of Events will be
     * saved and added to versionedEvents.
     * @param readOnlyEventStorage The new state of Events
     */
    public void saveNewState(ReadOnlyEventStorage readOnlyEventStorage) {
        UniqueEventList newState = generateUniqueEventList(readOnlyEventStorage);
        requireNonNull(readOnlyEventStorage);
        if (versionedEvents.size() > currentStatePointer) {
            trimVersionedEvents();
        }
        assert versionedEvents.size() == currentStatePointer;
        versionedEvents.add(currentStatePointer, newState);
    }
    private void trimVersionedEvents() {
        int size = versionedEvents.size();
        for (int i = currentStatePointer; i <= size - 1; i++) {
            versionedEvents.remove(i);
        }
    }
    /**
     * Points to the previous state of Events and returns it.
     * @throws CommandException if currentStatePointer is pointing to the initial state of Events.
     */
    public List<Event> undo() throws CommandException {
        canUndoVersionedEvents();
        shiftPointerBackwards();
        assert currentStatePointer >= 0;
        return getCurrentEventState();
    }
    private void canUndoVersionedEvents() throws CommandException {
        if (currentStatePointer == 0) {
            throw new CommandException("Cannot undo any further");
        }
        assert currentStatePointer > 0;
    }
    /**
     * Points to the next state of Events and returns it.
     * @throws CommandException if currentStatePointer is pointing to the latest state of Events.
     */
    public List<Event> redo() throws CommandException {
        canRedoVersionedEvents();
        shiftPointerForward();
        assert currentStatePointer < versionedEvents.size();
        return getCurrentEventState();
    }
    private List<Event> getCurrentEventState() {
        UniqueEventList newState = versionedEvents.get(currentStatePointer);
        return newState.asUnmodifiableObservableList();
    }
    private void canRedoVersionedEvents() throws CommandException {
        if (versionedEvents.size() == currentStatePointer + 1) {
            throw new CommandException("Unable to redo");
        }
        assert currentStatePointer < versionedEvents.size();
    }
    private static UniqueEventList generateUniqueEventList(ReadOnlyEventStorage readOnlyEventStorage) {
        requireNonNull(readOnlyEventStorage);
        List<Event> events = readOnlyEventStorage.getEventList();
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.setEvents(events);
        return uniqueEventList;
    }
}
