package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_ONE;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_ID_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deletestudentcommands.DeleteStudentByEmailCommand;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentByIdCommand;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentByIndexCommand;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteStudentCommandParserTest {

    private DeleteStudentCommandParser parser = new DeleteStudentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {

        // Delete by email
        DeleteStudentByEmailCommand expectedDeleteByEmailCommand = new DeleteStudentByEmailCommand(BOB.getEmail());
        assertParseSuccess(parser, EMAIL_DESC_BOB, expectedDeleteByEmailCommand);


        // Delete by student Id
        DeleteStudentByIdCommand expectedDeleteByIdCommand = new DeleteStudentByIdCommand(BOB.getStudentId());
        assertParseSuccess(parser, STUDENT_ID_DESC_BOB, expectedDeleteByIdCommand);

        // Delete by index
        DeleteStudentByIndexCommand expectedDeleteByIndexCommand = new DeleteStudentByIndexCommand(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, INDEX_ONE, expectedDeleteByIndexCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "a", expectedMessage);
    }
}
