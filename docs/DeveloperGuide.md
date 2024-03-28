---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# TA Helper Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

- `VersionedAddressBook#commit()` — Saves the current address book state in its history.
- `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
- `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**


* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

### \[Implemented\] Searching for students

The implemented search mechanism is facilitated by `SearchStudentCommand` and `SearchStudentCommandParser`.
`SearchStudentCommandParser` implements the `Parser` interface and it's operations. `SearchStudentCommand` extends the
`Command` class with the ability to update `Model`'s filtered person list using `Predicate`. It supports the following
`Predicate`:

- `NameContainsKeywordPredicate` — Search students based on name.
- `EmailContainsKeywordPredicate` — Search students based on email.
- `StudentIdContainsKeywordPredicate` — Search students based on student id.

Given below is an example usage scenario and how the search mechanism behaves at each step.

Example: `/search_student name/Bob`

<puml src="diagrams/SearchStudentSequence.puml" alt="SearchStudentSequence" />

Step 1. The user executes `/search_student name/Bob` command to find students with the keyword `Bob` in their name.
The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the
arguments of the command.

Step 2. The `AddressBookParser` then creates a new `SearchStudentCommandParser` and calling 
`SearchStudentCommandParser#parse()`, with `name/Bob` as the argument.

Step 3. The `SearchStudentCommandParser` parses the arguments to determine which prefix the user is searching in.

<box type="info" seamless>

**Note:** Only one prefix can be used per command. If there are multiple prefixes, the method will throw an exception.

</box>

Step 4. `SearchStudentCommandParser` creates `NameContainsKeywordPredicate` and `SearchStudentCommand`, passing the predicate
as an argument into the command.

Step 5. `LogicManager` calls `SearchStudentCommand#execute()`, passing `Model` as an argument. This method calls
`Model#updateFilteredPersonList()` with the given predicate, updating the filtered list in `Model` with students whose
name contains `Bob`.

Step 6. Finally, a `CommandResult` is created and the new filtered is displayed to the user.

### Design considerations:

- **Alternative 1 (current choice):** Only one prefix allowed per command.
  - Pros: Easy to implement. 
  - Cons: Does not allow users to fine tune searches based on multiple fields.
  
- **Alternative 2:** Allow for multiple prefixes.
  - Pros: Users can filter searches to a higher degree
  - Cons: Handling combinations of different fields could be complex
  
---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- has a need to manage a significant number of students' contacts
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**: manage students'contacts faster than a typical mouse/GUI driven app

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​ | I want to …​                                                                  | So that I can…​                                                        |
|----------|---------|-------------------------------------------------------------------------------|------------------------------------------------------------------------|
| `* * *`  | TA      | add new students to a class                                                   | maintain an up-to-date list of enrolled students.                      |
| `* * *`  | TA      | add partial info of students                                                  | still add students even if I don’t have all their information.         |
| `* * *`  | TA      | delete a student from my class if they drop the module/class                  | keep my class list accurate and up-to-date.                            |
| `* * `   | TA      | search for my students based on their NUS ID, emails, names or tutorial groups | locate details of students without having to go through the entire list |
| `* * *`  | TA      | view all students and their particulars                                       | have a comprehensive overview of the enrolled students in my class.    |
| `* *`    | TA      | add/remove different modules I am teaching                                    | manage my teaching assignments efficiently.                            |
| `* * *`  | TA      | view all the tutorial classes and their information                           | visibility into the schedule and details of all tutorial classes.      |

_{More to be added}_

### Use cases

(For all use cases below, the **System** is the `TA Helper` and the **Actor** is the `TA`, unless specified otherwise)

#### Use case 1: Add new students

**MSS:**

1. User specifies the student to be added.
2. System adds the student to the list of students.
3. System indicates successful addition of new student.
Use case ends.

**Extensions:**

- 1a. Student's name, email, id is not specified.
  - 1a1. Returns an error that informs the user to specify the missing field(s).
  - Use case ends.
- 1b. The specified email and/or id is tagged to an existing student in the list.
  - 1b1. Returns an error indicating that there is an existing entry with the same value.
  - Use case ends.
- 1c. Invalid input command.
  - 1c1. Return an error indicating that command is not recognised and provides the correct command format.
  - Use case ends.
