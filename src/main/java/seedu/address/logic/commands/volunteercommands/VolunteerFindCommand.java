package seedu.address.logic.commands.volunteercommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.volunteer.SkillNameContainsKeywordsPredicate;

/**
 * Finds and lists all volunteers in volunteer storage whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class VolunteerFindCommand extends Command {

    public static final String COMMAND_WORD = "vfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all volunteers whose names or skills contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice n/bob s/chef";

    private final SkillNameContainsKeywordsPredicate predicate;

    public VolunteerFindCommand(SkillNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredVolunteerList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_VOLUNTEERS_LISTED_OVERVIEW, model.getFilteredVolunteerList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerFindCommand)) {
            return false;
        }

        VolunteerFindCommand otherVolunteerFindCommand = (VolunteerFindCommand) other;
        return predicate.equals(otherVolunteerFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
