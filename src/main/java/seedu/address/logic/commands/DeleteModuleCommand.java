package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.messages.ModuleMessages.MESSAGE_DELETE_MODULE_SUCCESS;
import static seedu.address.logic.messages.ModuleMessages.MESSAGE_MODULE_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;

/**
 * A class used to handle the deletion of tutorial classes.
 */
public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = "/delete_module";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a module with the module code specified\n"
            + "Parameters:" + PREFIX_MODULECODE + "MODULE_CODE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MODULECODE + " CS2103T";

    private final ModuleCode module;

    /**
     * Constructs a DeleteClassCommand to delete the specified {@code TutorialClass}
     * from the specified {@code ModuleCode}.
     * @param module The module code of the tutorial class to be deleted.
     */
    public DeleteModuleCommand(ModuleCode module) {
        requireAllNonNull(module);
        this.module = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ModuleCode existingModule = model.findModuleFromList(module);
        if (existingModule == null) {
            String moduleNotFoundMessage = String.format(MESSAGE_MODULE_NOT_FOUND, module);
            throw new CommandException(moduleNotFoundMessage);
        } else {
            model.deleteModule(existingModule);
        }
        return new CommandResult(generateSuccessMessage(module));
    }

    /**
     * Generates a command execution success message based on whether the tutorial class is successfully deleted.
     * @param module The module code of the tutorial class.
     * @return The success message.
     */
    private String generateSuccessMessage(ModuleCode module) {
        return String.format(MESSAGE_DELETE_MODULE_SUCCESS, module.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteModuleCommand)) {
            return false;
        }

        DeleteModuleCommand e = (DeleteModuleCommand) other;
        return module.equals(e.module);
    }
}
