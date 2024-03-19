package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ModuleBuilder;

/**
 * Contains unit tests for the TutorialContainsKeywordPredicate class.
 */
public class TutorialContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String predicateKeyword = "keyword";

        TutorialContainsKeywordPredicate predicate = new TutorialContainsKeywordPredicate(predicateKeyword);

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        TutorialContainsKeywordPredicate predicateCopy = new TutorialContainsKeywordPredicate(predicateKeyword);
        assertTrue(predicate.equals(predicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

        // different person -> returns false
        String differentPredicateKeyword = "different keyword";
        TutorialContainsKeywordPredicate differentPredicate =
                new TutorialContainsKeywordPredicate(differentPredicateKeyword);
        assertFalse(predicate.equals(differentPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        String tutorialClass = "T01";

        // One keyword
        TutorialContainsKeywordPredicate predicate = new TutorialContainsKeywordPredicate(tutorialClass);
        ModuleCode moduleUnderTest = new ModuleBuilder().withTutorialClasses(tutorialClass).build();
        TutorialClass tutorialClassUnderTest = moduleUnderTest.getTutorialClasses().get(0);
        assertTrue(predicate.test(tutorialClassUnderTest));

        // Partial keyword
        predicate = new TutorialContainsKeywordPredicate("1");
        ModuleCode moduleUnderTestPartial = new ModuleBuilder().withTutorialClasses(tutorialClass).build();
        TutorialClass tutorialClassUnderTestPartial = moduleUnderTestPartial.getTutorialClasses().get(0);
        assertTrue(predicate.test(tutorialClassUnderTestPartial));

        // Mixed-case keywords
        predicate = new TutorialContainsKeywordPredicate("t01");
        ModuleCode moduleUnderTestMixed = new ModuleBuilder().withTutorialClasses(tutorialClass).build();
        TutorialClass tutorialClassUnderTestMixed = moduleUnderTestMixed.getTutorialClasses().get(0);
        assertTrue(predicate.test(tutorialClassUnderTestMixed));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        String defaultTutorialClass = "T01";

        // Non-matching keyword
        TutorialContainsKeywordPredicate predicate = new TutorialContainsKeywordPredicate("T15");
        ModuleCode moduleUnderTest = new ModuleBuilder().withTutorialClasses(defaultTutorialClass).build();
        TutorialClass tutorialClassUnderTest = moduleUnderTest.getTutorialClasses().get(0);
        assertFalse(predicate.test(tutorialClassUnderTest));

        // Keyword matches module code but does not match tutorial class
        predicate = new TutorialContainsKeywordPredicate("CS2100");
        ModuleCode moduleUnderTestModuleMatch = new ModuleBuilder().withModuleCode("CS2100")
                .withTutorialClasses(defaultTutorialClass).build();
        TutorialClass tutorialClassUnderTestModuleMatch = moduleUnderTestModuleMatch.getTutorialClasses().get(0);
        assertFalse(predicate.test(tutorialClassUnderTestModuleMatch));

        // Keyword matches tutorial class but does not match module code
        predicate = new TutorialContainsKeywordPredicate("T03");
        ModuleCode moduleUnderTestClassMatch = new ModuleBuilder().withModuleCode("CS2100")
                .withTutorialClasses(defaultTutorialClass).build();
        TutorialClass tutorialClassUnderTestClassMatch = moduleUnderTestClassMatch.getTutorialClasses().get(0);
        assertFalse(predicate.test(tutorialClassUnderTestClassMatch));
    }

    @Test
    public void toStringMethod() {
        String keyword = "keyword";
        TutorialContainsKeywordPredicate predicate = new TutorialContainsKeywordPredicate(keyword);

        String expected = TutorialContainsKeywordPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
