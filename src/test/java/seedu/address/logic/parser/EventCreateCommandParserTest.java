package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.DATEANDTIME_DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.EVENTNAME_DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUDGET_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATEANDTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENTNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MATERIAL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.MATERIAL_DESC_HANDS;
import static seedu.address.logic.commands.CommandTestUtil.MATERIAL_DESC_TRASHBAG;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BRAIN;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_CLEANER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATEANDTIME_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENTNAME_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATERIAL_HANDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATERIAL_TRASHBAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BRAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_CLEANER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_AND_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.CLEANUP;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventcommands.EventCreateCommand;
import seedu.address.logic.parser.eventcommandparsers.EventCreateCommandParser;
import seedu.address.model.event.Budget;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Material;
import seedu.address.model.event.Role;
import seedu.address.testutil.EventBuilder;

public class EventCreateCommandParserTest {
    private EventCreateCommandParser parser = new EventCreateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(CLEANUP).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER
                + DATEANDTIME_DESC_CLEANUP + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + MATERIAL_DESC_TRASHBAG
                + BUDGET_DESC_CLEANUP, new EventCreateCommand(expectedEvent));

        // multiple roles and materials - all accepted
        Event expectedEventMultipleRolesAndMaterials = new EventBuilder(CLEANUP)
                .withRoles(VALID_ROLE_CLEANER, VALID_ROLE_BRAIN)
                .withMaterials(VALID_MATERIAL_TRASHBAG, VALID_MATERIAL_HANDS).build();
        assertParseSuccess(parser, EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER + ROLE_DESC_BRAIN
                + DATEANDTIME_DESC_CLEANUP + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP
                + MATERIAL_DESC_TRASHBAG + MATERIAL_DESC_HANDS + BUDGET_DESC_CLEANUP,
                new EventCreateCommand(expectedEventMultipleRolesAndMaterials));
    }

    @Test
    public void parse_repeatedNonRoleOrMaterialValue_failure() {
        String validExpectedEventString = EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER
                + DATEANDTIME_DESC_CLEANUP + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + MATERIAL_DESC_TRASHBAG
                + BUDGET_DESC_CLEANUP;

        // multiple event names
        assertParseFailure(parser, EVENTNAME_DESC_CLEANUP + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));


        // multiple date and time
        assertParseFailure(parser, DATEANDTIME_DESC_CLEANUP + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE_AND_TIME));

        // multiple location
        assertParseFailure(parser, LOCATION_DESC_CLEANUP + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // multiple description
        assertParseFailure(parser, DESCRIPTION_DESC_CLEANUP + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // multiple budget
        assertParseFailure(parser, BUDGET_DESC_CLEANUP + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BUDGET));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedEventString + EVENTNAME_DESC_CLEANUP + DATEANDTIME_DESC_CLEANUP
                        + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + BUDGET_DESC_CLEANUP
                        + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_DATE_AND_TIME, PREFIX_LOCATION,
                        PREFIX_DESCRIPTION, PREFIX_BUDGET));

        // invalid value followed by valid value

        // invalid event name
        assertParseFailure(parser, INVALID_EVENTNAME_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid date and time
        assertParseFailure(parser, INVALID_DATEANDTIME_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE_AND_TIME));

        // invalid location
        assertParseFailure(parser, INVALID_LOCATION_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // invalid description
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // invalid budget
        assertParseFailure(parser, INVALID_BUDGET_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BUDGET));

        // valid value followed by invalid value

        // invalid event name
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENTNAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid date and time
        assertParseFailure(parser, validExpectedEventString + INVALID_DATEANDTIME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE_AND_TIME));

        // invalid location
        assertParseFailure(parser, validExpectedEventString + INVALID_LOCATION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // invalid description
        assertParseFailure(parser, validExpectedEventString + INVALID_DESCRIPTION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // invalid budget
        assertParseFailure(parser, validExpectedEventString + INVALID_BUDGET_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BUDGET));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero materials
        Event expectedEvent = new EventBuilder(CLEANUP).withMaterials().build();
        assertParseSuccess(parser, EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER + DATEANDTIME_DESC_CLEANUP
                        + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + BUDGET_DESC_CLEANUP,
                new EventCreateCommand(expectedEvent));

        // zero budget
        expectedEvent = new EventBuilder(CLEANUP).withBudget("").build();
        assertParseSuccess(parser, EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER + DATEANDTIME_DESC_CLEANUP
                        + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + MATERIAL_DESC_TRASHBAG,
                new EventCreateCommand(expectedEvent));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EventCreateCommand.MESSAGE_USAGE);

        // missing event name prefix
        assertParseFailure(parser, VALID_EVENTNAME_CLEANUP + ROLE_DESC_CLEANER
                        + DATEANDTIME_DESC_CLEANUP + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP
                        + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                expectedMessage);

        // missing role prefix
        assertParseFailure(parser, EVENTNAME_DESC_CLEANUP + VALID_ROLE_CLEANER + DATEANDTIME_DESC_CLEANUP
                        + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + MATERIAL_DESC_TRASHBAG
                        + BUDGET_DESC_CLEANUP,
                expectedMessage);

        // missing date and time prefix
        assertParseFailure(parser, EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER
                        + VALID_DATEANDTIME_CLEANUP + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP
                        + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                expectedMessage);

        // missing location prefix
        assertParseFailure(parser, EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER
                        + DATEANDTIME_DESC_CLEANUP + VALID_LOCATION_CLEANUP + DESCRIPTION_DESC_CLEANUP
                        + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER
                        + DATEANDTIME_DESC_CLEANUP + LOCATION_DESC_CLEANUP + VALID_DESCRIPTION_CLEANUP
                        + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENTNAME_CLEANUP + VALID_ROLE_CLEANER
                        + VALID_DATEANDTIME_CLEANUP + VALID_LOCATION_CLEANUP + VALID_DESCRIPTION_CLEANUP
                        + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event name
        assertParseFailure(parser, INVALID_EVENTNAME_DESC + ROLE_DESC_CLEANER + DATEANDTIME_DESC_CLEANUP
                + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                EventName.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, EVENTNAME_DESC_CLEANUP + INVALID_ROLE_DESC + DATEANDTIME_DESC_CLEANUP
                + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                Role.MESSAGE_CONSTRAINTS);

        // invalid date and time
        assertParseFailure(parser, EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER + INVALID_DATEANDTIME_DESC
                + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER + DATEANDTIME_DESC_CLEANUP
                + INVALID_LOCATION_DESC + DESCRIPTION_DESC_CLEANUP + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                Location.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER + DATEANDTIME_DESC_CLEANUP
                + LOCATION_DESC_CLEANUP + INVALID_DESCRIPTION_DESC + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                Description.MESSAGE_CONSTRAINTS);

        // invalid materials
        assertParseFailure(parser, EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER + DATEANDTIME_DESC_CLEANUP
                + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + INVALID_MATERIAL_DESC + BUDGET_DESC_CLEANUP,
                Material.MESSAGE_CONSTRAINTS);

        // invalid budget
        assertParseFailure(parser, EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER + DATEANDTIME_DESC_CLEANUP
                + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + MATERIAL_DESC_TRASHBAG + INVALID_BUDGET_DESC,
                Budget.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENTNAME_DESC + INVALID_ROLE_DESC + DATEANDTIME_DESC_CLEANUP
                + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                EventName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENTNAME_DESC_CLEANUP + ROLE_DESC_CLEANER
                        + DATEANDTIME_DESC_CLEANUP + LOCATION_DESC_CLEANUP + DESCRIPTION_DESC_CLEANUP
                        + MATERIAL_DESC_TRASHBAG + BUDGET_DESC_CLEANUP,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCreateCommand.MESSAGE_USAGE));
    }
}
