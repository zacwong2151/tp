package seedu.address.logic.commands.volunteerCommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.VolunteerStorage;

/**
 * Clears the volunteer storage.
 */
public class VolunteerClearCommand extends Command {

    public static final String COMMAND_WORD = "vclear";
    public static final String MESSAGE_SUCCESS = "Volunteer storage has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setVolunteerStorage(new VolunteerStorage());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
