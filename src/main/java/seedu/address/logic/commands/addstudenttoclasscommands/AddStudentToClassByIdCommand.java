package seedu.address.logic.commands.addstudenttoclasscommands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.PersonMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTutorialPair;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Adds a student to a class by student id.
 */
public class AddStudentToClassByIdCommand extends AddStudentToClassCommand {

    private final Predicate<Person> predicate;

    private final StudentId studentId;

    /**
     * Adds a student to a class by student id.
     * @param studentId
     * @param module
     * @param tutorialClass
     */
    public AddStudentToClassByIdCommand(StudentId studentId, ModuleCode module, TutorialClass tutorialClass) {
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
        Person personToAdd;
        personToAdd = model.searchPersonByPredicate(predicate);
        if (personToAdd == null) {
            throw new CommandException(String.format(PersonMessages.MESSAGE_PERSON_STUDENT_ID_NOT_FOUND, studentId));
        }
        checkIfCanAddStudent(tutorialClass, personToAdd);
        model.addPersonToTutorialClass(personToAdd, module, tutorialClass);
        return new CommandResult(String.format(PersonMessages.MESSAGE_ADD_STUDENT_TO_CLASS_SUCCESS,
                Messages.format(personToAdd), module, tutorialClass));
    }

    /**
     * Returns true if both AddStudentToClassByIdCommand have the same studentId.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddStudentToClassByIdCommand)) {
            return false;
        }

        AddStudentToClassByIdCommand otherAddCommand = (AddStudentToClassByIdCommand) other;
        return studentId.equals(otherAddCommand.studentId);
    }
}
