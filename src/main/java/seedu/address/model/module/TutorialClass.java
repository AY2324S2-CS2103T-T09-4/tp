package seedu.address.model.module;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.address.model.person.Person;

/**
 * Represents a Module's tutorial class code.
 * Guarantees: immutable; is valid as declared in {@link #isValidTutorialClass(String)}
 */
public class TutorialClass {

    public static final String MESSAGE_CONSTRAINTS =
        "Please enter a valid NUS tutorial class code eg. T01, and it should not be blank";

    /**
     * This regex validates the tutorial class code that user enters.
     * Supports format like "L07", "T01" and "T015".
     */
    public static final String VALIDATION_REGEX = "^[A-Z]\\d{2}$";

    public final String value;
    private final ArrayList<Person> students;

    /**
     * A constructor for TutorialClass. Creates an empty tutorial class with no students.
     *
     * @param name of tutorial to be added
     */
    public TutorialClass(String name) {
        requireAllNonNull(name);
        checkArgument(isValidTutorialClass(name), MESSAGE_CONSTRAINTS);
        this.value = name;
        this.students = new ArrayList<>();
    }

    /**
     * A constructor for TutorialClass. Creates a tutorial with the list of students specified.
     *
     * @param name of tutorial to be added
     * @param students to be in the added tutorial
     */
    public TutorialClass(String name, ArrayList<Person> students) {
        requireAllNonNull(name);
        checkArgument(isValidTutorialClass(name), MESSAGE_CONSTRAINTS);
        this.value = name;
        this.students = students;
    }

    /**
     * Returns true if a given string is a valid tutorial class code.
     */
    public static boolean isValidTutorialClass(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof TutorialClass)) {
            return false;
        }

        TutorialClass otherTutorialClass = (TutorialClass) other;
        return value.equals(otherTutorialClass.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
