package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyVolunteerStorage;
import seedu.address.model.VolunteerStorage;
import seedu.address.model.volunteer.Volunteer;

/**
 * An Immutable Volunteer Storage that is serializable to JSON format.
 */
@JsonRootName(value = "volunteerStorage")
class JsonSerializableVolunteerStorage {

    public static final String MESSAGE_DUPLICATE_VOLUNTEER = "Volunteers list contains duplicate volunteer(s).";

    private final List<JsonAdaptedVolunteer> volunteers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableVolunteerStorage} with the given volunteers.
     */
    @JsonCreator
    public JsonSerializableVolunteerStorage(@JsonProperty("volunteers") List<JsonAdaptedVolunteer> volunteers) {
        this.volunteers.addAll(volunteers);
    }

    /**
     * Converts a given {@code ReadOnlyVolunteerStorage} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableVolunteerStorage}.
     */
    public JsonSerializableVolunteerStorage(ReadOnlyVolunteerStorage source) {
        volunteers.addAll(source.getVolunteerList().stream().map(JsonAdaptedVolunteer::new)
                    .collect(Collectors.toList()));
    }

    /**
     * Converts this volunteer storage into the model's {@code VolunteerStorage} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public VolunteerStorage toModelType() throws IllegalValueException {
        VolunteerStorage volunteerStorage = new VolunteerStorage();
        for (JsonAdaptedVolunteer jsonAdaptedVolunteer : volunteers) {
            Volunteer volunteer = jsonAdaptedVolunteer.toModelType();
            if (volunteerStorage.hasVolunteer(volunteer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_VOLUNTEER);
            }
            volunteerStorage.addVolunteer(volunteer);
        }
        return volunteerStorage;
    }

}
