package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.module.ModuleCode.isValidModuleCode;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for the ModuleCode class.
 */
public class ModuleCodeTest {

    public static final String VALID_MODULE_CODE = "CS2103T";
    public static final String INVALID_MODULE_CODE = "CS210T";
    public static final String VALID_TUTORIAL_1 = "T01";
    public static final String VALID_TUTORIAL_2 = "T02";

    /**
     * Test cases to check the equality of two ModuleCode objects
     */
    @Test
    public void equals() {
        ModuleCode module = new ModuleCode("CS2103T");

        // same object -> returns true
        assertTrue(module.equals(module));

        // same values -> returns true
        ModuleCode remarkCopy = new ModuleCode(module.moduleCode);
        assertTrue(module.equals(remarkCopy));

        // different types -> returns false
        assertFalse(module.equals(1));

        // null -> returns false
        assertFalse(module.equals(null));

        // different remark -> returns false
        ModuleCode differentModule = new ModuleCode("CS1101S");
        assertFalse(module.equals(differentModule));
    }

    /**
     * Tests isValidModuleCode with a valid module code.
     */
    @Test
    void isValidModuleCode_success() {
        assertTrue(isValidModuleCode(VALID_MODULE_CODE));
    }

    /**
     * Tests isValidModuleCode with an invalid module code
     */
    @Test
    void isValidModuleCode_failure() {
        assertFalse(isValidModuleCode(INVALID_MODULE_CODE));
    }

    /**
     * Checks if the string output is as expected for a valid module.
     */
    @Test
    void testToString_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE);
        assertEquals(moduleCode.toString(), VALID_MODULE_CODE);
    }

    /**
     * Checks if the string output is as expected for a set list of tutorial classes.
     */
    @Test
    void listTutorialClasses_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE);
        moduleCode.addTutorialClass(new TutorialClass(VALID_TUTORIAL_1));
        moduleCode.addTutorialClass(new TutorialClass(VALID_TUTORIAL_2));
        String expectedString = "Tutorials in CS2103T: T01 T02";
        assertEquals(moduleCode.listTutorialClasses(), expectedString);
    }

    /**
     * Checks if the string output is as expected for an empty module.
     */
    @Test
    void listTutorialClasses_empty_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE);
        String expectedString = "Tutorials in CS2103T: None!";
        assertEquals(moduleCode.listTutorialClasses(), expectedString);
    }
}
