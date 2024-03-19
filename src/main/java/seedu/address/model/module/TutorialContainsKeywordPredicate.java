package seedu.address.model.module;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code TutorialClass}'s {@code TutorialClass} matches the keyword given.
 */
public class TutorialContainsKeywordPredicate implements Predicate<TutorialClass> {

    private final String keyword;
    /**
     * Constructs a {@code TutorialContainsKeywordPredicate} with the specified keyword.
     * @param keyword The keyword to be used for testing.
     */
    public TutorialContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(TutorialClass tutorialClass) {
        return StringUtil.containsPartialWordIgnoreCase(tutorialClass.getTutorialClass().tutorialName, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialContainsKeywordPredicate)) {
            return false;
        }

        TutorialContainsKeywordPredicate otherTutorialContainsKeywordPredicate =
                (TutorialContainsKeywordPredicate) other;
        return keyword.equals(otherTutorialContainsKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
