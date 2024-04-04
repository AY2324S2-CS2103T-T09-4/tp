package seedu.address.logic.commands.deletestudentfromteamcommands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;

/**
 * The abstract class that handles all delete student from tutorial team commands.
 */
public abstract class DeleteStudentFromTeamCommand extends Command {
    public static final String COMMAND_WORD = "/delete_student_from_team";
    public static final String MESSAGE_DELETE_STUDENT_FROM_TEAM_SUCCESS = "Deleted %s from %s %s, Team %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a student from an existing class\n"
            + "Parameters:" + "IDENTIFIER " + PREFIX_MODULECODE + "MODULE_CODE (must be a String) "
            + PREFIX_TUTORIALCLASS + "TUTORIAL_CLASS (must be a String)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EMAIL + "test@gmail.com " + PREFIX_MODULECODE + " CS2103T "
            + PREFIX_TUTORIALCLASS + "T09" + " " + PREFIX_TEAMNAME + "Team 4";

    protected final ModuleCode module;
    protected final TutorialClass tutorialClass;
    protected final TutorialTeam tutorialTeam;

    /**
     * A constructor for DeleteStudentFromTeam.
     */
    public DeleteStudentFromTeamCommand(ModuleCode module, TutorialClass tutorialClass, TutorialTeam tutorialTeam) {
        requireAllNonNull(module, tutorialClass);
        this.module = module;
        this.tutorialClass = tutorialClass;
        this.tutorialTeam = tutorialTeam;
    }

    protected ModuleCode getModule() {
        return module;
    }

    protected TutorialClass getTutorialClass() {
        return tutorialClass;
    }

    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract boolean equals(Object other);
}
