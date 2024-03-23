package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_SIZE;
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

/**
 * Contains integration tests (interaction with the Model) for
 * {@code AddTeamCommand}.
 */
public class AddTeamCommandIntegrationTest {

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
        this.testTutorial = newTutorialClass;
    }

    @Test
    public void execute_addTeamWithNoSize_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(new AddTeamCommand(testModule, testTutorial, VALID_TEAM_NAME_AMY),
                model,
                String.format(AddTeamCommand.MESSAGE_ADD_TEAM_SUCCESS_WITHOUT_SIZE, VALID_TEAM_NAME_AMY,
                        testModule, testTutorial),
                expectedModel);
    }

    @Test
    public void execute_addTeamWithSize_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(new AddTeamCommand(testModule, testTutorial, VALID_TEAM_NAME_AMY, VALID_TEAM_SIZE),
                model,
                String.format(AddTeamCommand.MESSAGE_ADD_TEAM_SUCCESS_WITH_SIZE, VALID_TEAM_NAME_AMY, VALID_TEAM_SIZE,
                        testModule, testTutorial),
                expectedModel);
    }

    @Test
    public void execute_addTeamToInvalidModule_throwsCommandException() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        ModuleCode invalidModule = new ModuleCode(VALID_MODULE_BOB);
        assertCommandFailure(new AddTeamCommand(invalidModule, testTutorial, VALID_TEAM_NAME_AMY),
                expectedModel,
                String.format(ModuleMessages.MESSAGE_MODULE_NOT_FOUND, invalidModule));
    }

    @Test
    public void execute_addTeamToInvalidTutorial_throwsCommandException() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        TutorialClass invalidTutorial = new TutorialClass(VALID_TUTORIAL_BOB);
        assertCommandFailure(new AddTeamCommand(testModule, invalidTutorial, VALID_TEAM_NAME_AMY),
                expectedModel,
                String.format(ModuleMessages.MESSAGE_TUTORIAL_DOES_NOT_BELONG_TO_MODULE, invalidTutorial, testModule));
    }
}
