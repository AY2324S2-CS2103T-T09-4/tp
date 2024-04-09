---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TAHelper User Guide

TAHelper is a **desktop app for managing contacts, optimized for use via a Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TAHelper can get your students' contact management tasks done faster than traditional GUI apps.

- For some uncommon or unfamiliar terms used in this User Guide, [click here](#glossary) for the definition and explanation of some.

## Why choose TAHelper?

## Table of contents
<!-- * Table of Contents -->
---

## Target Audience

- TAHelper is specifically designed to assist and help Teaching Assistants (TA) of NUS Computer Science Modules,
which caters to their need to store information in a way that is easy to track and visualise, as well as keep student's
details in a centralised storage. Our target audience is specifically only TAs of NUS Computer Science Modules.

[Back to table of contents](#table-of-contents)

## Purpose of User Guide
- The purpose of the User Guide (UG) for TAHelper is to provide our target users, TAs of NUS Computer Science modules, with a comprehensive resource that
helps user effectively use and understand our application. In the User Guide, we display clear instructions, a quick start guide, and explanations to help users use TAHelper seamlessly
and effectively. This help users learn the new system in an extremely short timeframe, while allowing them to understand this application deeply, optimise their workflows and improve their
student contact management in the classes they teach.

[Back to table of contents](#table-of-contents)

## Navigating the User Guide
Welcome to the TAHelper User Guide! Our goal is to assist and provide you the luxury of information, knowledge and utmost
confidence to make full use of TAHelper's features.

- Effortless Navigation: [Refer here](#table-of-contents) for the table of contents to aid your navigation.
- Quick start guide for New Users: If you're a new user, visit our [Quick Start](#quick-start) section to set up and launch the application!
- Features: Want to leverage the capabilities of TAHelper? Visit our [Features](#features) section to fully utilise the features we offer!

[Back to table of contents](#table-of-contents)

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
      - Then `cd` into the folder you put the jar file in.
      - It should look something like this:
      - ![cmd](images/cmdwinguide.png)

    - Mac OS:
      - Search for Terminal in "Utilities" under "Applications".
      - It should look something like this:
      - 

5. Launching TAHelper
    - Type `java -jar tahelper.jar` command and hit Enter to run TAHelper.<br>
    - It should look something like this (in this case my jar file is in a folder called tahelper): 
    - ![cmd](images/cmdwinguide2.png) 

   
    - A GUI similar to the below should appear in a few seconds.<br>
    - ![Ui](images/Ui.png) (to update!!).


6. Here are some commands to try out to get a feel of a TAHelper! type them in the Command box
    - `/add_student name/Dohn Joe email/johndoe@gmail.com id/A0123456A`: Adds a new student contact with the name, email and ID specified.
      - A new student entry should appear on the UI with the details reflected clearly.

    - `/delete_student id/A0123456A or /delete_student email/johndoe@gmail.com`: Deletes the student that you have add with the previous command.
      - The student entry with the ID `A0123456A` or email `johndoe@gmail.com` will be deleted.
      - This deletion will be reflected on User Interface of TAHelper as well.

    - For more Commands that will improve your experience, ![click here]

[Back to table of contents](#table-of-contents)

## Navigating the GUI

GUI Components



Commands on students:

   - `/add_student name/Dohn Joe email/johndoe@gmail.com id/A0123456A`: Adds a new student contact with all the details.

   - `/delete_student id/A0259209B or /delete_student email/johndoe@gmail.com` : Deletes a student contact with email `johndoe@gmail.com` or id `A0259209B`.

   - `/search_student id/A0123456A or /search_student email/johndoe@gmail.com` : Searches for a student with id `A0123456A` or email `johndoe@gmail.com`.

   - `/edit_student index/1 name/John` : Edits the first student's name to `John`

   - `/list_students` : View the list of all students available.

   - `/sort_students by/id or by/name` : Sorts the list of students in lexicographical order by id or name.

   - `/add_class module/CS2103T class/T09` : Adds a new tutorial class `T09` under the module `CS2103T`.

   - `/allocate_teams id/A1234567Z module/CS2101 tutorial/T01 name/team1` : Allocate a student to the specified team `team1` in the tutorial class `T01` of module `CS2101`.

   - `/add_student_to_class id/A1234567Z module/CS2101 tutorial/T01` : Add a student the tutorial class `T01` of module `CS2101`. 

Commands on modules:

   - `/add_class module/CS2103T tutorial/T09` : Adds a new tutorial class `T09` under the module `2103T`.

   - `/delete_class module/CS2103T tutorial/T09` : Deletes a tutorial class `T09` under the module `CS2103T`.

   - `/list_classes` : List of all tutorial classes available.

   - `/class_list_students module/CS2103T tutorial/T09` : List all the students in the tutorial class `T09` under the module `CS2103T`
   
   - `/view_teams name/Team 1 module/CS2103T tutorial/T09` or `/view_teams index/1 module/CS2103T tutorial/T09` : View the information of the team with team name `Team 1` or index `1` in tutorial class `T09` under module `CS2103T`

1. Refer to the [Features](#features) below for details of each command.


---
## Features

<box type="info" seamless>

**Notes about the command format:**<br>
### Command Format
Here are the main components of the commands:

| Component    | Example                | Description                                                                                                                                       |
|--------------|:-----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| Command Word | /add, /search, /delete | The type of command to be executed by the system.                                                                                                 |
| Prefix       | name/, email/, id/     | The attributes of the quantity observed.                                                                                                          |
| Parameters   | NAME, EMAIL, STUDENTID | The value of the attribute that the user have to provide after the prefix.                                                                        |
| Index        | 1, 3                   | The position of the student in the list it is referencing. Eg. Index 1 of tutorial class `T01` refers to the 1st student in the `T01` class list. | 

Here are symbols used in the commands:

| Symbol | Example         | Description                                                                          |
|--------|:----------------|--------------------------------------------------------------------------------------|
| `[ ]`  | `[email/EMAIL]` | The parameter `email` is optional and specifying it may not yield additional results |
| NONE   | `id/STUDENTID`  | The parameter `id` needs to be specified. It is compulsory.                          |

Parameters:

| Field       | Prefix       | Description/Constraints                                                                                                                                                     |
|-------------|:-------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| NAME        | name/        | Alphanumeric characters                                                                                                                                                     |
| EMAIL       | email/       | need to follow the format `example@mail.com`                                                                                                                                |
| STUDENTID   | id/          | Follows the format of NUS Student ID that starts with A. Format must be `A`, followed 7 numeric digits, and end off with a alphabetical letter.                             |
| MODULE      | module/      | Follows the format of NUS CS modules, which starts with either 2 or 3 alphabetical letters, followed by 4 numeric integer between 0-9, and an optional alphabetical letter. |
| TUTORIAL    | tutorial/    | Follows the format of NUS tutorial class naming, which starts with 1 alphabetical letter and 2 numeric integers from 0-9.                                                   |
| TEAMNAME    | team/        | Alphanumeric characters                                                                                                                                                     |
| TAG         | tag/         | tag associated with the student.  Alphanumeric characters                                                                                                                   |
| SIZE        | size/        | The size of the team or class. A single numeric integer value that is more than 0.                                                                                                   |
| DESCRIPTION | description/ | The description of the module.                                                                                                                                              |
| BY          | by/          | The parameter you want to search by, Alphanumeric characters                                                                                                                |
| INDEX       | index/       | The index of the associated student                                                                                                                                         |

Here are symbols used in the commands:

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

---
### Adding new students : `add_student`

Adds a new student contact with all the details that have been specified by the user.

Format: `/add_student name/NAME email/EMAIL id/STUDENT_ID`

- The following parameters to add a student contact are supported:

  1. Name   
  2. Email
  3. Student ID

- All fields must be specified
- Leading/trailing spaces are removed
- The parameter is case-insensitive
- If none of the parameters or an invalid parameter is specified, the command will return an error message indicating that a valid parameter must be provided.

Expected output:
Upon a successful add, the command will return a confirmation messaging stating that the specified student contact has been added.

Example:

- `/add_student name/Dohn Joe email/johndoe@gmail.com id/A0123456A`

Explanation: This adds a student with name `Dohn Joe`, email `johndoe@gmail.com` and ID `A0123456A` into the TAHelper system.

---
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

---
### Searching for students : `search_student`

Search for a student's contact based on specified query.

Format: `/search_student [id/STUDENT_ID] [email/EMAIL] [name/NAME]`

- At least one of the optional attributes must be provided.
- The search query is case-insensitive. e.g. `ian` will match `Ian`
- The search query will match information corresponding to the optional attribute. e.g. `id/` will only search for IDs
- Partial matches will also be displayed e.g. `@gmail` will return **ALL** emails containing `@gmail`

Expected output:
The command will display the list of all students that match the corresponding prefix.  If there are no existing students, the command will return a message indicating that there are no students listed.

Examples:
- `/search_student id/A012345A` Returns student with corresponding id
- `/search_student email/@GMAIL` Returns all students who have `@gmail` in their email

---
### Edit student contact: `edit_student`

Edit a student contact by index number in the displayed student list.

Format: `/edit_student index/INDEX [id/STUDENT_ID] [email/EMAIL] [name/NAME]`

- Index has to be a positive integer.
- At least one of the optional parameters must be provided.
- Multiple parameters can be provided (not duplicates). Command will edit all parameters specified.
- If the edited student id or email already exists in the list, the command will return an error message.

Expected output:
Upon successful editing, the command will return a confirmation message stating that the student contact has been edited along with the edited student contact.

Examples:
- `/edit_student index/1 name/Aaron` will edit the student's name at index 1
- `/edit_student index/5 id/A1234567B email/new_email@gmail.com` will edit the student's id and email at index 5

---
### Listing all students : `list_students`

View the list of all students available

Format: `/list_students`

Expected output:
The command will display the list of all students along with their student information. If there are no matching students, a message will display indicating no students listed.

---
### Sort all students: `sort_students`

Sorts the list of students by specified parameter

Format: `/sort_students by/BY`

- Available parameters include: `name`, `id`, `email`
- Only one parameter can be used per command call
- Students' contacts are sorted in lexicographical order.

Expected output:
The command will display the list of all students, sorted by the specified parameter, in lexicographical order.

Examples:
- `/sort_student by/name`
- `/sort_student by/email`

---
### Allocating students to tutorial teams : `allocate_team`

Allocates a student to an existing tutorial team within a tutorial class.

List of acceptable formats:<br>
    1. `/allocate_team id/ID module/MODULE tutorial/TUTORIAL team/TEAMNAME`<br>
    2. `/allocate_team email/EMAIL module/MODULE tutorial/TUTORIAL team/TEAMNAME`<br>
    3. `/allocate_team index/INDEX module/MODULE tutorial/TUTORIAL team/TEAMNAME`<br>

- All fields from any acceptable formats have to be specified.
- Leading/trailing spaces are removed.
- A student cannot be added to the same tutorial team, under the same module and tutorial class, more than once.
- The index specified is with respect to the index of the student in the tutorial class.

Pre-conditions:
1. The student you want to add has to already exist in the TAHelper system.
2. The tutorial class has to be associated and exist in the module specified.
3. The student you want to add has to already exist in the tutorial class specified.
4. The team you want to allocate the student into has to already exist within the specified tutorial class.

Important note:
- Specifying more than one way to allocate student such as `/allocate_team id/ID email/EMAIL module/MODULE tutorial/TUTORIAL team/TEAMNAME` is not advised.<br>
This is because TAHelper will prioritise looking for the student that matches the ID specified rather than the email specified. This prioritisation is a feature of the system.

Expected output:
Upon a successful allocation, the command will return a confirmation message stating that the specified student contact has been allocated to the tutorial team.

Examples:
1. `/allocate_team id/A1234567Z module/CS2101 tutorial/T01 team/team1`<br>

Explanation: This allocates a student with ID matching `A1234567Z` in the tutorial class `T01` to a tutorial team `team1` of the tutorial class `T01` under the module `CS2101`.

2. `/allocate_team email/johndoe@gmail.com module/CS2101 tutorial/T01 team/team2`<br>

Explanation: This allocates a student with email matching `johndoe@gmail.com` in the tutorial class `T01` to a tutorial team `team1` of the tutorial class `T01` under the module `CS2101`.

3. `/allocate_team index/1 module/CS2101 tutorial/T01 team/team1`<br>

Explanation: This allocates a student with index position `1` in the tutorial class `T01` to a tutorial team `team1` of the tutorial class `T01` under the module `CS2101`.


---
### Adding new tutorial class : `add_class`

Adds a tutorial class with the specified module code and name.

Format: `/add_class module/MODULE_CODE class/TUTORIAL_CLASS [description/DESC] [size/SIZE]`

Note:
- An optional class size can be specified to apply a size restriction on the class.
- Class size must be a positive integer. Any invalid inputs (non-numeric, negative integers) returns an error.
- If none of the parameters is specified, or if only one is specified, returns an error.

Examples:

- `/add_class module/CS2103T class/T10` <br>
Explanation: If module `CS2103T` already exists in the system, adds a tutorial class `T10` to the existing module.
Else, creates a new module `CS2103T` with 1 tutorial class under it: `T10`
- `/add_class module/CS2109S class/T01 description/Introduction to AI size/10` <br>
Explanation: If module `CS2109S` already exists in the system, adds a tutorial class `T01` to the existing module.
  Else, creates a new module `CS2109S` with 1 tutorial class with description `Introduction to AI` and class size of `10` under it: `T01`

---
### Deleting tutorial class : `delete_class`

Deletes a specified tutorial class from the list of classes.

Format: `/delete_class module/MODULE_CODE class/TUTORIAL_CLASS`

Note:
- If the module code does not exist, it returns an error.
- If the tutorial class within that module code does not exist, it returns an error and the list of tutorial classes in that module code.
- If no parameters are specified, returns an error
- The module itself will not be deleted upon deletion of all of its tutorial classes. To delete the module itself, see `delete_module`

Examples:

- `/delete_class module/CS2103T class/T10`
Explanation: Deletes tutorial class `T10` from the module `CS2103T`
- `/delete_class module/CS2109S class/T01`
Explanation: Deletes tutorial class `T01` from the module `CS2109S`
---
### Deleting modules : `delete_module`

Deletes a specified module from the system.

Format: `/delete_module module/MODULE_CODE`

- If the module code does not exist, it returns an error.
- If no parameters are specified, returns an error

Examples:

- `/delete_class module/CS2103T` <br>
Explanation: Deletes module `CS2103T` from the system
- `/delete_class module/CS2109S` <br>
Explanation: Deletes module `CS2109S` from the system

---
### Listing all tutorial classes: `list_classes`

Shows a list of all tutorial classes in the address book.

Format: `list_classes`

Expected output: The command will display the list of all tutorial classes. If there are no existing classes, the command will return a message indicating that there are no classes currently.

---
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

---

### Deleting students from tutorial class: `delete_student_from_class`

Deletes the specified student from a specified team.

Format: `/delete_student_from_class id/STUDENT_ID module/MODULE_CODE tutorial/TUTORIAL_CLASS` <br>or
`/delete_student_from_class email/EMAIL module/MODULE_CODE tutorial/TUTORIAL_CLASS` <br> or
`/delete_student_from_class index/INDEX module/MODULE_CODE tutorial/TUTORIAL_CLASS`

- The student detail can be specified with either student id, email or the index of the student within the tutorial class.

Important Note:
- The tutorial class needs to be associated with the module specified.
- The student must be in the team before command execution.

Expected output: `Deleted STUDENT_NAME from MODULE_CODE TUTORIAL_CLASS!`

Example: `/delete_student_from_team id/A0123456A module/CS2103T tutorial/T09`

Explanation: This deletes the student with student id `A0123456A` from tutorial class `T09` of module `CS2103T`

---
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
### Listing all students of a tutorial class : `class_list_students`

View the list of all students available

Format: `/class_list_student module/MODULE_CODE tutorial/TUTORIAL_CLASS`

- If the module code does not exist, it returns an error.
- If the tutorial class within that module code does not exist, it returns an error.

Expected output:
The command will display the list of all students along with their student information. If there are no existing students, the command will return a message indicating that there are no students in that tutorial class.

Example:

- `class_list_students module/CS2103T tutorial/T09`
- `class_list_students module/CS2101 tutorial/T01`

### View a team in a tutorial class : `view_teams`

View the information about a team in a tutorial class.

Format: `/view_teams name/TEAM_NAME module/MODULE_CODE tutorial/TUTORIAL_CLASS` or `/view_teams index/INDEX module/MODULE_CODE tutorial/TUTORIAL_CLASS`

- If the module code does not exist, it returns an error message.
- If the tutorial class within that module code does not exist, it returns an error message.
- If no parameters specified, it returns an error.
- If the team within that tutorial class does not exist, it returns an error.

Expected output:
The command will display the team with its information. If there is no such existing team, the command will return a error.

Example:

- View team by team name: `/view_teams name/Team 1 module/CS2103T class/T09`
- View team by index: `/view_teams index/1 module/CS2103T class/T09`

---

### Randomly allocate into teams all students in a tutorial class : `random_teams`

Randomly allocates all students in a tutorial class into different teams in the tutorial class.

Format: `/random_teams module/MODULE tutorial/TUTORIAL teams/NUMBEROFTEAMS`

- All fields from any acceptable formats have to be specified.
- Leading/trailing spaces are removed.

Important Note:
- The number of teams cannot be more than the number of students in the tutorial class.
- The tutorial class have to be associated with the module specified.
- The number of teams must be a non-negative integer value.

Expected output:
Upon a successful randomisation, the command will return a confirmation message stating that the students in the specified tutorial class has been randomly distributed into different teams in the tutorial class.

Example:
- `/random_teams module/CS2101 tutorial/T01 teams/2`

Explanation: This randomly allocates all the students in the tutorial class `T01` of module `CS2101` into 2 teams.

---
### Deleting students from team: `delete_student_from_team`

Deletes the specified student from a specified team.

Format: `/delete_student_from_team id/STUDENT_ID module/MODULE_CODE tutorial/TUTORIAL_CLASS team/team_name` <br>or
`/delete_student_from_team email/EMAIL module/MODULE_CODE tutorial/TUTORIAL_CLASS team/team_name` <br> or
`/delete_student_from_team index/INDEX module/MODULE_CODE tutorial/TUTORIAL_CLASS team/team_name`

- The student detail can be specified with either student id, email or the index of the student within the tutorial team.

Important Note:
- The team needs to be associated with the tutorial class specified.
- The tutorial class needs to be associated with the module specified.
- The student

Expected output: `Deleted STUDENT_NAME from MODULE_CODE TUTORIAL_CLASS, Team TEAM_NAME`

Example: `/delete_student_from_team id/A0123456A module/CS2103T tutorial/T09 team/4`

must be in the team before command execution.Explanation: This deletes the student with student id `A0123456A` from team `4` of tutorial class `T09` of module `CS2103T`

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
| **Lexicographical Order**          | is the ordering of strings based on the unicode value of each character in the string.                                                                                                                         |
| **CS**                             | Refers to Computer Science.                                                                                                                                                                                    |
| **NUS**                            | Refers to National University Of Singapore, which is located at Central Singapore.                                                                                                                             |

---

## Command summary

| Action                                  | Format, Examples                                                                                                                                                                                                                         |
|-----------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add New Students**                    | `/add_student name/ <student_name> id/ <student_id> email/ <student_email> tag/<tag>` <br> e.g., `/add_student name/Dohn Joe id/A0123456A email/johndoe@gmail.com id/A0123456A`                                                          |
| **Delete students**                     | `/delete_student <id/, email/> <id or email>`<br> e.g., `delete_student id/A0259209B` or `/delete_student email/johndoe@gmail.com`                                                                                                       |
| **Search for students**                 | `/search_student <id/, email/, tc/, name/> <id or email or tutorial or name>`<br> e.g.,`/search_student id/A0123456A`                                                                                                                    |
| **Edit student contact**                | `/edit_student index/<index> <id/, email/, name/, tag/>`<br> e.g.,`/edit_student index/1 name/John`                                                                                                                                      |
| **Sort students**                       | `/sort_students by/<name or email or id> `<br> e.g.,`search_student by/id` or `/search_student by/name`                                                                                                                                  |
| **View all students**                   | `/list_students`                                                                                                                                                                                                                         |
| **Add new tutorial class**              | `/add_class module/<module_code> tutorial/<tutorial_class> [description/<description>] [size/<class_size>]` <br> e.g., `/add_class module/CS2103T tutorial/T09 description/Software Engineering size/10`                                                                                                                          |
| **Delete tutorial class**               | `/delete_class module/<module_code> tutorial/<tutorial_class>` <br> e.g., `/delete_class module/CS2103T tutorial/T09`                                                                                                                    |
| **View all classes**                    | `/list_classes`                                                                                                                                                                                                                          |
| **Add New Student to tutorial class**   | `/add_student_to_class <index/, email/ , id/> <index or email or id> module/ <module> tutorial/ <class>` <br> e.g., `/add_student_to_class index/1 module/CS2103T tutorial/T09`                                                          |
| **Delete students from tutorial class** | `/delete_student_from_class email/<email> module/ <module> tutorial/ <class>` or `delete_student_from_class id/<id> module/ <module> tutorial/ <class>` <br> e.g., `/delete_student_from_class id/A1234561Z module/CS2103T tutorial/T09` |
| **List students of a tutorial class**   | `/class_list_student module/ <module> tutorial/ <class>` <br> e.g., `class_list_students module/CS2103T tutorial/T09`                                                                                                                    |
| **Delete module**                       | `/delete_module module/<module_code>` <br> e.g., `delete_class module/CS2103T`                                                                                                                                                           |
| **Add new team**                        | `/add_team module/<moduleCode> tutorial/<tutorialClass> name/<team_name> [size/<team_size>]` <br> e.g., `/add_team module/CS2103T tutorial/T09 name/Team 1 size/5`                                                                         |
| **Delete team**                         | `/delete_team module/<moduleCode> tutorial/<tutorialClass> name/<team_name>` <br> e.g., `/delete_team module/CS2103 tutorial/T09 name/Team 4`                                                                                            |
| **View team**                           | `/view_teams <index/, name/> <index or name> module/<moduleCode> tutorial/<tutorialClass>` <br> e.g., `/view_teams name/Team 1 module/CS2103T tutorial/T09`                                                                              |
| **Randomly allocate team**              | `/random_teams module/<module> tutorial/<tutorialClass> teams/<number_of_teams>` <br> e.g., `/random_teams module/CS2103 tutorial/T09 teams/4`                                                                                           |
| **Allocate students to team**           | `/allocate_team <id/, email/> <student_id or email> module/<moduleCode> tutorial/<tutorial_class> name/<team_name>` <br> e.g., `/allocate_team id/A1234567K module/CS2103 tutorial/T09 name/4`                                           |
| **Delete students from team**           | `/delete_student_from_team <id/, email/, index/> <student_id or email or index> module/<moduleCode> tutorial/<tutorialClass> name/<team_name>` <br> e.g., `/delete_student_from_team id/A1234567K module/CS2103 tutorial/T09 name/4`     |
