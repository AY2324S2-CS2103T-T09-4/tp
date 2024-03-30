package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_OF_TEAMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Person;

/**
 * A command to random allocate all students of a particular tutorial class into different teams.
 */
public class RandomTeamAllocationCommand extends Command {

    public static final String COMMAND_WORD = "/random_teams";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Randomly allocate all the students in the "
            + "tutorial class into teams \n"
            + "Parameters: "
            + PREFIX_MODULECODE + "MODULE CODE "
            + PREFIX_TUTORIALCLASS + "TUTORIAL CLASS "
            + PREFIX_NUM_OF_TEAMS + "NUMBER OF TEAMS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULECODE + "CS2103T "
            + PREFIX_TUTORIALCLASS + "T09 "
            + PREFIX_NUM_OF_TEAMS + "3 ";

    public static final String MESSAGE_MODULE_TUTORIAL_NOT_EXIST = "Please check if you have entered"
            + " an existing module and tutorial";
    public static final String MESSAGE_ALLOCATION_NOT_POSSIBLE = "Allocation not possible for tutorial class size %s"
            + " into %s different teams";

    public static final String MESSAGE_SUCCESS = "Successfully randomly allocate students for tutorial class %s";

    public static final String MESSAGE_SIZE_NEGATIVE = "Number of teams must be more than 0!";
    private final ModuleCode moduleCode;
    private final TutorialClass tutorialClass;
    private final int numOfTeams;

    /**
     * Creates a RandomTeamAllocation to randomly allocate students of the specified tutorial class to different teams.
     *
     * @param moduleCode of the tutorial class.
     * @param tutorialClass to allocate the teams.
     * @param teams to split into.
     */
    public RandomTeamAllocationCommand(ModuleCode moduleCode, TutorialClass tutorialClass, int teams) {
        requireAllNonNull(moduleCode, tutorialClass, teams);
        this.moduleCode = moduleCode;
        this.tutorialClass = tutorialClass;
        numOfTeams = teams;
    }

    /**
     * Checks if it is possible to split into different teams where each team has at least 1 student in it.
     *
     * @param tutorialClass to check the number of students
     * @param numOfTeams number of teams to split into
     * @return a boolean if it is possible.
     */
    public boolean checkTeamAllocationCondition(TutorialClass tutorialClass, int numOfTeams) {
        ArrayList<Person> studentList = tutorialClass.getStudents();
        int numOfStudents = studentList.size();
        if (Math.ceil((double) numOfStudents / numOfTeams) >= 1.0) {
            return true;
        }
        return false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        boolean doesModuleExist = model.hasModule(moduleCode);
        ModuleCode module = model.findModuleFromList(moduleCode);
        TutorialClass tutorial = model.findTutorialClassFromList(tutorialClass, module);

        if (!doesModuleExist || tutorial == null) {
            return new CommandResult(MESSAGE_MODULE_TUTORIAL_NOT_EXIST);
        }

        int classSize = tutorial.getStudents().size();
        boolean isAllocatePossible = checkTeamAllocationCondition(tutorial, numOfTeams);
        if (!isAllocatePossible) {
            return new CommandResult(String.format(MESSAGE_ALLOCATION_NOT_POSSIBLE, classSize, numOfTeams));
        }

        model.randomTeamAllocation(module, tutorial, numOfTeams);

        return new CommandResult(String.format(MESSAGE_SUCCESS, tutorial));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RandomTeamAllocationCommand)) {
            return false;
        }

        RandomTeamAllocationCommand otherRandomTeamAllocationCommand = (RandomTeamAllocationCommand) other;
        return moduleCode.equals(otherRandomTeamAllocationCommand.moduleCode)
                && tutorialClass.equals(otherRandomTeamAllocationCommand.tutorialClass)
                && numOfTeams == ((RandomTeamAllocationCommand) other).numOfTeams;
    }

}
