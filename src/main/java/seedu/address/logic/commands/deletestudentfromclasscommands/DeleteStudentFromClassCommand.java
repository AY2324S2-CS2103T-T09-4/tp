package seedu.address.logic.commands.deletestudentfromclasscommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.ModuleMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTutorialPair;
import seedu.address.model.module.TutorialClass;

/**
 * The abstract class that handles all delete student from tutorial class commands.
 */
public abstract class DeleteStudentFromClassCommand extends Command {
    public static final String COMMAND_WORD = "/delete_student_from_class";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a student from an existing class\n"
            + "Parameters:" + "IDENTIFIER " + PREFIX_MODULECODE + "MODULE_CODE (must be a String) "
            + PREFIX_TUTORIALCLASS + "TUTORIAL_CLASS (must be a String)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EMAIL + "test@gmail.com " + PREFIX_MODULECODE + " CS2103T "
            + PREFIX_TUTORIALCLASS + "T09";

    private final ModuleCode module;
    private final TutorialClass tutorialClass;

    /**
     * @param module of the tutorial class to be added
     */
    public DeleteStudentFromClassCommand(ModuleCode module, TutorialClass tutorialClass) {
        requireAllNonNull(module, tutorialClass);
        this.module = module;
        this.tutorialClass = tutorialClass;
    }

    protected ModuleTutorialPair getModuleAndTutorialClass(Model model) throws CommandException {
        requireNonNull(model);
        ModuleCode module = getModule();
        TutorialClass tutorialClass = getTutorialClass();
        ModuleCode existingModule = model.findModuleFromList(module);
        TutorialClass existingTutorialClass = model.findTutorialClassFromList(tutorialClass, existingModule);
        if (existingModule == null) {
            throw new CommandException(String.format(ModuleMessages.MESSAGE_MODULE_NOT_FOUND, module));
        }
        if (existingTutorialClass == null) {
            throw new CommandException(
                    String.format(ModuleMessages.MESSAGE_TUTORIAL_DOES_NOT_BELONG_TO_MODULE, tutorialClass, module));
        }
        return new ModuleTutorialPair(existingModule, existingTutorialClass);
    }

    protected ModuleCode getModule() {
        return module;
    }

    protected TutorialClass getTutorialClass() {
        return tutorialClass;
    }

    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract boolean equals(Object other);
}
