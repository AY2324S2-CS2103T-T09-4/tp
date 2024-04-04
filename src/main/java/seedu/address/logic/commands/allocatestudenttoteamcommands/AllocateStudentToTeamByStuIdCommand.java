package seedu.address.logic.commands.allocatestudenttoteamcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.commons.util.CollectionUtil;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Allocates a student to a team in a tutorial Class in TAHelper.
 */
public class AllocateStudentToTeamByStuIdCommand extends AllocateStudentToTeamCommand {

    public static final String COMMAND_WORD = "/allocate_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allocates a student a team in the tutorial class.\n"
            + "Parameters: "
            + PREFIX_STUDENTID + "STUDENT ID "
            + PREFIX_MODULECODE + "MODULE CODE "
            + PREFIX_TUTORIALCLASS + "TUTORIAL CLASS "
            + PREFIX_TEAMNAME + "TEAM NAME"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENTID + "A1234567L "
            + PREFIX_MODULECODE + "CS2101 "
            + PREFIX_TUTORIALCLASS + "T01 "
            + PREFIX_TEAMNAME + "Team 1 ";

    private final StudentId studentId;
    private final ModuleCode moduleCode;
    private final TutorialClass tutorialClass;
    private final TutorialTeam tutorialTeam;

    /**
     * Creates an AllocateStudentToTeam object.
     */
    public AllocateStudentToTeamByStuIdCommand(StudentId studentId, ModuleCode moduleCode,
            TutorialClass tutorialClass, TutorialTeam tutorialTeam) {
        CollectionUtil.requireAllNonNull(studentId, moduleCode, tutorialClass, tutorialTeam);
        this.studentId = studentId;
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

        Person student = model.getUniquePersonList().getPerson(studentId);
        TutorialTeam tutTeam = tutClass.getTutorialTeam(tutClass, tutorialTeam);

        if (student == null) {
            throw new CommandException(String.format(PersonMessages.MESSAGE_PERSON_STUDENT_ID_NOT_FOUND, studentId));
        }

        if (tutTeam == null) {
            throw new CommandException(
                    String.format(TutorialTeamMessages.MESSAGE_TEAM_NOT_FOUND, tutorialTeam, moduleCode, tutClass));
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

        AllocateStudentToTeamByStuIdCommand otherAllocateCommand = (AllocateStudentToTeamByStuIdCommand) other;
        return this.studentId.equals(otherAllocateCommand.studentId)
                && this.moduleCode.equals(otherAllocateCommand.moduleCode)
                && this.tutorialClass.equals(otherAllocateCommand.tutorialClass)
                && this.tutorialTeam.equals(otherAllocateCommand.tutorialTeam);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("moduleCode", moduleCode)
                .add("tutorialClass", tutorialClass)
                .add("tutorialTeam", tutorialTeam)
                .toString();
    }
}
