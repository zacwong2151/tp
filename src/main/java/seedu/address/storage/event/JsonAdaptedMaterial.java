package seedu.address.storage.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Material;

/**
 * Jackson-friendly version of {@link Material}.
 */
public class JsonAdaptedMaterial {

    private final String material;

    private final String currentQuantity;

    private final String requiredQuantity;

    /**
     * Constructs a {@code JsonAdaptedMaterial} with the given {@code material}, disregarding quantity.
     */
    public JsonAdaptedMaterial(String material) {
        this.material = material;
        this.currentQuantity = "0";
        this.requiredQuantity = "0";
    }

    /**
     * Constructs a {@code JsonAdaptedMaterial} with the given {@code material}.
     */
    @JsonCreator
    public JsonAdaptedMaterial(@JsonProperty("material") String material,
                               @JsonProperty("currentQuantity") String currentQuantity,
                               @JsonProperty("requiredQuantity") String requiredQuantity) {
        this.material = material;
        this.currentQuantity = currentQuantity;
        this.requiredQuantity = requiredQuantity;
    }

    /**
     * Converts a given {@code Material} into this class for Jackson use.
     */
    public JsonAdaptedMaterial(Material source) {
        material = source.material;
        currentQuantity = String.valueOf(source.currentQuantity);
        requiredQuantity = String.valueOf(source.requiredQuantity);
    }

    public String getMaterial() {
        return material;
    }

    /**
     * Converts this Jackson-friendly adapted material object into the model's {@code Material} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted material.
     */
    public Material toModelType() throws IllegalValueException {
        int currentQuantityInt;
        int requiredQuantityInt;

        try {
            currentQuantityInt = Integer.parseInt(currentQuantity);
            requiredQuantityInt = Integer.parseInt(requiredQuantity);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(Material.MESSAGE_CONSTRAINTS);
        }

        // validation for names and quantity values
        boolean isValidMaterial = (Material.isValidMaterialName(material)
                && Material.isValidQuantity(currentQuantityInt)
                && Material.isValidQuantity(requiredQuantityInt));

        if (!isValidMaterial) {
            throw new IllegalValueException(Material.MESSAGE_CONSTRAINTS);
        }

        return new Material(material, currentQuantityInt, requiredQuantityInt);
    }

}
