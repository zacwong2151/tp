package seedu.address.model.volunteer.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateVolunteerException extends RuntimeException {
    public DuplicateVolunteerException() {
        super("Operation would result in duplicate persons");
    }
}
