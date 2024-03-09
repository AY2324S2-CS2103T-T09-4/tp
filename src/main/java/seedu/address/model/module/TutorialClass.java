package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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

    /**
     * Constructs an {@code TutorialClass}.
     *
     * @param tutorialClass A valid tutorialClass.
     */
    public TutorialClass(String tutorialClass) {
        requireNonNull(tutorialClass);
        checkArgument(isValidTutorialClass(tutorialClass), MESSAGE_CONSTRAINTS);
        value = tutorialClass;
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
