package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * Represents a Module's tutorial class code.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidTutorialClass(String)}
 */
public class TutorialClass {

    public static final String MESSAGE_CONSTRAINTS = "Please enter a valid NUS tutorial class code "
            + "eg. T01, and it should not be blank";

    public static final String MESSAGE_SIZE_CONSTRAINTS = "Class size should be a positive integer";

    /**
     * This regex validates the tutorial class code that user enters.
     * Supports format like "L07", "T01" and "T015".
     */
    public static final String VALIDATION_REGEX = "^[A-Z]\\d{2}$";

    public final String tutorialName;
    private int classSize;
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
        this.classSize = Integer.MAX_VALUE;
    }

    /**
     * A constructor for TutorialClass. Creates a tutorial class with no students and a class size.
     * @param tutorialClass
     * @param classSize
     */
    public TutorialClass(String tutorialClass, int classSize) {
        requireAllNonNull(tutorialClass, classSize);
        checkArgument(isValidTutorialClass(tutorialClass), MESSAGE_CONSTRAINTS);
        this.tutorialName = tutorialClass;
        this.students = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.classSize = classSize;
    }

    /**
     * A constructor for TutorialClass. Creates an empty tutorial class with no
     * students.
     * @param tutorialClass to be added
     */
    public TutorialClass(String tutorialClass) {
        requireAllNonNull(tutorialClass);
        checkArgument(isValidTutorialClass(tutorialClass), MESSAGE_CONSTRAINTS);
        this.tutorialName = tutorialClass;
        this.students = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.classSize = Integer.MAX_VALUE;
    }

    /**
     * A constructor for TutorialClass. Creates a tutorial class with students.
     * @param tutorialClass to be added
     * @param students      in the tutorial class
     */
    public TutorialClass(String tutorialClass, ArrayList<Person> students) {
        requireAllNonNull(tutorialClass);
        checkArgument(isValidTutorialClass(tutorialClass), MESSAGE_CONSTRAINTS);
        this.tutorialName = tutorialClass;
        this.students = students;
        this.teams = new ArrayList<>();
        this.classSize = Integer.MAX_VALUE;
    }

    /**
     * A constructor for TutorialClass. Creates a tutorial class with students and
     * teams.
     * @param tutorialClass to be added
     * @param students      in the tutorial class
     * @param teams         in the tutorial class
     */
    public TutorialClass(String tutorialClass, int classSize, ArrayList<Person> students,
            ArrayList<TutorialTeam> teams) {
        requireAllNonNull(tutorialClass);
        checkArgument(isValidTutorialClass(tutorialClass), MESSAGE_CONSTRAINTS);
        this.tutorialName = tutorialClass;
        this.students = students;
        this.teams = teams;
        this.classSize = classSize;
    }

    /**
     * Set students to the tutorial class.
     * @param students
     */
    public void setStudents(ArrayList<Person> students) {
        this.students.addAll(students);
    }

    /**
     * Check if the tutorial class is full.
     * @return true if the tutorial class is full
     */
    public boolean isFull() {
        return students.size() >= classSize;
    }

    /**
     * Returns true if a given string is a valid tutorial class code.
     */
    public static boolean isValidTutorialClass(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Retrieves the tutorial class.
     * @return The tutorial class.
     */
    public TutorialClass getTutorialClass() {
        return this;
    }

    /**
     * Retrieves the list of students in the tutorial class.
     * @return The list of students in the tutorial class.
     */
    public ArrayList<Person> getStudents() {
        return this.students;
    }

    /**
     * Adds a student to the tutorial class.
     * @param student
     */
    public void addStudent(Person student) {
        students.add(student);
    }

    /**
     * Removes a student from the tutorial class if it exists.
     * Also removes the student from any tutorial class in this tutorial
     *
     * @param student
     * @return true if the student was removed
     */
    public boolean deleteStudent(Person student) {
        deleteStudentFromTeams(student);
        return students.remove(student);
    }

    /**
     * Deletes a specified student from all teams from the tutorial class.
     *
     * @param student to be deleted
     */
    public void deleteStudentFromTeams(Person student) {
        ArrayList<TutorialTeam> list = getTeams();
        for (TutorialTeam team : list) {
            team.deleteStudent(student);
        }
    }

    /**
     * Checks if the student is in the tutorial class.
     * @param student
     * @return true if the student is in the tutorial class
     */
    public boolean hasStudent(Person student) {
        return students.contains(student);
    }

    /**
     * Retrieves the list of teams in the tutorial class.
     * @return The list of teams in the tutorial class.
     */
    public ArrayList<TutorialTeam> getTeams() {
        return this.teams;
    }

    /**
     * Adds a team to the tutorial class.
     * @param team
     */
    public void addTeam(TutorialTeam team) {
        teams.add(team);
    }

    /**
     * Checks if a student is in the {@code tutorialClass} of that {@code moduleCode}
     * @param student to check if student exist in tutorialClass.
     * @param tutorialClass to check if the student is in
     * @return a boolean indicating if the student is in that {@code tutorialClass}
     */
    public boolean isStudentInTutorialClass(Person student, TutorialClass tutorialClass) {
        List<Person> students = tutorialClass.getStudents();
        return students.stream().anyMatch(student::isSamePerson);
    }

    /**
     * Checks if the team is in the tutorial class.
     * @param team
     */
    public boolean hasTeam(TutorialTeam team) {
        for (TutorialTeam tutorialTeam : this.teams) {
            if (tutorialTeam.getTeamName().equals(team.getTeamName())) {
                return true;
            }
        };
        return false;
    }

    /**
     * Returns true if a team with the same identity as {@code tutorialTeam} exists in the {@code tutorialClass}
     * @param tutorialClass of the tutorialTeam.
     * @param tutorialTeam to check if it exist.
     */
    public boolean hasTeamInTutorial(TutorialClass tutorialClass, TutorialTeam tutorialTeam) {
        requireNonNull(tutorialClass);
        requireNonNull(tutorialTeam);
        ArrayList<TutorialTeam> listOfTeams = tutorialClass.getTeams();
        ObservableList<TutorialTeam> teams = FXCollections.observableList(listOfTeams);
        return teams.stream().anyMatch(tutorialClass::hasTeam);
    }

    public TutorialTeam getTutorialTeam(TutorialClass tutorialClass, TutorialTeam tutorialTeam) {
        requireNonNull(tutorialClass);
        requireNonNull(tutorialTeam);
        TutorialTeam tutTeam = tutorialClass.getTeams().stream()
                .filter(team -> team.getTeamName().equals(tutorialTeam.getTeamName()))
                .findFirst()
                .orElse(null);
        return tutTeam;
    }

    /**
     * Returns true if the {@code student} is already in a team of {@code tutorialClass}.
     * @param tutorialClass of the teams.
     * @param student to search for.
     */
    public boolean isStudentInAnyTeam(Person student, TutorialClass tutorialClass) {
        boolean isStudentExist = false;
        for (TutorialTeam tutorialTeam : tutorialClass.getTeams()) {
            isStudentExist = tutorialTeam.hasStudentVerified(student, tutorialTeam);
            if (isStudentExist) {
                break;
            }
        }
        return isStudentExist;
    };

    /**
     * Retrieves the size of the tutorial class.
     * @return The size of the tutorial class.
     */
    public int getClassSize() {
        return this.classSize;
    }

    /**
     * Deletes a team from the tutorial class.
     * @param team
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
