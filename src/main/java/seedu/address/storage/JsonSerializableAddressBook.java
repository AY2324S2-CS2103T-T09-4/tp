package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_MODULE = "Modules list contains duplicate module(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and
     * modules.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("modules") List<JsonAdaptedModule> modules) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (modules != null) {
            this.modules.addAll(modules);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        modules.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            ModuleCode module = jsonAdaptedModule.toModelType();
            if (addressBook.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            addressBook.addModule(module,
                module.getDescription());
        }
        return addressBook;
    }

    /**
     * Adds a person to the students list of a specific tutorial class within a
     * module.
     * @param tutorialName The name of the tutorial class to which the person will
     *                     be added.
     * @param person       The person to be added.
     * @throws IllegalValueException if the tutorial class or module does not exist
     *                               in the address book.
     */
    public void addPersonToTutorialClass(String moduleName, String tutorialName, Person person)
            throws IllegalValueException {
        // Find the module that contains the specified tutorial class
        Optional<JsonAdaptedModule> moduleOptional = modules.stream()
                .filter(moduleCode -> moduleCode.getModuleName().equals(moduleName))
                .findFirst();

        if (moduleOptional.isPresent()) {
            JsonAdaptedModule module = moduleOptional.get();
            // Find the tutorial class with the specified name
            Optional<JsonAdaptedTutorialClass> tutorialOptional = module.getTutorialClasses().stream()
                    .filter(tutorial -> tutorial.getTutorialName().equals(tutorialName))
                    .findFirst();

            if (tutorialOptional.isPresent()) {
                JsonAdaptedTutorialClass tutorialClass = tutorialOptional.get();
                // Add the person to the students list of the tutorial class
                tutorialClass.getStudents().add(new JsonAdaptedPerson(person));
            } else {
                throw new IllegalValueException("Tutorial class '" + tutorialName + "' not found in module.");
            }
        } else {
            throw new IllegalValueException("Module containing tutorial class '" + tutorialName + "' not found.");
        }
    }

}
