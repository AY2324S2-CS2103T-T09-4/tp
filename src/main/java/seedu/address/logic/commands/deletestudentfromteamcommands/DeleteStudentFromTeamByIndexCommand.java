package seedu.address.logic.commands.deletestudentfromteamcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.TeamMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTutorialPair;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Person;


/**
 * Deletes a student from a specified tutorial team, by identifying
 * the student via their index.
 */
public class DeleteStudentFromTeamByIndexCommand extends DeleteStudentFromTeamCommand {
    private final Index targetIndex;


    /**
     * Deletes a student from a team by index.
     * @param targetIndex
     * @param module
     * @param tutorialClass
     * @param tutorialTeam
     */
    public DeleteStudentFromTeamByIndexCommand(Index targetIndex, ModuleCode module, TutorialClass tutorialClass,
                                               TutorialTeam tutorialTeam) {
        super(module, tutorialClass, tutorialTeam);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ModuleTutorialPair moduleAndTutorialClass = ModuleTutorialPair.getModuleAndTutorialClass(model,
                getModule(), getTutorialClass());
        TutorialClass tutorialClass = moduleAndTutorialClass.getTutorialClass();
        ModuleCode module = moduleAndTutorialClass.getModule();

        TutorialTeam team = tutorialClass.getTutorialTeam(tutorialClass, tutorialTeam);

        Person personToDelete;

        try {
            personToDelete = team.getStudents().get(targetIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(
                    String.format(TeamMessages.MESSAGE_PERSON_INDEX_NOT_FOUND, targetIndex.getOneBased(), team));
        }

        if (team == null) {
            throw new CommandException(String.format(TeamMessages.MESSAGE_TEAM_DOES_NOT_EXIST, tutorialTeam,
                    tutorialClass));
        }
        if (!(team.hasStudent(personToDelete))) {
            throw new CommandException(
                    String.format(TeamMessages.MESSAGE_STUDENT_NOT_FOUND_IN_TEAM,
                            Messages.format(personToDelete), tutorialClass));
        } else {
            model.deleteStudentFromTeam(personToDelete, team);
            return new CommandResult(
                    String.format(MESSAGE_DELETE_STUDENT_FROM_TEAM_SUCCESS,
                            Messages.format(personToDelete), module, tutorialClass, team));
        }
    }

    /**
     * Returns true if both DeleteStudentFromTeamByIndexCommand have the same index.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStudentFromTeamByIndexCommand)) {
            return false;
        }

        DeleteStudentFromTeamByIndexCommand otherDeleteCommand = (DeleteStudentFromTeamByIndexCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }
}
