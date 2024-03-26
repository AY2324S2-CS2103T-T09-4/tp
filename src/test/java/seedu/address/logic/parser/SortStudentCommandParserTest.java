package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SortStudentCommand;

/**
 * Contains unit tests for SortStudentCommandParser.
 */
public class SortStudentCommandParserTest {

    private SortStudentCommandParser parser = new SortStudentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortStudentCommand() {
        // valid name
        SortStudentCommand command = new SortStudentCommand("name");
        assertParseSuccess(parser, " " + PREFIX_SORT_BY + "name", command);

        // valid name
        command = new SortStudentCommand("id");
        assertParseSuccess(parser, " " + PREFIX_SORT_BY + "id", command);

        // valid name
        command = new SortStudentCommand("email");
        assertParseSuccess(parser, " " + PREFIX_SORT_BY + "email", command);

        // case-insensitive
        command = new SortStudentCommand("name");
        assertParseSuccess(parser, " " + PREFIX_SORT_BY + "NaMe", command);

        // extended preamble
        command = new SortStudentCommand("id");
        assertParseSuccess(parser, "      " + PREFIX_SORT_BY + "id      ", command);
    }

    @Test
    public void parse_invalidArgs_failure() {
        // sort by prefix present but missing input
        assertParseFailure(parser, " " + PREFIX_SORT_BY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortStudentCommand.MESSAGE_USAGE));

        // missing prefix
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortStudentCommand.MESSAGE_USAGE));

        // invalid prefix
        assertParseFailure(parser, " " + PREFIX_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortStudentCommand.MESSAGE_USAGE));

        // duplicate prefix
        assertParseFailure(parser, " " + PREFIX_SORT_BY + "name " + PREFIX_SORT_BY + "id",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SORT_BY));
    }
}
