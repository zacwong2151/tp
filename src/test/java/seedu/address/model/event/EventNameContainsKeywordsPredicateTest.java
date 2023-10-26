package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventNameContainsKeywordsPredicate firstPredicate =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventNameContainsKeywordsPredicate firstPredicateCopy =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("Clean"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Clean up").build()));

        // Multiple keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Clean", "up"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Clean up").build()));

        // Only one matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Sweep", "up"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Clean up").build()));

        // Mixed-case keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("cLeAn", "uP"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Clean up").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EventNameContainsKeywordsPredicate predicate = new EventNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withEventName("Clean").build()));

        // Non-matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Donation"));
        assertFalse(predicate.test(new EventBuilder().withEventName("Clean 1").build()));

        // Keywords match role, and location, but does not match name
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("DoNothing", "Event", "leader",
                "admiralty"));
        assertFalse(predicate.test(new EventBuilder().withEventName("Clean").withRoles("Event Leader")
                .withLocation("admiralty").withDescription("help out la").build()));
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Food"));
        assertFalse(predicate.test(new EventBuilder().withEventName("Clean up").build()));

        // Keywords match date, time, location and description, but does not match name
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("23/9/2023", "1800", "24/9/2023", "2100",
                "East", "dinnertime"));
        assertFalse(predicate.test(new EventBuilder().withEventName("Clean up beach").withStartDate("23/9/2023 1800")
                .withEndDate("24/9/2023 2100").withDescription("Clean very clean").withLocation("East").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        EventNameContainsKeywordsPredicate predicate = new EventNameContainsKeywordsPredicate(keywords);

        String expected = EventNameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
