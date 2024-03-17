package seedu.address.logic.commands.deletestudentcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Deletes a student identified using student ID from the address book.
 */
public class DeleteStudentByIdCommand extends DeleteStudentCommand {

    public static final String MESSAGE_PERSON_STUDENT_ID_NOT_FOUND = "The student with student ID %s "
            + "does not exist in the address book";

    private final StudentId studentId;

    /**
     * Creates a DeleteStudentCommand to delete the student with the specified
     * {@code studentId}.
     */
    public DeleteStudentByIdCommand(StudentId studentId) {
        this.studentId = studentId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToDelete;
        personToDelete = model.searchPersonByPredicate(person -> person.getStudentId().equals(studentId));
        if (personToDelete == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_STUDENT_ID_NOT_FOUND, studentId));
        }

        model.deletePerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteStudentByIdCommand)) {
            return false;
        }
        DeleteStudentByIdCommand otherDeleteCommand = (DeleteStudentByIdCommand) other;
        return studentId.equals(otherDeleteCommand.studentId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .toString();
    }
}
