package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link TutorialClass}.
 */
public class JsonAdaptedTutorialClass {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "tutorial name is missing!";
    private final String tutorialName;
    private final List<JsonAdaptedPerson> students = new ArrayList<>();
    private final List<JsonAdaptedTutorialTeam> teams = new ArrayList<>();
    private final int classSize;

    /**
     * Constructs a {@code JsonAdaptedTutorialClass} with the given {@code tutorialName} and {@code classSize}.
     */
    @JsonCreator
    public JsonAdaptedTutorialClass(@JsonProperty("tutorialName") String tutorialName,
            @JsonProperty("teams") List<JsonAdaptedTutorialTeam> teams,
            @JsonProperty("students") List<JsonAdaptedPerson> students, @JsonProperty("classSize") int classSize) {
        this.tutorialName = tutorialName;
        if (teams != null) {
            this.teams.addAll(teams);
        }

        if (students != null) {
            this.students.addAll(students);
        }
        this.classSize = classSize;
    }

    /**
     * Converts a given {@code TutorialClass} into this class for Jackson use.
     */
    public JsonAdaptedTutorialClass(TutorialClass source) {
        this.tutorialName = source.getTutorialClass().tutorialName;
        teams.addAll(source.getTeams().stream().map(JsonAdaptedTutorialTeam::new).collect(Collectors.toList()));
        students.addAll(source.getStudents().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        this.classSize = source.getClassSize();
    }

    public String getTutorialName() {
        return tutorialName;
    }

    public List<JsonAdaptedTutorialTeam> getTeams() {
        return new ArrayList<>(teams);
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
        ArrayList<Person> listOfStudents = new ArrayList<>();
        ArrayList<TutorialTeam> listOfTeams = new ArrayList<>();

        if (tutorialName == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        if (!TutorialClass.isValidTutorialClass(tutorialName)) {
            throw new IllegalValueException(TutorialClass.MESSAGE_CONSTRAINTS);
        }

        for (JsonAdaptedPerson student : students) {
            listOfStudents.add(student.toModelType());
        }
        for (JsonAdaptedTutorialTeam team : teams) {
            listOfTeams.add(team.toModelType());
        }
        return new TutorialClass(tutorialName, classSize, listOfStudents, listOfTeams);
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
        return tutorialName.equals(otherTutorialClass.tutorialName) && students.equals(otherTutorialClass.students)
                && teams.equals(otherTutorialClass.teams);
    }
}
