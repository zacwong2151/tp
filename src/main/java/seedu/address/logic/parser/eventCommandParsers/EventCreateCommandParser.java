package seedu.address.logic.parser.eventCommandParsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.eventCommands.EventCreateCommand;
import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.*;

/**
 * Parses input arguments and creates a new EventCreateCommand object
 */
public class EventCreateCommandParser implements Parser<EventCreateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EventCreateCommand
     * and returns an EventCreateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventCreateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE, PREFIX_DATE_AND_TIME,
                        PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_MATERIALS, PREFIX_BUDGET);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ROLE, PREFIX_DATE_AND_TIME,
                PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_MATERIALS, PREFIX_BUDGET)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventCreateCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ROLE, PREFIX_DATE_AND_TIME,
                PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_MATERIALS, PREFIX_BUDGET);
        EventName eventName = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get());
        Set<Role> roleList = ParserUtil.parseRoles(argMultimap.getAllValues(PREFIX_ROLE));
        DateTime dateTime = ParserUtil.parseDateAndTime(argMultimap.getValue(PREFIX_DATE_AND_TIME).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Set<Material> materialList = ParserUtil.parseMaterials(argMultimap.getAllValues(PREFIX_MATERIALS));
        Budget budget = ParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get());

        Event event = new Event(eventName, roleList, dateTime, location, description, materialList, budget);

        return new EventCreateCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
