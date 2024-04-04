package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME_NEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamByEmailCommand;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamByIdCommand;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamByIndexCommand;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamCommand;
import seedu.address.logic.messages.TeamMessages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteStudentFromTeamCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private TutorialClass tutorialClass;
    private Person validPerson;
    private Person validOtherPerson;
    private ModuleCode newModule;
    private TutorialTeam newTeam;
    private TutorialTeam tutTeam;

    @BeforeEach
    public void setUp() {
        validPerson = new PersonBuilder(AMY).build();
        validOtherPerson = new PersonBuilder(ALICE)
                .withStudentId(VALID_STUDENT_ID_BOB).withEmail(VALID_EMAIL_BOB).build();
        newModule = new ModuleCode(VALID_MODULE_AMY);
        model.addModule(newModule);
        model.addPerson(validPerson);
        model.addPerson(validOtherPerson);
        TutorialClass newTutorialClass = new TutorialClass(VALID_TUTORIAL_AMY);
        newModule.addTutorialClass(newTutorialClass);
        tutorialClass = newTutorialClass;
        newTeam = new TutorialTeam(VALID_TEAM_NAME, 1);
        tutTeam = new TutorialTeam(VALID_TEAM_NAME_BOB, 3);
        tutorialClass.addTeam(tutTeam);
        tutorialClass.addTeam(newTeam);
    }

    @Test
    public void invalidDeletionFromTeam_indexNotInSystem_failure() {
        Index index = Index.fromOneBased(1000);
        DeleteStudentFromTeamByIndexCommand deleteStudentFromTeamByIndexCommand = new
                DeleteStudentFromTeamByIndexCommand(index,
                newModule, tutorialClass, newTeam);
        assertCommandFailure(deleteStudentFromTeamByIndexCommand, model,
                String.format(TeamMessages.MESSAGE_PERSON_INDEX_NOT_FOUND,
                        index.getOneBased(), newTeam));
    }

    @Test
    public void execute_studentDoesNotExist_fail() {
        DeleteStudentFromTeamByEmailCommand deleteStudentFromTeamByEmailCommand =
                new DeleteStudentFromTeamByEmailCommand(validPerson.getEmail(),
                        newModule, tutorialClass, newTeam);

        DeleteStudentFromTeamByIdCommand deleteStudentFromTeamByIdCommand =
                new DeleteStudentFromTeamByIdCommand(validPerson.getStudentId(),
                        newModule, tutorialClass, newTeam);


        assertCommandFailure(deleteStudentFromTeamByIdCommand, model,
                String.format(TeamMessages.MESSAGE_STUDENT_NOT_FOUND_IN_TEAM, Messages.format(validPerson),
                        tutorialClass));

        assertCommandFailure(deleteStudentFromTeamByEmailCommand, model,
                String.format(TeamMessages.MESSAGE_STUDENT_NOT_FOUND_IN_TEAM, Messages.format(validPerson),
                        tutorialClass));
    }
    @Test
    public void invalidDeletionFromTeam_tutorialTeamNotExist_failure() {
        TutorialTeam team = new TutorialTeam(VALID_TEAM_NAME_NEW);

        DeleteStudentFromTeamByIdCommand deleteStudentFromTeamByIdCommand = new
                DeleteStudentFromTeamByIdCommand(validPerson.getStudentId(), newModule, tutorialClass,
                team);
        DeleteStudentFromTeamByEmailCommand deleteStudentFromTeamByEmailCommand = new
                DeleteStudentFromTeamByEmailCommand(validPerson.getEmail(), newModule, tutorialClass,
                team);
        assertCommandFailure(deleteStudentFromTeamByIdCommand, model,
                String.format(TeamMessages.MESSAGE_TEAM_DOES_NOT_EXIST, team, tutorialClass));
        assertCommandFailure(deleteStudentFromTeamByEmailCommand, model,
                String.format(TeamMessages.MESSAGE_TEAM_DOES_NOT_EXIST, team, tutorialClass));
    }

    @Test
    public void toString_test() {
        tutorialClass.addStudent(validPerson);
        DeleteStudentFromTeamByIdCommand deleteStudentFromTeamByIdCommand =
                new DeleteStudentFromTeamByIdCommand(validPerson.getStudentId(), newModule, tutorialClass, newTeam);
        DeleteStudentFromTeamByEmailCommand deleteOtherStudentFromTeamByEmailCommand =
                new DeleteStudentFromTeamByEmailCommand(validOtherPerson.getEmail(),
                        newModule, tutorialClass, newTeam);
        DeleteStudentFromTeamByIndexCommand allocateStudentToTeamByIndexCommand =
                new DeleteStudentFromTeamByIndexCommand(Index.fromZeroBased(0),
                        newModule, tutorialClass, newTeam);
        assertEquals(deleteOtherStudentFromTeamByEmailCommand.toString(),
                deleteOtherStudentFromTeamByEmailCommand.toString());
        assertEquals(deleteStudentFromTeamByIdCommand.toString(),
                deleteStudentFromTeamByIdCommand.toString());
        assertEquals(allocateStudentToTeamByIndexCommand.toString(),
                allocateStudentToTeamByIndexCommand.toString());

    }

    @Test
    public void execute_deleteStudentFromClassById_success() {
        int expectedTeamSizeBeforeDelete = 1;
        int expectedTeamSizeAfterDelete = 0;
        newTeam.addStudent(validPerson);
        assertEquals(expectedTeamSizeBeforeDelete, newTeam.getStudents().size());
        // Attempt to delete the student
        assertCommandSuccess(new DeleteStudentFromTeamByIdCommand(validPerson.getStudentId(),
                        newModule, tutorialClass, newTeam),
                model,
                String.format(DeleteStudentFromTeamCommand.MESSAGE_DELETE_STUDENT_FROM_TEAM_SUCCESS,
                        Messages.format(validPerson), newModule, tutorialClass, newTeam),
                model);
        assertEquals(expectedTeamSizeAfterDelete, newTeam.getStudents().size());
    }

    @Test
    public void execute_deleteStudentFromClassByEmail_success() {
        int expectedTeamSizeBeforeDelete = 1;
        int expectedTeamSizeAfterDelete = 0;
        newTeam.addStudent(validPerson);
        assertEquals(expectedTeamSizeBeforeDelete, newTeam.getStudents().size());
        // Attempt to delete the student
        assertCommandSuccess(new DeleteStudentFromTeamByEmailCommand(validPerson.getEmail(),
                        newModule, tutorialClass, newTeam),
                model,
                String.format(DeleteStudentFromTeamCommand.MESSAGE_DELETE_STUDENT_FROM_TEAM_SUCCESS,
                        Messages.format(validPerson), newModule, tutorialClass, newTeam), model);
        assertEquals(expectedTeamSizeAfterDelete, newTeam.getStudents().size());
    }

    @Test
    public void equals() {
        tutorialClass.addStudent(validPerson);
        tutorialClass.addStudent(validOtherPerson);
        // creation of 2 delete command based on 2 different student ID adding to the same team under
        // the same module and tutorial class.
        DeleteStudentFromTeamByIdCommand deleteStudentFromTeamByIdCommand =
                new DeleteStudentFromTeamByIdCommand(validPerson.getStudentId(), newModule, tutorialClass, newTeam);
        DeleteStudentFromTeamByIdCommand deleteOtherStudentFromTeamByIdCommand =
                new DeleteStudentFromTeamByIdCommand(validOtherPerson.getStudentId(),
                        newModule, tutorialClass, newTeam);

        // same object --> returns true
        assertTrue(deleteStudentFromTeamByIdCommand.equals(deleteStudentFromTeamByIdCommand));

        // different type --> returns false
        assertFalse(deleteStudentFromTeamByIdCommand.equals("hello world"));

        // null --> returns false
        assertFalse(deleteStudentFromTeamByIdCommand.equals(null));

        // allocation of a different person --> returns false
        assertFalse(deleteStudentFromTeamByIdCommand.equals(deleteOtherStudentFromTeamByIdCommand));

        // creation of 2 delete command based on 2 different student emails adding to the same team under
        // the same module and tutorial class.
        DeleteStudentFromTeamByEmailCommand deleteStudentFromTeamByEmailCommand =
                new DeleteStudentFromTeamByEmailCommand(validPerson.getEmail(), newModule, tutorialClass, newTeam);
        DeleteStudentFromTeamByEmailCommand deleteOtherStudentFromTeamByEmailCommand =
                new DeleteStudentFromTeamByEmailCommand(validOtherPerson.getEmail(),
                        newModule, tutorialClass, newTeam);

        // same object --> returns true
        assertTrue(deleteStudentFromTeamByEmailCommand.equals(deleteStudentFromTeamByEmailCommand));

        // different type --> returns false
        assertFalse(deleteStudentFromTeamByEmailCommand.equals("hello world"));

        // null --> returns false
        assertFalse(deleteStudentFromTeamByEmailCommand.equals(null));

        // allocation of a different person --> returns false
        assertFalse(deleteStudentFromTeamByEmailCommand.equals(deleteOtherStudentFromTeamByEmailCommand));


        Index testIndex = Index.fromOneBased(1);
        DeleteStudentFromTeamByIndexCommand deleteOtherStudentFromTeamByIndexCommand =
                new DeleteStudentFromTeamByIndexCommand(testIndex,
                        newModule, tutorialClass, newTeam);

        Index anotherTestIndex = Index.fromOneBased(2);
        DeleteStudentFromTeamByIndexCommand deleteAnotherStudentFromTeamByIndexCommand =
                new DeleteStudentFromTeamByIndexCommand(anotherTestIndex,
                        newModule, tutorialClass, newTeam);

        // same object
        assertTrue(deleteOtherStudentFromTeamByIndexCommand.equals(deleteOtherStudentFromTeamByIndexCommand));

        // different type --> returns false
        assertFalse(deleteOtherStudentFromTeamByIndexCommand.equals("hello world"));

        // null --> returns false
        assertFalse(deleteOtherStudentFromTeamByIndexCommand.equals(null));

        //different index --> returns false
        assertFalse(deleteOtherStudentFromTeamByIndexCommand.equals(deleteAnotherStudentFromTeamByIndexCommand));
    }
}
