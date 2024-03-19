package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentByEmailCommand;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentByIdCommand;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code DeleteStudentCommand}.
 */
public class DeleteStudentCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_deleteStudentById_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // First add student to the list
        Person validPerson = new PersonBuilder().build();
        expectedModel.addPerson(validPerson);

        // Attempt to delete the student
        assertCommandSuccess(new DeleteStudentByIdCommand(validPerson.getStudentId()), expectedModel,
                String.format(DeleteStudentCommand.MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_deleteStudentByEmail_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // First add student to the list
        Person validPerson = new PersonBuilder().build();
        expectedModel.addPerson(validPerson);

        // Attempt to delete the student
        assertCommandSuccess(new DeleteStudentByEmailCommand(validPerson.getEmail()), expectedModel,
                String.format(DeleteStudentCommand.MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_deleteStudentWithInvalidId_throwsCommandException() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // First add student to the list
        Person validPerson = new PersonBuilder().build();
        expectedModel.addPerson(validPerson);

        StudentId invalidId = new StudentId("A0123456X");

        // Attempt to delete the student
        assertCommandFailure(new DeleteStudentByIdCommand(invalidId), expectedModel,
                String.format(DeleteStudentByIdCommand.MESSAGE_PERSON_STUDENT_ID_NOT_FOUND, "A0123456X"));
    }

}