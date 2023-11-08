package seedu.address.model.volunteer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVolunteers.ALICE;
import static seedu.address.testutil.TypicalVolunteers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.VolunteerBuilder;

public class VolunteerTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Volunteer volunteer = new VolunteerBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> volunteer.getSkills().remove(0));
    }

    @Test
    public void isSameVolunteer() {
        /*
        a volunteer is 'same' if: phone OR email are the same
        a volunteer is 'different' if: both phone AND email are different
         */

        // same object -> returns true
        assertTrue(ALICE.isSameVolunteer(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameVolunteer(null));

        // same phone number, email address, all other attributes different -> returns true
        Volunteer editedBob = new VolunteerBuilder(BOB).withPhone(VALID_PHONE_ALICE).withEmail(VALID_EMAIL_ALICE)
                .withSkills(VALID_SKILL_HUSBAND).build();
        assertTrue(ALICE.isSameVolunteer(editedBob));

        // different phone number, different email, all other attributes same -> returns false
        Volunteer editedAlice = new VolunteerBuilder(ALICE).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameVolunteer(editedAlice));

        // different phone number, all other attributes same -> returns true
        editedAlice = new VolunteerBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSameVolunteer(editedAlice));

        // different email, all other attributes same -> returns true
        editedAlice = new VolunteerBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSameVolunteer(editedAlice));

        // name differs in case, all other attributes same -> returns true
        editedBob = new VolunteerBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameVolunteer(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new VolunteerBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSameVolunteer(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Volunteer aliceCopy = new VolunteerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different volunteer -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Volunteer editedAlice = new VolunteerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new VolunteerBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new VolunteerBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different skills -> returns false
        editedAlice = new VolunteerBuilder(ALICE).withSkills(VALID_SKILL_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Volunteer.class.getCanonicalName() + "{name=" + ALICE.getName()
                            + ", phone=" + ALICE.getPhone() + ", email=" + ALICE.getEmail()
                            + ", skills=" + ALICE.getSkills() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hashCodeMethod() {
        /*
        test case created by zac: I created this cos codecov CI was giving problems,
        hopefully this doesn't create problems down the line
        */
        Volunteer volunteer = new VolunteerBuilder(ALICE).build();
        int expected = volunteer.hashCode();
        assertEquals(expected, -1784892035);
    }

}
