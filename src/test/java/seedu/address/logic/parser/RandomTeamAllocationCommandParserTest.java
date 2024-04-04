package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NUM_OF_TEAMS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TUTORIAL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NUM_OF_TEAMS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIAL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIAL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUM_OF_TEAMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUM_OF_TEAMS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.module.ModuleCodeTest.VALID_MODULE_CODE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.RandomTeamAllocationCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.testutil.ModuleBuilder;

public class RandomTeamAllocationCommandParserTest {
    private RandomTeamAllocationCommandParser parser = new RandomTeamAllocationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ModuleCode expectedModuleCode = new ModuleBuilder().withModuleCode(VALID_MODULE_AMY)
                .withTutorialClasses(VALID_TUTORIAL_AMY).build();
        int expectedNumOfTeams = VALID_NUM_OF_TEAMS;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODULE_DESC_AMY + TUTORIAL_DESC_AMY
                + VALID_NUM_OF_TEAMS_DESC, new RandomTeamAllocationCommand(expectedModuleCode.getModule(),
                expectedModuleCode.getTutorialClasses().get(0), expectedNumOfTeams));
    }

    @Test
    public void parse_repeatedPrefixes_failure() {
        String validExpectedAllocatorString = MODULE_DESC_AMY + TUTORIAL_DESC_AMY + NUM_OF_TEAMS_DESC;

        // multiple module code
        assertParseFailure(parser, MODULE_DESC_BOB + validExpectedAllocatorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULECODE));

        // multiple tutorial class
        assertParseFailure(parser, TUTORIAL_DESC_BOB + validExpectedAllocatorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIALCLASS));

        // invalid moduleCode
        assertParseFailure(parser, INVALID_MODULE_DESC + validExpectedAllocatorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULECODE));

        // invalid tutorialClass
        assertParseFailure(parser, INVALID_TUTORIAL_DESC + validExpectedAllocatorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIALCLASS));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RandomTeamAllocationCommand.MESSAGE_USAGE);

        // missing ModuleCode prefix
        assertParseFailure(parser, VALID_MODULE_CODE + TUTORIAL_DESC_BOB + VALID_NUM_OF_TEAMS_DESC,
                expectedMessage);

        // missing TutorialClass Prefix
        assertParseFailure(parser, MODULE_DESC_BOB + VALID_TUTORIAL_BOB + VALID_NUM_OF_TEAMS_DESC,
                expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, VALID_MODULE_BOB + VALID_TUTORIAL_BOB + VALID_NUM_OF_TEAMS,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid moduleCode
        assertParseFailure(parser, INVALID_MODULE_DESC + TUTORIAL_DESC_BOB + VALID_NUM_OF_TEAMS_DESC,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid tutorialClass
        assertParseFailure(parser, MODULE_DESC_BOB + INVALID_TUTORIAL_DESC + VALID_NUM_OF_TEAMS_DESC,
                TutorialClass.MESSAGE_CONSTRAINTS);

        // invalid numOfTeams
        assertParseFailure(parser, MODULE_DESC_BOB + TUTORIAL_DESC_BOB + INVALID_NUM_OF_TEAMS_DESC,
                TutorialTeam.MESSAGE_NUMBER_CONSTRAINTS);
    }
}
