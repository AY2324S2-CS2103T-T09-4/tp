package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

class JsonAdaptedModuleTest {

    private static final String INVALID_MODULE = " ";
    private static final String VALID_MODULE = "CS2100";
    @Test
    void toModelType_success() throws Exception {
        ModuleCode module = new ModuleCode(VALID_MODULE);
        JsonAdaptedModule jsonModule = new JsonAdaptedModule(module);
        assertEquals(module, jsonModule.toModelType());
    }

    @Test
    void toModelType_invalidModuleCode_throwsIllegalValueException() {
        JsonAdaptedModule jsonModule = new JsonAdaptedModule(INVALID_MODULE, new ArrayList<TutorialClass>());
        String expectedMessage = JsonAdaptedModule.MISSING_FIELD_MESSAGE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, jsonModule::toModelType);
    }
}
