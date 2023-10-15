package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVolunteers.ALICE;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.exceptions.DuplicateVolunteerException;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final VolunteerStorage volunteerStorage = new VolunteerStorage();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), volunteerStorage.getVolunteerList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> volunteerStorage.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        VolunteerStorage newData = getTypicalVolunteerStorage();
        volunteerStorage.resetData(newData);
        assertEquals(newData, volunteerStorage);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Volunteer editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Volunteer> newVolunteers = Arrays.asList(ALICE, editedAlice);
        VolunteerStorageStub newData = new VolunteerStorageStub(newVolunteers);

        assertThrows(DuplicateVolunteerException.class, () -> volunteerStorage.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> volunteerStorage.hasVolunteer(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(volunteerStorage.hasVolunteer(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        volunteerStorage.addVolunteer(ALICE);
        assertTrue(volunteerStorage.hasVolunteer(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        volunteerStorage.addVolunteer(ALICE);
        Volunteer editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(volunteerStorage.hasVolunteer(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> volunteerStorage.getVolunteerList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = VolunteerStorage.class.getCanonicalName() + "{volunteers="
                            + volunteerStorage.getVolunteerList()
                            + "}";
        assertEquals(expected, volunteerStorage.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class VolunteerStorageStub implements ReadOnlyVolunteerStorage {
        private final ObservableList<Volunteer> volunteers = FXCollections.observableArrayList();

        VolunteerStorageStub(Collection<Volunteer> volunteers) {
            this.volunteers.setAll(volunteers);
        }

        @Override
        public ObservableList<Volunteer> getVolunteerList() {
            return volunteers;
        }
    }

}
