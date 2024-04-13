package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.ModuleMessages;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private ObservableList<Person> sortedPersons;
    private final ArrayList<ModuleCode> modules;
    private final ArrayList<TutorialClass> tutorialClasses;
    private final ArrayList<TutorialTeam> tutorialTeams;

    private ObservableList<Person> studentsInTeam;
    private ObservableList<Person> studentsInClass;
    private ObservableList<TutorialTeam> tutorialTeamsList;
    private ObservableList<TutorialClass> tutorialClassInModules;


    /*
     * The 'unusual' code block below is a non-static initialization block,
     * sometimes used to avoid duplication
     * between constructors. See
     * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other
     * ways to avoid duplication
     * among constructors.
     */
    {
        persons = new UniquePersonList();
        modules = new ArrayList<>();
        tutorialClasses = new ArrayList<>();
        tutorialTeams = new ArrayList<>();

    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     */
    public void setModules(List<ModuleCode> modules) {
        requireNonNull(modules);
        this.modules.clear();
        this.modules.addAll(modules);
    }

    public void setClass(List<TutorialClass> tutorialClasses) {
        requireNonNull(tutorialClasses);
        this.tutorialClasses.clear();
        this.tutorialClasses.addAll(tutorialClasses);
    }

    public void setTutorialTeams(List<TutorialTeam> tutorialTeams) {
        requireNonNull(tutorialTeams);
        this.tutorialTeams.clear();
        this.tutorialTeams.addAll(tutorialTeams);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
        setModules(newData.getModuleList());
        setClass(newData.getTutorialList());
        setTutorialTeams(newData.getTutorialTeamList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * the address book.
     */
    public boolean hasPersonWithStudentId(StudentId id) {
        requireNonNull(id);
        return persons.asUnmodifiableObservableList().stream().anyMatch(s -> s.getStudentId().equals(id));
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * the address book.
     */
    public boolean hasPersonWithEmail(Email email) {
        requireNonNull(email);
        return persons.asUnmodifiableObservableList().stream().anyMatch(s -> s.getEmail().equals(email));
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Returns true if a module with the same identity as {@code module} exists in
     * the address book.
     */
    @Override
    public boolean hasModule(ModuleCode module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Returns the module object from the module list if it exists.
     * Else, returns null
     *
     * @param module to be searched
     * @return the module object from the list, if it exists, else returns null
     */
    public ModuleCode findModuleFromList(ModuleCode module) {
        for (ModuleCode moduleInList : modules) {
            if (module.equals(moduleInList)) {
                return moduleInList;
            }
        }
        return null;
    }

    /**
     * Returns the tutorial object from the tutorial list if it exists.
     */
    public TutorialClass findTutorialClassFromList(TutorialClass tutorialClass, ModuleCode moduleCode)
            throws CommandException {
        requireNonNull(tutorialClass);
        requireNonNull(moduleCode);
        ModuleCode moduleInList = findModuleFromList(moduleCode);
        if (moduleInList == null) {
            throw new CommandException(String.format(ModuleMessages.MESSAGE_MODULE_NOT_FOUND, moduleCode));
        }
        TutorialClass tutorialClassInList = moduleInList.getTutorialClasses().stream()
                .filter(tutorial -> tutorial.equals(tutorialClass))
                .findFirst()
                .orElse(null);
        if (tutorialClassInList == null) {
            throw new CommandException(String.format(ModuleMessages.MESSAGE_TUTORIAL_DOES_NOT_BELONG_TO_MODULE,
                    tutorialClass, moduleCode));
        }
        return tutorialClassInList;
    }

    /**
     * Randomly allocates the students in {@code tutorialClass} into {@code numOfTeams} different teams.
     *
     * @param moduleCode that the {@code tutorialClass} is in.
     * @param tutorialClass to allocate the different students into the teams to.
     * @param numOfTeams to be randomly generated.
     */
    public void randomTeamAllocation(ModuleCode moduleCode, TutorialClass tutorialClass, int numOfTeams) {
        requireAllNonNull(moduleCode, tutorialClass, numOfTeams);
        ArrayList<TutorialTeam> teams = tutorialClass.getTeams();
        ArrayList<Person> classList = tutorialClass.getStudents();
        int classSize = classList.size();
        int teamSize = (int) Math.ceil((double) classSize / numOfTeams);
        teams.clear();

        // creating the teams to add into
        for (int i = 1; i <= numOfTeams; i++) {
            String teamName = "Team" + i;
            TutorialTeam team = new TutorialTeam(teamName, teamSize);
            addTeam(tutorialClass, team);
        }

        Random random = new Random();
        int listIndex = 0;
        while (listIndex < classSize) {
            int randInt = random.nextInt(numOfTeams);
            TutorialTeam currTeam = tutorialClass.getTeams().get(randInt);
            if (currTeam.hasTeamSizeExceeded(currTeam)) {
                continue;
            }

            Person student = classList.get(listIndex);
            if (!currTeam.isSamePersonInTeam(student, currTeam)) {
                currTeam.addStudent(student);
                listIndex++;
            }
        }
    }

    /**
     * Adds a module to the address book.
     * The module must not already exist in the address book. (TODO)
     */
    @Override
    public void addModule(ModuleCode m, String description) {
        m.setDescription(description);
        modules.add(m);
    }

    /**
     * Adds a person to the students list of a specific tutorial class within a
     * module.
     */
    public void addPersonToTutorialClass(Person person, ModuleCode module, TutorialClass tutorialClass) {
        requireNonNull(person);
        requireNonNull(module);
        requireNonNull(tutorialClass);

        ModuleCode moduleInList = findModuleFromList(module);
        if (moduleInList == null) {
            throw new IllegalArgumentException("Module does not exist in the address book.");
        }
        TutorialClass tutorialClassInList = moduleInList.getTutorialClasses().stream()
                .filter(tutorial -> tutorial.equals(tutorialClass))
                .findFirst()
                .orElse(null);
        tutorialClassInList.addStudent(person);
    }

    /**
     * Deletes a person from the students list of a specific tutorial class within a
     * module.
     */
    public void deletePersonFromTutorialClass(Person person, ModuleCode module, TutorialClass tutorialClass) {
        requireNonNull(person);
        requireNonNull(module);
        requireNonNull(tutorialClass);

        ModuleCode moduleInList = findModuleFromList(module);
        if (moduleInList == null) {
            throw new IllegalArgumentException("Module does not exist in the address book.");
        }
        TutorialClass tutorialClassInList = moduleInList.getTutorialClasses().stream()
                .filter(tutorial -> tutorial.equals(tutorialClass))
                .findFirst()
                .orElse(null);
        tutorialClassInList.deleteStudent(person);
    }

    /**
     * Allocates the {@code student} to the {@code tutorialTeam}
     * @param tutorialTeam to allocate the student into.
     */
    public void allocateStudentToTeam(Person student, TutorialTeam tutorialTeam) {
        requireNonNull(student);
        requireNonNull(tutorialTeam);
        tutorialTeam.addStudent(student);
    }

    /**
     * Deletes the {@code student} from the {@code tutorialTeam}
     * @param student to be deleted.
     * @param tutorialTeam to delete the student from.
     */
    public void deleteStudentFromTeam(Person student, TutorialTeam tutorialTeam) {
        requireNonNull(student);
        requireNonNull(tutorialTeam);
        tutorialTeam.deleteStudent(student);
    }

    /**
    * Adds a {@code tutorialTeam} into the {@code tutorialClass}
    *
    * @param tutorialClass to add the {@code tutorialTeam} to.
    * @param tutorialTeam to be added into the {@code tutorialClass}.
    */
    public void addTeam(TutorialClass tutorialClass, TutorialTeam tutorialTeam) {
        requireNonNull(tutorialClass);
        requireNonNull(tutorialTeam);
        tutorialClass.addTeam(tutorialTeam);
    }

    /**
     * Replaces the given person {@code target} in the list with
     * {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another
     * existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes Person {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes ModuleCode {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeModule(ModuleCode key) {
        modules.remove(key);
    }
    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public UniquePersonList getUniquePersonList() {
        return this.persons;
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<TutorialTeam> getTutorialTeamList() {
        return FXCollections.observableList(tutorialTeams);
    }

    @Override
    public void setStudentsInTeam(TutorialTeam tutorialTeam) {
        studentsInTeam = FXCollections.observableArrayList(tutorialTeam.getStudents());
    }
    @Override
    public ObservableList<Person> getStudentsInTeamList() {
        return studentsInTeam;
    }
    @Override
    public ObservableList<Person> getStudentsInTutorialClass(TutorialClass tutorialClass) {
        return FXCollections.observableList(tutorialClass.getStudents());
    }
    @Override
    public void setStudentsInTutorialClass(TutorialClass tutorialClass) {
        studentsInClass = FXCollections.observableArrayList(tutorialClass.getStudents());
    }

    @Override
    public ObservableList<Person> getStudentsInTutorialClassList() {
        return studentsInClass;
    }

    @Override
    public ObservableList<ModuleCode> getModuleList() {
        return FXCollections.observableList(modules);
    }
    @Override
    public ObservableList<TutorialClass> getTutorialList() {
        return FXCollections.observableList(tutorialClasses);
    }
    @Override
    public ObservableList<TutorialTeam> getTeamList() {
        return FXCollections.observableList(tutorialTeams);
    }
    @Override
    public void setTutorialClassesInModules(ModuleCode moduleCode) {
        tutorialClassInModules = FXCollections.observableArrayList(moduleCode.getTutorialClasses());
    }
    @Override
    public ObservableList<TutorialClass> getTutorialClassInModules() {
        return tutorialClassInModules;
    }
    @Override
    public void setTutorialTeamsInClass(TutorialClass tutorialClass) {
        tutorialTeamsList = FXCollections.observableArrayList(tutorialClass.getTeams());
    }
    @Override
    public ObservableList<TutorialTeam> getUiTutorialTeamList() {
        return tutorialTeamsList;
    }
    @Override
    public void setSortedPersonList(Comparator<Person> comparator) {
        sortedPersons = new FilteredList<>(persons.asUnmodifiableObservableList().sorted(comparator));
    }
    @Override
    public ObservableList<Person> getSortedPersonList() {
        return sortedPersons;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
