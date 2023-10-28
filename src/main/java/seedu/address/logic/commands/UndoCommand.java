package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * asd
 */
public class UndoCommand extends Command {
    @Override
    public CommandResult execute(Model model) {
        model.undoBothStorages();
    }
}
