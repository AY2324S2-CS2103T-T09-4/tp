package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts students information lexicographically based on given parameter
 */
public class SortStudentCommand extends Command {

    public static final String COMMAND_WORD = "/sort_student";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts students based on specified parameter.\n"
            + "Parameters: "
            + PREFIX_SORT_BY + "name " + "or "
            + PREFIX_SORT_BY + "id " + "or "
            + PREFIX_SORT_BY + "email\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SORT_BY + "name";

    public static final String MESSAGE_SORT_STUDENT_SUCCESS = "Students have been successfully sorted";
    public static final String MESSAGE_INVALID_PARAMETER = "Not a valid parameter for sorting.";
    public static final String MESSAGE_EMPTY_PARAMETER = "Please provide a parameter.";

    private final String sortBy;
    public SortStudentCommand(String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Comparator<Person> personComparator;
        switch (sortBy) {
        case "name":
            personComparator = Comparator.comparing(p -> p.getName().toString());
            break;
        case "id":
            personComparator = Comparator.comparing(p -> p.getStudentId().toString());
            break;
        case "email":
            personComparator = Comparator.comparing(p -> p.getEmail().toString());
            break;
        case "":
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        default:
            throw new CommandException(MESSAGE_INVALID_PARAMETER);
        }

        model.getSortedPersonList(personComparator);
        return new CommandResult(MESSAGE_SORT_STUDENT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortStudentCommand)) {
            return false;
        }

        SortStudentCommand otherSortStudentCommand = (SortStudentCommand) other;
        return sortBy.equals(otherSortStudentCommand.sortBy);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sortBy", sortBy)
                .toString();
    }
}
