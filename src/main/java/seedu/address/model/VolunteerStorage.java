package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.volunteer.UniqueVolunteerList;
import seedu.address.model.volunteer.Volunteer;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class VolunteerStorage implements ReadOnlyVolunteerStorage {

    private final UniqueVolunteerList volunteers;
    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        volunteers = new UniqueVolunteerList();
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
     * Replaces the contents of the volunteer list with {@code volunteers}.
     * {@code volunteers} must not contain duplicate volunteers.
     */
    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers.setPersons(volunteers);
    }

    /**
     * Resets the existing data of this {@code VolunteerStorage} with {@code newData}.
     */
    public void resetData(ReadOnlyVolunteerStorage newData) {
        requireNonNull(newData);

        setVolunteers(newData.getVolunteerList());
    }

    //// person-level operations

    /**
     * Returns true if a volunteer with the same identity as {@code volunteer} exists in the VolunteerStorage.
     */
    public boolean hasVolunteer(Volunteer volunteer) {
        requireNonNull(volunteer);
        return volunteers.contains(volunteer);
    }

    /**
     * Adds a volunteer to the volunteer storage.
     * The volunteer must not already exist in the volunteer storage.
     */
    public void addVolunteer(Volunteer p) {
        volunteers.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedVolunteer}.
     * {@code target} must exist in the volunteer storage.
     * The volunteer identity of {@code editedVolunteer} must not be the same as another existing volunteer
     * in the volunteer storage.
     */
    public void setVolunteer(Volunteer target, Volunteer editedVolunteer) {
        requireNonNull(editedVolunteer);

        volunteers.setVolunteer(target, editedVolunteer);
    }

    /**
     * Removes {@code key} from this {@code VolunteerStorage}.
     * {@code key} must exist in the volunteer storage.
     */
    public void removeVolunteer(Volunteer key) {
        volunteers.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("volunteers", volunteers)
                .toString();
    }

    @Override
    public ObservableList<Volunteer> getVolunteerList() {
        return volunteers.asUnmodifiableObservableList();
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
        return volunteers.equals(otherVolunteerStorage.volunteers);
    }

    @Override
    public int hashCode() {
        return volunteers.hashCode();
    }
}
