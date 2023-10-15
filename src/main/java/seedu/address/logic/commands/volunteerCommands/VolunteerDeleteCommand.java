package seedu.address.logic.commands.volunteerCommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.volunteer.Volunteer;

/**
 * Deletes a volunteer identified using it's displayed index from the volunteer storage.
 */
public class VolunteerDeleteCommand extends Command {

    public static final String COMMAND_WORD = "vdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the volunteer identified by the index number used in the displayed volunteer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_VOLUNTEER_SUCCESS = "Deleted Volunteer: %1$s";

    private final Index targetIndex;

    public VolunteerDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Volunteer> lastShownList = model.getFilteredVolunteerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        Volunteer volunteerToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteVolunteer(volunteerToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_VOLUNTEER_SUCCESS, Messages.format(volunteerToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerDeleteCommand)) {
            return false;
        }

        VolunteerDeleteCommand otherVolunteerDeleteCommand = (VolunteerDeleteCommand) other;
        return targetIndex.equals(otherVolunteerDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
