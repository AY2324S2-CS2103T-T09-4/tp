package seedu.address.logic.messages;

/**
 * A class that contains all the messages related to the Person class.
 */
public class PersonMessages {
    public static final String MESSAGE_PERSON_STUDENT_ID_NOT_FOUND = "The student with student ID %s "
            + "does not exist in the address book";
    public static final String MESSAGE_PERSON_EMAIL_NOT_FOUND = "The student with email %s "
            + "does not exist in the address book";
    public static final String MESSAGE_PERSON_INDEX_NOT_FOUND = "The student at index %s "
            + "does not exist in the address book";
    public static final String MESSAGE_ADD_STUDENT_TO_CLASS_SUCCESS = "Added student %1$s to %2$s %3$s";

    public static final String MESSAGE_DELETE_STUDENT_FROM_CLASS_SUCCESS = "Deleted student %1$s from %2$s %3$s";
    public static final String MESSAGE_DUPLICATE_STUDENT_IN_CLASS = "%1$s already added to %2$s!";
    public static final String MESSAGE_DUPLICATE_PERSON = "%1$s already exists in the address book!";
    public static final String MESSAGE_DUPLICATE_EMAIL = "A student with this email %1$s already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_STUDENTID = "A student with this student id %1$s already exists in the address book.";
}
