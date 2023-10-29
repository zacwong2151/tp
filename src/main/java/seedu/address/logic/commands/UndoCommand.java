package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Undo the latest action executed by the user.
 * Only actions that change the events or volunteers state can be undone.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo command success";
    public UndoCommand() {}
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.undoBothStorages();
        //model.updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
