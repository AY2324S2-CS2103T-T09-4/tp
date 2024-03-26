package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }
    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

    @Test
    public void readAddressBook_withModulesWithoutModuleList_addsModulesToList() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        StorageManager storageManager = new StorageManager(addressBookStorage,
            new JsonUserPrefsStorage(getTempFilePath("prefs")));
        storageManager.saveAddressBook(addressBook);

        // Modify the storage file to remove module list
        addressBookStorage.saveAddressBook(new AddressBook(), addressBookStorage.getAddressBookFilePath());

        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertTrue(retrieved.getModuleList().isEmpty());
    }

    @Test
    public void readAddressBook_addsModulesToListIfNotPresent() throws Exception {
        AddressBook addressBook = new AddressBook();
        // Add some modules to the address book
        addressBook.addModule(new ModuleCode("CS1010"), null);
        addressBook.addModule(new ModuleCode("MA1505"), null);

        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        StorageManager storageManager = new StorageManager(addressBookStorage,
            new JsonUserPrefsStorage(getTempFilePath("prefs")));
        storageManager.saveAddressBook(addressBook);

        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        // Check if modules are added correctly
        assertTrue(retrieved.hasModule(new ModuleCode("CS1010")));
        assertTrue(retrieved.hasModule(new ModuleCode("MA1505")));
    }

}
