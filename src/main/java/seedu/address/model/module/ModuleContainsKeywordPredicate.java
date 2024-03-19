package seedu.address.model.module;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code ModuleCode} matches the keyword given.
 */
public class ModuleContainsKeywordPredicate implements Predicate<ModuleCode> {

    private final String keyword;
    /**
     * Constructs a ModuleContainsKeywordPredicate with the specified keyword.
     * @param keyword The keyword to search for in module codes.
     */
    public ModuleContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ModuleCode moduleCode) {
        return StringUtil.containsPartialWordIgnoreCase(moduleCode.getModule().moduleCode, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleContainsKeywordPredicate)) {
            return false;
        }

        ModuleContainsKeywordPredicate otherModuleContainsKeywordPredicate = (ModuleContainsKeywordPredicate) other;
        return keyword.equals(otherModuleContainsKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
