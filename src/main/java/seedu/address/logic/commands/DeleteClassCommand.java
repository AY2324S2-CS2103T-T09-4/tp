package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.ModuleMessages;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * A class used to handle the deletion of tutorial classes.
 */
public class DeleteClassCommand extends Command {
    public static final String MESSAGE_DELETE_CLASS_SUCCESS = "Removed %1$s %2$s!";
    public static final String MESSAGE_MODULE_NOT_FOUND = "%1$s not in list!";
    public static final String MESSAGE_CLASS_NOT_FOUND = "%1$s %2$s not in list!";
    public static final String COMMAND_WORD = "/delete_class";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a class with the module code and tutorial class specified\n"
            + "Parameters:" + PREFIX_MODULECODE + "MODULE_CODE "
            + PREFIX_TUTORIALCLASS + "TUTORIAL_CLASS\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MODULECODE + " CS2103T "
            + PREFIX_TUTORIALCLASS + "T09";

    private final ModuleCode module;
    private final TutorialClass tutorialString;

    /**
     * Constructs a DeleteClassCommand to delete the specified {@code TutorialClass}
     * from the specified {@code ModuleCode}.
     * 
     * @param module        The module code of the tutorial class to be deleted.
     * @param tutorialClass The tutorial class to be deleted.
     */
    public DeleteClassCommand(ModuleCode module, TutorialClass tutorialClass) {
        requireAllNonNull(module);
        this.module = module;
        this.tutorialString = tutorialClass;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ModuleCode existingModule = model.findModuleFromList(module);
        if (existingModule == null) {
            String moduleNotFoundMessage = String.format(ModuleMessages.MESSAGE_MODULE_NOT_FOUND, module);
            throw new CommandException(moduleNotFoundMessage);
        }

        if (!(existingModule.hasTutorialClass(tutorialString))) {
            String classNotFoundMessage = String.format(ModuleMessages.MESSAGE_TUTORIAL_DOES_NOT_BELONG_TO_MODULE,
                    tutorialString, module);
            String tutorialList = existingModule.listTutorialClasses();
            throw new CommandException(classNotFoundMessage + "\n" + tutorialList);
        } else {
            existingModule.deleteTutorialClass(tutorialString);
        }

        return new CommandResult(generateSuccessMessage(module, tutorialString));
    }

    /**
     * Generates a command execution success message based on whether the tutorial
     * class is successfully deleted.
     * 
     * @param module         The module code of the tutorial class.
     * @param tutorialString The tutorial class.
     * @return The success message.
     */
    private String generateSuccessMessage(ModuleCode module, TutorialClass tutorialString) {
        return String.format(MESSAGE_DELETE_CLASS_SUCCESS, module.toString(), tutorialString.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteClassCommand)) {
            return false;
        }

        DeleteClassCommand e = (DeleteClassCommand) other;
        return module.equals(e.module);
    }
}
