package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventvolunteercommands.VolunteerListEventCommand;
import seedu.address.logic.parser.eventvolunteercommandparsers.VolunteerListEventCommandParser;

public class VolunteerListEventCommandParserTest {
    private VolunteerListEventCommandParser parser = new VolunteerListEventCommandParser();

    @Test
    public void parse_missingIndex_parseUnsuccessful() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                VolunteerListEventCommand.MESSAGE_USAGE);

        // missing argument
        assertParseFailure(parser, "",
                expectedMessage);
    }
    @Test
    public void parse_invalidIndex_parseUnsuccessful() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                VolunteerListEventCommand.MESSAGE_USAGE);

        // missing argument
        assertParseFailure(parser, "a",
                expectedMessage);
    }
}
