package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;

import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.SearchStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailContainsKeywordPredicate;
import seedu.address.model.person.NameContainsKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentIdContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new SearchStudentCommand object
 */
public class SearchStudentCommandParser implements Parser<SearchStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchStudentCommand
     * and returns a SearchStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchStudentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_STUDENTID, PREFIX_EMAIL);

        if (!hasOnlyOnePrefix(argMultimap, PREFIX_NAME, PREFIX_STUDENTID, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchStudentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_EMAIL, PREFIX_STUDENTID);

        Predicate<Person> predicate = null;

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String keyword = argMultimap.getValue(PREFIX_EMAIL).get();
            predicate = new EmailContainsKeywordPredicate(keyword);
        }
        if (argMultimap.getValue(PREFIX_STUDENTID).isPresent()) {
            String keyword = argMultimap.getValue(PREFIX_STUDENTID).get();
            predicate = new StudentIdContainsKeywordPredicate(keyword);
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String keyword = argMultimap.getValue(PREFIX_NAME).get();
            predicate = new NameContainsKeywordPredicate(keyword);
        }
        if (predicate == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchStudentCommand.MESSAGE_USAGE));
        }

        return new SearchStudentCommand(predicate);
    }

    /**
     * Returns true if only one of the prefixes contain {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean hasOnlyOnePrefix(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .map(prefix -> argumentMultimap.getValue(prefix).isPresent())
                .filter(bool -> bool).count() == 1;
    }
}
