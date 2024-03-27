package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.model.module.ModuleCode;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteModuleCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteModuleCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteModuleCommandParserTest {
    private final DeleteModuleCommandParser parser = new DeleteModuleCommandParser();


    @Test
    public void parse_validModule_success() {
        String userInput = MODULE_DESC_AMY;
        DeleteModuleCommand expectedCommand = new DeleteModuleCommand(new ModuleCode(VALID_MODULE_AMY));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage1 = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteModuleCommand.MESSAGE_USAGE);
        String expectedMessage2 = ModuleCode.MESSAGE_CONSTRAINTS;

        // no parameters
        assertParseFailure(parser, " ", expectedMessage1);

        // invalid module stated
        assertParseFailure(parser, " " + PREFIX_MODULECODE + "INVALID", expectedMessage2);
    }
}

