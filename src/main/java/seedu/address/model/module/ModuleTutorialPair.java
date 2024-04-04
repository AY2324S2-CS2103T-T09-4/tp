package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.ModuleMessages;
import seedu.address.model.Model;


/**
 * Represents a pair of a module and a tutorial class.
 */
public class ModuleTutorialPair {
    private ModuleCode module;
    private TutorialClass tutorialClass;

    /**
     * Creates a ModuleTutorialPair object.
     * @param module        The module code.
     * @param tutorialClass The tutorial class.
     */
    public ModuleTutorialPair(ModuleCode module, TutorialClass tutorialClass) {
        this.module = module;
        this.tutorialClass = tutorialClass;
    }

    /**
     * Gets the module code.
     * @return The module code.
     */
    public ModuleCode getModule() {
        return module;
    }

    /**
     * Gets the tutorial class.
     * @return The tutorial class.
     */
    public TutorialClass getTutorialClass() {
        return tutorialClass;
    }

    @Override
    public String toString() {
        return "(" + module + ", " + tutorialClass + ")";
    }

    public static ModuleTutorialPair getModuleAndTutorialClass(Model model, ModuleCode module,
                                                           TutorialClass tutorialClass) throws CommandException {
        requireNonNull(model);
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
}
