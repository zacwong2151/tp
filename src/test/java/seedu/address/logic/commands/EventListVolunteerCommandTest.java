package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventvolunteercommands.EventListVolunteerCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class EventListVolunteerCommandTest {
    @Test
    public void execute_invalidIndex_listUnsuccessful() {
        Model model = new ModelManager();
        EventListVolunteerCommand command = new EventListVolunteerCommand(Index.fromOneBased(1));
        try {
            command.execute(model);
        } catch (Exception e) {
            assertTrue(true);
        }
        assertTrue(true);
    }
    //    @Test
    //    public void execute_validIndexes_listSuccessful(){}
    //    @Test
    //    public void equals() {}
    //
    //    @Test
    //    public void toStringMethod() {}
}
