package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.module.TutorialTeam.isValidSize;
import static seedu.address.model.module.TutorialTeam.isValidTeamName;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for the TutorialTeam class.
 */
public class TutorialTeamTest {

    public static final String VALID_TEAM_NAME_1 = "Team 1";
    public static final String VALID_TEAM_NAME_2 = "Team 2";
    public static final String INVALID_TEAM_NAME = "Team 3!!!!";
    public static final int VALID_TEAM_SIZE = 5;
    public static final int INVALID_TEAM_SIZE = -1;


    /**
     * Test cases to check the equality of two TutorialTeam objects
     */
    @Test
    public void equals() {
        TutorialTeam team = new TutorialTeam(VALID_TEAM_NAME_1, VALID_TEAM_SIZE);

        // same object -> returns true
        assertTrue(team.equals(team));

        // same values -> returns true
        TutorialTeam teamCopy = new TutorialTeam(VALID_TEAM_NAME_1, VALID_TEAM_SIZE);
        assertTrue(team.equals(teamCopy));

        // different types -> returns false
        assertFalse(team.equals(1));

        // null -> returns false
        assertFalse(team.equals(null));

        // different remark -> returns false
        TutorialTeam differentTeam = new TutorialTeam(VALID_TEAM_NAME_2, VALID_TEAM_SIZE);
        assertFalse(team.equals(differentTeam));
    }

    /**
     * Tests isValidTeamName with a valid team name.
     */
    @Test
    void isValidModuleCode_success() {
        assertTrue(isValidTeamName(VALID_TEAM_NAME_1));
    }

    /**
     * Tests isValidTeamName with an invalid team name.
     */
    @Test
    void isValidTeamName_failure() {
        assertFalse(isValidTeamName(INVALID_TEAM_NAME));
    }

    /**
     * Tests isValidSize with a valid team size.
     */
    @Test
    void isValidSize_success() {
        assertTrue(isValidSize(VALID_TEAM_SIZE));
    }

    /**
     * Tests isValidSize with an invalid team size.
     */
    @Test
    void isValidSize_failure() {
        assertFalse(isValidSize(INVALID_TEAM_SIZE));
    }

    /**
     * Tests the constructor of TutorialTeam with a valid team name and team size.
     */
    @Test
    void constructor_validTeamNameAndSize_success() {
        TutorialTeam team = new TutorialTeam(VALID_TEAM_NAME_1, VALID_TEAM_SIZE);
        assertEquals(VALID_TEAM_NAME_1, team.getTeamName());
        assertEquals(VALID_TEAM_SIZE, team.getTeamSize());
    }

    /**
     * Tests the constructor of TutorialTeam with an invalid team name.
     */
    @Test
    void constructor_invalidTeamName_failure() {
        assertThrows(IllegalArgumentException.class, () -> new TutorialTeam(INVALID_TEAM_NAME, VALID_TEAM_SIZE));
    }

    /**
     * Tests the constructor of TutorialTeam with an invalid team size.
     */
    @Test
    void constructor_invalidTeamSize_failure() {
        assertThrows(IllegalArgumentException.class, () -> new TutorialTeam(VALID_TEAM_NAME_1, INVALID_TEAM_SIZE));
    }
}
