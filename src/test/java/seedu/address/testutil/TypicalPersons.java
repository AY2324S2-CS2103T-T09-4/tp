package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

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
            .withModuleCode("CS2100").withEmail("alice@example.com")
            .withTutorialClass("T01").withStudentId("A12345678Z")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withModuleCode("CS2102")
            .withEmail("johnd@example.com").withTutorialClass("T01").withStudentId("A12345678A")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withTutorialClass("T01").withStudentId("A12345678B")
            .withEmail("heinz@example.com").withModuleCode("CS2103").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withTutorialClass("T01").withStudentId("A12345678C")
            .withEmail("cornelia@example.com").withModuleCode("CS2104").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withTutorialClass("T01").withStudentId("A12345678D")
            .withEmail("werner@example.com").withModuleCode("CS2105").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withTutorialClass("T01").withStudentId("A12345678E")
            .withEmail("lydia@example.com").withModuleCode("CS2106").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withTutorialClass("T01").withStudentId("A12345678F")
            .withEmail("anna@example.com").withModuleCode("CS2107").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withTutorialClass("T01").withStudentId("A12345678G")
            .withEmail("stefan@example.com").withModuleCode("CS2030").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withTutorialClass("T01").withStudentId("A12345678H")
            .withEmail("hans@example.com").withModuleCode("CS2109").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withTutorialClass("T01").withStudentId(VALID_STUDENT_ID_AMY)
            .withEmail(VALID_EMAIL_AMY).withModuleCode(VALID_MODULE_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withTutorialClass("T01").withStudentId(VALID_STUDENT_ID_BOB)
            .withEmail(VALID_EMAIL_BOB).withModuleCode(VALID_MODULE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

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
