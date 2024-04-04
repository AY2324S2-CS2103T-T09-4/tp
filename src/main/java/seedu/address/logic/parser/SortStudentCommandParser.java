package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;

import seedu.address.logic.commands.sortstudentcommands.SortStudentCommand;
import seedu.address.logic.commands.sortstudentcommands.SortStudentParameter;
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

        String sortByParam = argMultimap.getValue(PREFIX_SORT_BY).get();
        SortStudentParameter sortStudentParameter = parseSortStudentParameter(sortByParam);
        return new SortStudentCommand(sortStudentParameter);
    }

    private SortStudentParameter parseSortStudentParameter(String param) {
        String trimmedParam = param.trim().toLowerCase();

        if (trimmedParam.equals("name")) {
            return SortStudentParameter.NAME;
        }

        if (trimmedParam.equals("id")) {
            return SortStudentParameter.STUDENTID;
        }

        if (trimmedParam.equals("email")) {
            return SortStudentParameter.EMAIL;
        }

        if (trimmedParam.equals("")) {
            return SortStudentParameter.EMPTY;
        }

        return SortStudentParameter.INVALID;
    }
}
