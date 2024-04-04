package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.module.TutorialTeamName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

public class ViewTeamCommandTest {

    @Test
    public void execute_validInput_teamFound() throws Exception {
        Model model = new ModelManager(); // Create a basic model
        ModuleCode moduleCode = new ModuleCode("CS2104");
        TutorialClass tutorialClass = new TutorialClass("T09");
        moduleCode.addTutorialClass(tutorialClass);
        model.addModule(moduleCode);

        TutorialTeamName teamName = new TutorialTeamName("Team A");
        TutorialTeam team = new TutorialTeam(teamName.fullName, 2);
        Person john = new Person(new Name("John"), new Email("john@example.com"),
            new StudentId("A1234561Z"), new HashSet<>());
        Person alice = new Person(new Name("Alice"), new Email("alice@example.com"),
            new StudentId("A1234562Z"), new HashSet<>());
        team.addStudent(john);
        team.addStudent(alice);
        tutorialClass.addTeam(team);

        ViewTeamCommand command = new ViewTeamCommand(PREFIX_NAME, "Team A", moduleCode, tutorialClass);
        CommandResult result = command.execute(model);

        assertEquals("Team Name: Team A, Team Size: 2, Students: name=John, email=john@example.com, "
            + "stuId=A1234561Z, name=Alice, email=alice@example.com, stuId=A1234562Z", result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidTeamName_exceptionThrown() {
        Model model = new ModelManager(); // Create a basic model
        ModuleCode moduleCode = new ModuleCode("CS2105");
        TutorialClass tutorialClass = new TutorialClass("T06");
        moduleCode.addTutorialClass(tutorialClass);
        model.addModule(moduleCode);

        TutorialTeamName teamName = new TutorialTeamName("Team A");
        TutorialTeam team = new TutorialTeam(teamName.fullName, 2);
        Person john = new Person(new Name("John"), new Email("john@example.com"),
            new StudentId("A1234561Z"), new HashSet<>());
        Person alice = new Person(new Name("Alice"), new Email("alice@example.com"),
            new StudentId("A1234562Z"), new HashSet<>());
        team.addStudent(john);
        team.addStudent(alice);
        tutorialClass.addTeam(team);

        ViewTeamCommand command = new ViewTeamCommand(PREFIX_NAME, "Team B", moduleCode, tutorialClass);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_noModule_exceptionThrown() throws CommandException {
        Model model = new ModelManager(); // Create a basic model

        ViewTeamCommand command = new ViewTeamCommand(PREFIX_NAME, "Team A",
            new ModuleCode("CS4423"), new TutorialClass("T01"));

        CommandResult result = command.execute(model);
        assertEquals(ViewTeamCommand.MESSAGE_NO_MODULE, result.getFeedbackToUser());
    }

    @Test
    public void execute_noTutorialClasses_exceptionThrown() throws CommandException {
        Model model = new ModelManager();
        ModuleCode moduleCode = new ModuleCode("CS4232");
        model.addModule(moduleCode);

        ViewTeamCommand command = new ViewTeamCommand(PREFIX_NAME, "Team A", moduleCode,
            new TutorialClass("T01"));

        CommandResult result = command.execute(model);
        assertEquals(String.format(ViewTeamCommand.MESSAGE_NO_TUTORIAL_CLASSES, moduleCode),
            result.getFeedbackToUser());
    }

    @Test
    public void execute_emptyTeams_exceptionThrown() throws CommandException {
        Model model = new ModelManager(); // Create a basic model
        ModuleCode moduleCode = new ModuleCode("CS4233");
        TutorialClass tutorialClass = new TutorialClass("T01");
        moduleCode.addTutorialClass(tutorialClass);
        model.addModule(moduleCode);

        ViewTeamCommand command = new ViewTeamCommand(PREFIX_NAME, "Team A", moduleCode, tutorialClass);

        CommandResult result = command.execute(model);
        assertEquals(String.format(ViewTeamCommand.MESSAGE_EMPTY, moduleCode, tutorialClass),
            result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPredicateType_exceptionThrown() throws CommandException {
        Model model = new ModelManager();
        ModuleCode moduleCode = new ModuleCode("CS2101");
        TutorialClass tutorialClass = new TutorialClass("T01");
        moduleCode.addTutorialClass(tutorialClass);
        model.addModule(moduleCode);
        TutorialTeam team = new TutorialTeam("Team A");
        tutorialClass.addTeam(team);

        ViewTeamCommand command = new ViewTeamCommand(PREFIX_DESCRIPTION, "Team A", moduleCode,
            tutorialClass);

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals("Invalid predicate type specified!", exception.getMessage());
    }

    @Test
    public void findTeamByIndex_validIndex_teamFound() throws CommandException {
        Model model = new ModelManager();
        ModuleCode moduleCode = new ModuleCode("CS2101");
        TutorialClass tutorialClass = new TutorialClass("T01");
        TutorialTeam team = new TutorialTeam("Team A");
        model.addModule(moduleCode);
        moduleCode.addTutorialClass(tutorialClass);
        tutorialClass.addTeam(team);

        ViewTeamCommand command = new ViewTeamCommand(PREFIX_INDEX, "0", moduleCode,
            tutorialClass);
        TutorialTeam foundTeam = command.findTeamByIndex(model, Index.fromZeroBased(0),
            tutorialClass, moduleCode);

        assertEquals(team, foundTeam);
    }

    @Test
    public void findTeamByIndex_invalidIndex_exceptionThrown() {
        Model model = new ModelManager(); // Create a basic model
        ModuleCode moduleCode = new ModuleCode("CS2101");
        TutorialClass tutorialClass = new TutorialClass("T01");
        moduleCode.addTutorialClass(tutorialClass);
        model.addModule(moduleCode);

        ViewTeamCommand command = new ViewTeamCommand(PREFIX_INDEX, "0", moduleCode,
            tutorialClass);
        assertThrows(CommandException.class, () ->
            command.findTeamByIndex(model, Index.fromZeroBased(0), tutorialClass, moduleCode));
    }
    @Test
    public void equals_sameObject_returnsTrue() {
        Model model = new ModelManager();
        ModuleCode moduleCode = new ModuleCode("CS2101");
        TutorialClass tutorialClass = new TutorialClass("T01");
        TutorialTeam team = new TutorialTeam("Team A");
        model.addModule(moduleCode);
        moduleCode.addTutorialClass(tutorialClass);
        tutorialClass.addTeam(team);

        ViewTeamCommand command = new ViewTeamCommand(PREFIX_NAME, team.getTeamName(), moduleCode,
            tutorialClass);
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_equalObjects_returnsTrue() {
        Model model = new ModelManager();
        ModuleCode moduleCode = new ModuleCode("CS2101");
        TutorialClass tutorialClass = new TutorialClass("T01");
        TutorialTeam team = new TutorialTeam("Team A");
        model.addModule(moduleCode);
        moduleCode.addTutorialClass(tutorialClass);
        tutorialClass.addTeam(team);

        ViewTeamCommand command1 = new ViewTeamCommand(PREFIX_NAME, team.getTeamName(), moduleCode,
            tutorialClass);
        ViewTeamCommand command2 = new ViewTeamCommand(PREFIX_NAME, team.getTeamName(), moduleCode,
            tutorialClass);
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentModuleCode_returnsFalse() {
        Model model = new ModelManager();
        ModuleCode moduleCode1 = new ModuleCode("CS2101");
        TutorialClass tutorialClass = new TutorialClass("T01");
        TutorialTeam team = new TutorialTeam("Team A");
        model.addModule(moduleCode1);
        moduleCode1.addTutorialClass(tutorialClass);
        tutorialClass.addTeam(team);

        ModuleCode moduleCode2 = new ModuleCode("CS2102");
        TutorialClass tutorialClass2 = new TutorialClass("T01");
        TutorialTeam team2 = new TutorialTeam("Team A");
        model.addModule(moduleCode2);
        moduleCode2.addTutorialClass(tutorialClass2);
        tutorialClass.addTeam(team2);

        ViewTeamCommand command1 = new ViewTeamCommand(PREFIX_NAME, team.getTeamName(), moduleCode1,
            tutorialClass);
        ViewTeamCommand command2 = new ViewTeamCommand(PREFIX_NAME, team.getTeamName(), moduleCode2,
            tutorialClass);
        assertFalse(command1.equals(command2));
    }

}
