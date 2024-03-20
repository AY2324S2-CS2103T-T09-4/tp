package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Contains unit tests for the NameContainsKeywordPredicate class.
 */
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
        // null keyword
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate(null);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // empty string keyword
        predicate = new NameContainsKeywordPredicate(" ");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordPredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keyword matches student id but does not match name
        predicate = new NameContainsKeywordPredicate("A1234567Z");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withStudentId("A1234567Z").build()));

        // Keyword matches email but does not match name
        predicate = new NameContainsKeywordPredicate("alice@gmail.com");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withEmail("alice@gmail.com").build()));

        // Keyword matches student id but does not match name
        predicate = new NameContainsKeywordPredicate("A7654321Z");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withStudentId("A7654321Z").build()));

        // Keyword matches email but does not match name
        predicate = new NameContainsKeywordPredicate("bob@gmail.com");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withEmail("bob@gmail.com").build()));
    }

    @Test
    public void toStringMethod() {
        String keyword = "keyword";
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate(keyword);

        String expected = NameContainsKeywordPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
