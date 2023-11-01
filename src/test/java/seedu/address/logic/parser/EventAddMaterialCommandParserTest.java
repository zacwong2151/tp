package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MATERIAL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MATERIAL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MATERIAL_DESC_TRASHBAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATERIAL_TRASHBAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventcommands.EventAddMaterialCommand;
import seedu.address.logic.parser.eventcommandparsers.EventAddMaterialCommandParser;

public class EventAddMaterialCommandParserTest {
    private EventAddMaterialCommandParser parser = new EventAddMaterialCommandParser();
    @Test
    public void parse_missingPrefixes_parseUnsuccessful() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddMaterialCommand.MESSAGE_USAGE);

        // missing m prefix
        assertParseFailure(parser, EVENTID_DESC + INDEX_FIRST,
                expectedMessage);

        // missing eid prefix
        assertParseFailure(parser, MATERIAL_DESC + INDEX_FIRST,
                expectedMessage);

        // both eid and m prefixes missing
        assertParseFailure(parser, "",
                expectedMessage);
    }
    @Test
    public void parse_missingArguments_parseUnsuccessful() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddMaterialCommand.MESSAGE_USAGE);

        // missing eid argument
        assertParseFailure(parser, EVENTID_DESC + MATERIAL_DESC + VALID_MATERIAL_TRASHBAG,
                expectedMessage);

        // missing m argument
        assertParseFailure(parser, EVENTID_DESC + INDEX_FIRST + MATERIAL_DESC,
                expectedMessage);

        // both eid and m arguments missing
        assertParseFailure(parser, EVENTID_DESC + MATERIAL_DESC,
                expectedMessage);
    }
    @Test
    public void parse_invalidArguments_parseUnsuccessful() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddMaterialCommand.MESSAGE_USAGE);

        // invalid eid argument
        assertParseFailure(parser, EVENTID_DESC + "two" + MATERIAL_DESC_TRASHBAG,
                expectedMessage);

        // invalid m argument
        assertParseFailure(parser, EVENTID_DESC + INDEX_FIRST + INVALID_MATERIAL_DESC,
                expectedMessage);

        // both eid and m arguments invalid
        assertParseFailure(parser, EVENTID_DESC + "-2" + INVALID_MATERIAL_DESC,
                expectedMessage);
    }
}
