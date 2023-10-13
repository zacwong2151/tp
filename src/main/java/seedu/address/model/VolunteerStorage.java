package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.UniqueVolunteerList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class VolunteerStorage implements ReadOnlyVolunteerStorage {

    private final UniqueVolunteerList persons;
    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueVolunteerList();
    }

    public VolunteerStorage() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public VolunteerStorage(ReadOnlyVolunteerStorage toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Volunteer> volunteers) {
        this.persons.setPersons(volunteers);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyVolunteerStorage newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Volunteer volunteer) {
        requireNonNull(volunteer);
        return persons.contains(volunteer);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Volunteer p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Volunteer target, Volunteer editedVolunteer) {
        requireNonNull(editedVolunteer);

        persons.setPerson(target, editedVolunteer);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Volunteer key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Volunteer> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerStorage)) {
            return false;
        }

        VolunteerStorage otherVolunteerStorage = (VolunteerStorage) other;
        return persons.equals(otherVolunteerStorage.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
