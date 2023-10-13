package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Volunteer;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class VolunteerCreateCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Volunteer validVolunteer = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getVolunteerStorage(), new UserPrefs());
        expectedModel.addPerson(validVolunteer);

        assertCommandSuccess(new VolunteerCreateCommand(validVolunteer), model,
                String.format(VolunteerCreateCommand.MESSAGE_SUCCESS, Messages.format(validVolunteer)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Volunteer volunteerInList = model.getVolunteerStorage().getPersonList().get(0);
        assertCommandFailure(new VolunteerCreateCommand(volunteerInList), model,
                VolunteerCreateCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
