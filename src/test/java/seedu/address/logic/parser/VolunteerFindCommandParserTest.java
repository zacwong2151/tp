package seedu.address.logic.parser;

import static seedu.address.logic.commands.VolunteerFindCommandTest.preparePredicate;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.volunteercommands.VolunteerFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.volunteercommandparsers.VolunteerFindCommandParser;
import seedu.address.model.volunteer.SkillNameContainsKeywordsPredicate;

public class VolunteerFindCommandParserTest {

    private VolunteerFindCommandParser parser = new VolunteerFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                VolunteerFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws ParseException {
        // no leading and trailing whitespaces
        SkillNameContainsKeywordsPredicate predicate_1 = preparePredicate(" n/Alice n/Bob");
        VolunteerFindCommand expectedVolunteerFindCommand_1 =
                new VolunteerFindCommand(predicate_1);
        assertParseSuccess(parser, " n/Alice n/Bob", expectedVolunteerFindCommand_1);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t n/Bob  \t", expectedVolunteerFindCommand_1);

        // one name and one skill
        SkillNameContainsKeywordsPredicate predicate_2 = preparePredicate(" n/Alice s/cook");
        VolunteerFindCommand expectedVolunteerFindCommand_2 =
                new VolunteerFindCommand(predicate_2);
        assertParseSuccess(parser, " n/Alice s/cook", expectedVolunteerFindCommand_2);

        // two skills
        SkillNameContainsKeywordsPredicate predicate_3 = preparePredicate(" s/chef n/cook");
        VolunteerFindCommand expectedVolunteerFindCommand_3 =
                new VolunteerFindCommand(predicate_3);
        assertParseSuccess(parser, " s/chef n/cook", expectedVolunteerFindCommand_3);

        // two names and one skill
        SkillNameContainsKeywordsPredicate predicate_4 = preparePredicate(" n/Alice n/Bob s/boxer");
        VolunteerFindCommand expectedVolunteerFindCommand_4 =
                new VolunteerFindCommand(predicate_4);
        assertParseSuccess(parser, " n/Alice n/Bob s/boxer", expectedVolunteerFindCommand_4);
    }

    @Test
    public void parse_invalidArgs_throwsException() {
        // name contains non-alphanumeric character
        assertThrows(ParseException.class, () -> preparePredicate(" n/Alex@"));
        // name is an empty string
        assertThrows(ParseException.class, () -> preparePredicate(" n/ "));
        // similar to the test above
        assertThrows(ParseException.class, () -> preparePredicate(" n/"));
        // no prefixes given in user input
        assertThrows(ParseException.class, () -> preparePredicate(" "));
    }
}
