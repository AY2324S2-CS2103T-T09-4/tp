package seedu.address.logic.commands.DeleteStudentCommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a student identified using index from the address book.
 */
public class DeleteStudentByIndexCommand extends DeleteStudentCommand {

    private final Index targetIndex;

    /**
     * Creates a DeleteStudentCommand to delete the student with the specified
     * {@code studentId}.
     */
    public DeleteStudentByIndexCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToDelete;
        personToDelete = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteStudentByIndexCommand)) {
            return false;
        }

        DeleteStudentByIndexCommand otherDeleteCommand = (DeleteStudentByIndexCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
