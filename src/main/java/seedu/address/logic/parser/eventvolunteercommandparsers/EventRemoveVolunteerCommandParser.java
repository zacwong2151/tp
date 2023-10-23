package seedu.address.logic.parser.eventvolunteercommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_ID;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventvolunteercommands.EventAddVolunteerCommand;
import seedu.address.logic.commands.eventvolunteercommands.EventRemoveVolunteerCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EventRemoveVolunteerCommand object
 */
public class EventRemoveVolunteerCommandParser implements Parser<EventRemoveVolunteerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EventRemoveVolunteerCommand
     * and returns a EventRemoveVolunteerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventRemoveVolunteerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ID, PREFIX_VOLUNTEER_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_ID, PREFIX_VOLUNTEER_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventAddVolunteerCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ID, PREFIX_VOLUNTEER_ID);
        try {
            Index assignedEventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT_ID).get());
            Index assignedVolunteerIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_VOLUNTEER_ID).get());
            return new EventRemoveVolunteerCommand(assignedEventIndex, assignedVolunteerIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventRemoveVolunteerCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
