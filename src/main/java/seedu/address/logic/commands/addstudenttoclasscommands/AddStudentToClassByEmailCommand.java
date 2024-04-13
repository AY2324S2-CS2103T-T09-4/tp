package seedu.address.logic.commands.addstudenttoclasscommands;

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
 * Adds a student to a class by email.
 */
public class AddStudentToClassByEmailCommand extends AddStudentToClassCommand {

    private final Predicate<Person> predicate;

    private final Email email;

    /**
     * Adds a student to a class by email.
     * @param email
     * @param module
     * @param tutorialClass
     */
    public AddStudentToClassByEmailCommand(Email email, ModuleCode module, TutorialClass tutorialClass) {
        super(module, tutorialClass);
        this.email = email;
        this.predicate = person -> person.getEmail().equals(email);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            ModuleTutorialPair moduleAndTutorialClass = ModuleTutorialPair.getModuleAndTutorialClass(model,
                    getModule(), getTutorialClass());
            TutorialClass tutorialClass = moduleAndTutorialClass.getTutorialClass();
            ModuleCode module = moduleAndTutorialClass.getModule();
            Person personToAdd;
            personToAdd = model.searchPersonByPredicate(predicate);
            if (personToAdd == null) {
                throw new CommandException(String.format(PersonMessages.MESSAGE_PERSON_EMAIL_NOT_FOUND, email));
            }
            checkIfCanAddStudent(tutorialClass, personToAdd);
            model.addPersonToTutorialClass(personToAdd, module, tutorialClass);
            model.getAddressBook().setStudentsInTutorialClass(tutorialClass);
            return new CommandResult(
                    String.format(TutorialClassMessages.MESSAGE_ADD_STUDENT_TO_CLASS_SUCCESS,
                            Messages.format(personToAdd), module, tutorialClass));
        } catch (CommandException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Returns true if both AddStudentToClassByEmailCommand have the same email.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddStudentToClassByEmailCommand)) {
            return false;
        }

        AddStudentToClassByEmailCommand otherAddCommand = (AddStudentToClassByEmailCommand) other;
        return email.equals(otherAddCommand.email);
    }
}
