package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIAL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * Contains unit tests for AddTeamCommandParser.
 */
public class DeleteTeamCommandParserTest {
    private final DeleteTeamCommandParser parser = new DeleteTeamCommandParser();

    @Test
    public void parse_deleteTeam_success() {
        String userInput = MODULE_DESC_AMY + TUTORIAL_DESC_AMY + TEAM_NAME_DESC_AMY;
        DeleteTeamCommand expectedCommand = new DeleteTeamCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY), VALID_TEAM_NAME_AMY);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_deleteTeamWithNoName_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE);
        String userInput = MODULE_DESC_AMY + TUTORIAL_DESC_AMY;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_deleteTeamWithNoModule_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE);
        String userInput = TUTORIAL_DESC_AMY + TEAM_NAME_DESC_AMY;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_deleteTeamWithNoTutorial_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE);
        String userInput = MODULE_DESC_AMY + TEAM_NAME_DESC_AMY;
        assertParseFailure(parser, userInput, expectedMessage);
    }

}
