package seedu.address.logic.commands.eventvolunteercommands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

/**
 * Removes a volunteer from an event.
 */
public class EventListVolunteerCommand extends Command {

    public static final String COMMAND_WORD = "elistv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all volunteer assigned to an event. "
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Listed all %2$d volunteers FROM EVENT: %1$s";
    private final Index eventIndex;

    /**
     * Creates an EventListVolunteerCommand to add the specified {@code Volunteer} to the {@code Event}
     */
    public EventListVolunteerCommand(Index eventIndex) {
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownEventList = model.getFilteredEventList();
        if (eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event event = lastShownEventList.get(eventIndex.getZeroBased());
        Predicate<Volunteer> predicateShowVolunteers = v
                -> event.getAssignedVolunteers().stream().anyMatch(y -> y.equals(v.getName()));
        model.updateFilteredVolunteerList(predicateShowVolunteers);

        return new CommandResult(String.format(MESSAGE_SUCCESS, event.getEventName().eventName,
                event.getAssignedVolunteers().size()),
                false,
                false,
                false);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventListVolunteerCommand)) {
            return false;
        }

        EventListVolunteerCommand otherEventListVolunteerCommand = (EventListVolunteerCommand) other;
        return eventIndex.equals(otherEventListVolunteerCommand.eventIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventIndex", eventIndex)
                .toString();
    }
}
