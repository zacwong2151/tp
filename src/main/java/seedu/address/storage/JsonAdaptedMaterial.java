package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Material;
import seedu.address.model.event.Role;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Material}.
 */
class JsonAdaptedMaterial {

    private final String material;

    /**
     * Constructs a {@code JsonAdaptedMaterial} with the given {@code material}.
     */
    @JsonCreator
    public JsonAdaptedMaterial(String material) {
        this.material = material;
    }

    /**
     * Converts a given {@code Material} into this class for Jackson use.
     */
    public JsonAdaptedMaterial(Material source) {
        material = source.material;
    }

    @JsonValue
    public String getMaterial() {
        return material;
    }

    /**
     * Converts this Jackson-friendly adapted material object into the model's {@code Material} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted material.
     */
    public Material toModelType() throws IllegalValueException {
        if (!Material.isValidMaterial(material)) {
            throw new IllegalValueException(Material.MESSAGE_CONSTRAINTS);
        }
        return new Material(material);
    }

}
