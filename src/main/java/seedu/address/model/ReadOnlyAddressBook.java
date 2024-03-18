package seedu.address.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.module.ModuleCode;
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

    ObservableList<ModuleCode> getModuleList();
    boolean hasModule(ModuleCode moduleCode);
    void addModule(ModuleCode moduleCode);
}
