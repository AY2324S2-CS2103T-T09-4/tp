package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link TutorialTeam}.
 */
public class JsonAdaptedTutorialTeam {

    private final String teamName;
    private final int teamSize;
    private final List<JsonAdaptedPerson> students;

    /**
     * Constructs a {@code JsonAdaptedTutorialTeam} with the given
     * {@code teamName}.
     */
    @JsonCreator
    public JsonAdaptedTutorialTeam(@JsonProperty("teamName") String teamName,
            @JsonProperty("teamSize") int teamSize,
            @JsonProperty("students") List<JsonAdaptedPerson> students) {
        this.teamName = teamName;
        this.students = students != null ? new ArrayList<>(students) : new ArrayList<>();
        this.teamSize = teamSize;
    }

    /**
     * Converts a given {@code TutorialTeam} into this class for Jackson use.
     */
    public JsonAdaptedTutorialTeam(TutorialTeam source) {
        this.teamName = source.teamName;
        this.teamSize = source.teamSize;
        this.students = source.getStudents().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList());
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public List<JsonAdaptedPerson> getStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Converts this Jackson-friendly adapted tutorial team object into the model's
     * {@code TutorialTeam} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted tutorial team.
     */
    public TutorialTeam toModelType() throws IllegalValueException {
        try {
            ArrayList<Person> students = new ArrayList<>();
            for (JsonAdaptedPerson student : this.students) {
                students.add(student.toModelType());
            }
            return new TutorialTeam(teamName, students, teamSize);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(TutorialTeam.MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JsonAdaptedTutorialTeam)) {
            return false;
        }

        JsonAdaptedTutorialTeam otherTutorialTeam = (JsonAdaptedTutorialTeam) other;
        return teamName.equals(otherTutorialTeam.teamName) && students.equals(otherTutorialTeam.students) && teamSize == otherTutorialTeam.teamSize;
    }
}