- 2a. Student's tutorial class is not specified.
  - 2a1. System adds student into the list of students.
  - 2a2. Student will not be placed under any tutorial group.
  - Use case ends.

<br>

#### Use case 2: Delete students

**MSS:**

1. User specifies the student to be deleted.
2. System deletes the student from the list of students and tutorial group (if any).
Use case ends.

**Extensions:**

- 1a. User specifies to delete student by student ID.
  - 1a1. Student ID does not exist in the system.
    - 1a1.1: Returns an error indicating that the student with the provided ID does not exist.
    - Use case ends.
- 1b. User specifies to delete student by email.
  - 1b1. Email does not exist in the system.
    - 1b1.1. Returns an error indicating that the student with the provided email does not exist.
    - Use case ends.
- 1c. Invalid input command.
  - 1c1: Returns an error indicating command not recognised and provides the correct command format.
<br>

#### Use case 3: Search for students

**MSS:**

1. User specifies the student to be searched for.
2. System generates a list of matching entries according to specified parameters.
Use case ends.

**Extensions:**

- 1a. Parameter not specified
  - 1a1/2a1. Returns an error indicating that the user needs to specify valid fields.
  - Use case ends.
- 1b. Invalid input command.
  - 1b1. Return an error indicating command not recognised and provides the correct command format.
  - Use case ends.
- 2a. Partial match for specified parameter.
  - 2a1. System will display all matching results for the specified value.
<br>

#### Use case 4: View all students

**MSS:**

1. User wants to view all students' information.
2. System displays all students information (name, email, student id and tutorial class).
Use case ends.

**Extensions:**

- 1a. Invalid input command.
  - 1a1. Return an error indicating command not recognised and provides the correct command format.
  - Use case ends.
- 1b. Additional arguments are specified after the command.
  - 1b1. System will ignore those arguments and execute the command as usual.
- 2a. No existing students in the list.
  - 2a1. System will return a message indicating that there are no students in the list.
  <br>

#### Use case 5: Add new tutorial class

**MSS:**

1. User specifies the class to be added.
2. System adds the tutorial class.
Use case ends.

**Extensions:**

- 1a. Invalid input command.
  - 1a1. Return an error indicating command not recognised and provides the correct command format.
  - Use case ends.
- 1b. Invalid tutorial class attributes are specified.
  - 2a1. Returns an error indicating that user has to specify tutorial class in the correct format.
  - Use case ends.
- 1c. The specified tutorial class already exists.
  - 1c1: Returns an error indicating that the tutorial class already exists
  - Use case ends.
<br>

#### Use case 6: Delete tutorial class

**MSS:**

1. User specifies the class to be deleted.
2. System deletes the tutorial class.
Use case ends.

**Extensions:**
- 1a. Invalid input command.
  - 1a1. Return an error indicating command not recognised and provides the correct command format.
  - Use case ends.
- 1b. The tutorial class specified does not exist.
  - 1b1. Returns an error indicating invalid tutorial class and shows the list of tutorial classes available.
  - Use case ends.
<br>

#### Use case 7: View all classes

**MSS**
1. User wants to view all classes.
2. System shows a list of all available classes.
Use case ends.

**Extensions**
- 1a. Invalid input command.
  - 1a1. Return an error indicating command not recognised and provides the correct command format.
  - Use case ends.
- 1b. Additional arguments are specified after the command.
  - 1b1. System will ignore those arguments and execute the comamnd as usual.
- 2a. There are no existing classes.
  - 2a1. System will return a message indicating that there are no existing classes in the list.
<br>


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should respond to user inputs within approximately 2-3 seconds.
5.  Should not depend on internet access to accomplish its core purpose.
6.  Should provide a simple and user-friendly GUI, focusing on readability and ease of use.
7.  Should be usable by a person who is TA-ing for the first time.
8.  Should provide comprehensive error messages and guidelines to recover from errors due to user input.
9.  Should provide a comprehensive and well-designed user documentation to guide users on how to use TAHelper.
10.  Should provide a comprehensive and well-designed developer documentation to guide developer on how to improve and develop TAHelper further.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **TA (Teaching Assistant)**: An individual who is responsible for teaching a tutorial class of University students.
* **TAHelper**: A contact management application to help TAs keep track of students in classes they teach
* **GUI**: Graphical User Interface
* **MSS**: Main Success Scenario

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more _exploratory_ testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
