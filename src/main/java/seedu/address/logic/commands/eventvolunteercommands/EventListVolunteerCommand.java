package seedu.address.logic.commands.eventvolunteercommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_ID;

import java.util.List;

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
    public static final String MESSAGE_SUCCESS = "Listed all volutneers FROM EVENT: %1$s";
    private final Index eventIndex;

    /**
     * Creates an EventAddVolunteerCommand to add the specified {@code Volunteer} to the {@code Event}
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
        // Get all volunteer names, map them to the actual volunteer objects
        // Make
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(eventToAssign)));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventRemoveVolunteerCommand)) {
            return false;
        }

        EventRemoveVolunteerCommand otherEventRemoveVolunteerCommand = (EventRemoveVolunteerCommand) other;
        return assignedEventIndex.equals(otherEventRemoveVolunteerCommand.assignedEventIndex)
                && assignedVolunteerIndex.equals(otherEventRemoveVolunteerCommand.assignedVolunteerIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignedEventIndex", assignedEventIndex)
                .add("assignedVolunteerIndex", assignedVolunteerIndex)
                .toString();
    }
}
