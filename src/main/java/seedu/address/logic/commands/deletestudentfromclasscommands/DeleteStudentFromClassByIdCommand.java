package seedu.address.logic.commands.deletestudentfromclasscommands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

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
import seedu.address.model.person.StudentId;

/**
 * Deletes a student from a specified tutorial class, by identifying
 * the student via their Student ID.
 */
public class DeleteStudentFromClassByIdCommand extends DeleteStudentFromClassCommand {

    private final Predicate<Person> predicate;

    private final StudentId studentId;

    /**
     * Deletes a student from a class by student id.
     * @param studentId
     * @param module
     * @param tutorialClass
     */
    public DeleteStudentFromClassByIdCommand(StudentId studentId, ModuleCode module, TutorialClass tutorialClass) {
        super(module, tutorialClass);
        this.studentId = studentId;
        this.predicate = person -> person.getStudentId().equals(studentId);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ModuleTutorialPair moduleAndTutorialClass = ModuleTutorialPair.getModuleAndTutorialClass(model,
                getModule(), getTutorialClass());
        ModuleCode module = moduleAndTutorialClass.getModule();
        TutorialClass tutorialClass = moduleAndTutorialClass.getTutorialClass();
        Person personToDelete;
        personToDelete = model.searchPersonByPredicate(predicate);
        if (personToDelete == null) {
            throw new CommandException(String.format(PersonMessages.MESSAGE_PERSON_STUDENT_ID_NOT_FOUND, studentId));
        }
        if (!(tutorialClass.hasStudent(personToDelete))) {
            throw new CommandException(String.format(TutorialClassMessages.MESSAGE_STUDENT_NOT_FOUND_IN_CLASS,
                    Messages.format(personToDelete), tutorialClass));
        } else {
            model.deletePersonFromTutorialClass(personToDelete, module, tutorialClass);
            return new CommandResult(String.format(PersonMessages.MESSAGE_DELETE_STUDENT_FROM_CLASS_SUCCESS,
                    Messages.format(personToDelete), module, tutorialClass));
        }
    }

    /**
     * Returns true if both DeleteStudentFromClassByIdCommand have the same studentId.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStudentFromClassByIdCommand)) {
            return false;
        }

        DeleteStudentFromClassByIdCommand otherDeleteCommand = (DeleteStudentFromClassByIdCommand) other;
        return studentId.equals(otherDeleteCommand.studentId);
    }
}
