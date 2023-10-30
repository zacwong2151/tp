package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UndoCommandTest {
    // I rly dk what to write for this test and RedoCommandTest... HAHAHA
    @Test
    public void messageSuccessTest() {
        assertEquals("Undo command success", UndoCommand.MESSAGE_SUCCESS);
    }
}
