package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.VolunteerStorage;
import seedu.address.model.ReadOnlyVolunteerStorage;
import seedu.address.model.person.Volunteer;

/**
 * An Immutable Volunteer Storage that is serializable to JSON format.
 */
@JsonRootName(value = "volunteerStorage")
class JsonSerializableVolunteerStorage {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedVolunteer> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableVolunteerStorage(@JsonProperty("persons") List<JsonAdaptedVolunteer> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableVolunteerStorage(ReadOnlyVolunteerStorage source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedVolunteer::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public VolunteerStorage toModelType() throws IllegalValueException {
        VolunteerStorage volunteerStorage = new VolunteerStorage();
        for (JsonAdaptedVolunteer jsonAdaptedVolunteer : persons) {
            Volunteer volunteer = jsonAdaptedVolunteer.toModelType();
            if (volunteerStorage.hasPerson(volunteer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            volunteerStorage.addPerson(volunteer);
        }
        return volunteerStorage;
    }

}
