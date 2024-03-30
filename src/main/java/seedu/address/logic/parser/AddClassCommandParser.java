package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddClassCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;



/**
 * Parses input arguments and creates a new {@code AddClassCommandParser} object
 */
public class AddClassCommandParser implements Parser<AddClassCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddClassCommandParser}
     * and returns a {@code AddClassCommandParser} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddClassCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
            PREFIX_MODULECODE, PREFIX_TUTORIALCLASS, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE, PREFIX_TUTORIALCLASS)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassCommand.MESSAGE_USAGE));
        }

        String moduleCode = argMultimap.getValue(PREFIX_MODULECODE).orElse("");
        String tutorialClass = argMultimap.getValue(PREFIX_TUTORIALCLASS).orElse("");
        Optional<String> description = argMultimap.getValue(PREFIX_DESCRIPTION);

        if (!(ModuleCode.isValidModuleCode(moduleCode))) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        if (!(TutorialClass.isValidTutorialClass(tutorialClass))) {
            throw new ParseException(TutorialClass.MESSAGE_CONSTRAINTS);
        }
        return new AddClassCommand(new ModuleCode(moduleCode), new TutorialClass(tutorialClass), description);
    }

    /**
     * Returns true if all the prefixes are present in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
