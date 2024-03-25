package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteStudentCommand}.
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

    @Test
    public void deleteStudentFromTutorialClasses_success() {
        //Create modules, tutorial classes and student objects
        ModuleCode cs2103T = new ModuleCode("CS2103T");
        ModuleCode cs2101 = new ModuleCode("CS2101");
        TutorialClass t01 = new TutorialClass("T01");
        TutorialClass t02 = new TutorialClass("T02");
        TutorialClass t03 = new TutorialClass("T03");
        TutorialClass t04 = new TutorialClass("T04");

        Person studentToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person student2 = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        //assign students to tutorials and tutorials to modules
        t01.addStudent(studentToDelete);
        t02.addStudent(student2);
        t03.addStudent(studentToDelete);
        t03.addStudent(student2);

        cs2103T.addTutorialClass(t01); //should have 0 people upon deletion
        cs2103T.addTutorialClass(t02); //should have 1 person upon deletion
        cs2101.addTutorialClass(t03); //should have 1 person upon deletion
        cs2101.addTutorialClass(t04); //should have 0 people upon deletion

        model.addModule(cs2103T);
        model.addModule(cs2101);

        DeleteStudentCommand deleteCommand = new DeleteStudentByIndexCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(studentToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(studentToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
        assertEquals(0, t01.getStudents().size());
        assertEquals(1, t02.getStudents().size());
        assertEquals(1, t03.getStudents().size());
        assertEquals(0, t04.getStudents().size());
        assertEquals(student2, t02.getStudents().get(0));
        assertEquals(student2, t03.getStudents().get(0));

    }
}
