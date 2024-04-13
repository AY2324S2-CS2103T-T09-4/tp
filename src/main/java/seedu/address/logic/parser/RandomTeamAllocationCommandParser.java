package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_OF_TEAMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import java.util.stream.Stream;

import seedu.address.logic.commands.RandomTeamAllocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * Parses input arguments and creates a new RandomTeamAllocationCommand object
 */
public class RandomTeamAllocationCommandParser implements Parser<RandomTeamAllocationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RandomTeamAllocationCommand
     * and returns a RandomTeamAllocationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RandomTeamAllocationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULECODE, PREFIX_TUTORIALCLASS, PREFIX_NUM_OF_TEAMS);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE, PREFIX_TUTORIALCLASS, PREFIX_NUM_OF_TEAMS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RandomTeamAllocationCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MODULECODE, PREFIX_TUTORIALCLASS, PREFIX_NUM_OF_TEAMS);
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULECODE).get());
        TutorialClass tutorialClass = ParserUtil.parseTutorialClass(argMultimap.getValue(PREFIX_TUTORIALCLASS).get());
        int numOfTeams = ParserUtil.parseNumberOfTeams(argMultimap.getValue(PREFIX_NUM_OF_TEAMS).get());

        if (numOfTeams <= 0) {
            throw new ParseException(String.format(RandomTeamAllocationCommand.MESSAGE_NUM_OF_TEAMS_NONZERO));
        }

        return new RandomTeamAllocationCommand(moduleCode, tutorialClass, numOfTeams);
    }

    /**
     * Returns true if all the prefixes are present in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
