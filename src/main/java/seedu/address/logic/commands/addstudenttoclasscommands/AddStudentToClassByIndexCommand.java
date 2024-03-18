package seedu.address.logic.commands.addstudenttoclasscommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.PersonMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTutorialPair;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Person;

/**
 * Adds a student to a class by index.
 */
public class AddStudentToClassByIndexCommand extends AddStudentToClassCommand {

    private final Index targetIndex;

    /**
     * Adds a student to a class by index.
     * @param targetIndex
     * @param module
     * @param tutorialClass
     */
    public AddStudentToClassByIndexCommand(Index targetIndex, ModuleCode module, TutorialClass tutorialClass) {
        super(module, tutorialClass);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ModuleTutorialPair moduleAndTutorialClass = getModuleAndTutorialClass(model);
        TutorialClass tutorialClass = moduleAndTutorialClass.getTutorialClass();
        Person personToAdd;
        try {
            personToAdd = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(
                    String.format(PersonMessages.MESSAGE_PERSON_INDEX_NOT_FOUND, targetIndex.getOneBased()));
        }
        if (tutorialClass.hasStudent(personToAdd)) {
            throw new CommandException(String.format(PersonMessages.MESSAGE_DUPLICATE_STUDENT_IN_CLASS, personToAdd,
                    tutorialClass));
        } else {
            tutorialClass.addStudent(personToAdd);
            return new CommandResult(String.format(PersonMessages.MESSAGE_ADD_STUDENT_TO_CLASS_SUCCESS, personToAdd));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddStudentToClassByIdCommand)) {
            return false;
        }

        AddStudentToClassByIndexCommand otherAddCommand = (AddStudentToClassByIndexCommand) other;
        return targetIndex.equals(otherAddCommand.targetIndex);
    }
}
