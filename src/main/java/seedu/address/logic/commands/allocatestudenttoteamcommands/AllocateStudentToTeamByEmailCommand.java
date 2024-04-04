package seedu.address.logic.commands.allocatestudenttoteamcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.ModuleMessages;
import seedu.address.logic.messages.PersonMessages;
import seedu.address.logic.messages.TutorialTeamMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;

/**
 * Allocates a student to a team in a tutorial Class in TAHelper.
 */
public class AllocateStudentToTeamByEmailCommand extends AllocateStudentToTeamCommand {

    public static final String COMMAND_WORD = "/allocate_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allocates a student a team in the tutorial class.\n"
            + "Parameters: "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_MODULECODE + "MODULE CODE "
            + PREFIX_TUTORIALCLASS + "TUTORIAL CLASS "
            + PREFIX_TEAMNAME + "TEAM NAME"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL + "zack@bmail.com "
            + PREFIX_MODULECODE + "CS2101 "
            + PREFIX_TUTORIALCLASS + "T01 "
            + PREFIX_TEAMNAME + "Team 1 ";

    private final Email email;
    private final ModuleCode moduleCode;
    private final TutorialClass tutorialClass;
    private final TutorialTeam tutorialTeam;

    /**
     * Creates an AllocateStudentToTeam object.
     */
    public AllocateStudentToTeamByEmailCommand(Email email, ModuleCode moduleCode,
            TutorialClass tutorialClass, TutorialTeam tutorialTeam) {
        this.email = email;
        this.moduleCode = moduleCode;
        this.tutorialClass = tutorialClass;
        this.tutorialTeam = tutorialTeam;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.findTutorialClassFromList(tutorialClass, moduleCode) == null) {
            throw new CommandException(String.format(ModuleMessages.MESSAGE_TUTORIAL_DOES_NOT_BELONG_TO_MODULE,
                    tutorialClass, moduleCode));
        }

        ModuleCode module = model.findModuleFromList(moduleCode);
        TutorialClass tutClass = model.findTutorialClassFromList(tutorialClass, module);

        Person student = model.getUniquePersonList().getPersonByEmail(email);
        TutorialTeam tutTeam = tutClass.getTutorialTeam(tutClass, tutorialTeam);

        if (student == null) {
            throw new CommandException(String.format(PersonMessages.MESSAGE_PERSON_EMAIL_NOT_FOUND, email));
        }

        if (tutTeam == null) {
            throw new CommandException(String.format(TutorialTeamMessages.MESSAGE_TEAM_NOT_FOUND,
                    tutorialTeam, moduleCode, tutClass));
        }

        // throws commandException if any condition fails
        checkAllocateCondition(model, student, tutClass, tutTeam, moduleCode);
        model.allocateStudentToTeam(student, tutTeam);

        return new CommandResult(String.format(MESSAGE_SUCCESS, tutTeam));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AllocateStudentToTeamCommand)) {
            return false;
        }

        AllocateStudentToTeamByEmailCommand otherAllocateCommand = (AllocateStudentToTeamByEmailCommand) other;
        return this.email.equals(otherAllocateCommand.email)
                && this.moduleCode.equals(otherAllocateCommand.moduleCode)
                && this.tutorialClass.equals(otherAllocateCommand.tutorialClass)
                && this.tutorialTeam.equals(otherAllocateCommand.tutorialTeam);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("email", email)
                .add("moduleCode", moduleCode)
                .add("tutorialClass", tutorialClass)
                .add("tutorialTeam", tutorialTeam)
                .toString();
    }
}
