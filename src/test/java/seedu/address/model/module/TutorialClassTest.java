package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.model.module.TutorialClass.isValidTutorialClass;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains unit tests for the TutorialClass class.
 */
public class TutorialClassTest {

    public static final String VALID_TUTORIAL = "T09";
    public static final String INVALID_TUTORIAL = "T09#0";
    @Test
    public void equals() {
        TutorialClass tutorialClass = new TutorialClass("T09");

        // same object -> returns true
        assertTrue(tutorialClass.equals(tutorialClass));

        // same values -> returns true
        TutorialClass remarkCopy = new TutorialClass(tutorialClass.tutorialName);
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
        String tutorialClassString = tutorialClass.tutorialName;
        assertTrue(VALID_TUTORIAL.equals(tutorialClassString));

        // different module but same tutorial class notation
        module = new ModuleBuilder().withModuleCode("CS2105").withTutorialClasses(VALID_TUTORIAL).build();
        tutorialClass = module.getTutorialClasses().get(1);
        tutorialClassString = tutorialClass.tutorialName;
        assertTrue(VALID_TUTORIAL.equals(tutorialClassString));
    }

    @Test
    void isDifferentTutorialClass() {
        // same module but different tutorial class
        ModuleCode module = new ModuleBuilder().withModuleCode("CS2102").withTutorialClasses(VALID_TUTORIAL).build();
        TutorialClass tutorialClass = module.getTutorialClasses().get(0);
        String tutorialClassString = tutorialClass.tutorialName;
        assertFalse(VALID_TUTORIAL.equals(tutorialClassString));

        // different module and different tutorial class
        module = new ModuleBuilder().withModuleCode("CS2109").withTutorialClasses("T05").build();
        tutorialClass = module.getTutorialClasses().get(1);
        String tutorialClassToCompare = tutorialClass.tutorialName;
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

    @Test
    void testEmptyConstructor() {
        TutorialClass tutorialClass = new TutorialClass();
        assertEquals("", tutorialClass.getTutorialClass().toString());
        try {
            Field field = TutorialClass.class.getDeclaredField("students");
            field.setAccessible(true); // Allow access to private field
            Object value = field.get(tutorialClass); // Get the value of the field
            assertTrue(((ArrayList<?>) value).isEmpty()); // Check if it's empty
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Exception thrown while accessing private field: " + e.getMessage());
        }
    }

    @Test
    void testConstructorWithStudents() {
        ArrayList<Person> students = new ArrayList<>();

        // Creating Person objects
        Name name1 = new Name("John");
        Email email1 = new Email("john@example.com");
        StudentId stuId1 = new StudentId("A0213333J");
        Set<Tag> tags1 = new HashSet<>();
        Person student1 = new Person(name1, email1, stuId1, tags1);

        Name name2 = new Name("Alice");
        Email email2 = new Email("alice@example.com");
        StudentId stuId2 = new StudentId("A0145678A");
        Set<Tag> tags2 = new HashSet<>();
        Person student2 = new Person(name2, email2, stuId2, tags2);

        students.add(student1);
        students.add(student2);

        TutorialClass tutorialClass = new TutorialClass(VALID_TUTORIAL, students);
        assertEquals(VALID_TUTORIAL, tutorialClass.getTutorialClass().toString());
        assertEquals(2, tutorialClass.getStudents().size()); // Accessing students via getter
        assertTrue(tutorialClass.getStudents().contains(student1));
        assertTrue(tutorialClass.getStudents().contains(student2));
    }
}
