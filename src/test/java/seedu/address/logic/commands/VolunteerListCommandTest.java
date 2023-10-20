package seedu.address.logic.commands;


import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showVolunteerAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventStorage;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VOLUNTEER_OR_EVENT;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerStorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.volunteercommands.VolunteerListCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class VolunteerListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEventStorage(), getTypicalVolunteerStorage(), new UserPrefs());
        expectedModel = new ModelManager(model.getEventStorage(), model.getVolunteerStorage(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new VolunteerListCommand(), model, VolunteerListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showVolunteerAtIndex(model, INDEX_FIRST_VOLUNTEER_OR_EVENT);
        assertCommandSuccess(new VolunteerListCommand(), model, VolunteerListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
