package seedu.address.logic.parser.eventcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_AND_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATERIALS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.eventcommands.EventCreateCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Budget;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Material;
import seedu.address.model.event.Role;


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
                PREFIX_LOCATION, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventCreateCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE_AND_TIME,
                PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_BUDGET);
        EventName eventName = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get());
        Set<Role> roleList = ParserUtil.parseRoles(argMultimap.getAllValues(PREFIX_ROLE));
        DateTime dateTime = ParserUtil.parseDateAndTime(argMultimap.getValue(PREFIX_DATE_AND_TIME).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Set<Material> materialList = ParserUtil.parseMaterials(argMultimap.getAllValues(PREFIX_MATERIALS));
        // Check if the command contains the optional budget field
        Budget budget = new Budget("");
        if (args.contains(PREFIX_BUDGET.getPrefix())) {
            budget = ParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get());
        }

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
