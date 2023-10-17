package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;

/**
 * Represents an Event's date and time in the Event list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS = "DateTime must be in the format DD/MM/YYYY TTTT, "
                                                        + "and it should not be blank";

    /*
     * To be changed
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final LocalDateTime dateTime;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid dateTime.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        this.dateTime = setDateTime(dateTime);
    }

    /**
     * Returns true if a given string is a valid budget.
     */
    public static boolean isValidDateTime(String dateAndTime) {
        String trimmedDateAndTime = dateAndTime.trim();
        String[] parts = dateAndTime.split(" ");

        // If there isn't exactly two components in the dateAndTime, return false
        if (parts.length != 2) {
            return false;
        }

        String date = parts[0];
        String time = parts[1];

        String[] dateParts = date.split("/");
        // If the date does not have three components, return false
        if (dateParts.length != 3) {
            return false;
        }

        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        // If time is not a four-digit number, return false
        if (time.length() != 4) {
            return false;
        }

        int hour = Integer.parseInt(time.substring(0, 2));
        int min = Integer.parseInt(time.substring(2, 4));
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, min);

        if (dateTime == null) {
            return false;
        }
        return true;
    }

    public static LocalDateTime setDateTime(String dateTime) {
        String trimmedDateTime = dateTime.trim();
        if (isValidDateTime(dateTime)) {
            String[] parts = dateTime.split(" ");
            String date = parts[0];
            String time = parts[1];

            String[] dateParts = date.split("/");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);

            int hour = Integer.parseInt(time.substring(0, 2));
            int min = Integer.parseInt(time.substring(2, 4));
            return LocalDateTime.of(year, month, day, hour, min);
        }
        return null;
    }

    @Override
    public String toString() {
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthValue();
        int year = dateTime.getYear();

        int hour = dateTime.getHour();
        int min = dateTime.getMinute();
        return day + "/" + month + "/" + year + " "
                + hour + min;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateTime)) {
            return false;
        }

        DateTime otherDateTime = (DateTime) other;
        return dateTime.equals(otherDateTime.dateTime);
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

}
