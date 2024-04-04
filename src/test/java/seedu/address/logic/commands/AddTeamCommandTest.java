package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AddTeamCommand.MESSAGE_ADD_TEAM_SUCCESS_WITHOUT_SIZE;
import static seedu.address.logic.commands.AddTeamCommand.MESSAGE_ADD_TEAM_SUCCESS_WITH_SIZE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_SIZE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddTeamCommand}.
 */
public class AddTeamCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private TutorialClass tutorialClass;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ModuleCode module = new ModuleCode(VALID_MODULE_AMY);
        model.addModule(module);
        TutorialClass tutorialClass = new TutorialClass(VALID_TUTORIAL_AMY);
        module.addTutorialClass(tutorialClass);
        this.tutorialClass = tutorialClass;
    }

    @Test
    public void execute_addTeamWithSizesuccess() {
        assertCommandSuccess(new AddTeamCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY), VALID_TEAM_NAME_AMY, VALID_TEAM_SIZE), model,
                String.format(MESSAGE_ADD_TEAM_SUCCESS_WITH_SIZE, VALID_TEAM_NAME_AMY, VALID_TEAM_SIZE,
                        VALID_MODULE_AMY, VALID_TUTORIAL_AMY),
                model);
    }

    @Test
    public void execute_addTeamWithoutSizesuccess() {

        assertCommandSuccess(new AddTeamCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY), VALID_TEAM_NAME_AMY), model,
                String.format(MESSAGE_ADD_TEAM_SUCCESS_WITHOUT_SIZE, VALID_TEAM_NAME_AMY, VALID_MODULE_AMY,
                        VALID_TUTORIAL_AMY),
                model);
    }

    @Test
    public void execute_duplicateTeam_fail() {
        TutorialTeam team = new TutorialTeam(VALID_TEAM_NAME_AMY, VALID_TEAM_SIZE);
        tutorialClass.addTeam(team);

        // Create a new model and add the module and tutorial class with the existing team
        Model model = new ModelManager();
        ModuleCode module = new ModuleCode(VALID_MODULE_AMY);
        TutorialClass tutorialClass = new TutorialClass(VALID_TUTORIAL_AMY);
        module.addTutorialClass(tutorialClass);
        tutorialClass.addTeam(team);

        // Create and execute the AddTeamCommand
        AddTeamCommand addTeamCommand = new AddTeamCommand(new ModuleCode(VALID_MODULE_AMY),
            new TutorialClass(VALID_TUTORIAL_AMY), VALID_TEAM_NAME_AMY);

        assertThrows(CommandException.class, () -> addTeamCommand.execute(model));
    }

    @Test
    public void equals() {
        final AddTeamCommand standardCommand = new AddTeamCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY), VALID_TEAM_NAME_AMY, VALID_TEAM_SIZE);

        // same values -> returns true
        AddTeamCommand commandWithSameValues = new AddTeamCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY), VALID_TEAM_NAME_AMY, VALID_TEAM_SIZE);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different team name -> returns false
        assertFalse(standardCommand.equals(new AddTeamCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY), VALID_TEAM_NAME_BOB, VALID_TEAM_SIZE)));
    }
}
