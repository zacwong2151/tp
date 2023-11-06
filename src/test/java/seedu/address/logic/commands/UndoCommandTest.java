package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;

public class UndoCommandTest {
    private ModelManager model = new ModelManager();
    private UndoCommand undoCommand = new UndoCommand();
    @Test
    public void messageSuccessTest() {
        assertEquals("Undo command success", UndoCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void execute() {
        // because you can't execute an undo command without first changing the event or volunteer state
        assertThrows(CommandException.class, () -> undoCommand.execute(model));
    }
    @Test
    public void equals() {
        UndoCommand undoFirstCommand = new UndoCommand();
        UndoCommand undoSecondCommand = new UndoCommand();

        // same object -> returns true
        assertTrue(undoFirstCommand.equals(undoFirstCommand));

        // different types -> returns false
        assertFalse(undoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(undoFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(undoFirstCommand.equals(undoSecondCommand));
    }

    @Test
    public void toStringMethod() {
        UndoCommand undoCommand = new UndoCommand();
        String expected = UndoCommand.class.getCanonicalName();
        assertEquals(expected + "{}", undoCommand.toString());
    }

}
