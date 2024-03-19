package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ModuleBuilder;

/**
 * Contains unit tests for the ModuleContainsKeywordPredicate class.
 */
public class ModuleContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String predicateKeyword = "keyword";

        ModuleContainsKeywordPredicate predicate = new ModuleContainsKeywordPredicate(predicateKeyword);

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        ModuleContainsKeywordPredicate predicateCopy = new ModuleContainsKeywordPredicate(predicateKeyword);
        assertTrue(predicate.equals(predicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

        // different person -> returns false
        String differentPredicateKeyword = "different keyword";
        ModuleContainsKeywordPredicate differentPredicate =
                new ModuleContainsKeywordPredicate(differentPredicateKeyword);
        assertFalse(predicate.equals(differentPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ModuleContainsKeywordPredicate predicate = new ModuleContainsKeywordPredicate("CS2101");
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2101").build()));

        // Partial keyword
        predicate = new ModuleContainsKeywordPredicate("CS");
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2101").build()));

        // Mixed-case keywords
        predicate = new ModuleContainsKeywordPredicate("cs2101");
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2101").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        ModuleContainsKeywordPredicate predicate = new ModuleContainsKeywordPredicate("CS2103T");
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("CS2101").build()));

        // Keyword matches tutorial class but does not match module code
        predicate = new ModuleContainsKeywordPredicate("T03");
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("CS2101").withTutorialClasses("T03").build()));

        // Keyword matches tutorialClass but does not match module code
        predicate = new ModuleContainsKeywordPredicate("T02");
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("CS2101")
                .withTutorialClasses("T02").build()));

        // Keyword matches tutorial class but does not match module code
        predicate = new ModuleContainsKeywordPredicate("T01");
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("CS2101").withTutorialClasses("T01").build()));
    }

    @Test
    public void toStringMethod() {
        String keyword = "keyword";
        ModuleContainsKeywordPredicate predicate = new ModuleContainsKeywordPredicate(keyword);

        String expected = ModuleContainsKeywordPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
