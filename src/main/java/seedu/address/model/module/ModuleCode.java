package seedu.address.model.module;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

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
    private final List<TutorialClass> tutorialClasses;
    private String description;

    /**
     * A constructor for ModuleCode. Used to initialise a new module with no tutorial classes
     *
     * @param moduleCode of the module to be created
     */
    public ModuleCode(String moduleCode) {
        requireAllNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode;
        this.tutorialClasses = new ArrayList<>();
    }

    /**
     * A constructor for ModuleCode. Used to initialise a new {@code moduleCode} with {@code tutorialClass}.
     *
     * @param moduleCode of the module to be created.
     * @param tutorialClass to be added within the module.
     */
    public ModuleCode(String moduleCode, String tutorialClass) {
        requireAllNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode;
        this.tutorialClasses = new ArrayList<>();
        tutorialClasses.add(new TutorialClass(tutorialClass));
    }
    /**
     * Constructs a ModuleCode object with the specified module code, list of tutorial classes, and description.
     *
     * @param moduleCode The module code associated with this ModuleCode object.
     * @param tutorialClass A list of tutorial classes associated with this ModuleCode object.
     * @param description The description of the module.
     * @throws NullPointerException if {@code moduleCode} is null.
     * @throws IllegalArgumentException if {@code moduleCode} does not match the valid module code format.
     */
    public ModuleCode(String moduleCode, List<TutorialClass> tutorialClass, String description) {
        requireAllNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode;
        this.description = description != null ? description : "";
        this.tutorialClasses = new ArrayList<>(tutorialClass);
    }

    /**
     * A constructor for ModuleCode. Used to initialise a new {@code moduleCode} with {@code tutorialClasses}.
     * Used to get the representation of the module from {@code tutorialClasses} and {@code moduleCode}.
     *
     * @param moduleCode of the module to be created
     * @param tutorialClasses of the module to be created
     */
    public ModuleCode(String moduleCode, List<TutorialClass> tutorialClasses) {
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
        return (ArrayList<TutorialClass>) tutorialClasses;
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
        return tutorialClasses.contains(tutorialClass);
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
     *
     * @return true if tutorial class was successfully deleted
     */
    public boolean deleteTutorialClass(TutorialClass tutorialClass) {
        return tutorialClasses.remove(tutorialClass);
    }

    public String getDescription() {
        return description != null ? description : "";
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
