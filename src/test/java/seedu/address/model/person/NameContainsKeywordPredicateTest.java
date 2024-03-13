package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String predicateKeyword = "keyword";

        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate(predicateKeyword);

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        NameContainsKeywordPredicate predicateCopy = new NameContainsKeywordPredicate(predicateKeyword);
        assertTrue(predicate.equals(predicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

        // different person -> returns false
        String differentPredicateKeyword = "different keyword";
        NameContainsKeywordPredicate differentPredicate = new NameContainsKeywordPredicate(differentPredicateKeyword);
        assertFalse(predicate.equals(differentPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Keyword with whitespace
        predicate = new NameContainsKeywordPredicate("Alice Bob");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob Carol").build()));

        // Partial keyword
        predicate = new NameContainsKeywordPredicate("ice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordPredicate("aLicE BOb");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keyword matches student id but does not match name
        predicate = new NameContainsKeywordPredicate("A1234567Z");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withStudentId("A1234567Z").build()));

        // Keyword matches email but does not match name
        predicate = new NameContainsKeywordPredicate("alice@gmail.com");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withEmail("alice@gmail.com").build()));

        // Keyword matches module code but does not match name
        predicate = new NameContainsKeywordPredicate("CS2101");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withModuleCode("CS2101").build()));

        // Keyword matches tutorial class but does not match name
        predicate = new NameContainsKeywordPredicate("T01");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTutorialClass("T01").build()));
    }

    @Test
    public void toStringMethod() {
        String keyword = "keyword";
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate(keyword);

        String expected = NameContainsKeywordPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
