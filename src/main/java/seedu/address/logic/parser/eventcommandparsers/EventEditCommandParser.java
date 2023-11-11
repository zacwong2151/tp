package seedu.address.logic.parser.eventcommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_PARAMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATERIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_VOLUNTEER_SIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventcommands.EventEditCommand;
import seedu.address.logic.commands.eventcommands.EventEditCommand.EditEventDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Material;
import seedu.address.model.event.Role;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EventEditCommandParser implements Parser<EventEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @param args The fields from user to update the event.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_START_DATETIME,
                        PREFIX_END_DATETIME, PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_MATERIAL, PREFIX_BUDGET,
                        PREFIX_MAX_VOLUNTEER_SIZE);

        Index index;
        DateTime startDate;
        DateTime endDate;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventEditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_START_DATETIME, PREFIX_END_DATETIME, PREFIX_LOCATION,
                PREFIX_DESCRIPTION, PREFIX_BUDGET);

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();

        // Check if there is update for certain field
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editEventDescriptor.setLocation(ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editEventDescriptor.setDescription(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION)
                                                                                        .get()));
        }
        if (argMultimap.getValue(PREFIX_BUDGET).isPresent()) {
            editEventDescriptor.setBudget(ParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get()));
        }
        if (argMultimap.getValue(PREFIX_MAX_VOLUNTEER_SIZE).isPresent()) {
            editEventDescriptor.setMaxVolunteerSize(
                    ParserUtil.parseMaxVolunteerSize(argMultimap.getValue(PREFIX_MAX_VOLUNTEER_SIZE).get()));
        }

        if (argMultimap.getValue(PREFIX_START_DATETIME).isPresent()) {
            startDate = ParserUtil.parseDateAndTime(argMultimap.getValue(PREFIX_START_DATETIME).get());
            editEventDescriptor.setStartDate(startDate);
        } else {
            startDate = null;
        }
        if (argMultimap.getValue(PREFIX_END_DATETIME).isPresent()) {
            endDate = ParserUtil.parseDateAndTime(argMultimap.getValue(PREFIX_END_DATETIME).get());
            editEventDescriptor.setEndDate(endDate);
        } else {
            endDate = null;
        }

        //compare end datetime from user to ensure it is after/same as start datetime from user
        if (startDate != null && endDate != null && endDate.dateAndTime.isBefore(startDate.dateAndTime)) {
            throw new ParseException(MESSAGE_INVALID_DATE_PARAMS);
        }

        parseRolesForEdit(argMultimap.getAllValues(PREFIX_ROLE)).ifPresent(editEventDescriptor::setRoles);
        parseMaterialsForEdit(argMultimap.getAllValues(PREFIX_MATERIAL)).ifPresent(editEventDescriptor::setMaterials);

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EventEditCommand.MESSAGE_NOT_EDITED);
        }

        return new EventEditCommand(index, editEventDescriptor);
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>} if {@code roles} is non-empty.
     * If {@code roles} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Role>} containing zero roles.
     *
     * @param roles The collection of role strings to parse.
     * @return An Optional containing a set of role objects parsed from the input collection. If the input collection
     *         is empty or only contains an empty string, an empty optional is returned.
     * @throws ParseException If the role does not comply to a certain format.
     */
    private Optional<Set<Role>> parseRolesForEdit(Collection<String> roles) throws ParseException {
        assert roles != null;

        if (roles.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> roleSet = roles.size() == 1 && roles.contains("") ? Collections.emptySet() : roles;
        return Optional.of(ParserUtil.parseRoles(roleSet));
    }

    /**
     * Parses {@code Collection<String> materials} into a {@code Set<Material>} if {@code materials} is non-empty.
     * If {@code materials} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Material>} containing zero materials.
     *
     * @param materials The collection of material strings to parse.
     * @return An Optional containing a set of material objects parsed from the input collection. If the input
     *         collection is empty or only contains an empty string, an empty Optional is returned.
     * @throws ParseException If the material does not comply to a certain format.
     */
    private Optional<Set<Material>> parseMaterialsForEdit(Collection<String> materials) throws ParseException {
        assert materials != null;

        if (materials.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> materialSet = materials.size() == 1 && materials.contains("") ? Collections.emptySet()
                                                                                            : materials;
        return Optional.of(ParserUtil.parseMaterials(materialSet));
    }
}
