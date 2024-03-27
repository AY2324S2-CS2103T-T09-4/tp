package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.ModuleMessages;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
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

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
        setModules(newData.getModuleList());
        setClass(newData.getTutorialList());

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
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
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
