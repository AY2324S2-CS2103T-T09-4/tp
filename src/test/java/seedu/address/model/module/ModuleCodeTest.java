package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.module.ModuleCode.isValidModuleCode;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {

    public static final String VALID_MODULE_CODE = "CS2103T";
    public static final String INVALID_MODULE_CODE = "CS210T";
    @Test
    public void equals() {
        ModuleCode module = new ModuleCode("CS2103T");

        // same object -> returns true
        assertTrue(module.equals(module));

        // same values -> returns true
        ModuleCode remarkCopy = new ModuleCode(module.value);
        assertTrue(module.equals(remarkCopy));

        // different types -> returns false
        assertFalse(module.equals(1));

        // null -> returns false
        assertFalse(module.equals(null));

        // different remark -> returns false
        ModuleCode differentModule = new ModuleCode("CS1101S");
        assertFalse(module.equals(differentModule));
    }
    @Test
    void isValidTutorialClass_success() {
        assertTrue(isValidModuleCode(VALID_MODULE_CODE));
    }

    @Test
    void isValidTutorialClass_failure() {
        assertFalse(isValidModuleCode(INVALID_MODULE_CODE));
    }

    @Test
    void testToString_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE);
        assertEquals(moduleCode.toString(), VALID_MODULE_CODE);
    }

}
