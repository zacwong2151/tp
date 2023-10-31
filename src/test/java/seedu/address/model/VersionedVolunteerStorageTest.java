package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.VolunteerBuilder;

class VersionedVolunteerStorageTest {
    private VersionedVolunteerStorage versionedVolunteerStorage;
    private VolunteerStorage volunteerStorage = new VolunteerStorage();
    @BeforeEach
    public void setUp() {
        List<Volunteer> volunteers = new ArrayList<>();
        Volunteer volunteer = new VolunteerBuilder().build();
        volunteers.add(volunteer);
        volunteerStorage.setVolunteers(volunteers);
        versionedVolunteerStorage = new VersionedVolunteerStorage(volunteerStorage);
    }

    @Test
    public void shiftPointerForward() {
        versionedVolunteerStorage.shiftPointerForward();
        assertEquals(1, versionedVolunteerStorage.getCurrentStatePointer());
    }

    @Test
    public void shiftPointerBackward() {
        versionedVolunteerStorage.shiftPointerForward();
        versionedVolunteerStorage.shiftPointerForward();
        versionedVolunteerStorage.shiftPointerBackwards();
        assertEquals(1, versionedVolunteerStorage.getCurrentStatePointer());
    }

    @Test
    public void constructor_validParams_success() {
        versionedVolunteerStorage = new VersionedVolunteerStorage(volunteerStorage);
    }

    @Test
    public void constructor_null_throwNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                versionedVolunteerStorage = new VersionedVolunteerStorage(null));
    }

    @Test
    public void saveNewState_addNewVersionedVolunteers_success() {
        int initialSize = versionedVolunteerStorage.getVersionedVolunteersSize();
        commitToVersionedVolunteerStorage();
        int updatedSize = versionedVolunteerStorage.getVersionedVolunteersSize();
        // a versionedVolunteers is added to the ArrayList of versionedVolunteers
        assertEquals(initialSize + 1, updatedSize);

        commitToVersionedVolunteerStorage();
        int updatedSizeAgain = versionedVolunteerStorage.getVersionedVolunteersSize();
        // at this point, size = 3, currentStatePointer = 2
        assertEquals(updatedSize + 1, updatedSizeAgain);

        // at this point, size = 3, currentStatePointer = 1
        try {
            versionedVolunteerStorage.undo();
        } catch (CommandException e) {
            fail();
        }

        /*
         currentStatePointer is incremented to 2. VersionedEventStorage#saveNewState will resize versionedEvents to
          contain only the first 2 elements. Then a versionedEvent will be added to the list of versionedEvents.
           Thus, size of versionedEvents becomes 3.
         */
        commitToVersionedVolunteerStorage();
        assertEquals(versionedVolunteerStorage.getCurrentStatePointer(), 2);
        assertEquals(versionedVolunteerStorage.getVersionedVolunteersSize(), 3);
    }

    @Test
    public void saveNewState_null_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> versionedVolunteerStorage.saveNewState(null));
    }

    @Test
    public void undo() {
        // First command user executes is undo
        assertThrows(CommandException.class, () -> versionedVolunteerStorage.undo());

        // A new state is saved, and it is undone, so the pointers will point at the same state
        int initialPointer = versionedVolunteerStorage.getCurrentStatePointer();
        commitToVersionedVolunteerStorage();

        try {
            versionedVolunteerStorage.undo();
        } catch (CommandException e) {
            fail();
        }

        int updatedPointer = versionedVolunteerStorage.getCurrentStatePointer();
        assertEquals(initialPointer, updatedPointer);

        // user tries to call the undo function again
        assertThrows(CommandException.class, () -> versionedVolunteerStorage.undo());

        // user saves 3 new states, and undos all of them
        commitToVersionedVolunteerStorage();
        commitToVersionedVolunteerStorage();
        commitToVersionedVolunteerStorage();

        try {
            versionedVolunteerStorage.undo();
            versionedVolunteerStorage.undo();
            versionedVolunteerStorage.undo();
        } catch (CommandException e) {
            fail();
        }

        // total of 4 Volunteers in the Volunteer history
        assertEquals(versionedVolunteerStorage.getVersionedVolunteersSize(), 4);

        // pointer now points at the initial state of Volunteers
        assertEquals(versionedVolunteerStorage.getCurrentStatePointer(), 0);

        // user tries to call the undo function again
        assertThrows(CommandException.class, () -> versionedVolunteerStorage.undo());
    }

    @Test
    public void redo() {
        // First command user executes is redo
        assertThrows(CommandException.class, () -> versionedVolunteerStorage.redo());

        // A new state is saved, it is undone, then redone. The updatedPointer will point at the
        // next state compared to the initialPointer
        int initialPointer = versionedVolunteerStorage.getCurrentStatePointer();
        commitToVersionedVolunteerStorage();

        try {
            versionedVolunteerStorage.undo();
            versionedVolunteerStorage.redo();
        } catch (CommandException e) {
            fail();
        }

        int updatedPointer = versionedVolunteerStorage.getCurrentStatePointer();
        assertEquals(initialPointer + 1, updatedPointer);

        // user tries to call the redo function again
        assertThrows(CommandException.class, () -> versionedVolunteerStorage.redo());

        // user saves 3 new states, undos all of them, and redos all
        commitToVersionedVolunteerStorage();
        commitToVersionedVolunteerStorage();
        commitToVersionedVolunteerStorage();

        try {
            versionedVolunteerStorage.undo();
            versionedVolunteerStorage.undo();
            versionedVolunteerStorage.undo();
            versionedVolunteerStorage.redo();
            versionedVolunteerStorage.redo();
            versionedVolunteerStorage.redo();
        } catch (CommandException e) {
            fail();
        }

        // total of 5 versionedVolunteers in the versionedVolunteers history, because 4 new
        // versionedVolunteers were saved
        assertEquals(versionedVolunteerStorage.getVersionedVolunteersSize(), 5);

        // pointer now points at the latest state of versionedVolunteers
        assertEquals(versionedVolunteerStorage.getCurrentStatePointer(), 4);

        // user tries to call the redo function again
        assertThrows(CommandException.class, () -> versionedVolunteerStorage.redo());
    }

    private void commitToVersionedVolunteerStorage() {
        versionedVolunteerStorage.shiftPointerForward();
        versionedVolunteerStorage.saveNewState(volunteerStorage);
    }
}
