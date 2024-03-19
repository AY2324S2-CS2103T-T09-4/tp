package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIAL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteClassCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

public class DeleteClassCommandParserTest {
    private final DeleteClassCommandParser parser = new DeleteClassCommandParser();


    @Test
    public void parse_tutorialTest() {
        // have tutorial class
        String expectedMessage = TutorialClass.MESSAGE_CONSTRAINTS;

        String userInput = MODULE_DESC_AMY + TUTORIAL_DESC_AMY;
        DeleteClassCommand expectedCommand = new DeleteClassCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no tutorial class
        userInput = MODULE_DESC_AMY + " " + PREFIX_TUTORIALCLASS;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage1 = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteClassCommand.MESSAGE_USAGE);
        String expectedMessage2 = ModuleCode.MESSAGE_CONSTRAINTS;

        // no parameters
        assertParseFailure(parser, " ", expectedMessage1);

        // no module stated
        assertParseFailure(parser, " " + PREFIX_MODULECODE + ""
                + TUTORIAL_DESC_AMY, expectedMessage2);
    }
}

