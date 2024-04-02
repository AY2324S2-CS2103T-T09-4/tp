---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TAHelper User Guide

TAHelper is a **desktop app for managing contacts, optimized for use via a Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TAHelper can get your students' contact management tasks done faster than traditional GUI apps.

- For some uncommon or unfamiliar terms used in this User Guide, ![click here](link to glossary) for the definition and explanation of some.

<!-- * Table of Contents -->
<page-nav-print />
---

## Target Audience

TAHelper is specifically designed to assist and help Teaching Assistants (TA) of NUS Computer Science Modules,
which caters to their need to store information in a way that is easy to track and visualise, as well as keep student's
details in a centralised storage. Our target audience is specifically only TAs of NUS Computer Science Modules.

## Purpose of User Guide

## Quick start

1. Ensuring the correct version of Java installed:
    - Ensure you have Java `11` or above installed in your Computer.


2. Download TAHelper from [here](https://github.com/AY2324S2-CS2103T-T09-4/tp/releases)
    - Download the latest `tahelper.jar`.


3. Set up your application environment
    - Copy the file `tahelper.jar` to the folder you want to use as the _home folder_ for your TAHelper.
    - Tip: name that folder `TAHelper` to facilitate organisation and easy access.


4. Using the Terminal to run the application
    - Windows OS:
      - Press the windows button and type `cmd` into the search bar.
      - It should look something like this:
      - ![cmd](images/cmdguidewin.png)

    - Mac OS:
      - Search for Terminal in "Utilities" under "Applications".
      - It should look something like this:
      - 


5. Launching TAHelper
    - `cd` into the folder you put the jar file in
    - Type `java -jar tahelper.jar` command and hit Enter to run TAHelper.<br>
    - It should look something like this (in this case my jar file is in a folder called tahelper): 
    - ![cmd](images/cmdguidewin2.png) 
   
    - A GUI similar to the below should appear in a few seconds.<br>
    - ![Ui](images/Ui.png) (to update!!).


6. Here are some commands to try out to get a feel of a TAHelper! type them in the Command box
    - `/add_student name/Dohn Joe email/johndoe@gmail.com id/A0123456A`: Adds a new student contact with the name, email and ID specified.
      - A new student entry should appear on the UI with the details reflected clearly.

    - `/delete_student id/A0123456A or /delete_student email/johndoe@gmail.com`: Deletes the student that you have add with the previous command.
      - The student entry with the ID `A0123456A` or email `johndoe@gmail.com` will be deleted.
      - This deletion will be reflected on User Interface of TAHelper as well.

    - For more Commands that will improve your experience, ![click here]



Commands on students:

   - `/add_student name/Dohn Joe email/johndoe@gmail.com id/A0123456A`: Adds a new student contact with all the details.

   - `/delete_student id/A0259209B or /delete_student email/johndoe@gmail.com` : Deletes a student contact with email `johndoe@gmail.com` or id `A0259209B`.

   - `/search_student id/A0123456A or /search_student email/johndoe@gmail.com` : Searches for a student with id `A0123456A` or email `johndoe@gmail.com`.

   - `/list_student` : View the list of all students available.

   - `/allocate_teams id/A1234567Z module/CS2101 tutorial/T01 name/team1` : Allocate a student to the specified team `team1` in the tutorial class `T01` of module `CS2101`.

   - `/add_student_to_class id/A1234567Z module/CS2101 tutorial/T01` : Add a student the tutorial class `T01` of module `CS2101`. 

Commands on modules:

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

- Extraneous parameters for commands that do not take in parameters (such as `list_student`) will be ignored.<br>
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

Format: `/delete_student [id/STUDENT_ID] [email/EMAIL] [index/INDEX]`

- At least one of the optional parameters (id/email/index) must be provided.
- Leading/trailing spaces are removed.
- A complete match must be provided in order for successful operation.
- If the specified student belongs to any tutorial class/teams, the student will be deleted from that particular class/team as well.
- If the specified student is not found, it returns an error.
- If no parameters are specified, it returns an error.

Expected output:
Upon successful deletion, the command will return a confirmation messaging stating that the specified student contact has been removed.

Examples:

- Delete by student ID: `/delete_student id/A01234567X`
- Delete by email: `/delete_student email/e0123456@u.nus.edu`
- Delete by index: `/delete_student index/1`

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

Shows a list of all classes in the address book.

Format: `list_class`

Expected output: The command will display the list of all classes. If there are no existing classes, the command will return a message indicating that there are no classes currently.

### Adding student to tutorial class : `add_student_to_class`

Adds a specified student based on the provided parameter to a specified tutorial class.

Format: `/add_student_to_class [id/STUDENTID] [index/INDEX] [email/EMAIL] module/MODULE_CODE class/TUTORIAL_CLASS`

- At least one of the optional parameters (id/email/index) must be provided.
- If the module code does not exist, it returns an error.
- If the tutorial class within that module code does not exist, it returns an error.
- If the student does not exist, it returns an error.
- If no parameters are specified, it returns an error.

Expected output:
Upon successful allocation of student to the tutorial class, the command will return a confirmation messaging stating that the specified student has been added to the class.

Examples:

- Add student by Student ID:`/add_student_to_class id/A01234567X module/CS2103T class/T10`
- Add student by email: `/add_student_to_class email/test@gmail.com module/CS2103T class/T10`
- Add student by index: `/add_student_to_class index/1 module/CS2103T class/T10`

### Adding new tutorial team : `add_team`

Adds a new team with the specified team name to the specified tutorial class.

Format: `/add_team module/MODULE_CODE class/TUTORIAL_CLASS name/TEAM_NAME [size/TEAM_SIZE]`

- An optional team size can be specified to apply a size restriction on the team.
- Team size must be a positive integer. Any invalid inputs (non-numeric, negative integers) returns an error.
- Two teams are equal if they have the same name and belong to the same tutorial (i.e no tutorial should have more than 1 team with the same name).
- If the module code does not exist, it returns an error.
- If the tutorial class within that module code does not exist, it returns an error.
- If no parameters are specified, it returns an error.

Expected output:
Upon successful addition, the command will return a confirmation messaging stating that the new team has been created and allocated to the specified tutorial class.

Examples:

- Without team size:`/add_team module/CS2103T class/T10 name/Team 1`
- With team size: `/add_team module/CS2103T class/T10 name/Team 1 size/3`

---
## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TAHelper home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

---

### Glossary

| Term                               | Definition and/or Explanation                                                                                                                                                                                  |
|------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **OS**                             | Refers to Operating System. Modern Operating System include Windows, Macs and Linux.                                                                                                                           |
| **TA (Teaching Assistant)**        | An individual who is responsible for teaching a tutorial class of University students.                                                                                                                         |
| **TAHelper**                       | A contact management application to help TAs keep track of students in classes they teach.                                                                                                                     |
| **Graphical User Interface (GUI)** | is a type of interface that allows users to interact with electronic devices through graphical icons and visual indicators,<br> as opposed to text-based interfaces, typed command labels, or text navigation. |
| **Command Line Interface (CLI)**   | is a text-based user interface used to interact with software, through the use of key words command such as 'cd'.                                                                                              |

---

## Command summary

| Action                     | Format, Examples                                                                                                                                                                                                       |
| -------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Add New Students**       | `add_student name/ <student_name> email/ <student_email> id/ <student_id> module/ <module> tutorial/ <class>` <br> e.g., `/add_student name/Dohn Joe email/johndoe@gmail.com id/A0123456A module/CS2103T tutorial/T09` |
| **Delete students**        | `delete_student <id/, email/> <id or email>`<br> e.g., `delete_student id/A0259209B` or `/delete_student email/johndoe@gmail.com`                                                                                      |
| **Search for students**    | `search_student <id/, email/, tc/, name/> <id or email or tutorial or name>`<br> e.g.,`search_student id/A0123456A`                                                                                                    |
| **View all students**      | `list_student`                                                                                                                                                                                                         |
| **Add new tutorial class** | `add_class <module_code> <tutorial_class>` <br> e.g., `add_class module/CS2103T tutorial/T09`                                                                                                                          |
| **Delete tutorial class**  | `delete_class <module_code> <tutorial_class>` <br> e.g., `delete_class module/CS2103T tutorial/T09`                                                                                                                    |
| **View all classes**       | `list_class`                                                                                                                                                                                                           |
