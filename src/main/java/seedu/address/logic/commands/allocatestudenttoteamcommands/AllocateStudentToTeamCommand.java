package seedu.address.logic.commands.allocatestudenttoteamcommands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.TutorialTeamMessages;
import seedu.address.model.Model;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Person;

/**
 * Allocates a student to a team in a tutorial Class in TAHelper.
 */
public abstract class AllocateStudentToTeamCommand extends Command {

    public static final String COMMAND_WORD = "/allocate_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allocates a student a team in the tutorial class.\n";

    public static final String MESSAGE_SUCCESS = "Allocate student to team: %s";
    public static final String MESSAGE_STUDENT_NOT_IN_TUTORIAL = "Student needs to be in that tutorial group first.";


    public AllocateStudentToTeamCommand() {}

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);

    /**
     * Check the condition needed to allocate the {@code student} to a {@code tutorialTeam} of {@code tutClass}.
     * @param student
     * @param tutClass
     * @param tutorialTeam
     * @throws CommandException
     */
    public void checkAllocateCondition(Person student, TutorialClass tutClass,
            TutorialTeam tutorialTeam)
            throws CommandException {
        if (!tutClass.isStudentInTutorialClass(student, tutClass)) {
            throw new CommandException(MESSAGE_STUDENT_NOT_IN_TUTORIAL);
        }

        if (!tutClass.hasTeamInTutorial(tutClass, tutorialTeam)) {
            throw new CommandException(String.format(TutorialTeamMessages.MESSAGE_TEAM_DOES_NOT_EXIST,
                    tutorialTeam, tutClass));
        }

        if (tutClass.isStudentInAnyTeam(student, tutClass)) {
            throw new CommandException(String.format(TutorialTeamMessages.MESSAGE_DUPLICATE_PERSON_IN_TEAM,
                    Messages.format(student), tutClass));
        }

        if (tutorialTeam.hasTeamSizeExceeded(tutorialTeam)) {
            throw new CommandException(String.format(TutorialTeamMessages.MESSAGE_TEAM_SIZE_EXCEEDED,
                    tutorialTeam.getTeamSize()));
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
