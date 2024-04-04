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
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;

/**
 * Deletes a student from a specified tutorial class, by identifying
 * the student via their email.
 */
public class DeleteStudentFromClassByEmailCommand extends DeleteStudentFromClassCommand {
    private final Predicate<Person> predicate;

    private final Email email;

    /**
     * Deletes a student from a class by email.
     * @param email
     * @param module
     * @param tutorialClass
     */
    public DeleteStudentFromClassByEmailCommand(Email email, ModuleCode module, TutorialClass tutorialClass) {
        super(module, tutorialClass);
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
        Person personToDelete;

        personToDelete = model.searchPersonByPredicate(predicate);
        if (personToDelete == null) {
            throw new CommandException(String.format(PersonMessages.MESSAGE_PERSON_EMAIL_NOT_FOUND, email));
        }
        if (!(tutorialClass.hasStudent(personToDelete))) {
            throw new CommandException(
                    String.format(TutorialClassMessages.MESSAGE_STUDENT_NOT_FOUND_IN_CLASS,
                            Messages.format(personToDelete), tutorialClass));
        } else {
            model.deletePersonFromTutorialClass(personToDelete, module, tutorialClass);
            return new CommandResult(
                    String.format(TutorialClassMessages.MESSAGE_DELETE_STUDENT_FROM_CLASS_SUCCESS,
                            Messages.format(personToDelete), module, tutorialClass));
        }
    }

    /**
     * Returns true if both DeleteStudentFromClassByEmailCommand have the same email.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStudentFromClassByEmailCommand)) {
            return false;
        }

        DeleteStudentFromClassByEmailCommand otherDeleteCommand = (DeleteStudentFromClassByEmailCommand) other;
        return email.equals(otherDeleteCommand.email);
    }
}
