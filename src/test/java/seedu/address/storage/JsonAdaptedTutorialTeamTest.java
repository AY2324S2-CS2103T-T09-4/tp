package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.module.TutorialTeam;
import seedu.address.testutil.PersonBuilder;

class JsonAdaptedTutorialTeamTest {

    private static final String VALID_TEAM_NAME = "Team 1";
    private static final String INVALID_TEAM_NAME = "";
    private static final int VALID_TEAM_SIZE = 10;
    private static final int INVALID_TEAM_SIZE = -1;
    private static final List<JsonAdaptedPerson> VALID_STUDENTS_LIST = new ArrayList<>(
            Arrays.asList(new PersonBuilder().build(), new PersonBuilder().build())).stream()
            .map(JsonAdaptedPerson::new)
            .collect(Collectors.toList());

    @Test
    void toModelType_success() throws Exception {
        TutorialTeam tutorialTeam = new TutorialTeam(VALID_TEAM_NAME, VALID_TEAM_SIZE);
        JsonAdaptedTutorialTeam jsonTutorialTeam = new JsonAdaptedTutorialTeam(tutorialTeam);
        assertEquals(tutorialTeam, jsonTutorialTeam.toModelType());
    }

    @Test
    void toModelType_invalidTeamName_throwsIllegalValueException() {
        JsonAdaptedTutorialTeam jsonTutorialTeam = new JsonAdaptedTutorialTeam(INVALID_TEAM_NAME, VALID_TEAM_SIZE,
                VALID_STUDENTS_LIST);
        String expectedMessage = TutorialTeam.MESSAGE_NAME_CONSTRAINTS;
        assertThrows(IllegalArgumentException.class, expectedMessage, jsonTutorialTeam::toModelType);
    }

    @Test
    void toModelType_invalidTeamSize_throwsIllegalValueException() {
        JsonAdaptedTutorialTeam jsonTutorialTeam = new JsonAdaptedTutorialTeam(VALID_TEAM_NAME, INVALID_TEAM_SIZE,
                VALID_STUDENTS_LIST);
        String expectedMessage = TutorialTeam.MESSAGE_SIZE_CONSTRAINTS;
        assertThrows(IllegalArgumentException.class, expectedMessage, jsonTutorialTeam::toModelType);
    }

}
