package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.messages.ModuleMessages.MESSAGE_MODULE_NOT_FOUND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code DeleteModuleCommand}.
 */
public class DeleteModuleCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_success() {
        Model actualModel = new ModelManager();
        Model expectedModel = new ModelManager();
        ModuleCode module = new ModuleCode(VALID_MODULE_AMY);
        actualModel.addModule(module);

        assertCommandSuccess(new DeleteModuleCommand(new ModuleCode(VALID_MODULE_AMY)), actualModel,
                String.format(DeleteModuleCommand.MESSAGE_DELETE_MODULE_SUCCESS, VALID_MODULE_AMY), expectedModel);

        assertEquals(actualModel.getFilteredModuleList().size(), 0);
    }

    @Test
    public void execute_moduleNotFound_fail() {
        ModuleCode module = new ModuleCode(VALID_MODULE_AMY);
        model.addModule(module);

        assertCommandFailure(new DeleteModuleCommand(new ModuleCode(VALID_MODULE_BOB)), model,
                String.format(MESSAGE_MODULE_NOT_FOUND, VALID_MODULE_BOB));
    }

    @Test
    public void equals() {
        final DeleteModuleCommand standardCommand = new DeleteModuleCommand(new ModuleCode(VALID_MODULE_AMY));

        // same values -> returns true
        DeleteModuleCommand commandWithSameValues = new DeleteModuleCommand(new ModuleCode(VALID_MODULE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different module code -> returns false
        assertFalse(standardCommand.equals(new DeleteModuleCommand(new ModuleCode(VALID_MODULE_BOB))));

        // different tutorial class -> returns true
        assertTrue(standardCommand.equals(new DeleteModuleCommand(new ModuleCode(VALID_MODULE_AMY))));
    }
}

