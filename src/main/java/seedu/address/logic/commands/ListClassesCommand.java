package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all modules and their associated tutorial classes in the address book to the user.
 */
public class ListClassesCommand extends Command {

    public static final String COMMAND_WORD = "/list_classes";
    public static final String MESSAGE_SUCCESS = "Listed all modules and their tutorial classes";
    public static final String MESSAGE_EMPTY = "No classes available!";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (model.getAddressBook().getModuleList().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }

        StringBuilder result = new StringBuilder();
        model.getAddressBook().getModuleList().forEach(module -> {
            result.append(module.toString()).append(": ");
            module.getTutorialClasses().forEach(tutorialClass -> result.append(tutorialClass.toString()).append(", "));
            result.append("\n");
        });

        return new CommandResult(result.toString().trim());
    }
}
