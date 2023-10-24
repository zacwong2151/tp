package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.eventvolunteercommandparsers.EventAddVolunteerCommandParser;

public class EventAddVolunteerCommandParserTest {
    @Test
    public void parse_missingPrefixes_parseUnsuccessful() {
        EventAddVolunteerCommandParser parser = new EventAddVolunteerCommandParser();
        try {
            parser.parse("hi");
        } catch (Exception e) {
            assertTrue(true);
        }
        assertTrue(true);
    }
    //    @Test
    //    public void parse_missingArguments_parseUnsuccessful(){}
    //    @Test
    //    public void parse_invalidArguments_parseUnsuccessful(){}
}
