package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's material in the Event list.
 * Guarantees: immutable; is valid as declared in {@link #isValidMaterial(String)}
 */
public class Material {

    public static final String MESSAGE_CONSTRAINTS = "Material can take any values, and it should not be blank";

    /*
     * The first character of the material must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The expression allows for multiple words to be inputted.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String material;

    /**
     * Constructs a {@code Material}.
     *
     * @param material A valid material.
     */
    public Material(String material) {
        requireNonNull(material);
        checkArgument(isValidMaterial(material), MESSAGE_CONSTRAINTS);
        this.material = material;
    }

    /**
     * Returns true if a given string is a valid material.
     */
    public static boolean isValidMaterial(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return material;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Material)) {
            return false;
        }

        Material otherMaterial = (Material) other;
        return material.equals(otherMaterial.material);
    }

    @Override
    public int hashCode() {
        return material.hashCode();
    }

}
