package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.PersonBuilder;

public class RandomTeamAllocationCommandTest {

    private Model model;
    private Person alice;
    private Person bob;
    private ModuleCode testModuleCode;
    private ModuleCode moduleWithNoClass;

    private TutorialClass testTutClass;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        alice = new PersonBuilder().withName("Alice").build();
        bob = new PersonBuilder().withName("Bob").build();
        testModuleCode = new ModuleBuilder().withTutorialClasses("T01").build();
        model.addModule(testModuleCode);
        testTutClass = testModuleCode.getTutorialClasses().get(0);
        testTutClass.addStudent(alice);
        testTutClass.addStudent(bob);
        moduleWithNoClass = new ModuleCode(VALID_MODULE_BOB);
    }

    @Test
    public void constructor_nullTutorialClass_throwsNullPointerException() {
        int numOfTeams = 1;
        assertThrows(NullPointerException.class, () -> new RandomTeamAllocationCommand(testModuleCode,
                null, numOfTeams));
    }

    @Test
    public void execute_randomTeamAllocation_success() throws CommandException {
        int numOfTeams = 2;
        RandomTeamAllocationCommand randTeamAllocationCommand = new
                RandomTeamAllocationCommand(testModuleCode, testTutClass, numOfTeams);

        CommandResult commandResult = randTeamAllocationCommand.execute(model);
        assertEquals(String.format(RandomTeamAllocationCommand.MESSAGE_SUCCESS, testTutClass),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_randomTeamAllocationModWithNoTut_failure() throws CommandException {
        int numOfTeams = 1;
        RandomTeamAllocationCommand randTeamAllocationCommand = new
                RandomTeamAllocationCommand(moduleWithNoClass, testTutClass, numOfTeams);

        CommandResult commandResult = randTeamAllocationCommand.execute(model);
        assertEquals(String.format(RandomTeamAllocationCommand.MESSAGE_MODULE_TUTORIAL_NOT_EXIST),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_randomTeamAllocationTutNotInModule_failure() throws CommandException {
        int numOfTeams = 1;
        RandomTeamAllocationCommand randTeamAllocationCommand = new
                RandomTeamAllocationCommand(testModuleCode, new TutorialClass("T12"), numOfTeams);

        CommandResult commandResult = randTeamAllocationCommand.execute(model);
        assertEquals(String.format(RandomTeamAllocationCommand.MESSAGE_MODULE_TUTORIAL_NOT_EXIST),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_randomTeamAllocationNumTeamsMoreNumStu_failure() throws CommandException {
        int numOfTeams = 3;
        RandomTeamAllocationCommand randTeamAllocationCommand = new
                RandomTeamAllocationCommand(testModuleCode, testTutClass, numOfTeams);

        CommandResult commandResult = randTeamAllocationCommand.execute(model);
        assertEquals(String.format(RandomTeamAllocationCommand.MESSAGE_ALLOCATION_NOT_POSSIBLE,
                        testTutClass.getStudents().size(), numOfTeams),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_randomTeamAllocationInvalidZeroTeams_failure() throws CommandException {
        int numOfTeams = 0;
        RandomTeamAllocationCommand randTeamAllocationCommand = new
                RandomTeamAllocationCommand(testModuleCode, testTutClass, numOfTeams);

        CommandResult commandResult = randTeamAllocationCommand.execute(model);
        assertEquals(String.format(RandomTeamAllocationCommand.MESSAGE_ALLOCATION_NOT_POSSIBLE,
                        testTutClass.getStudents().size(), numOfTeams),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_randomTeamAllocationInvalidNegTeams_failure() throws CommandException {
        int numOfTeams = -1;
        RandomTeamAllocationCommand randTeamAllocationCommand = new
                RandomTeamAllocationCommand(testModuleCode, testTutClass, numOfTeams);

        CommandResult commandResult = randTeamAllocationCommand.execute(model);
        assertEquals(String.format(RandomTeamAllocationCommand.MESSAGE_ALLOCATION_NOT_POSSIBLE,
                        testTutClass.getStudents().size(), numOfTeams),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {

        RandomTeamAllocationCommand randTeamAllocation = new
                RandomTeamAllocationCommand(testModuleCode, testTutClass, 2);

        // same object --> returns true
        assertTrue(randTeamAllocation.equals(randTeamAllocation));

        // same values --> returns true
        RandomTeamAllocationCommand otherRandTeamAllocation = new
                RandomTeamAllocationCommand(testModuleCode, testTutClass, 2);
        assertTrue(randTeamAllocation.equals(otherRandTeamAllocation));

        // different type of parameters --> returns false
        assertFalse(randTeamAllocation.equals(5));

        // null --> returns false
        assertFalse(randTeamAllocation.equals(null));

        // adding with different number of teams --> returns false
        RandomTeamAllocationCommand randTeamAllocationLesserTeams = new
                RandomTeamAllocationCommand(testModuleCode, testTutClass, 1);
        assertFalse(randTeamAllocation.equals(randTeamAllocationLesserTeams));
    }
}
