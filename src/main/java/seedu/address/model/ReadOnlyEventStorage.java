package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

/**
 * Unmodifiable view of an Event Storage
 */
public interface ReadOnlyEventStorage {

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

    /**
     * Returns the event in the events storage with the given {@code eventName}.
     */
    Event getEvent(EventName eventName);

}
