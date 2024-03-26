package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import seedu.address.logic.commands.SortStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortStudentCommand object
 */
public class SortStudentCommandParser implements Parser<SortStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortStudentCommand
     * and returns a SortStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortStudentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT_BY);

        boolean isSortByPresent = argMultimap.getValue(PREFIX_SORT_BY).isPresent();

        if (!isSortByPresent || !argMultimap.getPreamble().isEmpty()
                || argMultimap.getValue(PREFIX_SORT_BY).get().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortStudentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT_BY);

        return new SortStudentCommand(argMultimap.getValue(PREFIX_SORT_BY).get().toLowerCase());
    }

}
