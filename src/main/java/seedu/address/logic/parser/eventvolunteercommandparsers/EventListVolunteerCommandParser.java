package seedu.address.logic.parser.eventvolunteercommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventcommands.EventDeleteCommand;
import seedu.address.logic.commands.eventvolunteercommands.EventListVolunteerCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EventListVolunteerCommand object
 */
public class EventListVolunteerCommandParser implements Parser<EventListVolunteerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EventListVolunteerCommand
     * and returns a EventListVolunteerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventListVolunteerCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EventListVolunteerCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventListVolunteerCommand.MESSAGE_USAGE), pe);
        }
    }

}
