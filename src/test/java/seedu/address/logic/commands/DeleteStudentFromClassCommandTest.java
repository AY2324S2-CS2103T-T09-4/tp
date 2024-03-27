package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PERSON_EMAIL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PERSON_STUDENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.deletestudentfromclasscommands.DeleteStudentFromClassByEmailCommand;
import seedu.address.logic.commands.deletestudentfromclasscommands.DeleteStudentFromClassByIdCommand;
import seedu.address.logic.commands.deletestudentfromclasscommands.DeleteStudentFromClassByIndexCommand;
import seedu.address.logic.messages.PersonMessages;
import seedu.address.logic.messages.TutorialClassMessages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteStudentFromClassCommand}.
 */
public class DeleteStudentFromClassCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private TutorialClass tutorialClass;

    @BeforeEach
    public void setUp() {
        ModuleCode newModule = new ModuleCode(VALID_MODULE_AMY);
        model.addModule(newModule);
        TutorialClass newTutorialClass = new TutorialClass(VALID_TUTORIAL_AMY);
        newModule.addTutorialClass(newTutorialClass);
        tutorialClass = newTutorialClass;
    }

    @Test
    public void execute_invalidStudent_fail() {
        DeleteStudentFromClassByEmailCommand deleteStudentFromClassByEmailCommand =
                new DeleteStudentFromClassByEmailCommand(new Email(INVALID_PERSON_EMAIL),
                new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));

        DeleteStudentFromClassByIdCommand deleteStudentFromClassByIdCommand =
                new DeleteStudentFromClassByIdCommand(new StudentId(INVALID_PERSON_STUDENT_ID),
                new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));

        DeleteStudentFromClassByIndexCommand deleteStudentFromClassByIndexCommand =
                new DeleteStudentFromClassByIndexCommand(Index.fromOneBased(1000),
                new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));

        assertCommandFailure(deleteStudentFromClassByEmailCommand, model,
                String.format(PersonMessages.MESSAGE_PERSON_EMAIL_NOT_FOUND, INVALID_PERSON_EMAIL));

        assertCommandFailure(deleteStudentFromClassByIdCommand, model,
                String.format(PersonMessages.MESSAGE_PERSON_STUDENT_ID_NOT_FOUND, INVALID_PERSON_STUDENT_ID));

        assertCommandFailure(deleteStudentFromClassByIndexCommand, model,
                String.format(PersonMessages.MESSAGE_PERSON_INDEX_NOT_FOUND, 1000));
    }

    @Test
    public void execute_studentDoesNotExist_fail() {
        Person person = new PersonBuilder().build();
        Person otherPerson = new PersonBuilder().withName("otherPerson").build();
        model.addPerson(person);
        tutorialClass.addStudent(otherPerson);

        DeleteStudentFromClassByEmailCommand addStudentToClassByEmailCommand =
                new DeleteStudentFromClassByEmailCommand(person.getEmail(),
                new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));

        DeleteStudentFromClassByIdCommand addStudentToClassByIdCommand =
                new DeleteStudentFromClassByIdCommand(person.getStudentId(),
                new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));

        assertCommandFailure(addStudentToClassByIdCommand, model,
                String.format(TutorialClassMessages.MESSAGE_STUDENT_NOT_FOUND_IN_CLASS, Messages.format(person),
                        tutorialClass));

        assertCommandFailure(addStudentToClassByEmailCommand, model,
                String.format(TutorialClassMessages.MESSAGE_STUDENT_NOT_FOUND_IN_CLASS, Messages.format(person),
                        tutorialClass));
    }

    @Test
    public void equals() {

        // Test index based delete command
        DeleteStudentFromClassByIndexCommand deleteStudentFromClassByIndexFirstCommand =
                new DeleteStudentFromClassByIndexCommand(INDEX_FIRST_PERSON,
                new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));

        DeleteStudentFromClassByIndexCommand deleteStudentFromClassByIndexSecondCommand =
                new DeleteStudentFromClassByIndexCommand(INDEX_SECOND_PERSON,
                new ModuleCode(VALID_MODULE_BOB), new TutorialClass(VALID_TUTORIAL_BOB));

        // same object -> returns true
        assertTrue(deleteStudentFromClassByIndexFirstCommand.equals(deleteStudentFromClassByIndexFirstCommand));
        // different types -> returns false
        assertFalse(deleteStudentFromClassByIndexFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteStudentFromClassByIndexFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteStudentFromClassByIndexFirstCommand.equals(deleteStudentFromClassByIndexSecondCommand));

        // Test email based delete command
        DeleteStudentFromClassByEmailCommand deleteStudentFromClassByEmailFirstCommand =
                new DeleteStudentFromClassByEmailCommand(new Email(VALID_EMAIL_AMY),
                new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));

        DeleteStudentFromClassByEmailCommand deleteStudentFromClassByEmailSecondCommand =
                new DeleteStudentFromClassByEmailCommand(new Email(VALID_EMAIL_BOB),
                new ModuleCode(VALID_MODULE_BOB), new TutorialClass(VALID_TUTORIAL_BOB));

        // same object -> returns true
        assertTrue(deleteStudentFromClassByEmailFirstCommand.equals(deleteStudentFromClassByEmailFirstCommand));
        // different types -> returns false
        assertFalse(deleteStudentFromClassByEmailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteStudentFromClassByEmailFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteStudentFromClassByEmailFirstCommand.equals(deleteStudentFromClassByEmailSecondCommand));

        // Test student id based delete command
        DeleteStudentFromClassByIdCommand deleteStudentFromClassByIdFirstCommand =
                new DeleteStudentFromClassByIdCommand(new StudentId(VALID_STUDENT_ID_AMY),
                new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));

        DeleteStudentFromClassByIdCommand deleteStudentFromClassByIdSecondCommand =
                new DeleteStudentFromClassByIdCommand(new StudentId(VALID_STUDENT_ID_BOB),
                new ModuleCode(VALID_MODULE_BOB), new TutorialClass(VALID_TUTORIAL_BOB));

        // same object -> returns true
        assertTrue(deleteStudentFromClassByIdFirstCommand.equals(deleteStudentFromClassByIdFirstCommand));
        // different types -> returns false
        assertFalse(deleteStudentFromClassByIdFirstCommand.equals(1));
        // null -> returns false
        assertFalse(deleteStudentFromClassByIdFirstCommand.equals(null));
        // different person -> returns false
        assertFalse(deleteStudentFromClassByIdFirstCommand.equals(deleteStudentFromClassByIdSecondCommand));
    }
}
