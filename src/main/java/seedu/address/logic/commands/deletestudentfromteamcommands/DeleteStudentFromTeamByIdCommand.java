package seedu.address.logic.commands.deletestudentfromteamcommands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.PersonMessages;
import seedu.address.logic.messages.TeamMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTutorialPair;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Deletes a student from a specified tutorial team, by identifying
 * the student via their Student ID.
 */
public class DeleteStudentFromTeamByIdCommand extends DeleteStudentFromTeamCommand {
    private final Predicate<Person> predicate;

    private final StudentId studentId;

    /**
     * Deletes a student from a team by student id.
     *
     * @param studentId
     * @param module
     * @param tutorialClass
     * @param tutorialTeam
     */
    public DeleteStudentFromTeamByIdCommand(StudentId studentId, ModuleCode module, TutorialClass tutorialClass,
                                            TutorialTeam tutorialTeam) {
        super(module, tutorialClass, tutorialTeam);
        this.studentId = studentId;
        this.predicate = person -> person.getStudentId().equals(studentId);
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

        personToDelete = model.searchPersonByPredicate(predicate);
        if (personToDelete == null) {
            throw new CommandException(String.format(PersonMessages.MESSAGE_PERSON_STUDENT_ID_NOT_FOUND, studentId));
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
     * Returns true if both DeleteStudentFromTeamByIdCommand have the same studentId.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStudentFromTeamByIdCommand)) {
            return false;
        }

        DeleteStudentFromTeamByIdCommand otherDeleteCommand = (DeleteStudentFromTeamByIdCommand) other;
        return studentId.equals(otherDeleteCommand.studentId);
    }
}
