package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.module.TutorialClass.isValidTutorialClass;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.PersonBuilder;

public class TutorialClassTest {

    public static final String VALID_TUTORIAL = "T09";
    public static final String INVALID_TUTORIAL = "T09#0";
    @Test
    public void equals() {
        TutorialClass tutorialClass = new TutorialClass("T09");

        // same object -> returns true
        assertTrue(tutorialClass.equals(tutorialClass));

        // same values -> returns true
        TutorialClass remarkCopy = new TutorialClass(tutorialClass.value);
        assertTrue(tutorialClass.equals(remarkCopy));

        // different types -> returns false
        assertFalse(tutorialClass.equals(1));

        // null -> returns false
        assertFalse(tutorialClass.equals(null));

        // different remark -> returns false
        TutorialClass differentModule = new TutorialClass("T01");
        assertFalse(tutorialClass.equals(differentModule));
    }

    @Test
    void isValidTutorialClass_success() {
        assertTrue(isValidTutorialClass(VALID_TUTORIAL));
    }

    @Test
    void isValidTutorialClass_failure() {
        assertFalse(isValidTutorialClass(INVALID_TUTORIAL));
    }

    @Test
    void isSameTutorialClass() {
        ModuleCode module = new ModuleBuilder().withModuleCode("CS2102").withTutorialClasses(VALID_TUTORIAL).build();
        TutorialClass tutorialClass = module.getTutorialClasses().get(1);
        String tutorialClassString = tutorialClass.value;
        assertTrue(VALID_TUTORIAL.equals(tutorialClassString));

        // different module but same tutorial class notation
        module = new ModuleBuilder().withModuleCode("CS2105").withTutorialClasses(VALID_TUTORIAL).build();
        tutorialClass = module.getTutorialClasses().get(1);
        tutorialClassString = tutorialClass.value;
        assertTrue(VALID_TUTORIAL.equals(tutorialClassString));
    }

    @Test
    void isDifferentTutorialClass() {
        // same module but different tutorial class
        ModuleCode module = new ModuleBuilder().withModuleCode("CS2102").withTutorialClasses(VALID_TUTORIAL).build();
        TutorialClass tutorialClass = module.getTutorialClasses().get(0);
        String tutorialClassString = tutorialClass.value;
        assertFalse(VALID_TUTORIAL.equals(tutorialClassString));

        // different module and different tutorial class
        module = new ModuleBuilder().withModuleCode("CS2109").withTutorialClasses("T05").build();
        tutorialClass = module.getTutorialClasses().get(1);
        String tutorialClassToCompare = tutorialClass.value;
        assertFalse(tutorialClassString.equals(tutorialClassToCompare));
    }

    @Test
    void createTutorialClassWithExistingListOfStudentsSuccess() {
        ArrayList<Person> listOfStudents = new ArrayList<>();
        Person alice = new PersonBuilder(ALICE).build();
        listOfStudents.add(alice);
        TutorialClass tutorialClass = new TutorialClass(VALID_TUTORIAL, listOfStudents);
        assertTrue(tutorialClass.getStudents().equals(listOfStudents));
    }

    @Test
    void testToString_success() {
        TutorialClass tutorial = new TutorialClass(VALID_TUTORIAL);
        assertEquals(tutorial.toString(), VALID_TUTORIAL);
    }
}
