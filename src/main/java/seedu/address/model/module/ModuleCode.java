package seedu.address.model.module;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

/**
 * Represents a Module's module code.
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleCode(String)}
 */
public class ModuleCode {

    public static final String MESSAGE_CONSTRAINTS =
        "Please enter a valid NUS module code eg. ACC1701X, CS1101S, and it should not be blank";

    /**
     * This regex validates the module code that user enters.
     * Supports format like "CS1101S", "CS2106" and "ACC1701X".
     */
    public static final String VALIDATION_REGEX = "^[A-Z]{2,3}\\d{4}[A-Z]?$";

    public final String moduleCode;
    private final ArrayList<TutorialClass> tutorialClasses;

    /**
     * A constructor for Module. Used to initialise a new module with no tutorial classes
     *
     * @param moduleCode of the module to be created
     */
    public ModuleCode(String moduleCode) {
        requireAllNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode;
        this.tutorialClasses = new ArrayList<TutorialClass>();
    }

    /**
     * A constructor for Module. Used to initialise a new module with the tutorial class specified.
     * This is the constructor used when /add_class is used.
     *
     * @param moduleCode of the module to be created
     * @param tutorialClass to be added within the module
     */
    public ModuleCode(String moduleCode, String tutorialClass) {
        requireAllNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode;
        this.tutorialClasses = new ArrayList<TutorialClass>();
        tutorialClasses.add(new TutorialClass(tutorialClass));
    }

    /**
     * A constructor for Module. Used to initialise a new module with the list of tutorial classes specified.
     * Used to get the representation of the module from the list of classes and the moduleCode specified.
     *
     * @param moduleCode of the module to be created
     * @param tutorialClasses of the module to be created
     */
    public ModuleCode(String moduleCode, ArrayList<TutorialClass> tutorialClasses) {
        requireAllNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode;
        this.tutorialClasses = tutorialClasses;
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the ArrayList of tutorial classes under this module.
     *
     * @return the ArrayList of tutorial classes.
     */
    public ArrayList<TutorialClass> getTutorialClasses() {
        return tutorialClasses;
    }

    /**
     * Get the instance's object.
     * @return A ModuleCode instance object.
     */
    public ModuleCode getModule() {
        return this;
    }

    @Override
    public String toString() {
        return moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCode)) {
            return false;
        }

        ModuleCode otherModuleCode = (ModuleCode) other;
        return moduleCode.equals(otherModuleCode.moduleCode);
    }

    @Override
    public int hashCode() {
        return moduleCode.hashCode();
    }

    /**
     * Checks if the given tutorial class is already in the list.
     *
     * @param tutorialClass name of the tutorial class to be checked
     * @return true if the class name is in the list. False otherwise.
     */
    public boolean hasTutorialClass(TutorialClass tutorialClass) {
        for (TutorialClass tutorialClassInModule : tutorialClasses) {
            if (tutorialClass.equals(tutorialClassInModule)) {
                return true;
            }
        }
        return false;
    }


    /**
     * List all the tutorial classes under this module.
     *
     * @return String of tutorial classes under this module.
     */
    public String listTutorialClasses() {
        if (tutorialClasses.size() == 0) {
            return String.format("Tutorials in %s: None!", moduleCode);
        } else {
            StringBuilder tutorialsString = new StringBuilder(String.format("Tutorials in %s:", moduleCode));
            for (TutorialClass tutorialClass : tutorialClasses) {
                tutorialsString.append(" ");
                tutorialsString.append(tutorialClass.toString());
            }
            return tutorialsString.toString().trim();
        }
    }

    /**
     * Adds an empty tutorial with the given name into the module.
     *
     * @param tutorialClass name of tutorial class to be added.
     */
    public void addTutorialClass(TutorialClass tutorialClass) {
        tutorialClasses.add(tutorialClass);
    }

    /**
     * Deletes a tutorial with the given name from the module.
     * The tutorial has to exist to be used in this function.
     *
     * @param tutorialClass name of tutorial class to be deleted.
     */
    public void deleteTutorialClass(TutorialClass tutorialClass) {
        tutorialClasses.remove(tutorialClass);
    }
}
