package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteClassCommand.MESSAGE_CLASS_NOT_FOUND;
import static seedu.address.logic.commands.DeleteClassCommand.MESSAGE_DELETE_CLASS_SUCCESS;
import static seedu.address.logic.commands.DeleteClassCommand.MESSAGE_MODULE_NOT_FOUND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddClassCommand.
 */
public class DeleteClassCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_success() {
        Model actualModel = new ModelManager();
        Model expectedModel = new ModelManager();
        ModuleCode module = new ModuleCode(VALID_MODULE_AMY, VALID_TUTORIAL_AMY);
        actualModel.addModule(module);
        expectedModel.addModule(new ModuleCode(VALID_MODULE_AMY));

        assertCommandSuccess(new DeleteClassCommand(new ModuleCode(VALID_MODULE_AMY),
                        new TutorialClass(VALID_TUTORIAL_AMY)), actualModel,
                String.format(MESSAGE_DELETE_CLASS_SUCCESS, VALID_MODULE_AMY, VALID_TUTORIAL_AMY), expectedModel);

        ModuleCode moduleFromList = actualModel.findModuleFromList(module);
        assertEquals(moduleFromList.getTutorialClasses().size(), 0);
    }

    @Test
    public void execute_moduleNotFound_fail() {
        ModuleCode module = new ModuleCode(VALID_MODULE_AMY, VALID_TUTORIAL_AMY);
        model.addModule(module);

        assertCommandFailure(new DeleteClassCommand(new ModuleCode(VALID_MODULE_BOB),
                        new TutorialClass(VALID_TUTORIAL_AMY)), model,
                String.format(MESSAGE_MODULE_NOT_FOUND, VALID_MODULE_BOB));
    }

    @Test
    public void execute_tutorialNotFound_fail() {
        ModuleCode module = new ModuleCode(VALID_MODULE_AMY, VALID_TUTORIAL_AMY);
        model.addModule(module);

        String errorMessage = String.format(MESSAGE_CLASS_NOT_FOUND, VALID_MODULE_AMY, VALID_TUTORIAL_BOB);
        String listOfTutorials = module.listTutorialClasses();
        String expectedMessage = errorMessage + "\n" + listOfTutorials;
        assertCommandFailure(new DeleteClassCommand(new ModuleCode(VALID_MODULE_AMY),
                        new TutorialClass(VALID_TUTORIAL_BOB)), model, expectedMessage);
    }

    @Test
    public void equals() {
        final DeleteClassCommand standardCommand = new DeleteClassCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY));

        // same values -> returns true
        DeleteClassCommand commandWithSameValues = new DeleteClassCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different module code -> returns false
        assertFalse(standardCommand.equals(new DeleteClassCommand(new ModuleCode(VALID_MODULE_BOB),
                new TutorialClass(VALID_TUTORIAL_AMY))));

        // different tutorial class -> returns true
        assertTrue(standardCommand.equals(new DeleteClassCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_BOB))));
    }
}

