package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.volunteercommands.VolunteerFindCommand;
import seedu.address.model.ModelManager;

public class RedoCommandTest {
    private ModelManager model = new ModelManager();
    private RedoCommand redoCommand = new RedoCommand();

    @Test
    public void messageSuccessTest() {
        assertEquals("Redo command success", RedoCommand.MESSAGE_SUCCESS);
    }
    @Test
    public void execute() {
        // because you can't execute a redo command without first changing the event or volunteer state
        assertThrows(CommandException.class, () -> redoCommand.execute(model));
    }

    @Test
    public void equals() {
        RedoCommand redoFirstCommand = new RedoCommand();
        RedoCommand redoSecondCommand = new RedoCommand();

        // same object -> returns true
        assertTrue(redoFirstCommand.equals(redoFirstCommand));

        // different types -> returns false
        assertFalse(redoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(redoFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(redoFirstCommand.equals(redoSecondCommand));
    }

    @Test
    public void toStringMethod() {
        RedoCommand redoCommand = new RedoCommand();
        String expected = RedoCommand.class.getCanonicalName();
        assertEquals(expected + "{}", redoCommand.toString());
    }

}
