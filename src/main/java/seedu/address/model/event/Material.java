package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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
    public static final String VALIDATION_REGEX_UI = "[0-9]+\\s/\\s[0-9]+\\s[\\p{Alnum}][\\p{Alnum} ]*";

    /*
     * The first character of the material must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The expression allows for multiple words to be inputted.
     * The expression matches a valid integer, followed by the words to be inputted.
     */
    public static final String VALIDATION_REGEX = "[0-9]+\\s[\\p{Alnum}][\\p{Alnum} ]*";

    /*
     * The first character of the material must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The expression allows for multiple words to be inputted.
     * This regex applies only for the material name portion of the material string.
     */
    public static final String VALIDATION_REGEX_MATERIAL_NAME = "[\\p{Alnum}][\\p{Alnum} ]*";

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
        checkArgument(isValidMaterialName(material), MESSAGE_CONSTRAINTS);
        checkArgument(isValidQuantity(requiredQuantity), MESSAGE_CONSTRAINTS);
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
     * Constructs a {@code Material} from a {@code String material} in 2 formats: [quantity] [name] or
     * [quantity] / [quantity] [name] (UI string format). This allows the generation of a Material with a current
     * quantity field from a string.
     * @param material A valid material containing 1 or 2 valid whole number quantities in the format: [quantity] [name]
     *                 or [quantity] / [quantity] [name]
     * @return The material generated from the material string.
     * @throws IllegalArgumentException If the material cannot be parsed from its string or UI string representation.
     */
    public static Material fromUiString(String material) {
        requireNonNull(material);

        // of the format [quantity] [material name]
        if (isValidMaterial(material)) {
            return new Material(material);
        } else {
            checkArgument(isValidUiString(material), MESSAGE_CONSTRAINTS);
            int currentQuantity = Integer.parseInt(material.substring(0, material.indexOf(" / ")));
            String remainingStr = material.substring(material.indexOf(" / ") + 3);

            int requiredQuantity = Integer.parseInt(remainingStr.substring(0, remainingStr.indexOf(" ")));
            String materialName = remainingStr.substring(remainingStr.indexOf(" ") + 1);

            return new Material(materialName, currentQuantity, requiredQuantity);
        }
    }

    /**
     * Gets the material name from a {@code String material} of the format [quantity] [material name].
     *
     * @param material A valid material format containing a valid whole number quantity at the front.
     * @return The material name from the {@code String material}.
     */
    public static String nameFromString(String material) {
        requireNonNull(material);
        checkArgument(isValidMaterial(material), MESSAGE_CONSTRAINTS);
        return material.substring(material.indexOf(" ") + 1);
    }

    /**
     * Gets the quantity from a {@code String material} of the format [required/current quantity] [material name]. This
     * can parse required or current quantity depending on what is required.
     *
     * @param material A valid material format containing a valid whole number quantity at the front.
     * @return The material quantity from the {@code String material}.
     */
    public static int quantityFromString(String material) {
        requireNonNull(material);
        checkArgument(isValidMaterial(material), MESSAGE_CONSTRAINTS);
        return Integer.parseInt(material.substring(0, material.indexOf(" ")));
    }

    /**
     * Returns true if a given string is a valid material (of the format [quantity] [material name]).
     */
    public static boolean isValidMaterial(String test) {
        try {
            int quantity = Integer.parseInt(test.split("\\s+")[0]);
            return test.matches(VALIDATION_REGEX) && isValidQuantity(quantity);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // if the material cannot be parsed into format [number] [material name]
            // or the number is not valid
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid material (of the format [quantity] / [quantity] [material name]).
     */
    public static boolean isValidUiString(String test) {
        try {
            int currentQuantity = Integer.parseInt(test.split("\\s+")[0]);
            int requiredQuantity = Integer.parseInt(test.split("\\s+")[2]);
            return test.matches(VALIDATION_REGEX_UI)
                    && isValidQuantity(currentQuantity)
                    && isValidQuantity(requiredQuantity);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // if the material cannot be parsed into format [number] / [number] [material name]
            // or the number is not valid
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
        return currentQuantity + " / " + requiredQuantity + " " + material;
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

        // must be the same material name and quantities
        return material.equals(otherMaterial.material)
                && currentQuantity == otherMaterial.currentQuantity
                && requiredQuantity == otherMaterial.requiredQuantity;
    }

    @Override
    public int hashCode() {
        return material.hashCode();
    }

}
