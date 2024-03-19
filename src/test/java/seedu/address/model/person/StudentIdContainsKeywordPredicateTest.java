package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Contains unit tests for the StudentIdContainsKeywordPredicate class.
 */
public class StudentIdContainsKeywordPredicateTest {
    @Test
    public void equals() {
        String predicateKeyword = "keyword";

        StudentIdContainsKeywordPredicate predicate = new StudentIdContainsKeywordPredicate(predicateKeyword);

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        StudentIdContainsKeywordPredicate predicateCopy = new StudentIdContainsKeywordPredicate(predicateKeyword);
        assertTrue(predicate.equals(predicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

        // different person -> returns false
        String differentPredicateKeyword = "different keyword";
        StudentIdContainsKeywordPredicate differentPredicate =
                new StudentIdContainsKeywordPredicate(differentPredicateKeyword);
        assertFalse(predicate.equals(differentPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        StudentIdContainsKeywordPredicate predicate = new StudentIdContainsKeywordPredicate("A1234567Z");
        assertTrue(predicate.test(new PersonBuilder().withStudentId("A1234567Z").build()));

        // Partial keyword
        predicate = new StudentIdContainsKeywordPredicate("A123");
        assertTrue(predicate.test(new PersonBuilder().withStudentId("A1234567Z").build()));

        // Mixed-case keywords
        predicate = new StudentIdContainsKeywordPredicate("a1234567z");
        assertTrue(predicate.test(new PersonBuilder().withStudentId("A1234567Z").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        StudentIdContainsKeywordPredicate predicate = new StudentIdContainsKeywordPredicate("A1111111Z");
        assertFalse(predicate.test(new PersonBuilder().withStudentId("A1234567Z").build()));

        // Keyword matches name but does not match student id
        predicate = new StudentIdContainsKeywordPredicate("Alice");
        assertFalse(predicate.test(new PersonBuilder().withStudentId("A1234567Z").withName("Alice").build()));

        // Keyword matches email but does not match student id
        predicate = new StudentIdContainsKeywordPredicate("alice@gmail.com");
        assertFalse(predicate.test(new PersonBuilder().withStudentId("A1234567Z")
                .withEmail("alice@gmail.com").build()));
    }

    @Test
    public void toStringMethod() {
        String keyword = "keyword";
        StudentIdContainsKeywordPredicate predicate = new StudentIdContainsKeywordPredicate(keyword);

        String expected = StudentIdContainsKeywordPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
