package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.address.model.person.Person;

/**
 * Represents a Module's tutorial class code.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidTutorialClass(String)}
 */
public class TutorialTeam {

    public static final String MESSAGE_CONSTRAINTS = "Please enter a valid NUS tutorial class code "
            + "eg. T01, and it should not be blank";

    /**
     * This regex validates the tutorial class code that user enters.
     * Supports format like "L07", "T01" and "T015".
     */
    public static final String VALIDATION_REGEX = "^[A-Z]\\d{2}$";

    public final String teamName;
    public final int teamSize;
    private final ArrayList<Person> students;

    /**
     * Constructs a {@code TutorialClass} with default values.
     * Initializes the {@code value} field to an empty string and creates an empty
     * list for {@code students}.
     */
    public TutorialTeam() {
        this.teamName = "";
        this.students = new ArrayList<>();
        this.teamSize = Integer.MAX_VALUE;
    }

    /**
     * A constructor for TutorialClass. Creates a tutorial class of a certain size with no students.
     * @param tutorialTeam
     * @param teamSize
     */
    public TutorialTeam(String tutorialTeam, int teamSize) {
        requireAllNonNull(tutorialTeam, teamSize);
        this.teamName = tutorialTeam;
        this.students = new ArrayList<>();
        this.teamSize = teamSize;
    }

    /**
     * A constructor for TutorialTeam. Creates a tutorial team with students.
     * 
     * @param tutorialClass to be added
     * @param students      in the tutorial class
     */
    public TutorialTeam(String tutorialTeam, ArrayList<Person> students) {
        requireAllNonNull(tutorialTeam);
        this.teamName = tutorialTeam;
        this.students = students;
        this.teamSize = Integer.MAX_VALUE;
    }

    /**
     * A constructor for TutorialTeam. Creates a tutorial team with students and team size.
     * 
     * @param tutorialClass to be added
     * @param students      in the tutorial class
     * @param teamSize      of the tutorial team
     */
    public TutorialTeam(String tutorialTeam, ArrayList<Person> students, int teamSize) {
        requireAllNonNull(tutorialTeam);
        this.teamName = tutorialTeam;
        this.students = students;
        this.teamSize = teamSize;
    }

    /**
     * Set students to the tutorial team.
     * 
     * @param students
     */
    public void setStudents(ArrayList<Person> students) {
        this.students.addAll(students);
    }

    /**
     * Retrieves the tutorial team.
     * 
     * @return The tutorial team.
     */
    public TutorialTeam getTutorialTeam() {
        return this;
    }

    /**
     * Retrieves the list of students in the tutorial team.
     * 
     * @return The list of students in the tutorial team.
     */
    public ArrayList<Person> getStudents() {
        return this.students;
    }

    /**
     * Adds a student to the tutorial team.
     */
    public void addStudent(Person student) {
        students.add(student);
    }

    /**
     * Removes a student from the tutorial class if it exists.
     *
     * @return true if the student was removed
     */
    public boolean deleteStudent(Person student) {
        return students.remove(student);
    }

    /**
     * Checks if the student is in the tutorial team.
     * 
     * @param student
     * @return true if the student is in the tutorial class
     */
    public boolean hasStudent(Person student) {
        return students.contains(student);
    }

    @Override
    public String toString() {
        return teamName;
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
        return teamName.equals(otherTutorialTeam.teamName);
    }

    @Override
    public int hashCode() {
        return teamName.hashCode();
    }
}
