package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.TypicalEvents.FIFTH;
import static seedu.address.testutil.TypicalEvents.FIRST;
import static seedu.address.testutil.TypicalEvents.FOURTH;
import static seedu.address.testutil.TypicalEvents.SECOND;
import static seedu.address.testutil.TypicalEvents.SEVENTH;
import static seedu.address.testutil.TypicalEvents.SIXTH;
import static seedu.address.testutil.TypicalEvents.THIRD;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventcommands.EventFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;

public class EventFindCommandTest {
    private Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(),
            new UserPrefs());

    @Test
    public void equals() {
        List<String> array1 = new ArrayList<>();
        array1.add("first");
        List<String> array2 = new ArrayList<>();
        array2.add("second");
        EventNameContainsKeywordsPredicate firstPredicate = new EventNameContainsKeywordsPredicate(array1);
        EventNameContainsKeywordsPredicate secondPredicate = new EventNameContainsKeywordsPredicate(array2);

        EventFindCommand findFirstCommand = new EventFindCommand(firstPredicate);
        EventFindCommand findSecondCommand = new EventFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        EventFindCommand findFirstCommandCopy = new EventFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_unknownEvent_noEventFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventNameContainsKeywordsPredicate predicate = preparePredicate(" n/random");
        EventFindCommand command = new EventFindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 7);
        EventNameContainsKeywordsPredicate predicate = preparePredicate(" n/clean");
        EventFindCommand command = new EventFindCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH),
                model.getFilteredEventList());
    }

    @Test
    public void toStringMethod() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Arrays.asList("keyword1"));
        EventFindCommand EventFindCommand = new EventFindCommand(predicate);
        String expected = EventFindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, EventFindCommand.toString());
    }

    @Test
    public void preparePredicateTest() throws ParseException {
        EventNameContainsKeywordsPredicate predicate = preparePredicate(" n/food distribution");
        assertEquals(EventNameContainsKeywordsPredicate.class.getCanonicalName()
                + "{event names=[food distribution]}", predicate.toString());
    }
    /**
     * Parses {@code userInput} into a {@code EventNameContainsKeywordsPredicate}.
     */
    public static EventNameContainsKeywordsPredicate preparePredicate(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!argMultimap.getValue(PREFIX_NAME).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventFindCommand.MESSAGE_USAGE));
        }

        assert argMultimap.getValue(PREFIX_NAME).isPresent() : "At least one PREFIX_NAME should be present";

        Set<String> eventNameKeywords = ParserUtil.parseEventNames(argMultimap.getAllValues(PREFIX_NAME));
        List<String> eventNames = new ArrayList<>(eventNameKeywords);

        return new EventNameContainsKeywordsPredicate(eventNames);
    }
}
