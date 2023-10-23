package seedu.address.model.volunteer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.VolunteerFindCommandTest.preparePredicate;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.VolunteerBuilder;

public class SkillNameContainsKeywordsPredicateTest {

    @Test
    public void equals() throws ParseException {
        SkillNameContainsKeywordsPredicate firstPredicate = preparePredicate(" n/first");
        SkillNameContainsKeywordsPredicate secondPredicate = preparePredicate(" n/first n/second");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SkillNameContainsKeywordsPredicate firstPredicateCopy = preparePredicate(" n/first");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different volunteer -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() throws ParseException {
        // One keyword
        SkillNameContainsKeywordsPredicate predicate = preparePredicate(" n/Alice");
        assertTrue(predicate.test(new VolunteerBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = preparePredicate(" n/Alice n/Bob");
        assertTrue(predicate.test(new VolunteerBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = preparePredicate(" n/Bob n/Carol");
        assertTrue(predicate.test(new VolunteerBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = preparePredicate(" n/aLIce n/bOB");
        assertTrue(predicate.test(new VolunteerBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() throws ParseException {
        // Zero keywords
        SkillNameContainsKeywordsPredicate predicate1 = new SkillNameContainsKeywordsPredicate(Collections.emptyList());
        assertThrows(AssertionError.class, () -> predicate1.test(new VolunteerBuilder().withName("Alice").build()));

        // Non-matching keyword
        SkillNameContainsKeywordsPredicate predicate2 = preparePredicate(" n/Carol");
        assertFalse(predicate2.test(new VolunteerBuilder().withName("Alice Bob").build()));

        // name with non-alphanumeric characters
        assertThrows(ParseException.class, () -> preparePredicate(" n/alice@email.com"));
        // name matches but skill does not match
        SkillNameContainsKeywordsPredicate predicate3 = preparePredicate(" n/Alice s/smart");
        assertFalse(predicate3.test(new VolunteerBuilder().withName("Alice").withSkills("intelligent").build()));
    }

    @Test
    public void toStringMethod() throws ParseException {
        SkillNameContainsKeywordsPredicate predicate = preparePredicate(" n/keyword1 s/keyword2");

        String expected = SkillNameContainsKeywordsPredicate.class.getCanonicalName() + "{names=[keyword1], "
                + "skills=[keyword2]}";
        assertEquals(expected, predicate.toString());
    }
}
