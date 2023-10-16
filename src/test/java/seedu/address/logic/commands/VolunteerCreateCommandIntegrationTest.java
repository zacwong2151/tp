package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.volunteercommands.VolunteerCreateCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.VolunteerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class VolunteerCreateCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Volunteer validVolunteer = new VolunteerBuilder().build();

        Model expectedModel = new ModelManager(model.getEventStorage(), model.getVolunteerStorage(), new UserPrefs());
        expectedModel.addVolunteer(validVolunteer);

        assertCommandSuccess(new VolunteerCreateCommand(validVolunteer), model,
                String.format(VolunteerCreateCommand.MESSAGE_SUCCESS, Messages.format(validVolunteer)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Volunteer volunteerInList = model.getVolunteerStorage().getVolunteerList().get(0);
        assertCommandFailure(new VolunteerCreateCommand(volunteerInList), model,
                VolunteerCreateCommand.MESSAGE_DUPLICATE_VOLUNTEER);
    }

}
