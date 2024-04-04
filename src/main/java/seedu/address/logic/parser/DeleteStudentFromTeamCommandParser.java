package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamByEmailCommand;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamByIdCommand;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamByIndexCommand;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Email;
import seedu.address.model.person.StudentId;

/**
 * Parses input arguments and creates a new DeleteStudentFromClassCommand object
 */
public class DeleteStudentFromTeamCommandParser implements Parser<DeleteStudentFromTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteStudentFromClass and returns a DeleteStudentFromClassCommand object for
     * execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteStudentFromTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_EMAIL, PREFIX_STUDENTID,
                PREFIX_MODULECODE, PREFIX_TUTORIALCLASS, PREFIX_TEAMNAME);
        boolean isIndexPresent = argMultimap.getValue(PREFIX_INDEX).isPresent();
        boolean isEmailPresent = argMultimap.getValue(PREFIX_EMAIL).isPresent();
        boolean isStudentIdPresent = argMultimap.getValue(PREFIX_STUDENTID).isPresent();
        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE, PREFIX_TUTORIALCLASS,
                PREFIX_TEAMNAME) || (!isIndexPresent && !isEmailPresent && !isStudentIdPresent)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentFromTeamCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_STUDENTID, PREFIX_EMAIL,
                PREFIX_MODULECODE, PREFIX_TUTORIALCLASS, PREFIX_TEAMNAME);
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULECODE).get());
        TutorialClass tutorialClass = ParserUtil.parseTutorialClass(argMultimap.getValue(PREFIX_TUTORIALCLASS).get());
        TutorialTeam tutorialTeam = ParserUtil.parseTutorialTeam(argMultimap.getValue(PREFIX_TEAMNAME).get());
        if (isIndexPresent) {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
            return new DeleteStudentFromTeamByIndexCommand(index, moduleCode, tutorialClass, tutorialTeam);
        } else if (isStudentIdPresent) {
            StudentId studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENTID).get());
            return new DeleteStudentFromTeamByIdCommand(studentId, moduleCode, tutorialClass, tutorialTeam);
        } else if (isEmailPresent) {
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            return new DeleteStudentFromTeamByEmailCommand(email, moduleCode, tutorialClass, tutorialTeam);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentFromTeamCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
