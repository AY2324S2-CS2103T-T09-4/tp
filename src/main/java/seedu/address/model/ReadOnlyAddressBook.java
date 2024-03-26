package seedu.address.model;

import java.util.Comparator;

import javafx.collections.ObservableList;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<ModuleCode> getModuleList();

    void setSortedPersonList(Comparator<Person> comparator);
    ObservableList<Person> getSortedPersonList();

    /**
     * Checks if the address book contains the specified module code.
     *
     * @param moduleCode The module code to check.
     * @return {@code true} if the address book contains the specified module code, {@code false} otherwise.
     */
    boolean hasModule(ModuleCode moduleCode);
    void addModule(ModuleCode moduleCode, String description);

    ObservableList<TutorialClass> getTutorialList();
}
