package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Email} matches the keyword given.
 */
public class EmailContainsKeywordPredicate implements Predicate<Person> {

    private final String keyword;

    /**
     * Constructs an {@code EmailContainsKeywordPredicate} with the specified keyword.
     * @param keyword The keyword to be used for testing.
     */
    public EmailContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsPartialWordIgnoreCase(person.getEmail().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailContainsKeywordPredicate)) {
            return false;
        }

        EmailContainsKeywordPredicate otherEmailContainsKeywordPredicate = (EmailContainsKeywordPredicate) other;
        return keyword.equals(otherEmailContainsKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
