package seedu.address.logic.commands.addstudenttoclasscommands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * A class that handles the /add_student_to_class command execution.
 */
public abstract class AddStudentToClassCommand extends Command {
    public static final String COMMAND_WORD = "/add_student_to_class";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to an existing class\n"
            + "Parameters:" + "IDENTIFIER " + PREFIX_MODULECODE + "MODULE_CODE (must be a String) "
            + PREFIX_TUTORIALCLASS + "TUTORIAL_CLASS (must be a String)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EMAIL + "test@gmail.com " + PREFIX_MODULECODE + " CS2103T "
            + PREFIX_TUTORIALCLASS + "T09";

    private final ModuleCode module;
    private final TutorialClass tutorialClass;

    /**
     * @param module of the tutorial class to be added
     */
    public AddStudentToClassCommand(ModuleCode module, TutorialClass tutorialClass) {
        requireAllNonNull(module, tutorialClass);
        this.module = module;
        this.tutorialClass = tutorialClass;
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
