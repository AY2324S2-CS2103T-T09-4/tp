package seedu.address.logic.commands.deletestudentfromclasscommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.PersonMessages;
import seedu.address.logic.messages.TutorialClassMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTutorialPair;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Person;


/**
 * Deletes a student from a specified tutorial class, by identifying
 * the student via their index.
 */
public class DeleteStudentFromClassByIndexCommand extends DeleteStudentFromClassCommand {
    private final Index targetIndex;

    /**
     * Deletes a student from a class by index.
     * @param targetIndex
     * @param module
     * @param tutorialClass
     */
    public DeleteStudentFromClassByIndexCommand(Index targetIndex, ModuleCode module, TutorialClass tutorialClass) {
        super(module, tutorialClass);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ModuleTutorialPair moduleAndTutorialClass = ModuleTutorialPair.getModuleAndTutorialClass(model,
                getModule(), getTutorialClass());
        TutorialClass tutorialClass = moduleAndTutorialClass.getTutorialClass();
        ModuleCode module = moduleAndTutorialClass.getModule();
        Person personToDelete;
        try {
            personToDelete = model.getStudentsInTutorialClass(tutorialClass).get(targetIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(
                    String.format(TutorialClassMessages.MESSAGE_PERSON_INDEX_NOT_FOUND, targetIndex.getOneBased(),
                            tutorialClass));
        }

        if (!(tutorialClass.hasStudent(personToDelete))) {
            throw new CommandException(String.format(TutorialClassMessages.MESSAGE_STUDENT_NOT_FOUND_IN_CLASS,
                    Messages.format(personToDelete), tutorialClass));
        } else {
            model.deletePersonFromTutorialClass(personToDelete, module, tutorialClass);
            return new CommandResult(
                    String.format(PersonMessages.MESSAGE_DELETE_STUDENT_FROM_CLASS_SUCCESS,
                            Messages.format(personToDelete), module, tutorialClass));
        }
    }

    /**
     * Returns true if both DeleteStudentFromClassByIndexCommand have the same index.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStudentFromClassByIndexCommand)) {
            return false;
        }

        DeleteStudentFromClassByIndexCommand otherDeleteCommand = (DeleteStudentFromClassByIndexCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }
}
