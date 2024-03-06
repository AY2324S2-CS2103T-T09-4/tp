---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# AB-3 User Guide

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

---

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `addressbook.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   - `/list_student` : View the list of all students available.

   - `/add_student /name Dohn Joe /email johndoe@gmail.com /id A0123456A /tc CS2103T T09` : Adds a new student contact with all the details that have been specified to the relevant module and tutorial class.

   - `/delete_student /id A0259209B | /delete_student /email johndoe@gmail.com` : Deletes a student contact with email `johndoe@gmail.com` or id `A0259209B`.

   - `/search_student /id A0123456A` : Searches for a student with id `A0123456A`.

   - `/add_class CS2103T T09` : Adds a new tutorial class `T09` under the module `2103T`.

   - `/delete_class CS2103T T09` : Deletes a tutorial class `T09` under the module `2103T`.

   - `/list_class` : List of all tutorial classes available.

1. Refer to the [Features](#features) below for details of each command.

---

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

- Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

- Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

- Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### Adding new students : `add_student`

Adds a new student contact with all the details that have been specified by the user to the relevant module and tutorial class

Format: `/add_student /name NAME /email EMAIL /id STUDENT_ID /tc TUTORIAL_CLASS`

- The following parameters to add a student contact are supported:
    1. Name
    2. Email
    3. Student ID
    4. Tutorial class

- Leading/trailing spaces are removed
- The parameter is case insensitive
- If none of the paremeters or an invalid parameter is specified, the command will return an error message indicating that a valid parameter must be provided.

Expected output:
Upon a successful add, the command will return a confirmation messaging stating that the specified student contact has been added.

Examples:
- `/add_student /name Dohn Joe /email johndoe@gmail.com /id A0123456A /tc CS2103T T09`


### Deleting students : `delete_student`

Delete a student contact based on the parameter specified by the user.

Format: `/delete_student /id STUDENT_ID /email EMAIL`

- The following parameters to delete a student contact are supported:
  1. Student ID
  2. Email
- Leading/trailing spaces are removed
- The parameter is case insensitive
- If none of the paremeters or an invalid parameter is specified, the command will return an error message indicating that a valid parameter must be provided.

Expected output:
Upon successful deletion, the command will return a confirmation messaging stating that the specified student contact has been removed.

Examples:

- Delete by student ID: `/delete_student /id A01234567X`
- Delete by email: `/delete_student /email e0123456@u.nus.edu`

### Searching for students : `search`

Search for a student's contact based on specified query.

Format: `/search_student [id/STUDENT_ID] [email/EMAIL] [class/TUTORIAL_CLASS] [name/NAME]`

- At least one of the optional attributes must be provided.
- The search query is case-insensitive. e.g. `ian` will match `Ian`
- The search query will match information corresponding to the optional attribute. e.g. `id/` will only search for IDs
- Partial matches will also be displayed e.g. `@gmail` will return **ALL** emails containing `@gmail`

Examples:
- `/search_student id/A012345A` Returns student with corresponding id
- `/search_student email/@GMAIL` Returns all students who have `@gmail` in their email

### Listing all students : `list_student`

View the list of all students available

Format: `/list_student`

Expected output:
The command will display the list of all students along with their student information. If there are no existing students, the command will return a message indicating that there are no students currently.

### Adding new tutorial class : `add_class`

Adds a tutorial class with the specified module code and name.

Format: `/add_class module/MODULE_CODE class/TUTORIAL_CLASS`

- If none of the parameters is specified, or if only one is specified, returns an error.

Examples:

- `/add_class module/CS2103T class/T10`
- `/add_class module/CS2109S class/T01`

### Deleting tutorial class : `delete_class`

Deletes a specified tutorial class from the list of classes.

Format: `/delete_class module/MODULE_CODE class/TUTORIAL_CLASS`

- If the module code does not exist, it returns an error.
- If the tutorial class within that module code does not exist, it returns an error and the list of tutorial classes in that module code.
- If no parameters are specified, returns an error

Examples:

- `/delete_class module/CS2103T class/T10`
- `/delete_class module/CS2109S class/T01`

### Listing all classes: `list`

Shows a list of all persons in the address book.

Format: `list_class`

**BELOW ARE JUST FORMATS FROM ORIGINAL AD3.
DELETE AFT LAST PERSON IS DONE WITH FEATURES.**

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:

- `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
- `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

- Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
- You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:

- `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
- `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive. e.g `hans` will match `Hans`
- The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
- Only the name is searched.
- Only full words will be matched e.g. `Han` will not match `Hans`
- Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

- `find John` returns `john` and `John Doe`
- `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

- Deletes the person at the specified `INDEX`.
- The index refers to the index number shown in the displayed person list.
- The index **must be a positive integer** 1, 2, 3, …​

Examples:

- `list` followed by `delete 2` deletes the 2nd person in the address book.
- `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Viewing all classes

Shows all tutorial classes available.

Format: `list`

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

---

## Command summary

| Action                     | Format, Examples                                                                                                                                                                               |
|----------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add New Students**       | `add_student /name <student_name> /email <student_email> /id <student_id> /tc <tutorial_class>` <br> e.g., `add_student /name Dohn Joe /email johndoe@gmail.com /id A0123456A /tc CS2103T T09` |
| **Delete students**        | `delete_student </id, /email> <id or email>`<br> e.g., `delete_student /id A0259209B` or `/delete_student email johndoe@gmail.com`                                                             |
| **Search for students**    | `search_student </id, /email, /tc, /name> <search_query>`<br> e.g.,`search_student /id A0123456A`                                                                                              |
| **View all students**      | `list_student`                                                                                                                                                                                 |
| **Add new tutorial class** | `add_class <module_code> <tutorial_class>` <br> e.g., `add_class CS2103T T09`                                                                                                                  |
| **Delete tutorial class**  | `delete_class <module_code> <tutorial_class>` <br> e.g., `delete_class CS2103T T09`                                                                                                            |
| **View all classes**       | `list_class`                                                                                                                                                                                   |
