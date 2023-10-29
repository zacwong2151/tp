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
    private ArrayList<UniqueEventList> versionedEvents = new ArrayList<>();
    /**
     * Points to the current state of Events.
     */
    private int currentStatePointer;
    /**
     * Upon running the app, initialises the history of Events.
     */
    public VersionedEventStorage(ReadOnlyEventStorage toBeCopied) {
        initState(toBeCopied);
        this.currentStatePointer = 0;
    }

    /**
     * Stores the initial state of Events in versionedEvents.
     */
    public void initState(ReadOnlyEventStorage initialState) {
        requireNonNull(initialState);
        UniqueEventList uniqueEventList = generateUniqueEventList(initialState);
        this.versionedEvents.add(uniqueEventList);
    }

    /**
     * When commands that change the current state of Events are executed, currentStatePointer is shifted
     * to the newly inserted state of Events.
     */
    public void shiftPointerForward() {
        this.currentStatePointer += 1;
    }

    /**
     * When undo command is executed, currentStatePointer is shifted to point to the previous state of Events.
     * @throws CommandException if current state of events is equivalent to the initial state of events.
     */
    public void shiftPointerBackwards() throws CommandException {
        if (this.currentStatePointer == 0) {
            throw new CommandException("Cannot undo any further");
        }
        this.currentStatePointer -= 1;
    }

    /**
     * When commands that change the current state of Events are executed, the new state of Events will be
     * saved and added to versionedEvents.
     * @param readOnlyEventStorage The new state of Events
     */
    public void saveNewState(ReadOnlyEventStorage readOnlyEventStorage) {
        UniqueEventList newState = generateUniqueEventList(readOnlyEventStorage);
        if (versionedEvents.size() > currentStatePointer) {
            versionedEvents = new ArrayList<>(versionedEvents.subList(0, currentStatePointer));
        }
        assert versionedEvents.size() == currentStatePointer;
        this.versionedEvents.add(currentStatePointer, newState);
    }
    /**
     * Points to the previous state of Events and returns it.
     */
    public List<Event> undo() throws CommandException {
        shiftPointerBackwards();
        UniqueEventList newState = this.versionedEvents.get(currentStatePointer);
        return newState.asUnmodifiableObservableList();
    }
    private static UniqueEventList generateUniqueEventList(ReadOnlyEventStorage readOnlyEventStorage) {
        List<Event> events = readOnlyEventStorage.getEventList();
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.setEvents(events);
        return uniqueEventList;
    }
}
