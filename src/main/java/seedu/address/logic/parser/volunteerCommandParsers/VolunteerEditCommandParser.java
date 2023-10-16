package seedu.address.logic.parser.volunteerCommandParsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.volunteerCommands.VolunteerEditCommand;
import seedu.address.logic.commands.volunteerCommands.VolunteerEditCommand.EditVolunteerDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.skill.Skill;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class VolunteerEditCommandParser implements Parser<VolunteerEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VolunteerEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_SKILL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                    VolunteerEditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        EditVolunteerDescriptor editVolunteerDescriptor = new EditVolunteerDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editVolunteerDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editVolunteerDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editVolunteerDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        parseSkillsForEdit(argMultimap.getAllValues(PREFIX_SKILL)).ifPresent(editVolunteerDescriptor::setSkills);

        if (!editVolunteerDescriptor.isAnyFieldEdited()) {
            throw new ParseException(VolunteerEditCommand.MESSAGE_NOT_EDITED);
        }

        return new VolunteerEditCommand(index, editVolunteerDescriptor);
    }

    /**
     * Parses {@code Collection<String> skills} into a {@code Set<Skill>} if {@code skills} is non-empty.
     * If {@code skills} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Skill>} containing zero skills.
     */
    private Optional<Set<Skill>> parseSkillsForEdit(Collection<String> skills) throws ParseException {
        assert skills != null;

        if (skills.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = skills.size() == 1 && skills.contains("") ? Collections.emptySet() : skills;
        return Optional.of(ParserUtil.parseSkills(tagSet));
    }

}
