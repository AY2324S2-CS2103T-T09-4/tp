package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;



/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withModuleCode("CS2105").withEmail("alice@example.com")
            .withStudentId("A1234567A").withTutorialClass("T01")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withModuleCode("CS2105")
            .withEmail("johnd@example.com").withStudentId("A1234567B").withTutorialClass("T01")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withStudentId("A1234567C")
            .withTutorialClass("T01").withEmail("heinz@example.com").withModuleCode("CS2105").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withStudentId("A1234567D")
            .withTutorialClass("T01").withEmail("cornelia@example.com").withModuleCode("CS2105").withTags("friends")
            .build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withStudentId("A1234567E")
            .withTutorialClass("T01").withEmail("werner@example.com").withModuleCode("CS2105").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withStudentId("A1234567F")
            .withTutorialClass("T01").withEmail("lydia@example.com").withModuleCode("CS2105").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withStudentId("A1234567G")
            .withTutorialClass("T01").withEmail("anna@example.com").withModuleCode("CS2105").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withStudentId("A1234567H")
            .withTutorialClass("T01").withEmail("stefan@example.com").withModuleCode("CS2105").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withStudentId("A1234567I")
            .withTutorialClass("T01").withEmail("hans@example.com").withModuleCode("CS2105").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withStudentId(VALID_STUDENT_ID_AMY)
            .withTutorialClass(VALID_TUTORIAL_AMY).withEmail(VALID_EMAIL_AMY).withModuleCode(VALID_MODULE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withStudentId(VALID_STUDENT_ID_BOB)
            .withTutorialClass(VALID_TUTORIAL_BOB).withEmail(VALID_EMAIL_BOB).withModuleCode(VALID_MODULE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
