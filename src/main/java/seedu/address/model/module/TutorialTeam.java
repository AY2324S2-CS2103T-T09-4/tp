package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.model.person.Person;

/**
 * Represents a Module's tutorial team.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidTeamName(String)}
 */
public class TutorialTeam {

    public static final String MESSAGE_CONSTRAINTS = "Please enter a valid tutorial team name "
            + "eg. Team 1, and it should not be blank";
    public static final String MESSAGE_NAME_CONSTRAINTS = "Team names should only contain alphanumeric "
            + "characters and spaces, and it should not be blank";
    public static final String MESSAGE_SIZE_CONSTRAINTS = "Team size should be a positive integer";

    public static final String MESSAGE_NUMBER_CONSTRAINTS = "Please enter a valid integer for team size";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final TutorialTeamName teamName;
    public final int teamSize;
    private final ArrayList<Person> students;

    /**
     * Constructs a {@code TutorialTeam} with default values.
     * Initializes the {@code value} field to an empty string, creates an empty
     * list for {@code students} and sets the {@code teamSize} to the maximum.
     */
    public TutorialTeam() {
        this.teamName = new TutorialTeamName("");
        this.students = new ArrayList<>();
        this.teamSize = Integer.MAX_VALUE;
    }

    /**
     * A constructor for TutorialTeam. Creates a tutorial team of unspecified size
     * with no students.
     * @param tutorialTeam
     */
    public TutorialTeam(String tutorialTeam) {
        requireAllNonNull(tutorialTeam);
        checkArgument(isValidTeamName(tutorialTeam), MESSAGE_NAME_CONSTRAINTS);
        this.teamName = new TutorialTeamName(tutorialTeam);
        this.students = new ArrayList<>();
        this.teamSize = Integer.MAX_VALUE;
    }

    /**
     * A constructor for TutorialTeam. Creates a tutorial team of a certain size
     * with no students.
     * @param tutorialTeam
     * @param teamSize
     */
    public TutorialTeam(String tutorialTeam, int teamSize) {
        requireAllNonNull(tutorialTeam, teamSize);
        checkArgument(isValidTeamName(tutorialTeam), MESSAGE_NAME_CONSTRAINTS);
        checkArgument(isValidSize(teamSize), MESSAGE_SIZE_CONSTRAINTS);
        this.teamName = new TutorialTeamName(tutorialTeam);
        this.students = new ArrayList<>();
        this.teamSize = teamSize;
    }

    /**
     * A constructor for TutorialTeam. Creates a tutorial team with students.
     * @param tutorialTeam to be added
     * @param students      in the tutorial class
     */
    public TutorialTeam(String tutorialTeam, ArrayList<Person> students) {
        requireAllNonNull(tutorialTeam);
        checkArgument(isValidTeamName(tutorialTeam), MESSAGE_NAME_CONSTRAINTS);
        this.teamName = new TutorialTeamName(tutorialTeam);
        this.students = students;
        this.teamSize = Integer.MAX_VALUE;
    }

    /**
     * A constructor for TutorialTeam. Creates a tutorial team with students and
     * team size.
     * @param tutorialTeam to be added
     * @param students      in the tutorial class
     * @param teamSize      of the tutorial team
     */
    public TutorialTeam(String tutorialTeam, ArrayList<Person> students, int teamSize) {
        requireAllNonNull(tutorialTeam);
        checkArgument(isValidTeamName(tutorialTeam), MESSAGE_NAME_CONSTRAINTS);
        checkArgument(isValidSize(teamSize), MESSAGE_SIZE_CONSTRAINTS);
        this.teamName = new TutorialTeamName(tutorialTeam);
        this.students = students;
        this.teamSize = teamSize;
    }

    /**
     * Returns true if a given string is a valid tutorial team name.
     * @param test
     */
    public static boolean isValidTeamName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given integer is a valid team size.
     * @param test
     */
    public static boolean isValidSize(int test) {
        return test > 0;
    }

    /**
     * Set students to the tutorial team.
     * @param students
     */
    public void setStudents(ArrayList<Person> students) {
        this.students.addAll(students);
    }

    /**
     * Retrieves the tutorial team.
     * @return The tutorial team.
     */
    public TutorialTeam getTutorialTeam() {
        return this;
    }

    /**
     * Retrieves the team name.
     * @return The team name.
     */
    public String getTeamName() {
        return teamName.toString();
    }

    /**
     * Retrieves the team size.
     * @return The team size.
     */
    public int getTeamSize() {
        return teamSize;
    }

    /**
     * Retrieves the list of students in the tutorial team.
     * @return The list of students in the tutorial team.
     */
    public ArrayList<Person> getStudents() {
        return this.students;
    }

    /**
     * Adds a student to the tutorial team.
     * @param student
     */
    public void addStudent(Person student) {
        students.add(student);
    }

    /**
     * Removes a student from the tutorial class if it exists.
     * @param student
     * @return true if the student was removed
     */
    public boolean deleteStudent(Person student) {
        return students.remove(student);
    }

    /**
     * Checks if the student is in the tutorial team.
     * @param student
     * @return true if the student is in the tutorial class
     */
    public boolean hasStudent(Person student) {
        return students.contains(student);
    }

    /**
     * Checks if a student exist, check by their studentId.
     * @return true if a person is in the list of students
     */
    public boolean hasStudentVerified(Person student, TutorialTeam tutorialTeam) {
        ArrayList<Person> teamList = tutorialTeam.getStudents();
        if (teamList.size() == 0 || teamList == null) {
            return false;
        }
        for (Person eachStudent : teamList) {
            if (eachStudent.getStudentId().value.equals(student.getStudentId().value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the {@code tutorialTeam} size has exceeded its limit.
     * @param tutorialTeam size to check.
     * @return a boolean that indicates whether the team size will be exceeded by adding another person.
     */
    public boolean hasTeamSizeExceeded(TutorialTeam tutorialTeam) {
        requireNonNull(tutorialTeam);
        int maxTeamSize = tutorialTeam.getTeamSize();
        int currTeamSize = tutorialTeam.getStudents().size();
        return (maxTeamSize <= currTeamSize);
    };

    /**
     * Checks if the same person is in the team already.
     * @param student to check if exist in team.
     * @param tutorialTeam to check.
     * @return a boolean true if the student exists in the team, else false.
     */
    public boolean isSamePersonInTeam(Person student, TutorialTeam tutorialTeam) {
        for (Person person : tutorialTeam.getStudents()) {
            if (person.isSamePerson(student)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return teamName.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialTeam)) {
            return false;
        }

        TutorialTeam otherTutorialTeam = (TutorialTeam) other;
        return teamName.toString().equalsIgnoreCase(otherTutorialTeam.teamName.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName, teamSize);
    }
}
