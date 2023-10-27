package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventcommands.EventEditCommand;
import seedu.address.logic.commands.eventcommands.EventEditCommand.EditEventDescriptor;
import seedu.address.logic.parser.eventcommandparsers.EventEditCommandParser;
import seedu.address.model.event.*;
import seedu.address.model.skill.Skill;
import seedu.address.model.event.Email;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EventEditCommandParserTest {

    private static final String SKILL_EMPTY = " " + PREFIX_SKILL;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventEditCommand.MESSAGE_USAGE);

    private EventEditCommandParser parser = new EventEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, INVALID_EVENTNAME_DESC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EventEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EVENTNAME_DESC_CLEANUP, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EVENTNAME_DESC_CLEANUP, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event name
        assertParseFailure(parser, "1" + INVALID_EVENTNAME_DESC, EventName.MESSAGE_CONSTRAINTS);
        // invalid role
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS);
        // invalid start date
        assertParseFailure(parser, "1" + INVALID_START_DATETIME_DESC, DateTime.MESSAGE_CONSTRAINTS);
        // invalid end date
        assertParseFailure(parser, "1" + INVALID_END_DATETIME_DESC, DateTime.MESSAGE_CONSTRAINTS);
        // invalid location
        assertParseFailure(parser, "1" + INVALID_LOCATION_DESC, Location.MESSAGE_CONSTRAINTS);
        // invalid description
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);
        // invalid location
        assertParseFailure(parser, "1" + INVALID_LOCATION_DESC, Location.MESSAGE_CONSTRAINTS);
        // invalid material
        assertParseFailure(parser, "1" + INVALID_MATERIAL_DESC, Material.MESSAGE_CONSTRAINTS);
        // invalid budget
        assertParseFailure(parser, "1" + INVALID_BUDGET_DESC, Budget.MESSAGE_CONSTRAINTS);

        // invalid start date followed by valid budget
        assertParseFailure(parser, "1" + INVALID_START_DATETIME_DESC +  BUDGET_DESC_CLEANUP,
                            DateTime.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_SKILL} alone will reset the skills of the {@code event} being edited,
        // parsing it together with a valid skill results in error
        assertParseFailure(parser, "1" + SKILL_DESC_FRIEND + SKILL_DESC_HUSBAND + SKILL_EMPTY,
                Skill.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + SKILL_DESC_FRIEND + SKILL_EMPTY + SKILL_DESC_HUSBAND,
                Skill.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + SKILL_EMPTY + SKILL_DESC_FRIEND + SKILL_DESC_HUSBAND,
                Skill.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_EVENTNAME_DESC
                    + INVALID_START_DATETIME_DESC + LOCATION_DESC_CLEANUP, EventName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + SKILL_DESC_HUSBAND
                + EMAIL_DESC_AMY + NAME_DESC_AMY + SKILL_DESC_FRIEND;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withSkills(VALID_SKILL_HUSBAND, VALID_SKILL_FRIEND).build();
        EventEditCommand expectedCommand = new EventEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EventEditCommand expectedCommand = new EventEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EventEditCommand expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditEventDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditEventDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // skills
        userInput = targetIndex.getOneBased() + SKILL_DESC_FRIEND;
        descriptor = new EditEventDescriptorBuilder().withSkills(VALID_SKILL_FRIEND).build();
        expectedCommand = new EventEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonSkillValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + SKILL_DESC_FRIEND + PHONE_DESC_AMY + EMAIL_DESC_AMY + SKILL_DESC_FRIEND
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + SKILL_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));
    }

    @Test
    public void parse_resetSkills_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + SKILL_EMPTY;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withSkills().build();
        EventEditCommand expectedCommand = new EventEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
