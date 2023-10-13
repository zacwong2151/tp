package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateVolunteerException;
import seedu.address.model.person.exceptions.VolunteerNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Volunteer#isSamePerson(Volunteer)
 */
public class UniqueVolunteerList implements Iterable<Volunteer> {

    private final ObservableList<Volunteer> internalList = FXCollections.observableArrayList();
    private final ObservableList<Volunteer> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Volunteer toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Volunteer toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateVolunteerException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Volunteer target, Volunteer editedVolunteer) {
        requireAllNonNull(target, editedVolunteer);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new VolunteerNotFoundException();
        }

        if (!target.isSamePerson(editedVolunteer) && contains(editedVolunteer)) {
            throw new DuplicateVolunteerException();
        }

        internalList.set(index, editedVolunteer);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Volunteer toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new VolunteerNotFoundException();
        }
    }

    public void setPersons(UniqueVolunteerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Volunteer> volunteers) {
        requireAllNonNull(volunteers);
        if (!personsAreUnique(volunteers)) {
            throw new DuplicateVolunteerException();
        }

        internalList.setAll(volunteers);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Volunteer> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Volunteer> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueVolunteerList)) {
            return false;
        }

        UniqueVolunteerList otherUniqueVolunteerList = (UniqueVolunteerList) other;
        return internalList.equals(otherUniqueVolunteerList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Volunteer> volunteers) {
        for (int i = 0; i < volunteers.size() - 1; i++) {
            for (int j = i + 1; j < volunteers.size(); j++) {
                if (volunteers.get(i).isSamePerson(volunteers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
