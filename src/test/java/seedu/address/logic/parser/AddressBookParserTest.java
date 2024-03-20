package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddClassCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteClassCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListClassesCommand;
import seedu.address.logic.commands.ListStudentsCommand;
import seedu.address.logic.commands.SearchStudentCommand;
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassByEmailCommand;
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassByIdCommand;
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
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
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
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
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
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
                new TutorialClass(tutorialClass)), command);
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
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), (
                ) -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
