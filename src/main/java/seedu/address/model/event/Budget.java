package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's budget in the Event list.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudget(String)}
 */
public class Budget {

    public static final String MESSAGE_CONSTRAINTS = "Budget must be in two decimal places, and it should not be blank";

    /*
     * To be changed
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String budget;

    /**
     * Constructs a {@code Location}.
     *
     * @param budget A valid budget.
     */
    public Budget(String budget) {
        requireNonNull(budget);
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);
        this.budget = budget;
    }

    /**
     * Returns true if a given string is a valid budget.
     */
    public static boolean isValidBudget(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return budget;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return budget.equals(otherBudget.budget);
    }

    @Override
    public int hashCode() {
        return budget.hashCode();
    }

}
