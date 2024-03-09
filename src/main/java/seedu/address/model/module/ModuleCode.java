package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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

    /**
     * Constructs an {@code ModuleCode}.
     *
     * @param modCode A valid modCode.
     */
    public ModuleCode(String modCode) {
        requireNonNull(modCode);
        checkArgument(isValidModuleCode(modCode), MESSAGE_CONSTRAINTS);
        value = modCode;
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
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

}
