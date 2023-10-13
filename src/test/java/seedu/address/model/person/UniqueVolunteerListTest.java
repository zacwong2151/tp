package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateVolunteerException;
import seedu.address.model.person.exceptions.VolunteerNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueVolunteerListTest {

    private final UniqueVolunteerList uniqueVolunteerList = new UniqueVolunteerList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueVolunteerList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueVolunteerList.add(ALICE);
        assertTrue(uniqueVolunteerList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueVolunteerList.add(ALICE);
        Volunteer editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueVolunteerList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueVolunteerList.add(ALICE);
        assertThrows(DuplicateVolunteerException.class, () -> uniqueVolunteerList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(VolunteerNotFoundException.class, () -> uniqueVolunteerList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueVolunteerList.add(ALICE);
        uniqueVolunteerList.setPerson(ALICE, ALICE);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(ALICE);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueVolunteerList.add(ALICE);
        Volunteer editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueVolunteerList.setPerson(ALICE, editedAlice);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(editedAlice);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueVolunteerList.add(ALICE);
        uniqueVolunteerList.setPerson(ALICE, BOB);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(BOB);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueVolunteerList.add(ALICE);
        uniqueVolunteerList.add(BOB);
        assertThrows(DuplicateVolunteerException.class, () -> uniqueVolunteerList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(VolunteerNotFoundException.class, () -> uniqueVolunteerList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueVolunteerList.add(ALICE);
        uniqueVolunteerList.remove(ALICE);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.setPersons((UniqueVolunteerList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueVolunteerList.add(ALICE);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(BOB);
        uniqueVolunteerList.setPersons(expectedUniqueVolunteerList);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.setPersons((List<Volunteer>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueVolunteerList.add(ALICE);
        List<Volunteer> volunteerList = Collections.singletonList(BOB);
        uniqueVolunteerList.setPersons(volunteerList);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(BOB);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Volunteer> listWithDuplicateVolunteers = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateVolunteerException.class, () -> uniqueVolunteerList.setPersons(listWithDuplicateVolunteers));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueVolunteerList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueVolunteerList.asUnmodifiableObservableList().toString(), uniqueVolunteerList.toString());
    }
}
