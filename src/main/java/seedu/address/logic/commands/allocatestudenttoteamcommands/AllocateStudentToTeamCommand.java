package seedu.address.logic.commands.allocatestudenttoteamcommands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.TutorialTeamMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Person;

/**
 * Allocates a student to a team in a tutorial Class in TAHelper.
 */
public abstract class AllocateStudentToTeamCommand extends Command {

    public static final String COMMAND_WORD = "/allocate_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allocates a student a team in the tutorial class.\n"
            + "Parameters: "
            + PREFIX_STUDENTID + "STUDENT ID "
            + PREFIX_MODULECODE + "MODULE CODE "
            + PREFIX_TUTORIALCLASS + "TUTORIAL CLASS "
            + PREFIX_TEAMNAME + "TEAM NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENTID + "A1234567L "
            + PREFIX_MODULECODE + "CS2101 "
            + PREFIX_TUTORIALCLASS + "T01 "
            + PREFIX_TEAMNAME + "Team 1 ";

    public static final String MESSAGE_SUCCESS = "Allocate student to team: %s";
    public static final String MESSAGE_STUDENT_NOT_IN_TUTORIAL = "Student needs to be in that tutorial group first.";

    public static final String MESSAGE_TEAM_SIZE_EXCEEDED = "Max team size of %d reached";

    public AllocateStudentToTeamCommand() {
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);

    /**
     * Check the condition needed to allocate the student to a tutorial team.
     * @param student
     * @param tutClass
     * @param tutorialTeam
     * @throws CommandException
     */
    public void checkAllocateCondition(Model model, Person student, TutorialClass tutClass,
            TutorialTeam tutorialTeam, ModuleCode moduleCode)
            throws CommandException {
        if (!tutClass.isStudentInTutorialClass(student, tutClass)) {
            throw new CommandException(MESSAGE_STUDENT_NOT_IN_TUTORIAL);
        }

        if (!model.hasTeamInTutorial(tutClass, tutorialTeam)) {
            throw new CommandException(
                    String.format(TutorialTeamMessages.MESSAGE_TEAM_NOT_FOUND, tutorialTeam, moduleCode, tutClass));
        }

        if (model.isStudentInAnyTeam(student, tutClass)) {
            throw new CommandException(String.format(TutorialTeamMessages.MESSAGE_DUPLICATE_PERSON_IN_TEAM,
                    Messages.format(student), tutClass));
        }

        if (tutorialTeam.hasTeamSizeExceeded(tutorialTeam)) {
            throw new CommandException(String.format(MESSAGE_TEAM_SIZE_EXCEEDED, tutorialTeam.getTeamSize()));
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
