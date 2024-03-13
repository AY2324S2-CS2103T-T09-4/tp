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

    public final String value;
    private final ArrayList<TutorialClass> tutorialClasses;

    /**
     * A constructor for Module. Used to initialise a new module with no tutorial classes
     *
     * @param name of the module to be created
     */
    public ModuleCode(String name) {
        requireAllNonNull(name);
        checkArgument(isValidModuleCode(name), MESSAGE_CONSTRAINTS);
        this.value = name;
        this.tutorialClasses = new ArrayList<TutorialClass>();
    }

    /**
     * A constructor for Module. Used to initialise a new module with the tutorial class specified.
     * This is the constructor used when /add_class is used.
     *
     * @param name of the module to be created
     * @param tutorialClass to be added within the module
     */
    public ModuleCode(String name, String tutorialClass) {
        requireAllNonNull(name);
        checkArgument(isValidModuleCode(name), MESSAGE_CONSTRAINTS);
        this.value = name;
        this.tutorialClasses = new ArrayList<TutorialClass>();
        tutorialClasses.add(new TutorialClass(tutorialClass));
    }

    /**
     * A constructor for Module. Used to initialise a new module with the list of tutorial classes specified.
     * Used to get the representation of the module from the list of classes and the name specified.
     *
     * @param name of the module to be created
     * @param tutorialClasses of the module to be created
     */
    public ModuleCode(String name, ArrayList<TutorialClass> tutorialClasses) {
        requireAllNonNull(name);
        checkArgument(isValidModuleCode(name), MESSAGE_CONSTRAINTS);
        this.value = name;
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

    public String getTutorialClassesNames() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < tutorialClasses.size(); i++) {
            output.append(tutorialClasses.get(i).toString()).append(", ");
        }
        return output.toString();
    }

    @Override
    public String toString() {
        return value;
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
        return value.equals(otherModuleCode.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Checks if the given tutorial class is already in the list.
     *
     * @param tutorialString name of the tutorial class to be checked
     * @return true if the class name is in the list. False otherwise.
     */
    public boolean hasTutorialClass(String tutorialString) {
        for (TutorialClass tutorialClass : tutorialClasses) {
            String tutorialInList = tutorialClass.toString();
            if (tutorialString.equals(tutorialInList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an empty tutorial with the given name into the module.
     *
     * @param tutorialString name of tutorial class to be added.
     */
    public void addTutorialClass(String tutorialString) {
        tutorialClasses.add(new TutorialClass((tutorialString)));
    }
}
