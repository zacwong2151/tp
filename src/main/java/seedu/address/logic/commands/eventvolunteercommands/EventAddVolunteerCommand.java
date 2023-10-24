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
 * Adds a volunteer to an event.
 */
public class EventAddVolunteerCommand extends Command {

    public static final String COMMAND_WORD = "eaddv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a volunteer to an event. "
            + "Parameters: "
            + PREFIX_EVENT_ID + "EVENT ID "
            + PREFIX_VOLUNTEER_ID + "VOLUNTEER ID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_ID + "1 "
            + PREFIX_VOLUNTEER_ID + "2 ";
    public static final String MESSAGE_SUCCESS = "New VOLUNTEER added TO EVENT: %1$s\n"
            + "Event currently has %2$d volunteers";
    public static final String MESSAGE_DUPLICATE_VOLUNTEER = "This volunteer is already assigned to this event";
    private final Index assignedEventIndex;

    private final Index assignedVolunteerIndex;

    /**
     * Creates an EventAddVolunteerCommand to add the specified {@code Volunteer} to the {@code Event}
     */
    public EventAddVolunteerCommand(Index assignedEventIndex, Index assignedVolunteerIndex) {
        this.assignedEventIndex = assignedEventIndex;
        this.assignedVolunteerIndex = assignedVolunteerIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownEventList = model.getFilteredEventList();
        List<Volunteer> lastShownVolunteerList = model.getFilteredVolunteerList();
        if (assignedEventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (assignedVolunteerIndex.getZeroBased() >= lastShownVolunteerList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        Event eventToAssign = lastShownEventList.get(assignedEventIndex.getZeroBased());
        Volunteer volunteerToAssign = lastShownVolunteerList.get(assignedVolunteerIndex.getZeroBased());
        if (eventToAssign.hasVolunteer(volunteerToAssign)) {
            throw new CommandException(MESSAGE_DUPLICATE_VOLUNTEER);
        }
        volunteerToAssign.addEvent(eventToAssign);
        eventToAssign.addVolunteer(volunteerToAssign);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(eventToAssign),
                eventToAssign.getAssignedVolunteers().size()));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventAddVolunteerCommand)) {
            return false;
        }

        EventAddVolunteerCommand otherEventAddVolunteerCommand = (EventAddVolunteerCommand) other;
        return assignedEventIndex.equals(otherEventAddVolunteerCommand.assignedEventIndex)
                && assignedVolunteerIndex.equals(otherEventAddVolunteerCommand.assignedVolunteerIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignedEventIndex", assignedEventIndex)
                .add("assignedVolunteerIndex", assignedVolunteerIndex)
                .toString();
    }
}
