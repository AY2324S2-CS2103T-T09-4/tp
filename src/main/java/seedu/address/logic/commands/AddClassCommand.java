package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * A class that handles the /add_class command execution.
 */
public class AddClassCommand extends Command {
    public static final String MESSAGE_ADD_CLASS_SUCCESS = "Added %1$s %2$s";
    public static final String MESSAGE_DUPLICATE_CLASS = "%1$s %2$s already added!";
    public static final String COMMAND_WORD = "/add_class";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a class with the module code specified\n"
        + "Parameters:" + PREFIX_MODULECODE + "MODULE_CODE (must be a String) "
        + PREFIX_TUTORIALCLASS + "TUTORIAL_CLASS (must be a String)"
        + "Example: " + COMMAND_WORD + PREFIX_MODULECODE + " CS2103T "
        + PREFIX_TUTORIALCLASS + "T09";

    private final ModuleCode module;
    private final TutorialClass tutorialString;

    /**
     * @param module of the tutorial class to be added
     */
    public AddClassCommand(ModuleCode module, TutorialClass tutorialClass) {
        requireAllNonNull(module);
        this.module = module;
        this.tutorialString = tutorialClass;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ModuleCode existingModule = model.findModuleFromList(module);
        if (existingModule != null) {
            if (existingModule.hasTutorialClass(tutorialString)) {
                String duplicateMessage = String.format(MESSAGE_DUPLICATE_CLASS, module, tutorialString);
                throw new CommandException(duplicateMessage);
            } else {
                existingModule.addTutorialClass(tutorialString);
            }
        } else {
            module.addTutorialClass(tutorialString);
            model.addModule(module);
        }
        return new CommandResult(generateSuccessMessage(module, tutorialString));
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(ModuleCode module, TutorialClass tutorialString) {
        return String.format(MESSAGE_ADD_CLASS_SUCCESS, module.toString(), tutorialString.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddClassCommand)) {
            return false;
        }

        AddClassCommand e = (AddClassCommand) other;
        return module.equals(e.module);
    }
}
