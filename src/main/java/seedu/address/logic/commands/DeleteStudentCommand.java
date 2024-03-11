package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Deletes a student identified using either the index, student ID or email from
 * the address book.
 */
public class DeleteStudentCommand extends Command {

    public static final String COMMAND_WORD = "delete_student";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the student ID or email used in the displayed students list.\n"
            + "Parameters: IDENTIFIER (must be a valid student ID or a valid email address)\n"
            + "Example: " + COMMAND_WORD + " email/johndoe@gmail.com";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Student: %1$s";

    public static final String MESSAGE_MISSING_IDENTIFIER = "Missing identifier. "
            + "Please provide either a student ID or an email address.";

    public static final String MESSAGE_PERSON_STUDENT_ID_NOT_FOUND = "The student with student ID %s "
            + "does not exist in the address book";
    public static final String MESSAGE_PERSON_EMAIL_NOT_FOUND = "The student with email %s "
            + "does not exist in the address book";

    private final StudentId studentId;
    private final Email email;
    private final Index index;

    /**
     * Creates a DeleteStudentCommand to delete the student with the specified
     * {@code studentId}.
     */
    public DeleteStudentCommand(StudentId studentId) {
        this.studentId = studentId;
        this.email = null;
        this.index = null;
    }

    /**
     * Creates a DeleteStudentCommand to delete the student with the specified
     * {@code email}.
     */
    public DeleteStudentCommand(Email email) {
        this.studentId = null;
        this.email = email;
        this.index = null;
    }

    /**
     * Creates a DeleteStudentCommand to delete the student at the specified
     * {@code index}.
     */
    public DeleteStudentCommand(Index index) {
        this.index = index;
        this.studentId = null;
        this.email = null;
    }

    private boolean isDeleteByStudentId() {
        return studentId != null;
    }

    private boolean isDeleteByEmail() {
        return email != null;
    }

    private boolean isDeleteByIndex() {
        return index != null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToDelete;
        if (isDeleteByIndex()) {
            personToDelete = model.getFilteredPersonList().get(index.getZeroBased());
        } else if (isDeleteByStudentId()) {
            personToDelete = model.searchPersonByPredicate(person -> person.getStudentId().equals(studentId));
            if (personToDelete == null) {
                throw new CommandException(String.format(MESSAGE_PERSON_STUDENT_ID_NOT_FOUND, studentId));
            }
        } else if (isDeleteByEmail()) {
            personToDelete = model.searchPersonByPredicate(person -> person.getEmail().equals(email));
            if (personToDelete == null) {
                throw new CommandException(String.format(MESSAGE_PERSON_EMAIL_NOT_FOUND, email));
            }
        } else {
            throw new CommandException(MESSAGE_MISSING_IDENTIFIER);
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
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteStudentCommand otherDeleteCommand = (DeleteStudentCommand) other;
        return studentId.equals(otherDeleteCommand.studentId) || email.equals(otherDeleteCommand.email);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("studentId", studentId)
                .add("email", email)
                .toString();
    }
}
