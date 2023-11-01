package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's maximum volunteer size in the event storage.
 * Guarantees: immutable; is valid as declared in {@link #isValidMaxVolunteerSize(String)}
 */
public class MaxVolunteerSize {

    public static final String MESSAGE_CONSTRAINTS =
            "The maximum volunteer size should be a non-zero unsigned integer, or 0 for no limit.";

    /*
     * The first character of the event name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The expression allows for multiple words to be inputted.
     */
    public static final String VALIDATION_REGEX = "[0-9]+";

    public final long maxVolunteerSize;

    /**
     * Constructs a {@code MaxVolunteerSize} with the default value of {@code Long.MAX_VALUE}.
     */
    public MaxVolunteerSize() {
        maxVolunteerSize = Long.MAX_VALUE;
    }

    /**
     * Constructs a {@code MaxVolunteerSize}.
     *
     * @param maxVolunteerSize A valid unsigned long integer.
     */
    public MaxVolunteerSize(String maxVolunteerSize) {
        requireNonNull(maxVolunteerSize);
        checkArgument(isValidMaxVolunteerSize(maxVolunteerSize), MESSAGE_CONSTRAINTS);

        // NumberFormatException is handled in the checkArgument call above
        long maxVolunteerSizeLong = Long.parseLong(maxVolunteerSize);
        this.maxVolunteerSize = maxVolunteerSizeLong != 0
                ? maxVolunteerSizeLong
                : Long.MAX_VALUE;
    }

    /**
     * Constructs a {@code MaxVolunteerSize} from a long integer.
     *
     * @param maxVolunteerSize A valid unsigned long integer.
     */
    public MaxVolunteerSize(long maxVolunteerSize) {
        checkArgument(isValidMaxVolunteerSize(maxVolunteerSize), MESSAGE_CONSTRAINTS);
        if (maxVolunteerSize == 0) {
            this.maxVolunteerSize = Long.MAX_VALUE;
        } else {
            this.maxVolunteerSize = maxVolunteerSize;
        }
    }

    /**
     * Returns true if a given string is a valid maximum volunteer size.
     */
    public static boolean isValidMaxVolunteerSize(String test) {
        try {
            return test.matches(VALIDATION_REGEX)
                    && isValidMaxVolunteerSize(Long.parseLong(test));
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns true if a given long integer is a valid maximum volunteer size.
     */
    public static boolean isValidMaxVolunteerSize(long test) {
        return (test >= 0);
    }

    @Override
    public String toString() {
        return String.valueOf(maxVolunteerSize);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MaxVolunteerSize)) {
            return false;
        }

        MaxVolunteerSize otherMaxVolunteerSize = (MaxVolunteerSize) other;
        return maxVolunteerSize == otherMaxVolunteerSize.maxVolunteerSize;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(maxVolunteerSize).hashCode();
    }

}
