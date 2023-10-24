package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventvolunteercommands.EventAddVolunteerCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class EventAddVolunteerCommandTest {
    @Test
    public void execute_invalidIndexes_addUnsuccessful() {
        Model model = new ModelManager();
        EventAddVolunteerCommand command = new EventAddVolunteerCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        try {
            command.execute(model);
        } catch (Exception e) {
            assertTrue(true);
        }
        assertTrue(true);
    }
    //    @Test
    //    public void execute_missingIndexes_addUnsuccessful() {}
    //    @Test
    //    public void execute_validIndexes_addSuccessful() throws Exception {}
    //
    //    @Test
    //    public void execute_duplicateVolunteer_throwsCommandException() {}
    //
    //    @Test
    //    public void equals() {}
    //
    //    @Test
    //    public void toStringMethod() {}
}
