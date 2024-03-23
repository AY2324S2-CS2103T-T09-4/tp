package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.module.TutorialClass;
import seedu.address.testutil.PersonBuilder;

class JsonAdaptedTutorialClassTest {

    private static final String VALID_TUTORIAL_NAME = "T01";
    private static final String INVALID_TUTORIAL_NAME = "";
    private static final List<JsonAdaptedPerson> VALID_STUDENTS_LIST = new ArrayList<>(
            Arrays.asList(new PersonBuilder().build(), new PersonBuilder().build())).stream()
            .map(JsonAdaptedPerson::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTutorialTeam> VALID_TEAMS_LIST = new ArrayList<>(
            Arrays.asList(new JsonAdaptedTutorialTeam("Team 1", 1, VALID_STUDENTS_LIST)));

    @Test
    void toModelType_success() throws Exception {
        TutorialClass tutorialClass = new TutorialClass(VALID_TUTORIAL_NAME);
        JsonAdaptedTutorialClass jsonTutorialClass = new JsonAdaptedTutorialClass(tutorialClass);
        assertEquals(tutorialClass, jsonTutorialClass.toModelType());
    }

    @Test
    void toModelType_invalidTutorialName_throwsIllegalValueException() {
        JsonAdaptedTutorialClass jsonTutorialClass = new JsonAdaptedTutorialClass(INVALID_TUTORIAL_NAME,
                VALID_TEAMS_LIST, VALID_STUDENTS_LIST);
        String expectedMessage = TutorialClass.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalArgumentException.class, expectedMessage, jsonTutorialClass::toModelType);
    }

}
