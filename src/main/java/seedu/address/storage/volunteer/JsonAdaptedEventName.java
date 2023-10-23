package seedu.address.storage.volunteer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.EventName;
import seedu.address.model.skill.Skill;

/**
 * Jackson-friendly version of {@link EventName}.
 */
public class JsonAdaptedEventName {

    private final String eventName;

    /**
     * Constructs a {@code JsonAdaptedEventName} with the given {@code eventName}.
     */
    @JsonCreator
    public JsonAdaptedEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Converts a given {@code EventName} into this class for Jackson use.
     */
    public JsonAdaptedEventName(EventName source) {
        eventName = source.eventName;
    }

    @JsonValue
    public String getEventName() {
        return eventName;
    }

    /**
     * Converts this Jackson-friendly adapted event name object into the model's {@code EventName} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event name.
     */
    public EventName toModelType() throws IllegalValueException {
        if (!EventName.isValidEventName(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }
        return new EventName(eventName);
    }

}
