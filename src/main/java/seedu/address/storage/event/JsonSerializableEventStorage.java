package seedu.address.storage.event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EventStorage;
import seedu.address.model.ReadOnlyEventStorage;
import seedu.address.model.event.Event;

/**
 * An Immutable Event Storage that is serializable to JSON format.
 */
@JsonRootName(value = "eventStorage")
public class JsonSerializableEventStorage {

    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";

    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEventStorage} with the given events.
     */
    @JsonCreator
    public JsonSerializableEventStorage(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ReadOnlyEventStorage} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEventStorage}.
     */
    public JsonSerializableEventStorage(ReadOnlyEventStorage source) {
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Event Storage into the model's {@code EventStorage} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EventStorage toModelType() throws IllegalValueException {
        EventStorage eventStorage = new EventStorage();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (eventStorage.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            eventStorage.addEvent(event);
        }
        return eventStorage;
    }

}
