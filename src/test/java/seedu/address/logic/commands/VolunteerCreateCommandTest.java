package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.volunteerCommands.VolunteerCreateCommand;
import seedu.address.model.VolunteerStorage;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyVolunteerStorage;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.PersonBuilder;

public class VolunteerCreateCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new VolunteerCreateCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Volunteer validVolunteer = new PersonBuilder().build();

        CommandResult commandResult = new VolunteerCreateCommand(validVolunteer).execute(modelStub);

        assertEquals(String.format(VolunteerCreateCommand.MESSAGE_SUCCESS, Messages.format(validVolunteer)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validVolunteer), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Volunteer validVolunteer = new PersonBuilder().build();
        VolunteerCreateCommand volunteerCreateCommand = new VolunteerCreateCommand(validVolunteer);
        ModelStub modelStub = new ModelStubWithPerson(validVolunteer);

        assertThrows(CommandException.class, VolunteerCreateCommand.MESSAGE_DUPLICATE_PERSON, ()
                                                    -> volunteerCreateCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Volunteer alice = new PersonBuilder().withName("Alice").build();
        Volunteer bob = new PersonBuilder().withName("Bob").build();
        VolunteerCreateCommand addAliceCommand = new VolunteerCreateCommand(alice);
        VolunteerCreateCommand addBobCommand = new VolunteerCreateCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        VolunteerCreateCommand addAliceCommandCopy = new VolunteerCreateCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        VolunteerCreateCommand volunteerCreateCommand = new VolunteerCreateCommand(ALICE);
        String expected = VolunteerCreateCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, volunteerCreateCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getVolunteerStorageFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVolunteerStorageFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Volunteer volunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVolunteerStorage(ReadOnlyVolunteerStorage newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyVolunteerStorage getVolunteerStorage() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Volunteer volunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Volunteer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Volunteer target, Volunteer editedVolunteer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Volunteer> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredVolunteerList(Predicate<Volunteer> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Volunteer volunteer;

        ModelStubWithPerson(Volunteer volunteer) {
            requireNonNull(volunteer);
            this.volunteer = volunteer;
        }

        @Override
        public boolean hasPerson(Volunteer volunteer) {
            requireNonNull(volunteer);
            return this.volunteer.isSamePerson(volunteer);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Volunteer> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Volunteer volunteer) {
            requireNonNull(volunteer);
            return personsAdded.stream().anyMatch(volunteer::isSamePerson);
        }

        @Override
        public void addPerson(Volunteer volunteer) {
            requireNonNull(volunteer);
            personsAdded.add(volunteer);
        }

        @Override
        public ReadOnlyVolunteerStorage getVolunteerStorage() {
            return new VolunteerStorage();
        }
    }

}
