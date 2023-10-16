package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.volunteercommands.VolunteerClearCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VolunteerStorage;

public class VolunteerClearCommandTest {

    @Test
    public void execute_emptyVolunteerStorage_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new VolunteerClearCommand(), model, VolunteerClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyVolunteerStorage_success() {
        Model model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());
        expectedModel.setVolunteerStorage(new VolunteerStorage());

        assertCommandSuccess(new VolunteerClearCommand(), model, VolunteerClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
