package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddClassCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.AddTeamCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteClassCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListClassesCommand;
import seedu.address.logic.commands.ListStudentsCommand;
import seedu.address.logic.commands.ListStudentsOfClassCommand;
import seedu.address.logic.commands.SearchStudentCommand;
import seedu.address.logic.commands.ViewTeamCommand;
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassByEmailCommand;
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassByIdCommand;
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassCommand;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentByIdCommand;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentCommand;
import seedu.address.logic.commands.deletestudentfromclasscommands.DeleteStudentFromClassByEmailCommand;
import seedu.address.logic.commands.deletestudentfromclasscommands.DeleteStudentFromClassByIdCommand;
import seedu.address.logic.commands.deletestudentfromclasscommands.DeleteStudentFromClassCommand;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamByEmailCommand;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamByIdCommand;
import seedu.address.logic.commands.deletestudentfromteamcommands.DeleteStudentFromTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Email;
import seedu.address.model.person.NameContainsKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

/**
 * Contains unit tests for AddressBookParser.
 */
public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddStudentCommand command = (AddStudentCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddStudentCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_ONE + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_searchStudent() throws Exception {
        String keyword = "foo";
        SearchStudentCommand command = (SearchStudentCommand) parser.parseCommand(
                SearchStudentCommand.COMMAND_WORD + " " + PREFIX_NAME + keyword);
        assertEquals(new SearchStudentCommand(new NameContainsKeywordPredicate(keyword)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListStudentsCommand.COMMAND_WORD) instanceof ListStudentsCommand);
        assertTrue(parser.parseCommand(ListStudentsCommand.COMMAND_WORD + " 3") instanceof ListStudentsCommand);
    }

    @Test
    public void parseCommand_addClass() throws Exception {
        final String moduleCode = "CS2103T";
        final String tutorialClass = "T09";
        AddClassCommand command = (AddClassCommand) parser.parseCommand(AddClassCommand.COMMAND_WORD + " "
                + PREFIX_MODULECODE + moduleCode + " " + PREFIX_TUTORIALCLASS + tutorialClass);
        assertEquals(new AddClassCommand(new ModuleCode(moduleCode),
                new TutorialClass(tutorialClass),
                Optional.ofNullable(new ModuleCode(moduleCode).getDescription())), command);
    }

    @Test
    public void parseCommand_deleteClass() throws Exception {
        final String moduleCode = "CS2103T";
        final String tutorialClass = "T09";
        DeleteClassCommand command = (DeleteClassCommand) parser.parseCommand(DeleteClassCommand.COMMAND_WORD + " "
                + PREFIX_MODULECODE + moduleCode + " " + PREFIX_TUTORIALCLASS + tutorialClass);
        assertEquals(new DeleteClassCommand(new ModuleCode(moduleCode),
                new TutorialClass(tutorialClass)), command);
    }

    @Test
    public void parseCommand_deleteModule() throws Exception {
        final String moduleCode = "CS2103T";
        DeleteModuleCommand command = (DeleteModuleCommand) parser.parseCommand(DeleteModuleCommand.COMMAND_WORD + " "
                + PREFIX_MODULECODE + moduleCode);
        assertEquals(new DeleteModuleCommand(new ModuleCode(moduleCode)), command);
    }



    @Test
    public void parseCommand_listClasses() throws Exception {
        assertTrue(parser.parseCommand(ListClassesCommand.COMMAND_WORD) instanceof ListClassesCommand);
        assertTrue(parser.parseCommand(ListClassesCommand.COMMAND_WORD + " 3") instanceof ListClassesCommand);
    }

    @Test
    public void parseCommand_addStudentToClass() throws Exception {
        final String moduleCode = "CS2103T";
        final String tutorialClass = "T09";
        final String email = VALID_EMAIL_AMY;
        final String id = VALID_STUDENT_ID_AMY;
        final Index index = INDEX_FIRST_PERSON;
        AddStudentToClassCommand addByEmailCommand = (AddStudentToClassCommand) parser.parseCommand(
                AddStudentToClassCommand.COMMAND_WORD + " " + PREFIX_EMAIL + email + " " + PREFIX_MODULECODE
                        + moduleCode + " "
                        + PREFIX_TUTORIALCLASS + tutorialClass);
        AddStudentToClassCommand addByIdCommand = (AddStudentToClassCommand) parser.parseCommand(
                AddStudentToClassCommand.COMMAND_WORD + " " + PREFIX_STUDENTID + id + " " + PREFIX_MODULECODE
                        + moduleCode + " "
                        + PREFIX_TUTORIALCLASS + tutorialClass);
        assertEquals(new AddStudentToClassByEmailCommand(new Email(VALID_EMAIL_AMY), new ModuleCode(moduleCode),
                new TutorialClass(tutorialClass)), addByEmailCommand);
        assertEquals(new AddStudentToClassByIdCommand(new StudentId(VALID_STUDENT_ID_AMY), new ModuleCode(moduleCode),
                new TutorialClass(tutorialClass)), addByIdCommand);

    }

    @Test
    public void parseCommand_deleteStudentFromClass() throws Exception {
        final String moduleCode = "CS2103T";
        final String tutorialClass = "T09";
        final String email = VALID_EMAIL_AMY;
        final String id = VALID_STUDENT_ID_AMY;
        final Index index = INDEX_FIRST_PERSON;
        DeleteStudentFromClassCommand deleteByEmailCommand = (DeleteStudentFromClassCommand) parser.parseCommand(
                DeleteStudentFromClassCommand.COMMAND_WORD + " "
                        + PREFIX_EMAIL + email + " " + PREFIX_MODULECODE
                        + moduleCode + " " + PREFIX_TUTORIALCLASS + tutorialClass);

        DeleteStudentFromClassCommand deleteByIdCommand = (DeleteStudentFromClassCommand) parser.parseCommand(
                DeleteStudentFromClassCommand.COMMAND_WORD + " "
                        + PREFIX_STUDENTID + id + " " + PREFIX_MODULECODE
                        + moduleCode + " " + PREFIX_TUTORIALCLASS + tutorialClass);

        assertEquals(new DeleteStudentFromClassByEmailCommand(new Email(VALID_EMAIL_AMY),
                new ModuleCode(moduleCode), new TutorialClass(tutorialClass)), deleteByEmailCommand);

        assertEquals(new DeleteStudentFromClassByIdCommand(new StudentId(VALID_STUDENT_ID_AMY),
                new ModuleCode(moduleCode), new TutorialClass(tutorialClass)), deleteByIdCommand);
    }

    @Test
    public void parseCommand_addTeam() throws Exception {
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        TutorialClass tutorialClass = new TutorialClass("T01");
        String teamName = "Team A";

        AddTeamCommand command = (AddTeamCommand) parser.parseCommand(
            AddTeamCommand.COMMAND_WORD + " " + PREFIX_MODULECODE + " " + moduleCode + " "
                + PREFIX_TUTORIALCLASS + " " + tutorialClass + " "
                + PREFIX_TEAMNAME + " " + teamName);
        assertEquals(new AddTeamCommand(moduleCode, tutorialClass, teamName), command);
    }

    @Test
    public void parseCommand_deleteTeam() throws Exception {
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        TutorialClass tutorialClass = new TutorialClass("T01");
        String teamName = "Team A";

        DeleteTeamCommand command = (DeleteTeamCommand) parser.parseCommand(
            DeleteTeamCommand.COMMAND_WORD + " " + PREFIX_MODULECODE + " " + moduleCode + " "
                + PREFIX_TUTORIALCLASS + " " + tutorialClass + " "
                + PREFIX_TEAMNAME + " " + teamName);
        assertEquals(new DeleteTeamCommand(moduleCode, tutorialClass, teamName), command);
    }

    @Test
    public void parseCommand_viewTeam() throws Exception {
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        TutorialClass tutorialClass = new TutorialClass("T01");
        String teamName = "Team A";

        ViewTeamCommand command = (ViewTeamCommand) parser.parseCommand(
            ViewTeamCommand.COMMAND_WORD + " " + PREFIX_MODULECODE + " " + moduleCode + " "
                + PREFIX_TUTORIALCLASS + " " + tutorialClass + " "
                + PREFIX_NAME + " " + teamName);
        assertEquals(new ViewTeamCommand(PREFIX_NAME, teamName, moduleCode, tutorialClass), command);
    }
    @Test
    public void parseCommand_deleteStudentFromTeam() throws Exception {
        final String moduleCode = "CS2103T";
        final String tutorialClass = "T09";
        final String tutorialTeam = "Team 4";
        final String email = VALID_EMAIL_AMY;
        final String id = VALID_STUDENT_ID_AMY;
        final Index index = INDEX_FIRST_PERSON;

        DeleteStudentFromTeamCommand deleteByEmailCommand = (DeleteStudentFromTeamCommand) parser.parseCommand(
                DeleteStudentFromTeamCommand.COMMAND_WORD + " "
                        + PREFIX_EMAIL + email + " " + PREFIX_MODULECODE
                        + moduleCode + " " + PREFIX_TUTORIALCLASS + tutorialClass
                        + " " + PREFIX_TEAMNAME + tutorialTeam);

        DeleteStudentFromTeamCommand deleteByIdCommand = (DeleteStudentFromTeamCommand) parser.parseCommand(
                DeleteStudentFromTeamCommand.COMMAND_WORD + " "
                        + PREFIX_STUDENTID + id + " " + PREFIX_MODULECODE
                        + moduleCode + " " + PREFIX_TUTORIALCLASS + tutorialClass
                        + " " + PREFIX_TEAMNAME + tutorialTeam);

        assertEquals(new DeleteStudentFromTeamByEmailCommand(new Email(VALID_EMAIL_AMY),
                new ModuleCode(moduleCode), new TutorialClass(tutorialClass),
                new TutorialTeam(tutorialTeam)), deleteByEmailCommand);

        assertEquals(new DeleteStudentFromTeamByIdCommand(new StudentId(VALID_STUDENT_ID_AMY),
                new ModuleCode(moduleCode), new TutorialClass(tutorialClass),
                new TutorialTeam(tutorialTeam)), deleteByIdCommand);
    }
    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), (
                        ) -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }


    @Test
    public void parseCommand_listStudentsOfClass() throws Exception {
        final String moduleCode = "CS2103T";
        final String tutorialClass = "T09";
        ListStudentsOfClassCommand command = (ListStudentsOfClassCommand) parser.parseCommand(
            ListStudentsOfClassCommand.COMMAND_WORD + " "
                + PREFIX_MODULECODE + moduleCode + " " + PREFIX_TUTORIALCLASS + tutorialClass);
        assertEquals(new ListStudentsOfClassCommand(new ModuleCode(moduleCode),
            new TutorialClass(tutorialClass)), command);
    }

    @Test
    public void parseCommand_deleteStudent() throws Exception {
        final String studentId = "A1234561Z";
        DeleteStudentCommand
            command = (DeleteStudentCommand) parser.parseCommand(DeleteStudentCommand.COMMAND_WORD + " "
            + PREFIX_STUDENTID + studentId);
        assertEquals(new DeleteStudentByIdCommand(new StudentId(studentId)), command);
    }
}
