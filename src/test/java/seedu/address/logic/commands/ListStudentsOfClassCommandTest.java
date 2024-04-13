package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListStudentsOfClassCommand.
 */
public class ListStudentsOfClassCommandTest {
    private Model model;
    private TutorialClass tutorialClass;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        ModuleCode newModule = new ModuleCode("CS2103T");
        model.addModule(newModule);
        TutorialClass newTutorialClass = new TutorialClass("T09");
        newModule.addTutorialClass(newTutorialClass);
        tutorialClass = newTutorialClass;
    }

    @Test
    public void execute_listStudentsOfClass_success() throws CommandException {
        ListStudentsOfClassCommand listStudentsOfClassCommand = new ListStudentsOfClassCommand(
            new ModuleCode("CS2103T"), new TutorialClass("T09"));
        CommandResult commandResult = listStudentsOfClassCommand.execute(model);

        // Update the expected result to match the actual result
        String expectedFeedback = "No students found in the specified tutorial class";

        assertEquals(expectedFeedback, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_listStudentsOfClassNoSuchModule_fail() throws CommandException {
        ListStudentsOfClassCommand listStudentsOfClassCommand = new ListStudentsOfClassCommand(
            new ModuleCode("CS1111"), new TutorialClass("T09"));

        // Execute the command and capture the CommandResult
        CommandResult commandResult = listStudentsOfClassCommand.execute(model);

        // Assert that the CommandResult contains the expected message
        assertEquals("Module CS1111 or tutorial class T09 not found", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_listStudentsOfClassNoSuchClass_fail() throws CommandException {
        ListStudentsOfClassCommand listStudentsOfClassCommand = new ListStudentsOfClassCommand(
            new ModuleCode("CS2103T"), new TutorialClass("T99"));

        CommandResult commandResult = listStudentsOfClassCommand.execute(model);

        assertEquals(String.format("Module %s or tutorial class %s not found", "CS2103T", "T99"),
            commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        ListStudentsOfClassCommand listStudentsOfClassFirstCommand = new ListStudentsOfClassCommand(
            new ModuleCode("CS2103T"), new TutorialClass("T09"));
        ListStudentsOfClassCommand listStudentsOfClassSecondCommand = new ListStudentsOfClassCommand(
            new ModuleCode("CS2103T"), new TutorialClass("T09"));
        ListStudentsOfClassCommand listStudentsOfClassDifferentModule = new ListStudentsOfClassCommand(
            new ModuleCode("CS2101"), new TutorialClass("T09"));
        ListStudentsOfClassCommand listStudentsOfClassDifferentClass = new ListStudentsOfClassCommand(
            new ModuleCode("CS2103T"), new TutorialClass("T10"));

        // same object -> returns true
        assertTrue(listStudentsOfClassFirstCommand.equals(listStudentsOfClassFirstCommand));

        // same values -> returns true
        assertTrue(listStudentsOfClassFirstCommand.equals(listStudentsOfClassSecondCommand));

        // different types -> returns false
        assertFalse(listStudentsOfClassFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listStudentsOfClassFirstCommand.equals(null));

        // different module -> returns false
        assertFalse(listStudentsOfClassFirstCommand.equals(listStudentsOfClassDifferentModule));

        // different class -> returns false
        assertFalse(listStudentsOfClassFirstCommand.equals(listStudentsOfClassDifferentClass));
    }
    @Test
    public void execute_listActualStudentsOfClass_success() throws CommandException {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("tag1"));
        tags.add(new Tag("tag2"));

        Person alice = new Person(new Name("Alice"), new Email("alice@example.com"),
            new StudentId("A1234561Z"), tags);
        Person bob = new Person(new Name("Bob"), new Email("bob@example.com"),
            new StudentId("A1234562Z"), tags);

        TutorialClass tutorialClass = new TutorialClass("T01");
        tutorialClass.addStudent(alice);
        tutorialClass.addStudent(bob);

        ModuleCode moduleCode = new ModuleCode("CS2101");

        // Set up the model to return the desired TutorialClass when findTutorialClassFromList is called
        model.addModule(moduleCode);
        moduleCode.addTutorialClass(tutorialClass);

        ListStudentsOfClassCommand listCommand = new ListStudentsOfClassCommand(moduleCode, tutorialClass);
        CommandResult commandResult = listCommand.execute(model);

        StringBuilder expectedResult = new StringBuilder();
        expectedResult.append("Module: CS2101, Tutorial Class: T01\nStudents: Alice (ID: A1234561Z), "
            + "Bob (ID: A1234562Z),");

        assertEquals(expectedResult.toString(), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_listStudentsOfClassEmptyClass_success() throws CommandException {
        TutorialClass tutorialClass = new TutorialClass("T05");
        ModuleCode moduleCode = new ModuleCode("CS2105");
        model.addModule(moduleCode);
        moduleCode.addTutorialClass(tutorialClass);

        ListStudentsOfClassCommand listCommand = new ListStudentsOfClassCommand(moduleCode, tutorialClass);
        CommandResult commandResult = listCommand.execute(model);

        assertEquals("No students found in the specified tutorial class", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_listStudentsOfClassNoSuchModule2_fail() throws CommandException {
        ModuleCode moduleCode = new ModuleCode("CS1111");
        TutorialClass tutorialClass = new TutorialClass("T09");

        ListStudentsOfClassCommand listCommand = new ListStudentsOfClassCommand(moduleCode, tutorialClass);
        CommandResult commandResult = listCommand.execute(model);

        assertEquals("Module CS1111 or tutorial class T09 not found", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_listStudentsOfClassNoSuchClass2_fail() throws CommandException {
        ModuleCode moduleCode = new ModuleCode("CS2103T");
        TutorialClass tutorialClass = new TutorialClass("T99");

        ListStudentsOfClassCommand listCommand = new ListStudentsOfClassCommand(moduleCode, tutorialClass);
        CommandResult commandResult = listCommand.execute(model);

        assertEquals("Module CS2103T or tutorial class T99 not found", commandResult.getFeedbackToUser());
    }
}
