package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.EventFindCommandTest.preparePredicate;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventcommands.EventFindCommand;
import seedu.address.logic.parser.eventcommandparsers.EventFindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;

public class EventFindCommandParserTest {
    private EventFindCommandParser parser = new EventFindCommandParser();
    @Test
    public void parse_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws ParseException {
        // no leading and trailing whitespaces
        EventNameContainsKeywordsPredicate predicate1 = preparePredicate(" n/food n/donation");
        EventFindCommand expectedEventFindCommand1 =
                new EventFindCommand(predicate1);
        assertParseSuccess(parser, " n/food n/donation", expectedEventFindCommand1);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/food \n \t n/  donation  \t", expectedEventFindCommand1);

        // one name with multiple words
        EventNameContainsKeywordsPredicate predicate2 = preparePredicate(" n/food distribution centre");
        EventFindCommand expectedEventFindCommand2 =
                new EventFindCommand(predicate2);
        assertParseSuccess(parser, " n/food distribution centre", expectedEventFindCommand2);
    }

    @Test
    public void parse_invalidArgs_throwsException() {
        // event name contains non-alphanumeric character
        assertThrows(ParseException.class, () -> preparePredicate(" n/foodie@"));
        // event name is an empty string
        assertThrows(ParseException.class, () -> preparePredicate(" n/ "));
        // similar to the test above
        assertThrows(ParseException.class, () -> preparePredicate(" n/"));
        // no prefixes given in user input
        assertThrows(ParseException.class, () -> preparePredicate(" "));
    }
}
