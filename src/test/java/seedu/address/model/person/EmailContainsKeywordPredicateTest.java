package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordPredicateTest {
    @Test
    public void equals() {
        String predicateKeyword = "keyword";

        EmailContainsKeywordPredicate predicate = new EmailContainsKeywordPredicate(predicateKeyword);

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        EmailContainsKeywordPredicate predicateCopy = new EmailContainsKeywordPredicate(predicateKeyword);
        assertTrue(predicate.equals(predicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

        // different person -> returns false
        String differentPredicateKeyword = "different keyword";
        EmailContainsKeywordPredicate differentPredicate = new EmailContainsKeywordPredicate(differentPredicateKeyword);
        assertFalse(predicate.equals(differentPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordPredicate predicate = new EmailContainsKeywordPredicate("alice@gmail.com");
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));

        // Partial keyword
        predicate = new EmailContainsKeywordPredicate("@gmail");
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordPredicate("ALicE@GMaIl.Com");
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        EmailContainsKeywordPredicate predicate = new EmailContainsKeywordPredicate("alice@yahoo.com");
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));

        // Keyword matches student id but does not match email
        predicate = new EmailContainsKeywordPredicate("A1234567Z");
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@gmail.com")
                .withStudentId("A1234567Z").build()));

        // Keyword matches name but does not match email
        predicate = new EmailContainsKeywordPredicate("Alice");
        assertFalse(predicate.test(new PersonBuilder().withEmail("al1c3@gmail.com").withName("Alice").build()));

        // Keyword matches student id but does not match email
        predicate = new EmailContainsKeywordPredicate("A1234567A");
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@gmail.com")
                .withStudentId("A1234567A").build()));

        // Keyword matches name but does not match email
        predicate = new EmailContainsKeywordPredicate("Bob");
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").withName("Bob").build()));
    }

    @Test
    public void toStringMethod() {
        String keyword = "keyword";
        EmailContainsKeywordPredicate predicate = new EmailContainsKeywordPredicate(keyword);

        String expected = EmailContainsKeywordPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
