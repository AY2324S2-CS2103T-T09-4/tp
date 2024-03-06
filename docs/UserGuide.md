---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TAHelper User Guide

TAHelper is a **desktop app for managing contacts, optimized for use via a Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TAHelper can get your students' contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

---

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `tahelper.jar` from [here](https://github.com/AY2324S2-CS2103T-T09-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your TAHelper.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar tahelper.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`list_student`** and pressing Enter will display a list of students.<br>
   Some example commands you can try:

   - `/add_student name/Dohn Joe email/johndoe@gmail.com id/A0123456A module/CS2103T class/T09` : Adds a new student contact with all the details that have been specified to the relevant module and tutorial class.

   - `/delete_student id/A0259209B | /delete_student email/johndoe@gmail.com` : Deletes a student contact with email `johndoe@gmail.com` or id `A0259209B`.

   - `/search_student id/A0123456A` : Searches for a student with id `A0123456A`.

   - `/list_student` : View the list of all students available.

   - `/add_class module/CS2103T class/T09` : Adds a new tutorial class `T09` under the module `2103T`.

   - `/delete_class module/CS2103T class/T09` : Deletes a tutorial class `T09` under the module `2103T`.

   - `/list_class` : List of all tutorial classes available.

1. Refer to the [Features](#features) below for details of each command.

---

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add name/NAME`, `NAME` is a parameter which can be used as `add name/John Doe`.

- Items in square brackets are optional.<br>
  e.g `name/NAME` can be used as `name/John Doe`.

- Parameters must follow the order specified in the user guide.<br>
  e.g. if the command specifies `name/NAME id/ID', it has to be in this format.

- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `list_student 123`, it will be interpreted as `list_student`.

- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### Adding new students : `add_student`

Adds a new student contact with all the details that have been specified by the user to the relevant module and tutorial class

Format: `/add_student name/NAME email/EMAIL id/STUDENT_ID module/MODULE_CODE tutorial/TUTORIAL_CLASS`

- The following parameters to add a student contact are supported:
    1. Name
    2. Email
    3. Student ID
    4. Module Code
    5. Tutorial class 

- Leading/trailing spaces are removed
- The parameter is case-insensitive
- If none of the parameters or an invalid parameter is specified, the command will return an error message indicating that a valid parameter must be provided.
- For V1.2, we will only be adding students to their respective tutorial group. Instead, what happens is that we will add all the students to the respective module headers.

Expected output:
Upon a successful add, the command will return a confirmation messaging stating that the specified student contact has been added.

Examples:
- `/add_student name/Dohn Joe email/johndoe@gmail.com id/A0123456A module/CS2103T tutorial/T09`


### Deleting students : `delete_student`

Delete a student contact based on the parameter specified by the user.

Format: `/delete_student /id STUDENT_ID /email EMAIL`

- The following parameters to delete a student contact are supported:
  1. Student ID
  2. Email
- Leading/trailing spaces are removed
- The parameter is case-insensitive
- If none of the parameters or an invalid parameter is specified, the command will return an error message indicating that a valid parameter must be provided.

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

### Listing all classes: `list_class`

Shows a list of all persons in the address book.

Format: `list_class`

**BELOW ARE JUST FORMATS FROM ORIGINAL AD3.
DELETE AFT LAST PERSON IS DONE WITH FEATURES.**

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:

- `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
- `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`


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

| Action                     | Format, Examples                                                                                                                                                                                                       |
|----------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add New Students**       | `add_student name/ <student_name> email/ <student_email> id/ <student_id> module/ <module> tutorial/ <class>` <br> e.g., `/add_student name/Dohn Joe email/johndoe@gmail.com id/A0123456A module/CS2103T tutorial/T09` |
| **Delete students**        | `delete_student <id/, email/> <id or email>`<br> e.g., `delete_student id/A0259209B` or `/delete_student email/johndoe@gmail.com`                                                                                      |
| **Search for students**    | `search_student <id/, email/, tc/, name/> <id or email or tutorial or name>`<br> e.g.,`search_student id/A0123456A`                                                                                                    |
| **View all students**      | `list_student`                                                                                                                                                                                                         |
| **Add new tutorial class** | `add_class <module_code> <tutorial_class>` <br> e.g., `add_class module/CS2103T tutorial/T09`                                                                                                                          |
| **Delete tutorial class**  | `delete_class <module_code> <tutorial_class>` <br> e.g., `delete_class module/CS2103T tutorial/T09`                                                                                                                    |
| **View all classes**       | `list_class`                                                                                                                                                                                                           |
