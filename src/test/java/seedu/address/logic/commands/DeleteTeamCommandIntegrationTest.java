package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.messages.ModuleMessages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code DeleteTeamCommand}.
 */
public class DeleteTeamCommandIntegrationTest {

    private Model model;
    private ModuleCode testModule;
    private TutorialClass testTutorial;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ModuleCode newModule = new ModuleCode(VALID_MODULE_AMY);
        model.addModule(newModule);
        this.testModule = newModule;
        TutorialClass newTutorialClass = new TutorialClass(VALID_TUTORIAL_AMY);
        newModule.addTutorialClass(newTutorialClass);
        TutorialTeam newTeam = new TutorialTeam(VALID_TEAM_NAME_AMY);
        newTutorialClass.addTeam(newTeam);
        this.testTutorial = newTutorialClass;
    }

    @Test
    public void execute_deleteTeam_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(new DeleteTeamCommand(testModule, testTutorial, VALID_TEAM_NAME_AMY),
                model,
                String.format(DeleteTeamCommand.MESSAGE_DELETE_TEAM_SUCCESS, VALID_TEAM_NAME_AMY,
                        testModule, testTutorial),
                expectedModel);
    }

    @Test
    public void execute_deleteTeamWithInvalidModule_throwsCommandException() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        ModuleCode wrongModule = new ModuleCode(VALID_MODULE_BOB);
        assertCommandFailure(new DeleteTeamCommand(wrongModule, testTutorial, VALID_TEAM_NAME_AMY),
                expectedModel,
                String.format(ModuleMessages.MESSAGE_MODULE_NOT_FOUND, wrongModule));
    }

    @Test
    public void execute_deleteTeamWithInvalidTutorial_throwsCommandException() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        TutorialClass wrongTutorial = new TutorialClass(VALID_TUTORIAL_BOB);
        assertCommandFailure(new DeleteTeamCommand(testModule, wrongTutorial, VALID_TEAM_NAME_AMY),
                expectedModel,
                String.format(ModuleMessages.MESSAGE_TUTORIAL_DOES_NOT_BELONG_TO_MODULE, wrongTutorial, testModule));
    }
}
