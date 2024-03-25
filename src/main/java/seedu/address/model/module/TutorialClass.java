package seedu.address.model.module;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.address.model.person.Person;

/**
 * Represents a Module's tutorial class code.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidTutorialClass(String)}
 */
public class TutorialClass {

    public static final String MESSAGE_CONSTRAINTS = "Please enter a valid NUS tutorial class code "
            + "eg. T01, and it should not be blank";

    /**
     * This regex validates the tutorial class code that user enters.
     * Supports format like "L07", "T01" and "T015".
     */
    public static final String VALIDATION_REGEX = "^[A-Z]\\d{2}$";

    public final String tutorialName;
    private final ArrayList<Person> students;
    private final ArrayList<TutorialTeam> teams;

    /**
     * Constructs a {@code TutorialClass} with default values.
     * Initializes the {@code value} field to an empty string and creates an empty
     * list for {@code students}.
     */
    public TutorialClass() {
        this.tutorialName = "";
        this.students = new ArrayList<>();
        this.teams = new ArrayList<>();
    }

    /**
     * A constructor for TutorialClass. Creates an empty tutorial class with no
     * students.
     * 
     * @param tutorialClass to be added
     */
    public TutorialClass(String tutorialClass) {
        requireAllNonNull(tutorialClass);
        checkArgument(isValidTutorialClass(tutorialClass), MESSAGE_CONSTRAINTS);
        this.tutorialName = tutorialClass;
        this.students = new ArrayList<>();
        this.teams = new ArrayList<>();
    }

    /**
     * A constructor for TutorialClass. Creates a tutorial class with students.
     * 
     * @param tutorialClass to be added
     * @param students      in the tutorial class
     */
    public TutorialClass(String tutorialClass, ArrayList<Person> students) {
        requireAllNonNull(tutorialClass);
        checkArgument(isValidTutorialClass(tutorialClass), MESSAGE_CONSTRAINTS);
        this.tutorialName = tutorialClass;
        this.students = students;
        this.teams = new ArrayList<>();
    }

    /**
     * A constructor for TutorialClass. Creates a tutorial class with students and
     * teams.
     * 
     * @param tutorialClass to be added
     * @param students      in the tutorial class
     * @param teams         in the tutorial class
     */
    public TutorialClass(String tutorialClass, ArrayList<Person> students, ArrayList<TutorialTeam> teams) {
        requireAllNonNull(tutorialClass);
        checkArgument(isValidTutorialClass(tutorialClass), MESSAGE_CONSTRAINTS);
        this.tutorialName = tutorialClass;
        this.students = students;
        this.teams = teams;
    }

    /**
     * Set students to the tutorial class.
     * 
     * @param students
     */
    public void setStudents(ArrayList<Person> students) {
        this.students.addAll(students);
    }

    /**
     * Returns true if a given string is a valid tutorial class code.
     */
    public static boolean isValidTutorialClass(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Retrieves the tutorial class.
     * 
     * @return The tutorial class.
     */
    public TutorialClass getTutorialClass() {
        return this;
    }

    /**
     * Retrieves the list of students in the tutorial class.
     * 
     * @return The list of students in the tutorial class.
     */
    public ArrayList<Person> getStudents() {
        return this.students;
    }

    /**
     * Adds a student to the tutorial class.
     * 
     * @param student
     */
    public void addStudent(Person student) {
        students.add(student);
    }

    /**
     * Removes a student from the tutorial class if it exists.
     * 
     * @param student
     * @return true if the student was removed
     */
    public boolean deleteStudent(Person student) {
        return students.remove(student);
    }

    /**
     * Checks if the student is in the tutorial class.
     * 
     * @param student
     * @return true if the student is in the tutorial class
     */
    public boolean hasStudent(Person student) {
        return students.contains(student);
    }

    /**
     * Retrieves the list of teams in the tutorial class.
     * 
     * @return The list of teams in the tutorial class.
     */
    public ArrayList<TutorialTeam> getTeams() {
        return this.teams;
    }

    /**
     * Adds a team to the tutorial class.
     * 
     * @param team
     */
    public void addTeam(TutorialTeam team) {
        teams.add(team);
    }

    /**
     * Checks if the team is in the tutorial class.
     * 
     * @param team
     */
    public boolean hasTeam(TutorialTeam team) {
        return teams.contains(team);
    }

    /**
     * Deletes a team from the tutorial class.
     * 
     */
    public void deleteTeam(TutorialTeam team) {
        teams.remove(team);
    }

    @Override
    public String toString() {
        return tutorialName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialClass)) {
            return false;
        }

        TutorialClass otherTutorialClass = (TutorialClass) other;
        return tutorialName.equals(otherTutorialClass.tutorialName);
    }

    @Override
    public int hashCode() {
        return tutorialName.hashCode();
    }
}
