package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;



/**
 * Parses input arguments and creates a new {@code DeleteClassCommandParser} object
 */
public class DeleteModuleCommandParser implements Parser<DeleteModuleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteClassCommandParser}
     * and returns a {@code DeleteClassCommandParser} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULECODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModuleCommand.MESSAGE_USAGE));
        }

        String moduleCode = argMultimap.getValue(PREFIX_MODULECODE).orElse("");
        if (!(ModuleCode.isValidModuleCode(moduleCode))) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new DeleteModuleCommand(new ModuleCode(moduleCode));
    }
    /**
     * Returns true if all the prefixes are present in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

