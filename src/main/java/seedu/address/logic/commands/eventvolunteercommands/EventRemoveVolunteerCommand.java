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
public class EventRemoveVolunteerCommand extends Command {

    public static final String COMMAND_WORD = "eremovev";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a volunteer from an event. "
            + "Parameters: "
            + PREFIX_EVENT_ID + "EVENT ID "
            + PREFIX_VOLUNTEER_ID + "VOLUNTEER ID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_ID + "1 "
            + PREFIX_VOLUNTEER_ID + "2 ";
    public static final String MESSAGE_SUCCESS = "VOLUNTEER removed FROM EVENT: %1$s %1$s\n"
            + "Event currently has %2$d volunteers";
    public static final String MESSAGE_VOLUNTEER_NOT_IN_EVENT = "VOLUNTEER is not assigned TO EVENT";
    private final Index assignedEventIndex;

    private final Index assignedVolunteerIndex;

    /**
     * Creates an EventAddVolunteerCommand to add the specified {@code Volunteer} to the {@code Event}
     */
    public EventRemoveVolunteerCommand(Index assignedEventIndex, Index assignedVolunteerIndex) {
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
        if (!eventToAssign.hasVolunteer(volunteerToAssign)) {
            throw new CommandException(MESSAGE_VOLUNTEER_NOT_IN_EVENT);
        }
        Volunteer updatedVolunteer = volunteerToAssign.removeEvent(eventToAssign);
        Event updatedEvent = eventToAssign.removeVolunteer(volunteerToAssign);
        model.setVolunteer(volunteerToAssign, updatedVolunteer);
        model.setEvent(eventToAssign, updatedEvent);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(eventToAssign),
                eventToAssign.getAssignedVolunteers().size()));
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
