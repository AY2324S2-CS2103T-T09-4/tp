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
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;

/**
 * Deletes a student from a specified tutorial team, by identifying
 * the student via their email.
 */
public class DeleteStudentFromTeamByEmailCommand extends DeleteStudentFromTeamCommand {
    private final Predicate<Person> predicate;
    private final Email email;


    /**
     * Deletes a student from a team by email.
     * @param email
     * @param module
     * @param tutorialClass
     * @param tutorialTeam
     */
    public DeleteStudentFromTeamByEmailCommand(Email email, ModuleCode module, TutorialClass tutorialClass,
                                               TutorialTeam tutorialTeam) {
        super(module, tutorialClass, tutorialTeam);
        this.email = email;
        this.predicate = person -> person.getEmail().equals(email);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ModuleTutorialPair moduleAndTutorialClass = ModuleTutorialPair.getModuleAndTutorialClass(model,
                getModule(), getTutorialClass());
        TutorialClass tutorialClass = moduleAndTutorialClass.getTutorialClass();
        ModuleCode module = moduleAndTutorialClass.getModule();

        TutorialTeam team = tutorialClass.getTutorialTeam(tutorialClass, tutorialTeam);
        if (team == null) {
            throw new CommandException(String.format(TeamMessages.MESSAGE_TEAM_DOES_NOT_EXIST, tutorialTeam,
                    tutorialClass));
        }

        Person personToDelete;
        personToDelete = model.searchPersonByPredicate(predicate);
        if (personToDelete == null) {
            throw new CommandException(String.format(PersonMessages.MESSAGE_PERSON_EMAIL_NOT_FOUND, email));
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
     * Returns true if both DeleteStudentFromTeamByEmailCommand have the same email.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStudentFromTeamByEmailCommand)) {
            return false;
        }

        DeleteStudentFromTeamByEmailCommand otherDeleteCommand = (DeleteStudentFromTeamByEmailCommand) other;
        return email.equals(otherDeleteCommand.email);
    }
}
