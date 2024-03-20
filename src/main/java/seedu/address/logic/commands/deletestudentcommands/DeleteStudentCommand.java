package seedu.address.logic.commands.deletestudentcommands;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Person;


/**
 * Abstract class for DeleteStudentCommand
 */
public abstract class DeleteStudentCommand extends Command {

    public static final String COMMAND_WORD = "/delete_student";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by parameter in the displayed students list.\n"
            + "Parameters: index/INDEX or email/EMAIL or id/STUDENT ID\n"
            + "Example: " + COMMAND_WORD + " email/johndoe@gmail.com";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Student: %1$s";

    public static final String MESSAGE_MISSING_IDENTIFIER = "Missing identifier. "
            + "Please provide either a student ID or an email address.";

    public DeleteStudentCommand() {

    }

    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract boolean equals(Object other);

    public abstract String toString();

    /**
     * Deletes a specified student from all tutorial classes from all modules in a model.
     *
     * @param model that contains the modules and tutorial classes
     * @param student to be deleted
     */
    public void deleteStudentFromTutorialClasses(Model model, Person student) {
        ObservableList<ModuleCode> list = model.getAddressBook().getModuleList();
        for (ModuleCode module : list) {
            ArrayList<TutorialClass> tutorialClassesOfModule = module.getTutorialClasses();
            for (TutorialClass tutorialClass : tutorialClassesOfModule) {
                tutorialClass.deleteStudent(student);
            }
        }
    }
}
