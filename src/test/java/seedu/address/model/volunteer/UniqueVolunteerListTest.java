package seedu.address.model.volunteer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVolunteers.ALICE;
import static seedu.address.testutil.TypicalVolunteers.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.volunteer.exceptions.DuplicateVolunteerException;
import seedu.address.model.volunteer.exceptions.VolunteerNotFoundException;
import seedu.address.testutil.VolunteerBuilder;

public class UniqueVolunteerListTest {

    private final UniqueVolunteerList uniqueVolunteerList = new UniqueVolunteerList();

    @Test
    public void contains_nullVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.contains(null));
    }

    @Test
    public void contains_volunteerNotInList_returnsFalse() {
        assertFalse(uniqueVolunteerList.contains(ALICE));
    }

    @Test
    public void contains_volunteerInList_returnsTrue() {
        uniqueVolunteerList.add(ALICE);
        assertTrue(uniqueVolunteerList.contains(ALICE));
    }

    @Test
    public void contains_volunteerWithSameIdentityFieldsInList_returnsTrue() {
        uniqueVolunteerList.add(ALICE);
        Volunteer editedAlice = new VolunteerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withSkills(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueVolunteerList.contains(editedAlice));
    }

    @Test
    public void add_nullVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.add(null));
    }

    @Test
    public void add_duplicateVolunteer_throwsDuplicateVolunteerException() {
        uniqueVolunteerList.add(ALICE);
        assertThrows(DuplicateVolunteerException.class, () -> uniqueVolunteerList.add(ALICE));
    }

    @Test
    public void setVolunteer_nullTargetVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.setVolunteer(null, ALICE));
    }

    @Test
    public void setVolunteer_nullEditedVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.setVolunteer(ALICE, null));
    }

    @Test
    public void setVolunteer_targetVolunteerNotInList_throwsVolunteerNotFoundException() {
        assertThrows(VolunteerNotFoundException.class, () -> uniqueVolunteerList.setVolunteer(ALICE, ALICE));
    }

    @Test
    public void setVolunteer_editedVolunteerIsSameVolunteer_success() {
        uniqueVolunteerList.add(ALICE);
        uniqueVolunteerList.setVolunteer(ALICE, ALICE);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(ALICE);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setVolunteer_editedVolunteerHasSameIdentity_success() {
        uniqueVolunteerList.add(ALICE);
        Volunteer editedAlice = new VolunteerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withSkills(VALID_TAG_HUSBAND)
                .build();
        uniqueVolunteerList.setVolunteer(ALICE, editedAlice);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(editedAlice);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setVolunteer_editedVolunteerHasDifferentIdentity_success() {
        uniqueVolunteerList.add(ALICE);
        uniqueVolunteerList.setVolunteer(ALICE, BOB);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(BOB);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setVolunteer_editedVolunteerHasNonUniqueIdentity_throwsDuplicateVolunteerException() {
        uniqueVolunteerList.add(ALICE);
        uniqueVolunteerList.add(BOB);
        assertThrows(DuplicateVolunteerException.class, () -> uniqueVolunteerList.setVolunteer(ALICE, BOB));
    }

    @Test
    public void remove_nullVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.remove(null));
    }

    @Test
    public void remove_volunteerDoesNotExist_throwsVolunteerNotFoundException() {
        assertThrows(VolunteerNotFoundException.class, () -> uniqueVolunteerList.remove(ALICE));
    }

    @Test
    public void remove_existingVolunteer_removesVolunteer() {
        uniqueVolunteerList.add(ALICE);
        uniqueVolunteerList.remove(ALICE);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setVolunteers_nullUniqueVolunteerList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.setVolunteers((UniqueVolunteerList) null));
    }

    @Test
    public void setVolnuteers_uniqueVolunteerList_replacesOwnListWithProvidedUniqueVolunteerList() {
        uniqueVolunteerList.add(ALICE);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(BOB);
        uniqueVolunteerList.setVolunteers(expectedUniqueVolunteerList);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setVolunteers_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.setVolunteers((List<Volunteer>) null));
    }

    @Test
    public void setVolunteers_list_replacesOwnListWithProvidedList() {
        uniqueVolunteerList.add(ALICE);
        List<Volunteer> volunteerList = Collections.singletonList(BOB);
        uniqueVolunteerList.setVolunteers(volunteerList);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(BOB);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Volunteer> listWithDuplicateVolunteers = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateVolunteerException.class, () ->
                        uniqueVolunteerList.setVolunteers(listWithDuplicateVolunteers));
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
