package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_SIZE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIAL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_SIZE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTeamCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;

/**
 * Contains unit tests for AddTeamCommandParser.
 */
public class AddTeamCommandParserTest {
    private final AddTeamCommandParser parser = new AddTeamCommandParser();

    @Test
    public void parse_teamWithNoSize_success() {
        String userInput = MODULE_DESC_AMY + TUTORIAL_DESC_AMY + TEAM_NAME_DESC_AMY;
        AddTeamCommand expectedCommand = new AddTeamCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY), VALID_TEAM_NAME_AMY);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_teamWithSize_success() {
        String userInput = MODULE_DESC_AMY + TUTORIAL_DESC_AMY + TEAM_NAME_DESC_AMY + TEAM_SIZE_DESC;
        AddTeamCommand expectedCommand = new AddTeamCommand(new ModuleCode(VALID_MODULE_AMY),
                new TutorialClass(VALID_TUTORIAL_AMY), VALID_TEAM_NAME_AMY, VALID_TEAM_SIZE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_teamWithInvalidName_failure() {
        String expectedMessage = TutorialTeam.MESSAGE_NAME_CONSTRAINTS;
        String userInput = MODULE_DESC_AMY + TUTORIAL_DESC_AMY + " " + PREFIX_NAME + " ";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_teamWithNoName_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeamCommand.MESSAGE_USAGE);
        String userInput = MODULE_DESC_AMY + TUTORIAL_DESC_AMY;
        assertParseFailure(parser, userInput, expectedMessage);
    }

}
