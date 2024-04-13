package seedu.address.logic.commands.allocatestudenttoteamcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.ModuleMessages;
import seedu.address.logic.messages.PersonMessages;
import seedu.address.logic.messages.TutorialClassMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Person;

/**
 * Allocates a student to a team in a tutorial Class in TAHelper.
 */
public class AllocateStudentToTeamByIndexCommand extends AllocateStudentToTeamCommand {

    public static final String COMMAND_WORD = "/allocate_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allocates a student a team in the tutorial class.\n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_MODULECODE + "MODULE CODE "
            + PREFIX_TUTORIALCLASS + "TUTORIAL CLASS "
            + PREFIX_TEAMNAME + "TEAM NAME"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_MODULECODE + "CS2101 "
            + PREFIX_TUTORIALCLASS + "T01 "
            + PREFIX_TEAMNAME + "Team 1 ";

    private final Index index;
    private final ModuleCode moduleCode;
    private final TutorialClass tutorialClass;
    private final TutorialTeam tutorialTeam;

    /**
     * Creates an AllocateStudentToTeam object based on an Index.
     */
    public AllocateStudentToTeamByIndexCommand(Index index, ModuleCode moduleCode,
            TutorialClass tutorialClass, TutorialTeam tutorialTeam) {
        CollectionUtil.requireAllNonNull(index, moduleCode, tutorialClass, tutorialTeam);
        this.index = index;
        this.moduleCode = moduleCode;
        this.tutorialClass = tutorialClass;
        this.tutorialTeam = tutorialTeam;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        boolean doesTutorialExist = model.findTutorialClassFromList(tutorialClass, moduleCode) != null;
        if (!doesTutorialExist) {
            throw new CommandException(String.format(ModuleMessages.MESSAGE_TUTORIAL_DOES_NOT_BELONG_TO_MODULE,
                    tutorialClass, moduleCode));
        }

        ModuleCode module = model.findModuleFromList(moduleCode);
        TutorialClass tutClass = model.findTutorialClassFromList(tutorialClass, module);

        Person studentToAllocate;
        try {
            studentToAllocate = model.getStudentsInTutorialClass(tutClass).get(index.getZeroBased());
        } catch (IndexOutOfBoundsException err) {
            throw new CommandException(
                    String.format(TutorialClassMessages.MESSAGE_PERSON_INDEX_NOT_FOUND_IN_CLASS,
                            index.getOneBased(), tutClass));
        }

        TutorialTeam tutTeam = tutClass.getTutorialTeam(tutClass, tutorialTeam);
        boolean doesTutTeamExist = tutTeam != null;
        if (!doesTutTeamExist) {
            throw new CommandException(
                    String.format(PersonMessages.MESSAGE_PERSON_INDEX_NOT_FOUND, index.getOneBased()));
        }

        // throws commandException if any condition fails
        checkAllocateCondition(studentToAllocate, tutClass, tutTeam);
        model.allocateStudentToTeam(studentToAllocate, tutTeam);
        model.getAddressBook().setStudentsInTeam(tutTeam);

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

        AllocateStudentToTeamByIndexCommand otherAllocateCommand = (AllocateStudentToTeamByIndexCommand) other;
        return this.index.equals(otherAllocateCommand.index)
                && this.moduleCode.equals(otherAllocateCommand.moduleCode)
                && this.tutorialClass.equals(otherAllocateCommand.tutorialClass)
                && this.tutorialTeam.equals(otherAllocateCommand.tutorialTeam);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("moduleCode", moduleCode)
                .add("tutorialClass", tutorialClass)
                .add("tutorialTeam", tutorialTeam)
                .toString();
    }
}
