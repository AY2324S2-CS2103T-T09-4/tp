package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.module.TutorialClass.isValidTutorialClass;

import org.junit.jupiter.api.Test;

public class TutorialClassTest {

    public static final String VALID_TUTORIAL = "T09";
    public static final String INVALID_TUTORIAL = "T09#0";
    @Test
    public void equals() {
        TutorialClass tutorialClass = new TutorialClass("T09");

        // same object -> returns true
        assertTrue(tutorialClass.equals(tutorialClass));

        // same values -> returns true
        TutorialClass remarkCopy = new TutorialClass(tutorialClass.value);
        assertTrue(tutorialClass.equals(remarkCopy));

        // different types -> returns false
        assertFalse(tutorialClass.equals(1));

        // null -> returns false
        assertFalse(tutorialClass.equals(null));

        // different remark -> returns false
        TutorialClass differentModule = new TutorialClass("T01");
        assertFalse(tutorialClass.equals(differentModule));
    }

    @Test
    void isValidTutorialClass_success() {
        assertTrue(isValidTutorialClass(VALID_TUTORIAL));
    }

    @Test
    void isValidTutorialClass_failure() {
        assertFalse(isValidTutorialClass(INVALID_TUTORIAL));
    }

    @Test
    void testToString_success() {
        TutorialClass tutorial = new TutorialClass(VALID_TUTORIAL);
        assertEquals(tutorial.toString(), VALID_TUTORIAL);
    }
}
