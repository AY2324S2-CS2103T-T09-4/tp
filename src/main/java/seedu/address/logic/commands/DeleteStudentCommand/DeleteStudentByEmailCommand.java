package seedu.address.logic.commands.deletestudentcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;

/**
 * Deletes a student identified using email from the address book.
 */
public class DeleteStudentByEmailCommand extends DeleteStudentCommand {

    public static final String MESSAGE_PERSON_EMAIL_NOT_FOUND = "The student with email %s "
            + "does not exist in the address book";

    private final Email email;

    /**
     * Creates a DeleteStudentCommand to delete the student with the specified
     * {@code studentId}.
     */
    public DeleteStudentByEmailCommand(Email email) {
        this.email = email;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToDelete;
        personToDelete = model.searchPersonByPredicate(person -> person.getEmail().equals(email));
        if (personToDelete == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_EMAIL_NOT_FOUND, email));

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
        if (!(other instanceof DeleteStudentByEmailCommand)) {
            return false;
        }

        DeleteStudentByEmailCommand otherDeleteCommand = (DeleteStudentByEmailCommand) other;
        return email.equals(otherDeleteCommand.email);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("email", email)
                .toString();
    }
}
