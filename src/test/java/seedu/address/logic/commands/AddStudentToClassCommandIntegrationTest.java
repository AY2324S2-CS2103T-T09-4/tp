package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassByEmailCommand;
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassByIdCommand;
import seedu.address.logic.messages.ModuleMessages;
import seedu.address.logic.messages.PersonMessages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code AddStudentToClassCommand}.
 */
public class AddStudentToClassCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ModuleCode newModule = new ModuleCode(VALID_MODULE_AMY);
        model.addModule(newModule);
        TutorialClass newTutorialClass = new TutorialClass(VALID_TUTORIAL_AMY);
        newModule.addTutorialClass(newTutorialClass);
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);
    }

    @Test
    public void execute_addStudentToClassById_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        ModuleCode newModule = new ModuleCode(VALID_MODULE_AMY);
        expectedModel.addModule(newModule);
        TutorialClass newTutorialClass = new TutorialClass(VALID_TUTORIAL_AMY);
        newModule.addTutorialClass(newTutorialClass);
        Person validPerson = new PersonBuilder().build();

        // Attempt to delete the student
        assertCommandSuccess(new AddStudentToClassByIdCommand(validPerson.getStudentId(), newModule, newTutorialClass),
                model,
                String.format(PersonMessages.MESSAGE_ADD_STUDENT_TO_CLASS_SUCCESS, Messages.format(validPerson),
                        newModule, newTutorialClass),
                expectedModel);
    }

    @Test
    public void execute_addStudentToClassByEmail_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        ModuleCode newModule = new ModuleCode(VALID_MODULE_AMY);
        expectedModel.addModule(newModule);
        TutorialClass newTutorialClass = new TutorialClass(VALID_TUTORIAL_AMY);
        newModule.addTutorialClass(newTutorialClass);
        Person validPerson = new PersonBuilder().build();

        // Attempt to delete the student
        assertCommandSuccess(new AddStudentToClassByEmailCommand(validPerson.getEmail(), newModule, newTutorialClass),
                model,
                String.format(PersonMessages.MESSAGE_ADD_STUDENT_TO_CLASS_SUCCESS, Messages.format(validPerson),
                        newModule, newTutorialClass),
                expectedModel);
    }

    @Test
    public void execute_addStudentToInvalidClass_throwsCommandException() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        ModuleCode newModule = new ModuleCode(VALID_MODULE_BOB);
        TutorialClass newTutorial = new TutorialClass(VALID_TUTORIAL_BOB);
        Person validPerson = new PersonBuilder().build();

        assertCommandFailure(new AddStudentToClassByEmailCommand(validPerson.getEmail(), newModule, newTutorial),
                expectedModel, String.format(ModuleMessages.MESSAGE_MODULE_NOT_FOUND, newModule));
    }

    @Test
    public void execute_addStudentToValidModuleButInvalidTutorial_throwsCommandException() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        ModuleCode newModule = new ModuleCode(VALID_MODULE_AMY);
        expectedModel.addModule(newModule);
        TutorialClass newTutorial = new TutorialClass(VALID_TUTORIAL_BOB);
        Person validPerson = new PersonBuilder().build();
        assertCommandFailure(new AddStudentToClassByEmailCommand(validPerson.getEmail(), newModule, newTutorial),
                expectedModel,
                String.format(ModuleMessages.MESSAGE_TUTORIAL_DOES_NOT_BELONG_TO_MODULE, newTutorial, newModule));
    }

}
