package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Role in an event.
 * Guarantees: immutable; role is valid as declared in {@link #isValidRoleName(String)}
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS = "Role should have the format [role quantity] [role name], "
            + "role quantity should be a whole number (0 or positive), and it should not be blank";

    /*
     * The first character of the role must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The expression allows for multiple words to be inputted.
     * The expression matches a valid integer, followed by the words to be inputted.
     */
    public static final String VALIDATION_REGEX_UI = "[0-9]+\\s/\\s[0-9]+\\s[\\p{Alnum}][\\p{Alnum} ]*";

    /*
     * The first character of the role must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The expression allows for multiple words to be inputted.
     * The expression matches a valid integer, followed by the words to be inputted.
     */
    public static final String VALIDATION_REGEX = "[0-9]+\\s[\\p{Alnum}][\\p{Alnum} ]*";

    /*
     * The first character of the role must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The expression allows for multiple words to be inputted.
     * This regex applies only for the role name portion of the role string.
     */
    public static final String VALIDATION_REGEX_ROLE_NAME = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String roleName;

    public final int currentQuantity;

    public final int requiredQuantity;

    /**
     * Constructs a {@code Role} with a specified quantity. Current quantity is set to 0. The required
     * quantity should be included in the {@code role} parameter.
     *
     * @param role A valid role containing a valid whole number quantity at the front.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        this.roleName = role.substring(role.indexOf(" ") + 1);
        this.currentQuantity = 0;
        this.requiredQuantity = Integer.parseInt(role.substring(0, role.indexOf(" ")));
    }

    /**
     * Constructs a {@code Role} with a specified quantity. Current quantity is set to 0.
     *
     * @param roleName A valid role name.
     * @param requiredQuantity A valid integer quantity (0 or positive integer).
     */
    public Role(String roleName, int requiredQuantity) {
        requireNonNull(roleName);
        checkArgument(isValidRoleName(roleName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidQuantity(requiredQuantity), MESSAGE_CONSTRAINTS);
        this.roleName = roleName;
        this.currentQuantity = 0;
        this.requiredQuantity = requiredQuantity;
    }

    /**
     * Constructs a {@code Role} with a specified current and required quantity.
     *
     * @param roleName A valid role name.
     * @param currentQuantity A valid integer current quantity (0 or positive integer).
     * @param requiredQuantity A valid integer required quantity (0 or positive integer).
     */
    public Role(String roleName, int currentQuantity, int requiredQuantity) {
        requireNonNull(roleName);
        checkArgument(isValidRoleName(roleName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidQuantity(currentQuantity), MESSAGE_CONSTRAINTS);
        checkArgument(isValidQuantity(requiredQuantity), MESSAGE_CONSTRAINTS);
        this.roleName = roleName;
        this.currentQuantity = currentQuantity;
        this.requiredQuantity = requiredQuantity;
    }

    /**
     * Constructs a {@code Role} from a {@code String role} in 2 formats: [quantity] [role name] or
     * [quantity] / [quantity] [role name] (UI string format). This allows the generation of a Role with a current
     * quantity field from a string.
     * @param role A valid role containing 1 or 2 valid whole number quantities in the format: [quantity] [role name]
     *                 or [quantity] / [quantity] [role name]
     * @return The role generated from the role string.
     * @throws IllegalArgumentException If the role cannot be parsed from its string or UI string representation.
     */
    public static Role fromUiString(String role) {
        requireNonNull(role);

        // of the format [quantity] [role name]
        if (isValidRole(role)) {
            return new Role(role);
        } else {
            checkArgument(isValidUiString(role), MESSAGE_CONSTRAINTS);
            int currentQuantity = Integer.parseInt(role.substring(0, role.indexOf(" / ")));
            String remainingStr = role.substring(role.indexOf(" / ") + 3);

            int requiredQuantity = Integer.parseInt(remainingStr.substring(0, remainingStr.indexOf(" ")));
            String roleName = remainingStr.substring(remainingStr.indexOf(" ") + 1);

            return new Role(roleName, currentQuantity, requiredQuantity);
        }
    }

    /**
     * Gets the role name from a {@code String role} of the format [quantity] [role name].
     *
     * @param role A valid role format containing a valid whole number quantity at the front.
     * @return The role name from the {@code String role}.
     */
    public static String nameFromString(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        return role.substring(role.indexOf(" ") + 1);
    }

    /**
     * Gets the quantity from a {@code String role} of the format [required/current quantity] [role name]. This
     * can parse required or current quantity depending on what is required.
     *
     * @param role A valid role format containing a valid whole number quantity at the front.
     * @return The role quantity from the {@code String role}.
     */
    public static int quantityFromString(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        return Integer.parseInt(role.substring(0, role.indexOf(" ")));
    }

    /**
     * Returns true if a given string is a valid role (of the format [quantity] [role name]).
     */
    public static boolean isValidRole(String test) {
        try {
            int quantity = Integer.parseInt(test.split("\\s+")[0]);
            return test.matches(VALIDATION_REGEX) && isValidQuantity(quantity);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // if the role cannot be parsed into format [number] [role name]
            // or the number is not valid
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid role (of the format [quantity] / [quantity] [role name]).
     */
    public static boolean isValidUiString(String test) {
        try {
            int currentQuantity = Integer.parseInt(test.split("\\s+")[0]);
            int requiredQuantity = Integer.parseInt(test.split("\\s+")[2]);
            return test.matches(VALIDATION_REGEX_UI)
                    && isValidQuantity(currentQuantity)
                    && isValidQuantity(requiredQuantity);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // if the role cannot be parsed into format [number] / [number] [role name]
            // or the number is not valid
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid role name.
     */
    public static boolean isValidRoleName(String test) {
        return test.matches(VALIDATION_REGEX_ROLE_NAME);
    }

    /**
     * Returns true if a given integer is a valid quantity.
     */
    public static boolean isValidQuantity(int test) {
        return (test >= 0);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return roleName.equals(otherRole.roleName)
                && currentQuantity == otherRole.currentQuantity
                && requiredQuantity == otherRole.requiredQuantity;
    }

    @Override
    public int hashCode() {
        return roleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return currentQuantity + " / " + requiredQuantity + " " + roleName;
    }

    /**
     * Adds 1 to the current quantity, and returns a new Role with the new quantity.
     * @return A new Role with the updated quantity.
     */
    public Role addRoleManpower() {
        return new Role(roleName, currentQuantity + 1, requiredQuantity);
    }

    /**
     * Removes 1 to the current quantity, and returns a new Role with the new quantity.
     * @return A new Role with the updated quantity.
     */
    public Role decreaseRoleManpower() {
        return new Role(roleName, currentQuantity - 1, requiredQuantity);
    }

    public boolean hasEnoughManpower() {
        return currentQuantity >= requiredQuantity;
    }

    public String toUiString() {
        return currentQuantity + " / " + requiredQuantity + " " + roleName;
    }
}
