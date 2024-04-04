package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_SIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTutorialPair;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;

/**
 * A class that handles the /add_team command execution.
 */
public class AddTeamCommand extends Command {
    public static final String MESSAGE_ADD_TEAM_SUCCESS_WITHOUT_SIZE = "Added %1$s to %2$s %3$s";
    public static final String MESSAGE_ADD_TEAM_SUCCESS_WITH_SIZE = "Added %1$s of size %2$s to %3$s %4$s";
    public static final String MESSAGE_DUPLICATE_TEAM = "%1$s already added to %2$s %3$s!";
    public static final String COMMAND_WORD = "/add_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Create a team with a name and an optional size, assigned to a particular tutorial class\n"
            + "Parameters: " + PREFIX_MODULECODE + "MODULE_CODE "
            + PREFIX_TUTORIALCLASS + "TUTORIAL_CLASS " + PREFIX_NAME + "TEAM_NAME " + PREFIX_TEAM_SIZE + "TEAM_SIZE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MODULECODE + "CS2103T "
            + PREFIX_TUTORIALCLASS + "T09 " + PREFIX_NAME + "Team 1 " + PREFIX_TEAM_SIZE + "5";

    private final ModuleCode module;
    private final TutorialClass tutorialClass;
    private final String teamName;
    private final int teamSize;

    /**
     * Constructs an AddTeamCommand to add the specified {@code TutorialTeam} to the
     * specified {@code TutorialClass}, without the specified team size.
     * @param module        The module code of the tutorial class to be added.
     * @param tutorialClass The tutorial class to be added.
     * @param teamName      The name of the team to be added.
     */
    public AddTeamCommand(ModuleCode module, TutorialClass tutorialClass, String teamName) {
        requireAllNonNull(module);
        this.module = module;
        this.tutorialClass = tutorialClass;
        this.teamName = teamName;
        this.teamSize = Integer.MAX_VALUE;
    }

    /**
     * Constructs an AddTeamCommand to add the specified {@code TutorialTeam} to the
     * specified {@code TutorialClass}, with the specified team size.
     * @param module        The module code of the tutorial class to be added.
     * @param tutorialClass The tutorial class to be added.
     * @param teamName      The name of the team to be added.
     * @param teamSize      The size of the team to be added.
     */
    public AddTeamCommand(ModuleCode module, TutorialClass tutorialClass, String teamName, int teamSize) {
        requireAllNonNull(module);
        this.module = module;
        this.tutorialClass = tutorialClass;
        this.teamName = teamName;
        this.teamSize = teamSize;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ModuleTutorialPair moduleAndTutorialClass = ModuleTutorialPair.getModuleAndTutorialClass(model,
                module, tutorialClass);
        ModuleCode module = moduleAndTutorialClass.getModule();
        TutorialClass tutorialClass = moduleAndTutorialClass.getTutorialClass();

        TutorialTeam newTeam = new TutorialTeam(teamName, teamSize);
        if (tutorialClass.hasTeam(newTeam)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TEAM, teamName, module, tutorialClass));
        } else {
            model.addTeam(tutorialClass, newTeam);
        }

        if (teamSize != Integer.MAX_VALUE) {
            return new CommandResult(generateSuccessMessage(module, tutorialClass, teamName, teamSize));
        } else {
            return new CommandResult(generateSuccessMessage(module, tutorialClass, teamName));
        }
    }

    protected ModuleCode getModule() {
        return module;
    }

    protected TutorialClass getTutorialClass() {
        return tutorialClass;
    }

    /**
     * Generates a command execution success message based on whether the tutorial
     * team is added successfully and if the team size is not specified.
     * @param module         The module code of the tutorial class.
     * @param tutorialString The tutorial class.
     * @param teamName       The name of the team.
     * @return The success message.
     */
    private String generateSuccessMessage(ModuleCode module, TutorialClass tutorialString, String teamName) {
        return String.format(MESSAGE_ADD_TEAM_SUCCESS_WITHOUT_SIZE, teamName, module,
                tutorialString);
    }

    /**
     * Generates a command execution success message based on whether the tutorial
     * team is added successfully and if the team size is specified.
     * @param module         The module code of the tutorial class.
     * @param tutorialString The tutorial class.
     * @param teamName       The name of the team.
     * @param teamSize       The size of the team.
     * @return The success message.
     */
    private String generateSuccessMessage(ModuleCode module, TutorialClass tutorialString, String teamName,
            int teamSize) {
        return String.format(MESSAGE_ADD_TEAM_SUCCESS_WITH_SIZE, teamName, teamSize, module, tutorialString);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTeamCommand)) {
            return false;
        }

        AddTeamCommand e = (AddTeamCommand) other;
        return module.equals(e.module) && tutorialClass.equals(e.tutorialClass) && teamName.equals(e.teamName)
                && teamSize == e.teamSize;
    }
}
