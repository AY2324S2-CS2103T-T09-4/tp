package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;


/**
 * Parses input arguments and creates a new ViewTeamCommand object.
 */
public class ViewTeamCommandParser implements Parser<ViewTeamCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AllocateStudentToTeamCommand
     * and returns an AllocateStudentToTeamCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INDEX,
            PREFIX_MODULECODE, PREFIX_TUTORIALCLASS);
        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE, PREFIX_TUTORIALCLASS)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewTeamCommand.MESSAGE_USAGE));
        }
        Prefix predicateType;
        String predicateValue;
        ModuleCode moduleCode;
        TutorialClass tutorialClass;
        predicateType = getPredicateType(argMultimap);
        predicateValue = argMultimap.getValue(predicateType).orElseThrow(() ->
            new ParseException("Predicate type is missing"));
        moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULECODE)
            .orElseThrow(() -> new ParseException("Module code is missing")));
        tutorialClass = ParserUtil.parseTutorialClass(argMultimap.getValue(PREFIX_TUTORIALCLASS)
            .orElseThrow(() -> new ParseException("Tutorial class is missing")));

        return new ViewTeamCommand(predicateType, predicateValue, moduleCode, tutorialClass);
    }

    /**
     * Returns the predicate type specified in the argument multimap.
     * @throws ParseException if no valid allocation type is found
     */
    private Prefix getPredicateType(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return PREFIX_NAME;
        } else if (argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            return PREFIX_INDEX;
        } else {
            throw new ParseException("Predicate type is missing");
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
