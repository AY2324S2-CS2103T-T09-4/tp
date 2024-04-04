package seedu.address.logic.commands.deletestudentfromclasscommands;

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

    protected ModuleCode getModule() {
        return module;
    }

    protected TutorialClass getTutorialClass() {
        return tutorialClass;
    }

    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract boolean equals(Object other);
}
