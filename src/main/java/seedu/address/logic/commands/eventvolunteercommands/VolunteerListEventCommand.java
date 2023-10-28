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
 * Lists all events joined by a volunteer.
 */
public class VolunteerListEventCommand extends Command {

    public static final String COMMAND_WORD = "vliste";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List events joined by a volunteer.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Listed all %2$d events joined by VOLUNTEER: %1$s";
    private final Index volunteerIndex;

    /**
     * Creates an VolunteerListEventCommand to list all {@code Event} joined by {@code Volunteer}.
     */
    public VolunteerListEventCommand(Index volunteerIndex) {
        this.volunteerIndex = volunteerIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Volunteer> lastShownVolunteerList = model.getFilteredVolunteerList();
        if (volunteerIndex.getZeroBased() >= lastShownVolunteerList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Volunteer volunteer = lastShownVolunteerList.get(volunteerIndex.getZeroBased());
        Predicate<Event> predicateShowEvents = v
                -> volunteer.getAssignedEvents().stream().anyMatch(y -> y.equals(v.getEventName()));
        model.updateFilteredEventList(predicateShowEvents);

        return new CommandResult(String.format(MESSAGE_SUCCESS, volunteer.getName().fullName,
                volunteer.getAssignedEvents().size()),
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
        if (!(other instanceof VolunteerListEventCommand)) {
            return false;
        }

        VolunteerListEventCommand otherVolunteerListEventCommand = (VolunteerListEventCommand) other;
        return volunteerIndex.equals(otherVolunteerListEventCommand.volunteerIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("volunteerIndex", volunteerIndex)
                .toString();
    }

}
