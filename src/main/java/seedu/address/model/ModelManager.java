package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.UniquePersonList;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<ModuleCode> filteredModules;
    private final FilteredList<TutorialTeam> filteredTeams;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredModules = new FilteredList<>(this.addressBook.getModuleList());
        filteredTeams = new FilteredList<>(this.addressBook.getTeamList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        return addressBook.hasModule(moduleCode);
    }

    @Override
    public boolean hasPersonWithStudentId(StudentId id) {
        requireAllNonNull(id);
        return addressBook.hasPersonWithStudentId(id);
    }

    @Override
    public boolean hasPersonWithEmail(Email email) {
        requireAllNonNull(email);
        return addressBook.hasPersonWithEmail(email);
    }

    @Override
    public ModuleCode findModuleFromList(ModuleCode module) {
        requireNonNull(module);
        return addressBook.findModuleFromList(module);
    }

    @Override
    public TutorialClass findTutorialClassFromList(TutorialClass tutorialClass, ModuleCode moduleCode) {
        try {
            return addressBook.findTutorialClassFromList(tutorialClass, moduleCode);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void deleteModule(ModuleCode target) {
        addressBook.removeModule(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Allocates the {@code student} to the {@code tutorialTeam}
     * @param tutorialTeam to allocate the {@code student} into.
     */
    @Override
    public void allocateStudentToTeam(Person student, TutorialTeam tutorialTeam) {
        requireAllNonNull(student, tutorialTeam);
        addressBook.allocateStudentToTeam(student, tutorialTeam);
    }

    @Override
    public void deleteStudentFromTeam(Person student, TutorialTeam tutorialTeam) {
        requireAllNonNull(student, tutorialTeam);
        addressBook.deleteStudentFromTeam(student, tutorialTeam);
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
        addressBook.randomTeamAllocation(moduleCode, tutorialClass, numOfTeams);
    }

    /**
     * Add a team to the tutorial class
     * @param tutorialTeam to be added to the {@code tutorialClass}.
     */
    @Override
    public void addTeam(TutorialClass tutorialClass, TutorialTeam tutorialTeam) {
        requireNonNull(tutorialTeam);
        addressBook.addTeam(tutorialClass, tutorialTeam);
    }

    @Override
    public void addModule(ModuleCode module) {
        addressBook.addModule(module,
            module.getDescription());
    }

    @Override
    public void addPersonToTutorialClass(Person person, ModuleCode module, TutorialClass tutorialClass) {
        addressBook.addPersonToTutorialClass(person, module, tutorialClass);
    }

    @Override
    public void deletePersonFromTutorialClass(Person person, ModuleCode module, TutorialClass tutorialClass) {
        addressBook.deletePersonFromTutorialClass(person, module, tutorialClass);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     * @return UniquePersonList.
     */
    @Override
    public UniquePersonList getUniquePersonList() {
        return addressBook.getUniquePersonList();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<ModuleCode> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public ObservableList<Person> getSortedPersonList(Comparator<Person> comparator) {
        addressBook.setSortedPersonList(comparator);
        return addressBook.getSortedPersonList();
    }

    @Override
    public ObservableList<Person> getStudentsInTeamList() {
        return addressBook.getStudentsInTeamList();
    }
    @Override
    public ObservableList<Person> getStudentsInClassList() {
        return addressBook.getStudentsInTutorialClassList();
    }

    @Override
    public ObservableList<Person> getStudentsInTutorialClass(TutorialClass tutorialClass) {
        return addressBook.getStudentsInTutorialClass(tutorialClass);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredModuleList(Predicate<ModuleCode> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    /**
     * Searches for a person in the list of filtered persons based on the given predicate.
     *
     * @param predicate The predicate used to filter persons.
     * @return The first person that matches the predicate, or {@code null} if no person matches.
     * @throws NullPointerException if the predicate is {@code null}.
     */
    public Person searchPersonByPredicate(Predicate<Person> predicate) {
        requireNonNull(predicate);
        return filteredPersons.stream().filter(predicate).findFirst().orElse(null);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
            && userPrefs.equals(otherModelManager.userPrefs)
            && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    /**
     * Searches for a tutorial team within the specified tutorial class and module using the given predicate.
     *
     * @param predicate     The predicate used to filter teams.
     * @param tutorialClass The tutorial class to search within.
     * @param moduleCode    The code of the module containing the tutorial class.
     * @return The tutorial team that matches the predicate, or {@code null} if no team matches the predicate.
     * @throws NullPointerException if the predicate is null.
     */
    public TutorialTeam searchTeamByPredicate(Predicate<TutorialTeam> predicate, TutorialClass tutorialClass,
                                              ModuleCode moduleCode) {
        requireNonNull(predicate);
        List<TutorialTeam> teams = findTutorialClassFromList(tutorialClass, moduleCode).getTeams();
        return teams.stream().filter(predicate).findFirst().orElse(null);
    }
}
