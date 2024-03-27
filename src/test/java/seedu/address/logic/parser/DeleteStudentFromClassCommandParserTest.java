package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_ID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIAL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deletestudentfromclasscommands.DeleteStudentFromClassByEmailCommand;
import seedu.address.logic.commands.deletestudentfromclasscommands.DeleteStudentFromClassByIdCommand;
import seedu.address.logic.commands.deletestudentfromclasscommands.DeleteStudentFromClassCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

public class DeleteStudentFromClassCommandParserTest {

    private DeleteStudentFromClassCommandParser parser = new DeleteStudentFromClassCommandParser();
    @Test
    public void parse_validArgs_returnsDeleteStudentFromClassCommand() {

        String commandSuffix = MODULE_DESC_AMY + TUTORIAL_DESC_AMY;

        // Delete by email
        DeleteStudentFromClassByEmailCommand expectedDeleteByEmailCommand =
                new DeleteStudentFromClassByEmailCommand(BOB.getEmail(),
                new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));
        assertParseSuccess(parser, EMAIL_DESC_BOB + commandSuffix, expectedDeleteByEmailCommand);

        // Delete by student Id
        DeleteStudentFromClassByIdCommand expectedDeleteByIdCommand =
                new DeleteStudentFromClassByIdCommand(BOB.getStudentId(),
                new ModuleCode(VALID_MODULE_AMY), new TutorialClass(VALID_TUTORIAL_AMY));
        assertParseSuccess(parser, STUDENT_ID_DESC_BOB + commandSuffix, expectedDeleteByIdCommand);

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteStudentFromClassCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "a", expectedMessage);
    }
}
