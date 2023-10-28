package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;

/**
 * Finds one or more events.
 */
public class EventFindCommand extends Command {

    public static final String COMMAND_WORD = "efind";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all events whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME [n/MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/food distribution";

    private final EventNameContainsKeywordsPredicate predicate;

    public EventFindCommand(EventNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventFindCommand)) {
            return false;
        }

        EventFindCommand otherEventFindCommand = (EventFindCommand) other;
        return predicate.equals(otherEventFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
