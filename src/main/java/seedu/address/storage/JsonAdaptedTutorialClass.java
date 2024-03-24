package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link TutorialClass}.
 */
public class JsonAdaptedTutorialClass {

    private final String tutorialName;
    private final List<JsonAdaptedPerson> students;

    /**
     * Constructs a {@code JsonAdaptedTutorialClass} with the given
     * {@code tutorialName}.
     */
    @JsonCreator
    public JsonAdaptedTutorialClass(@JsonProperty("tutorialName") String tutorialName,
            @JsonProperty("students") List<JsonAdaptedPerson> students) {
        this.tutorialName = tutorialName;
        this.students = students != null ? new ArrayList<>(students) : new ArrayList<>();
    }

    /**
     * Converts a given {@code TutorialClass} into this class for Jackson use.
     */
    public JsonAdaptedTutorialClass(TutorialClass source) {
        this.tutorialName = source.tutorialName;
        this.students = source.getStudents().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList());
    }

    public String getTutorialName() {
        return tutorialName;
    }

    public List<JsonAdaptedPerson> getStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Converts this Jackson-friendly adapted tutorial class object into the model's
     * {@code TutorialClass} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted tutorial class.
     */
    public TutorialClass toModelType() throws IllegalValueException {
        try {
            ArrayList<Person> students = new ArrayList<>();
            for (JsonAdaptedPerson student : this.students) {
                students.add(student.toModelType());
            }
            return new TutorialClass(tutorialName, students);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(TutorialClass.MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JsonAdaptedTutorialClass)) {
            return false;
        }

        JsonAdaptedTutorialClass otherTutorialClass = (JsonAdaptedTutorialClass) other;
        return tutorialName.equals(otherTutorialClass.tutorialName) && students.equals(otherTutorialClass.students);
    }
}
