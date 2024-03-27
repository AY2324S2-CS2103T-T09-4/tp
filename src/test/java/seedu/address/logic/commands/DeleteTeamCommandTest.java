package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteTeamCommand}.
 */
public class DeleteTeamCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ModuleCode moduleCodeAmy = new ModuleCode(VALID_MODULE_AMY);
        TutorialClass tutorialClassAmy = new TutorialClass(VALID_TUTORIAL_AMY);
        ModuleCode moduleCodeBob = new ModuleCode(VALID_MODULE_AMY);
        TutorialClass tutorialClassBob = new TutorialClass(VALID_TUTORIAL_BOB);

        DeleteTeamCommand deleteTeamFirstCommand = new DeleteTeamCommand(moduleCodeAmy, tutorialClassAmy,
                VALID_TEAM_NAME_AMY);

        DeleteTeamCommand deleteTeamSecondCommand = new DeleteTeamCommand(moduleCodeBob, tutorialClassBob,
                VALID_TEAM_NAME_BOB);

        // same object -> returns true
        assertTrue(deleteTeamFirstCommand.equals(deleteTeamFirstCommand));
        // different types -> returns false
        assertFalse(deleteTeamFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteTeamFirstCommand.equals(null));

        // different team -> returns false
        assertFalse(deleteTeamFirstCommand.equals(deleteTeamSecondCommand));
    }
}
