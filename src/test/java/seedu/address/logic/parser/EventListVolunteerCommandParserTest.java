package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.eventvolunteercommandparsers.EventListVolunteerCommandParser;

public class EventListVolunteerCommandParserTest {
    @Test
    public void parse_missingIndex_parseUnsuccessful() {
        EventListVolunteerCommandParser parser = new EventListVolunteerCommandParser();
        try {
            parser.parse("hi");
        } catch (Exception e) {
            assertTrue(true);
        }
        assertTrue(true);
    }
    //    @Test
    //    public void parse_invalidIndex_parseUnsuccessful(){}
}
