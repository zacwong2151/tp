package seedu.address.logic.commands.volunteercommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VOLUNTEERS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all volunteers in the volunteer storage to the user.
 */
public class VolunteerListCommand extends Command {

    public static final String COMMAND_WORD = "vlist";

    public static final String MESSAGE_SUCCESS = "Listed all volunteers";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
