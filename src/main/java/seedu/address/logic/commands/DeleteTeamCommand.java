package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.ModuleMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTutorialPair;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;

/**
 * A class used to handle the deletion of tutorial team.
 */
public class DeleteTeamCommand extends Command {
    public static final String MESSAGE_DELETE_TEAM_SUCCESS = "Removed %1$s from %2$s %3$s!";
    public static final String MESSAGE_TEAM_NOT_FOUND = "%1$s not in %2$s %3$s!";
    public static final String COMMAND_WORD = "/delete_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a team from the tutorial class specified\n"
            + "Parameters:" + PREFIX_MODULECODE + "MODULE_CODE "
            + PREFIX_TUTORIALCLASS + "TUTORIAL_CLASS " + PREFIX_NAME + "TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MODULECODE + " CS2103T "
            + PREFIX_TUTORIALCLASS + "T09 " + PREFIX_NAME + "Team 1";

    private final ModuleCode module;
    private final TutorialClass tutorialClass;
    private final TutorialTeam team;

    /**
     * Constructs a DeleteTeamCommand to delete the specified {@code TutorialTeam}
     * from the specified {@code ModuleCode} and {@code TutorialClass}.
     * 
     * @param module        The module code of the tutorial class to be deleted.
     * @param tutorialClass The tutorial class to be deleted.
     * @param teamName      The name of the team to be deleted.
     */
    public DeleteTeamCommand(ModuleCode module, TutorialClass tutorialClass, String teamName) {
        requireAllNonNull(module, tutorialClass, teamName);
        this.module = module;
        this.tutorialClass = tutorialClass;
        this.team = new TutorialTeam(teamName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ModuleTutorialPair moduleAndTutorialClass = getModuleAndTutorialClass(model);
        ModuleCode module = moduleAndTutorialClass.getModule();
        TutorialClass tutorialClass = moduleAndTutorialClass.getTutorialClass();
        if (tutorialClass.hasTeam(team)) {
            tutorialClass.deleteTeam(team);
        } else {
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, team, module, tutorialClass));
        }

        return new CommandResult(generateSuccessMessage(module, tutorialClass, team));
    }

    protected ModuleTutorialPair getModuleAndTutorialClass(Model model) throws CommandException {
        requireNonNull(model);
        ModuleCode module = getModule();
        TutorialClass tutorialClass = getTutorialClass();
        ModuleCode existingModule = model.findModuleFromList(module);
        TutorialClass existingTutorialClass = model.findTutorialClassFromList(tutorialClass, existingModule);
        if (existingModule == null) {
            throw new CommandException(String.format(ModuleMessages.MESSAGE_MODULE_NOT_FOUND, module));
        }
        if (existingTutorialClass == null) {
            throw new CommandException(
                    String.format(ModuleMessages.MESSAGE_TUTORIAL_DOES_NOT_BELONG_TO_MODULE, tutorialClass, module));
        }
        return new ModuleTutorialPair(existingModule, existingTutorialClass);
    }

    protected ModuleCode getModule() {
        return module;
    }

    protected TutorialClass getTutorialClass() {
        return tutorialClass;
    }

    /**
     * Generates a command execution success message based on whether the tutorial
     * class is successfully deleted.
     * 
     * @param module         The module code of the tutorial class.
     * @param tutorialString The tutorial class.
     * @return The success message.
     */
    private String generateSuccessMessage(ModuleCode module, TutorialClass tutorialString, TutorialTeam team) {
        return String.format(MESSAGE_DELETE_TEAM_SUCCESS, team, module, tutorialString);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTeamCommand)) {
            return false;
        }

        DeleteTeamCommand e = (DeleteTeamCommand) other;
        return module.equals(e.module) && tutorialClass.equals(e.tutorialClass) && team.equals(e.team);
    }
}
