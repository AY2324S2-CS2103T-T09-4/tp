package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Searches and lists all students in TAHelper whose information contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class SearchStudentCommand extends Command {

    public static final String COMMAND_WORD = "/search_student";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all students whose information contain "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Only one attribute can be matched per search query.\n"
            + "Partial matches are also displayed.\n"
            + "Parameters: [name/NAME] [id/STUDENT_ID] [email/EMAIL]\n"
            + "Example: " + COMMAND_WORD + " id/A012345A";

    private final Predicate<Person> predicate;

    public SearchStudentCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchStudentCommand)) {
            return false;
        }

        SearchStudentCommand otherSearchStudentCommand = (SearchStudentCommand) other;
        return predicate.equals(otherSearchStudentCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
