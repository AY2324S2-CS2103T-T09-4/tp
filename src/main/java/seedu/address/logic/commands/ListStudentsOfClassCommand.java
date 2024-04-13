package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * A command to list all students of a particular tutorial class.
 */
public class ListStudentsOfClassCommand extends Command {

    public static final String COMMAND_WORD = "/class_list_students";
    public static final String MESSAGE_STUDENT_LIST_EMPTY = "No students found in the specified tutorial class";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List students of the tutorial class. \n"
        + "Parameters: "
        + PREFIX_MODULECODE + "MODULE CODE "
        + PREFIX_TUTORIALCLASS + "TUTORIAL CLASS "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_MODULECODE + "CS2103T "
        + PREFIX_TUTORIALCLASS + "T09 ";

    private final ModuleCode module;
    private final TutorialClass tutorialClass;

    /**
     * Creates a ListStudentsCommand to list students of the specified tutorial class.
     *
     * @param module        The module code.
     * @param tutorialClass The tutorial class.
     */
    public ListStudentsOfClassCommand(ModuleCode module, TutorialClass tutorialClass) {
        requireNonNull(module);
        this.module = module;
        this.tutorialClass = tutorialClass;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ModuleCode existingModule = model.findModuleFromList(module);
        if (existingModule == null || !existingModule.hasTutorialClass(tutorialClass)) {
            return new CommandResult(String.format("Module %s or tutorial class %s not found",
                module, tutorialClass, MESSAGE_STUDENT_LIST_EMPTY));
        }
        TutorialClass existingTutorialClass = model.findTutorialClassFromList(tutorialClass, module);
        if (existingTutorialClass.getStudents().isEmpty()) {
            return new CommandResult(MESSAGE_STUDENT_LIST_EMPTY);
        }

        StringBuilder result = new StringBuilder();
        result.append("Module: ").append(module).append(", Tutorial Class: ")
            .append(tutorialClass).append("\nStudents: ");
        existingTutorialClass.getStudents().forEach(student -> result.append(student.getName())
            .append(" (ID: ").append(student.getStudentId()).append("), "));
        model.getAddressBook().setStudentsInTutorialClass(existingTutorialClass);
        return new CommandResult(result.toString().trim());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListStudentsOfClassCommand)) {
            return false;
        }
        ListStudentsOfClassCommand command = (ListStudentsOfClassCommand) other;
        return module.equals(command.module) && tutorialClass.equals(command.tutorialClass);
    }
}
