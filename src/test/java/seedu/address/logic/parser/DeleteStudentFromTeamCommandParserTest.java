package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.module.ModuleCodeTest.VALID_MODULE_CODE;
import static seedu.address.model.module.ModuleCodeTest.VALID_TUTORIAL_1;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamByEmailCommand;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamByIdCommand;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamCommand;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteStudentFromTeamCommandParserTest {
    private DeleteStudentFromTeamCommandParser parser = new DeleteStudentFromTeamCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteStudentFromTeamCommand() {
        Person person = new PersonBuilder(AMY).build();
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE);
        TutorialClass tutorialClass = new TutorialClass(VALID_TUTORIAL_1);
        TutorialTeam tutorialTeam = new TutorialTeam(VALID_TEAM_NAME);
        moduleCode.addTutorialClass(tutorialClass);
        tutorialClass.addTeam(tutorialTeam);

        DeleteStudentFromTeamByIdCommand deleteStudentFromTeamByStuIdCommand =
                new DeleteStudentFromTeamByIdCommand(person.getStudentId(), moduleCode, tutorialClass, tutorialTeam);
        DeleteStudentFromTeamByEmailCommand deleteStudentFromTeamByEmailCommand =
                new DeleteStudentFromTeamByEmailCommand(person.getEmail(), moduleCode, tutorialClass, tutorialTeam);

        String userInput = " " + PREFIX_MODULECODE + moduleCode.moduleCode
                + " " + PREFIX_TUTORIALCLASS + tutorialClass.tutorialName
                + " " + PREFIX_TEAMNAME + tutorialTeam.getTeamName();
        assertParseSuccess(parser, " " + PREFIX_STUDENTID + person.getStudentId().value + userInput,
                deleteStudentFromTeamByStuIdCommand);
        assertParseSuccess(parser, " " + PREFIX_EMAIL + person.getEmail().value + userInput,
                deleteStudentFromTeamByEmailCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteStudentFromTeamCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "a", expectedMessage);
    }

}
