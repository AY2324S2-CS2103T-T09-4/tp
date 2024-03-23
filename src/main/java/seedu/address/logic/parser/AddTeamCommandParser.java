package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_SIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;

/**
 * Parses input arguments and creates a new {@code AddClassCommandParser} object
 */
public class AddTeamCommandParser implements Parser<AddTeamCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@code AddClassCommandParser}
     * and returns a {@code AddClassCommandParser} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTeamCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULECODE, PREFIX_TUTORIALCLASS,
                PREFIX_NAME, PREFIX_TEAM_SIZE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE, PREFIX_TUTORIALCLASS, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeamCommand.MESSAGE_USAGE));
        }

        boolean isTeamSizePresent = argMultimap.getValue(PREFIX_TEAM_SIZE).isPresent();
        String moduleCode = argMultimap.getValue(PREFIX_MODULECODE).orElse("");
        String tutorialClass = argMultimap.getValue(PREFIX_TUTORIALCLASS).orElse("");
        String teamName = argMultimap.getValue(PREFIX_NAME).orElse("");
        if (!(ModuleCode.isValidModuleCode(moduleCode))) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        if (!(TutorialClass.isValidTutorialClass(tutorialClass))) {
            throw new ParseException(TutorialClass.MESSAGE_CONSTRAINTS);
        }

        if (!TutorialTeam.isValidTeamName(teamName)) {
            throw new ParseException(TutorialTeam.MESSAGE_NAME_CONSTRAINTS);
        }
        if (isTeamSizePresent) {
            int teamSize = Integer.parseInt(argMultimap.getValue(PREFIX_TEAM_SIZE).get());
            if (teamSize <= 0) {
                throw new ParseException(TutorialTeam.MESSAGE_SIZE_CONSTRAINTS);
            }
            return new AddTeamCommand(new ModuleCode(moduleCode), new TutorialClass(tutorialClass), teamName, teamSize);
        } else {
            return new AddTeamCommand(new ModuleCode(moduleCode), new TutorialClass(tutorialClass), teamName);
        }
    }

    /**
     * Returns true if all the prefixes are present in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
