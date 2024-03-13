package seedu.address.logic.commands.DeleteStudentCommand;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Abstract class for DeleteStudentCommand 
 */
public abstract class DeleteStudentCommand extends Command {

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


    public DeleteStudentCommand() {

    }

    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract boolean equals(Object other);

    public abstract String toString();
}
