package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.tag.Tag;

/**
 * Represents a Student in TAHelper.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Email email;
    private final StudentId stuId;

    // Data fields
    private final ModuleCode module;
    private final TutorialClass tutorial;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Email email, StudentId stuId, ModuleCode module, TutorialClass tutorial, Set<Tag> tags) {
        requireAllNonNull(name, email, stuId, module, tutorial, tags);
        this.name = name;
        this.email = email;
        this.stuId = stuId;
        this.module = module;
        this.tutorial = tutorial;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }
    public Email getEmail() {
        return email;
    }
    public StudentId getStudentId() {
        return stuId;
    }
    public ModuleCode getModule() {
        return module;
    }
    public TutorialClass getTutorialClass() {
        return tutorial;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same StudentId.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getStudentId().equals(getStudentId());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && email.equals(otherPerson.email)
                && stuId.equals(otherPerson.stuId)
                && module.equals(otherPerson.module)
                && tutorial.equals(otherPerson.tutorial)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, email, stuId, module, tutorial, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("email", email)
                .add("stuId", stuId)
                .add("module", module)
                .add("tutorial", tutorial)
                .add("tags", tags)
                .toString();
    }

}
