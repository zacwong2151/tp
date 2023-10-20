package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_AND_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATERIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.volunteercommands.VolunteerEditCommand;
import seedu.address.model.Model;
import seedu.address.model.VolunteerStorage;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.NameContainsKeywordsPredicate;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EditVolunteerDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    // Event fields
    public static final String VALID_EVENTNAME_CLEANUP = "Clean up";
    public static final String VALID_EVENTNAME_HELPOUT = "Help out";
    public static final String VALID_ROLE_CLEANER = "cleaner";
    public static final String VALID_ROLE_BRAIN = "brain";
    public static final String VALID_DATEANDTIME_CLEANUP = "23/9/2023 1800";
    public static final String VALID_DATEANDTIME_HELPOUT = "25/10/2023 2000";
    public static final String VALID_LOCATION_CLEANUP = "serangoon";
    public static final String VALID_LOCATION_HELPOUT = "admiralty";
    public static final String VALID_DESCRIPTION_CLEANUP = "clean it up";
    public static final String VALID_DESCRIPTION_HELPOUT = "help out la";
    public static final String VALID_MATERIAL_TRASHBAG = "trash bag";
    public static final String VALID_MATERIAL_HANDS = "hands";
    public static final String VALID_BUDGET_CLEANUP = "80.00";
    public static final String VALID_BUDGET_HELPOUT = "100.00";
    public static final String EVENTNAME_DESC_CLEANUP = " " + PREFIX_NAME + VALID_EVENTNAME_CLEANUP;
    public static final String EVENTNAME_DESC_HELPOUT = " " + PREFIX_NAME + VALID_EVENTNAME_HELPOUT;
    public static final String ROLE_DESC_CLEANER = " " + PREFIX_ROLE + VALID_ROLE_CLEANER;
    public static final String ROLE_DESC_BRAIN = " " + PREFIX_ROLE + VALID_ROLE_BRAIN;
    public static final String DATEANDTIME_DESC_CLEANUP = " " + PREFIX_DATE_AND_TIME + VALID_DATEANDTIME_CLEANUP;
    public static final String DATEANDTIME_DESC_HELPOUT = " " + PREFIX_DATE_AND_TIME + VALID_DATEANDTIME_HELPOUT;
    public static final String LOCATION_DESC_CLEANUP = " " + PREFIX_LOCATION + VALID_LOCATION_CLEANUP;
    public static final String LOCATION_DESC_HELPOUT = " " + PREFIX_LOCATION + VALID_LOCATION_HELPOUT;
    public static final String DESCRIPTION_DESC_CLEANUP = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_CLEANUP;
    public static final String DESCRIPTION_DESC_HELPOUT = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_CLEANUP;
    public static final String MATERIAL_DESC_TRASHBAG = " " + PREFIX_MATERIAL + VALID_MATERIAL_TRASHBAG;
    public static final String MATERIAL_DESC_HANDS = " " + PREFIX_MATERIAL + VALID_MATERIAL_HANDS;
    public static final String BUDGET_DESC_CLEANUP = " " + PREFIX_BUDGET + VALID_BUDGET_CLEANUP;
    public static final String BUDGET_DESC_HELPOUT = " " + PREFIX_BUDGET + VALID_BUDGET_HELPOUT;


    public static final String INVALID_EVENTNAME_DESC = " " + PREFIX_NAME + "Clean&";
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "cleaner&";
    public static final String INVALID_DATEANDTIME_DESC = " " + PREFIX_DATE_AND_TIME + "23-9-2023 1800";
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + "sembawang&";
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "clean&";
    public static final String INVALID_MATERIAL_DESC = " " + PREFIX_MATERIAL + "trash bag&";
    public static final String INVALID_BUDGET_DESC = " " + PREFIX_BUDGET + "50.0";

    // Volunteer fields
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_SKILL_HUSBAND = "husband";
    public static final String VALID_SKILL_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String SKILL_DESC_FRIEND = " " + PREFIX_SKILL + VALID_SKILL_FRIEND;
    public static final String SKILL_DESC_HUSBAND = " " + PREFIX_SKILL + VALID_SKILL_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_SKILL_DESC = " " + PREFIX_SKILL + "hubby*"; // '*' not allowed in skills

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final VolunteerEditCommand.EditVolunteerDescriptor DESC_AMY;
    public static final VolunteerEditCommand.EditVolunteerDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditVolunteerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withSkills(VALID_SKILL_FRIEND).build();
        DESC_BOB = new EditVolunteerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withSkills(VALID_SKILL_HUSBAND, VALID_SKILL_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered volunteer list and selected volunteer in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        VolunteerStorage expectedAddressBook = new VolunteerStorage(actualModel.getVolunteerStorage());
        List<Volunteer> expectedFilteredList = new ArrayList<>(actualModel.getFilteredVolunteerList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getVolunteerStorage());
        assertEquals(expectedFilteredList, actualModel.getFilteredVolunteerList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the volunteer at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showVolunteerAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredVolunteerList().size());

        Volunteer volunteer = model.getFilteredVolunteerList().get(targetIndex.getZeroBased());
        final String[] splitName = volunteer.getName().fullName.split("\\s+");
        model.updateFilteredVolunteerList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredVolunteerList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        model.updateFilteredEventList(e -> e.equals(event));
        assertEquals(1, model.getFilteredEventList().size());
    }
}
