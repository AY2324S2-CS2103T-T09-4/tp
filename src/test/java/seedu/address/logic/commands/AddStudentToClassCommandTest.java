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
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassByEmailCommand;
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassByIdCommand;
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassByIndexCommand;
import seedu.address.logic.messages.PersonMessages;
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
 * {@code AddStudentToClassCommand}.
 */
public class AddStudentToClassCommandTest {

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
        AddStudentToClassByEmailCommand addStudentToClassByEmailCommand = new AddStudentToClassByEmailCommand(
                new Email(INVALID_PERSON_EMAIL), new ModuleCode(VALID_MODULE_AMY),
                        new TutorialClass(VALID_TUTORIAL_AMY));
        AddStudentToClassByIdCommand addStudentToClassByIdCommand = new AddStudentToClassByIdCommand(
                new StudentId(INVALID_PERSON_STUDENT_ID), new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY));
        AddStudentToClassByIndexCommand addStudentToClassByIndexCommand = new AddStudentToClassByIndexCommand(
            Index.fromOneBased(1000), new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));
        assertCommandFailure(addStudentToClassByEmailCommand, model,
                String.format(PersonMessages.MESSAGE_PERSON_EMAIL_NOT_FOUND, INVALID_PERSON_EMAIL));
        assertCommandFailure(addStudentToClassByIdCommand, model,
                String.format(PersonMessages.MESSAGE_PERSON_STUDENT_ID_NOT_FOUND, INVALID_PERSON_STUDENT_ID));
        assertCommandFailure(addStudentToClassByIndexCommand, model,
                String.format(PersonMessages.MESSAGE_PERSON_INDEX_NOT_FOUND, 1000));
    }

    @Test
    public void execute_duplicateStudent_fail() {
        Person person = new PersonBuilder().build();
        model.addPerson(person);
        tutorialClass.addStudent(person);
        AddStudentToClassByEmailCommand addStudentToClassByEmailCommand = new AddStudentToClassByEmailCommand(
                person.getEmail(), new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));
        AddStudentToClassByIdCommand addStudentToClassByIdCommand = new AddStudentToClassByIdCommand(
                person.getStudentId(), new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY));
        assertCommandFailure(addStudentToClassByIdCommand, model,
                String.format(PersonMessages.MESSAGE_DUPLICATE_STUDENT_IN_CLASS, Messages.format(person),
                        tutorialClass));
        assertCommandFailure(addStudentToClassByEmailCommand, model,
                String.format(PersonMessages.MESSAGE_DUPLICATE_STUDENT_IN_CLASS, Messages.format(person),
                        tutorialClass));
    }

    @Test
    public void equals() {

        // Test index based delete command
        AddStudentToClassByIndexCommand addStudentToClassByIndexFirstCommand = new AddStudentToClassByIndexCommand(
                INDEX_FIRST_PERSON, new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));
        AddStudentToClassByIndexCommand addStudentToClassByIndexSecondCommand = new AddStudentToClassByIndexCommand(
                INDEX_SECOND_PERSON, new ModuleCode(VALID_MODULE_BOB), new TutorialClass(VALID_TUTORIAL_BOB));
        // same object -> returns true
        assertTrue(addStudentToClassByIndexFirstCommand.equals(addStudentToClassByIndexFirstCommand));
        // different types -> returns false
        assertFalse(addStudentToClassByIndexFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addStudentToClassByIndexFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(addStudentToClassByIndexFirstCommand.equals(addStudentToClassByIndexSecondCommand));

        // Test email based delete command
        AddStudentToClassByEmailCommand addStudentToClassByEmailFirstCommand = new AddStudentToClassByEmailCommand(
                new Email(VALID_EMAIL_AMY), new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));
        AddStudentToClassByEmailCommand addStudentToClassByEmailSecondCommand = new AddStudentToClassByEmailCommand(
                new Email(VALID_EMAIL_BOB), new ModuleCode(VALID_MODULE_BOB), new TutorialClass(VALID_TUTORIAL_BOB));

        // same object -> returns true
        assertTrue(addStudentToClassByEmailFirstCommand.equals(addStudentToClassByEmailFirstCommand));
        // different types -> returns false
        assertFalse(addStudentToClassByEmailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addStudentToClassByEmailFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(addStudentToClassByEmailFirstCommand.equals(addStudentToClassByEmailSecondCommand));

        // Test student id based delete command
        AddStudentToClassByIdCommand addStudentToClassByIdFirstCommand = new AddStudentToClassByIdCommand(
                new StudentId(VALID_STUDENT_ID_AMY), new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY));
        AddStudentToClassByIdCommand addStudentToClassByIdSecondCommand = new AddStudentToClassByIdCommand(
                new StudentId(VALID_STUDENT_ID_BOB), new ModuleCode(VALID_MODULE_BOB),
                new TutorialClass(VALID_TUTORIAL_BOB));

        // same object -> returns true
        assertTrue(addStudentToClassByIdFirstCommand.equals(addStudentToClassByIdFirstCommand));
        // different types -> returns false
        assertFalse(addStudentToClassByIdFirstCommand.equals(1));
        // null -> returns false
        assertFalse(addStudentToClassByIdFirstCommand.equals(null));
        // different person -> returns false
        assertFalse(addStudentToClassByIdFirstCommand.equals(addStudentToClassByIdSecondCommand));

    }
}
