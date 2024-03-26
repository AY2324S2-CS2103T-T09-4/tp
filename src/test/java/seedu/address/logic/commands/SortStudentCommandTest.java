package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortStudentCommand.MESSAGE_INVALID_PARAMETER;
import static seedu.address.logic.commands.SortStudentCommand.MESSAGE_SORT_STUDENT_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code SortStudentCommand}.
 */
public class SortStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SortStudentCommand firstCommand = new SortStudentCommand("name");
        SortStudentCommand secondCommand = new SortStudentCommand("id");

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        SortStudentCommand sortFirstCommandCopy = new SortStudentCommand("name");
        assertTrue(firstCommand.equals(sortFirstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different sortBy -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_validParameter_success() {
        String expectedMessage = MESSAGE_SORT_STUDENT_SUCCESS;

        // sort by name
        Comparator<Person> comparator = Comparator.comparing(p -> p.getName().toString());
        SortStudentCommand command = new SortStudentCommand("name");

        expectedModel.getSortedPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // sort by id
        comparator = Comparator.comparing(p -> p.getStudentId().toString());
        command = new SortStudentCommand("id");

        expectedModel.getSortedPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // sort by email
        comparator = Comparator.comparing(p -> p.getEmail().toString());
        command = new SortStudentCommand("id");

        expectedModel.getSortedPersonList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidParameters_failure() {
        // empty parameter
        SortStudentCommand command = new SortStudentCommand("");
        assertCommandFailure(command, model,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortStudentCommand.MESSAGE_USAGE));

        // invalid parameter
        command = new SortStudentCommand("test");
        assertCommandFailure(command, model, MESSAGE_INVALID_PARAMETER);

        // case-sensitive
        command = new SortStudentCommand("NAME");
        assertCommandFailure(command, model, MESSAGE_INVALID_PARAMETER);
    }

    @Test
    public void toStringMethod() {
        SortStudentCommand command = new SortStudentCommand("name");
        String expected = SortStudentCommand.class.getCanonicalName() + "{sortBy=name}";
        assertEquals(expected, command.toString());
    }
}
