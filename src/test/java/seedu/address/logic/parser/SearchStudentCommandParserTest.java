package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_ID_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SearchStudentCommand;
import seedu.address.model.person.EmailContainsKeywordPredicate;
import seedu.address.model.person.NameContainsKeywordPredicate;
import seedu.address.model.person.StudentIdContainsKeywordPredicate;

/**
 * Contains unit tests for SearchStudentCommandParser.
 */
public class SearchStudentCommandParserTest {

    private SearchStudentCommandParser parser = new SearchStudentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // valid name
        SearchStudentCommand expectedSearchStudentCommand =
                new SearchStudentCommand(new NameContainsKeywordPredicate(BOB.getName().fullName));
        assertParseSuccess(parser, NAME_DESC_BOB, expectedSearchStudentCommand);

        // valid student id
        expectedSearchStudentCommand =
                new SearchStudentCommand(new StudentIdContainsKeywordPredicate(BOB.getStudentId().value));
        assertParseSuccess(parser, STUDENT_ID_DESC_BOB, expectedSearchStudentCommand);

        // valid email id
        expectedSearchStudentCommand =
                new SearchStudentCommand(new EmailContainsKeywordPredicate(BOB.getEmail().value));
        assertParseSuccess(parser, EMAIL_DESC_BOB, expectedSearchStudentCommand);


        // extended preamble
        expectedSearchStudentCommand =
                new SearchStudentCommand(new NameContainsKeywordPredicate(BOB.getName().fullName));
        assertParseSuccess(parser, "      " + PREFIX_NAME + BOB.getName().fullName , expectedSearchStudentCommand);

        // mixed-case argument
        expectedSearchStudentCommand =
                new SearchStudentCommand(new NameContainsKeywordPredicate("ALiCe"));
        assertParseSuccess(parser, " " + PREFIX_NAME + "ALiCe", expectedSearchStudentCommand);
    }

    @Test
    public void parse_invalidArgs_failure() {
        // missing prefix
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchStudentCommand.MESSAGE_USAGE));

        // multiple prefixes
        assertParseFailure(parser, NAME_DESC_BOB + EMAIL_DESC_BOB + STUDENT_ID_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchStudentCommand.MESSAGE_USAGE));

        // duplicate prefix
        assertParseFailure(parser, NAME_DESC_BOB + NAME_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }
}
