package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.PersonMessages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a Person object to TAHelper.
 */
public class AddStudentCommand extends Command {

    public static final String COMMAND_WORD = "/add_student";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to TAHelper.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_STUDENTID + "STUDENT ID "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Zack "
            + PREFIX_STUDENTID + "A1234567Z "
            + PREFIX_EMAIL + "Zack@example.com "
            + PREFIX_TAG + "Student ";

    public static final String MESSAGE_ADD_STUDENT_SUCCESS = "New person added: %1$s";


    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddStudentCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(String.format(PersonMessages.MESSAGE_DUPLICATE_PERSON, Messages.format(toAdd)));
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_ADD_STUDENT_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddStudentCommand)) {
            return false;
        }

        AddStudentCommand otherAddCommand = (AddStudentCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
