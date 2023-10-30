package seedu.address.storage.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Role;

/**
 * Jackson-friendly version of {@link Role}.
 */
public class JsonAdaptedRole {

    private final String roleName;

    private final String currentQuantity;

    private final String requiredQuantity;

    /**
     * Constructs a {@code JsonAdaptedRole} with the given {@code roleName}, disregarding quantity.
     */
    public JsonAdaptedRole(String roleName) {
        this.roleName = roleName;
        this.currentQuantity = "0";
        this.requiredQuantity = "0";
    }

    /**
     * Constructs a {@code JsonAdaptedRole} with the given {@code role}.
     */
    @JsonCreator
    public JsonAdaptedRole(@JsonProperty("roleName") String roleName,
                           @JsonProperty("currentQuantity") String currentQuantity,
                           @JsonProperty("requiredQuantity") String requiredQuantity) {
        this.roleName = roleName;
        this.currentQuantity = currentQuantity;
        this.requiredQuantity = requiredQuantity;
    }

    /**
     * Converts a given {@code Role} into this class for Jackson use.
     */
    public JsonAdaptedRole(Role source) {
        roleName = source.roleName;
        currentQuantity = String.valueOf(source.currentQuantity);
        requiredQuantity = String.valueOf(source.requiredQuantity);
    }

    public String getRoleName() {
        return roleName;
    }

    /**
     * Converts this Jackson-friendly adapted role object into the model's {@code Role} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted role.
     */
    public Role toModelType() throws IllegalValueException {
        int currentQuantityInt;
        int requiredQuantityInt;

        try {
            currentQuantityInt = Integer.parseInt(currentQuantity);
            requiredQuantityInt = Integer.parseInt(requiredQuantity);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }

        // validation for role name and quantity values
        boolean isValidRole = (Role.isValidRoleName(roleName)
                && Role.isValidQuantity(currentQuantityInt)
                && Role.isValidQuantity(requiredQuantityInt));

        if (!isValidRole) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }

        return new Role(roleName, currentQuantityInt, requiredQuantityInt);
    }

}
