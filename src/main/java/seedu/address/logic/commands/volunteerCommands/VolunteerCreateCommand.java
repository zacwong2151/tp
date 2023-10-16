package seedu.address.logic.commands.volunteerCommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.volunteer.Volunteer;

/**
 * Adds a volunteer to the volunteer storage.
 */
public class VolunteerCreateCommand extends Command {

    public static final String COMMAND_WORD = "vcreate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a volunteer to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_SKILL + "SKILL]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_SKILL + "friends "
            + PREFIX_SKILL + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New volunteer added: %1$s";
    public static final String MESSAGE_DUPLICATE_VOLUNTEER = "This volunteer already exists in the address book";

    private final Volunteer toAdd;

    /**
     * Creates a VolunteerCreateCommand to add the specified {@code Volunteer}
     */
    public VolunteerCreateCommand(Volunteer volunteer) {
        requireNonNull(volunteer);
        toAdd = volunteer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasVolunteer(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_VOLUNTEER);
        }

        model.addVolunteer(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerCreateCommand)) {
            return false;
        }

        VolunteerCreateCommand otherVolunteerCreateCommand = (VolunteerCreateCommand) other;
        return toAdd.equals(otherVolunteerCreateCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
