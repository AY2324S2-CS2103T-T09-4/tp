package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.UniquePersonList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<ModuleCode> PREDICATE_SHOW_ALL_MODULES = unused -> true;


    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a module with the same moduleCode as {@code moduleCode} exists in the address book.
     */
    boolean hasModule(ModuleCode moduleCode);

    /**
     * Returns true if a person with the same email as {@code person} exists in the address book.
     */
    boolean hasPersonWithEmail(Email email);

    /**
     * Returns true if a person with the same student id as {@code person} exists in the address book.
     */
    boolean hasPersonWithStudentId(StudentId id);

    /**
     * Finds the module object from the list if it exists. Else, returns null.
     *
     * @param module to be searched
     * @return the module if it exists, else return null.
     */
    ModuleCode findModuleFromList(ModuleCode module);

    /**
     * Find tutorial object from the list if it exists. Else, returns null.
     * @param tutorialClass to be searched
     * @param moduleCode    to be searched
     */
    TutorialClass findTutorialClassFromList(TutorialClass tutorialClass, ModuleCode moduleCode) throws CommandException;

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * adds a team into the tutorial class
     * @param tutorialClass to add the tutorialTeam to.
     * @param tutorialTeam to be added into the tutorialClass.
     */
    public void addTeam(TutorialClass tutorialClass, TutorialTeam tutorialTeam);

    /**
     * Deletes the given module.
     * The module must exist in the address book.
     */
    void deleteModule(ModuleCode target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given module.
     * {@code ModuleCode} must not already exist in the address book.
     */
    void addModule(ModuleCode module);

    /**
     * Allocates the {@code student} to the {@code tutorialTeam}
     * @param student to be allocated
     * @param tutorialTeam to allocate the student into.
     */
    void allocateStudentToTeam(Person student, TutorialTeam tutorialTeam);

    /**
     * Deletes the {@code student} from the {@code tutorialTeam}
     * @param student to be deleted
     * @param tutorialTeam to delete the student from.
     */
    void deleteStudentFromTeam(Person student, TutorialTeam tutorialTeam);

    /**
    * Randomly allocates the students in {@code tutorial class} into {@code numOfTeams} of different teams.
    *
    * @param moduleCode
    * @param tutorialClass
    * @param numOfTeams
    */
    void randomTeamAllocation(ModuleCode moduleCode, TutorialClass tutorialClass, int numOfTeams);

    /**
     * Adds the given person to the given tutorial class in the given module.
     */
    void addPersonToTutorialClass(Person person, ModuleCode module, TutorialClass tutorialClass);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();
    UniquePersonList getUniquePersonList();
    ObservableList<ModuleCode> getFilteredModuleList();
    ObservableList<Person> getSortedPersonList(Comparator<Person> comparator);
    ObservableList<Person> getStudentsInTeamList();
    ObservableList<Person> getStudentsInTutorialClass(TutorialClass tutorialClass);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @param predicate The predicate used to filter the person list.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     *
     * @param predicate The predicate used to filter the module list.
     * @throws NullPointerException if {@code predicate} is null.
     */

    void updateFilteredModuleList(Predicate<ModuleCode> predicate);

    /**
     * Search for person by a given {@code predicate}.
     */
    Person searchPersonByPredicate(Predicate<Person> predicate);

    /**
     * Deletes the given person from the given tutorial class in the given module.
     */
    void deletePersonFromTutorialClass(Person personToAdd, ModuleCode module, TutorialClass tutorialClass);

    TutorialTeam searchTeamByPredicate(Predicate<TutorialTeam> predicate, TutorialClass tutorialClass,
                                       ModuleCode moduleCode);
}
