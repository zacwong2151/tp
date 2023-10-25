package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Event's material in the Event list.
 * Guarantees: immutable; is valid as declared in {@link #isValidMaterial(String)}
 */
public class Material {

    public static final String MESSAGE_CONSTRAINTS = "Material should have the format [item quantity] [item name], "
            + "item quantity should be a whole number (0 or positive), and it should not be blank";

    /*
     * The first character of the material must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The expression allows for multiple words to be inputted.
     * The expression matches a valid integer, followed by the words to be inputted.
     */
    public static final String VALIDATION_REGEX = "[0-9]+\\s[A-Za-z0-9 _.,!\"'/$]+";

    /*
     * The first character of the material must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The expression allows for multiple words to be inputted.
     * This regex applies only for the material name portion of the material string.
     */
    public static final String VALIDATION_REGEX_MATERIAL_NAME = "[A-Za-z0-9 _.,!\"'/$]+";

    public final String material;

    public final int currentQuantity;

    public final int requiredQuantity;

    /**
     * Constructs a {@code Material} with a specified quantity. Current quantity is set to 0. The required
     * quantity should be included in the {@code material} parameter.
     *
     * @param material A valid material containing a valid whole number quantity at the front.
     */
    public Material(String material) {
        requireNonNull(material);
        checkArgument(isValidMaterial(material), MESSAGE_CONSTRAINTS);
        this.material = material.substring(material.indexOf(" ") + 1);
        this.currentQuantity = 0;
        this.requiredQuantity = Integer.parseInt(material.substring(0, material.indexOf(" ")));
    }

    /**
     * Constructs a {@code Material} with a specified quantity. Current quantity is set to 0.
     *
     * @param material A valid material name.
     * @param requiredQuantity A valid integer quantity (0 or positive integer).
     */
    public Material(String material, int requiredQuantity) {
        requireNonNull(material);
        checkArgument(isValidMaterial(material), MESSAGE_CONSTRAINTS);
        this.material = material;
        this.currentQuantity = 0;
        this.requiredQuantity = requiredQuantity;
    }

    /**
     * Constructs a {@code Material} with a specified current and required quantity.
     *
     * @param material A valid material name.
     * @param currentQuantity A valid integer current quantity (0 or positive integer).
     * @param requiredQuantity A valid integer required quantity (0 or positive integer).
     */
    public Material(String material, int currentQuantity, int requiredQuantity) {
        requireNonNull(material);
        checkArgument(isValidMaterialName(material), MESSAGE_CONSTRAINTS);
        checkArgument(isValidQuantity(currentQuantity), MESSAGE_CONSTRAINTS);
        checkArgument(isValidQuantity(requiredQuantity), MESSAGE_CONSTRAINTS);
        this.material = material;
        this.currentQuantity = currentQuantity;
        this.requiredQuantity = requiredQuantity;
    }

    /**
     * Returns true if a given string is a valid material.
     */
    public static boolean isValidMaterial(String test) {
        try {
            int quantity = Integer.parseInt(test.split("\\s+")[0]);
            return test.matches(VALIDATION_REGEX) && isValidQuantity(quantity);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid material name.
     */
    public static boolean isValidMaterialName(String test) {
        return test.matches(VALIDATION_REGEX_MATERIAL_NAME);
    }

    /**
     * Returns true if a given integer is a valid quantity.
     */
    public static boolean isValidQuantity(int test) {
        return (test >= 0);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("material", material)
                .add("currentQuantity", currentQuantity)
                .add("requiredQuantity", requiredQuantity)
                .toString();
    }

    /**
     * Adds an amount of items to the current quantity, and returns a new Material with the new quantity.
     * @param addedQuantity The quantity of material to add.
     * @return A new Material with the updated quantity.
     */
    public Material addItems(int addedQuantity) {
        return new Material(material, currentQuantity + addedQuantity, requiredQuantity);
    }

    public boolean hasEnoughItems() {
        return currentQuantity >= requiredQuantity;
    }

    public String toUiString() {
        return currentQuantity + " / " + requiredQuantity + " " + material;
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
