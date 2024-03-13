package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AddClassCommand.MESSAGE_ADD_REMARK_SUCCESS;
import static seedu.address.logic.commands.AddClassCommand.MESSAGE_DUPLICATE_CLASS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddClassCommand.
 */
public class AddClassCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_success() {

        assertCommandSuccess(new AddClassCommand(new ModuleCode(VALID_MODULE_AMY),
                        VALID_TUTORIAL_AMY), model,
                String.format(MESSAGE_ADD_REMARK_SUCCESS, VALID_MODULE_AMY, VALID_TUTORIAL_AMY), model);
    }

    @Test
    public void execute_duplicateModuleAndClass_fail() {
        ModuleCode module = new ModuleCode(VALID_MODULE_AMY, VALID_TUTORIAL_AMY);
        model.addModule(module);

        assertCommandFailure(new AddClassCommand(new ModuleCode(VALID_MODULE_AMY),
                        VALID_TUTORIAL_AMY), model,
                String.format(MESSAGE_DUPLICATE_CLASS, VALID_MODULE_AMY, VALID_TUTORIAL_AMY));
    }

    @Test
    public void execute_duplicateModuleDifferentTutorial_success() {
        Model actualModel = new ModelManager();
        Model expectedModel = new ModelManager();

        ModuleCode module = new ModuleCode(VALID_MODULE_AMY, VALID_TUTORIAL_AMY);
        actualModel.addModule(module);

        ArrayList<TutorialClass> classes = new ArrayList<>();
        classes.add(new TutorialClass(VALID_TUTORIAL_AMY));
        classes.add(new TutorialClass(VALID_TUTORIAL_BOB));

        ModuleCode modifiedModule = new ModuleCode(VALID_MODULE_AMY, classes);
        expectedModel.addModule(modifiedModule);

        assertCommandSuccess(new AddClassCommand(new ModuleCode(VALID_MODULE_AMY),
                        VALID_TUTORIAL_BOB), actualModel,
                String.format(MESSAGE_ADD_REMARK_SUCCESS, VALID_MODULE_AMY, VALID_TUTORIAL_BOB), expectedModel);

        ModuleCode moduleFromList = actualModel.findModuleFromList(module);
        assertEquals(moduleFromList.getTutorialClasses().get(1).toString(), VALID_TUTORIAL_BOB);
    }

    @Test
    public void equals() {
        final AddClassCommand standardCommand = new AddClassCommand(new ModuleCode(VALID_MODULE_AMY),
                VALID_TUTORIAL_AMY);

        // same values -> returns true
        AddClassCommand commandWithSameValues = new AddClassCommand(new ModuleCode(VALID_MODULE_AMY),
                VALID_TUTORIAL_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different module code -> returns false
        assertFalse(standardCommand.equals(new AddClassCommand(new ModuleCode(VALID_MODULE_BOB),
                VALID_TUTORIAL_AMY)));

        // different tutorial class -> returns true
        assertTrue(standardCommand.equals(new AddClassCommand(new ModuleCode(VALID_MODULE_AMY),
                VALID_TUTORIAL_BOB)));
    }
}

