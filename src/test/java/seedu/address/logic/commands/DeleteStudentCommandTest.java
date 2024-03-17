package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentByEmailCommand;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentByIdCommand;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentByIndexCommand;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteStudentCommand deleteCommand = new DeleteStudentByIndexCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteStudentCommand deleteCommand = new DeleteStudentByIndexCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        // Test index based delete command
        DeleteStudentByIndexCommand deleteByIndexFirstCommand = new DeleteStudentByIndexCommand(INDEX_FIRST_PERSON);
        DeleteStudentByIndexCommand deleteByIndexSecondCommand = new DeleteStudentByIndexCommand(INDEX_SECOND_PERSON);
        // same object -> returns true
        assertTrue(deleteByIndexFirstCommand.equals(deleteByIndexFirstCommand));
        // different types -> returns false
        assertFalse(deleteByIndexFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteByIndexFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteByIndexFirstCommand.equals(deleteByIndexSecondCommand));

        // Test email based delete command
        DeleteStudentByEmailCommand deleteByEmailFirstCommand = new DeleteStudentByEmailCommand(
                new Email(VALID_EMAIL_AMY));
        DeleteStudentByEmailCommand deleteByEmailSecondCommand = new DeleteStudentByEmailCommand(
                new Email(VALID_EMAIL_BOB));
        // same object -> returns true
        assertTrue(deleteByEmailFirstCommand.equals(deleteByEmailFirstCommand));
        // different types -> returns false
        assertFalse(deleteByEmailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteByEmailFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteByEmailFirstCommand.equals(deleteByEmailSecondCommand));

        // Test student id based delete command
        DeleteStudentByIdCommand deleteByIdFirstCommand = new DeleteStudentByIdCommand(
                new StudentId(VALID_STUDENT_ID_AMY));
        DeleteStudentByIdCommand deleteByIdSecondCommand = new DeleteStudentByIdCommand(
                new StudentId(VALID_STUDENT_ID_BOB));
        // same object -> returns true
        assertTrue(deleteByIdFirstCommand.equals(deleteByIdFirstCommand));
        // different types -> returns false
        assertFalse(deleteByIdFirstCommand.equals(1));
        // null -> returns false
        assertFalse(deleteByIdFirstCommand.equals(null));
        // different person -> returns false
        assertFalse(deleteByIdFirstCommand.equals(deleteByIdSecondCommand));

    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
