package seedu.address.model.volunteer;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

/**
 * asd
 */
public class NameContainsKeywordsPredicate implements Predicate<Event> {
    private final List<EventName> eventNames;

    /**
     * Constructor that takes in strings
     * @param eventNames list of string
     */
    public NameContainsKeywordsPredicate(List<EventName> eventNames) {
        this.eventNames = eventNames;
    }

    @Override
    public boolean test(Event event) {
        assert !eventNames.isEmpty() : "eventNames should not be an empty list";

        return eventNames.stream()
                .anyMatch(name ->
                        StringUtil.containsWordIgnoreCase(event.getEventName().eventName, name.eventName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (NameContainsKeywordsPredicate) other;
        return eventNames.equals(otherNameContainsKeywordsPredicate.eventNames);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventNames", eventNames)
                .toString();
    }
}
