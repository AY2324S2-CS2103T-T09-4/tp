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

### \[Implemented\] Add student to TAHelper with unique ID or Email

The implemented add mechanism is facilitated by the abstract `AddStudentCommand` along with its specific commands `AddStudentCommand`, as well as the parser `AddStudentCommandParser`.

`AddStudentCommandParser` implements the `Parser` interface and its relevant operations.

`AddStudentCommand` extends the `Command` class and contains auxillary operations that supports the mechanism.

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/add_student name/john email/john@example.com id/A1234567L tags/`

<puml src="diagrams/AddStudentSequence.puml" alt="AddStudentSequence" />

Execution steps:
Step 1. The user inputs and executes `/add_student name/john email/john@example.com id/A1234567L tags/` command to add a student with name `john`, along with
a unique email `john@example.com` and unique id `A1234567L`

The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the arguments of the command.

Step 2. The `AddressBookParser` then creates a new `AddStudentCommandParser` and calls `AddStudentCommandParser#parse()`, with `name/john`, `email/john@example.com`, `id/A1234567L` as the arguments for the function.

Step 3. The `AddStudentCommandParser` parses the arguments and get the values of the user input associated with the prefixes, from there determine the name, email and id of the student to add.

<box type="info" seamless>

**Important Note:** All fields must be specified. There must be a valid value for name, email and id.
Additionally, email and id must be unique compared to the values already present in the system to get achieve a sucessful add.
Tags here are optional and need not be specified.

</box>

Step 4. Based on the prefixes, `AddStudentCommandParser` creates an `AddStudentCommand` object. Each command contains all the required prefixes and values to used to create the command object.

Step 5. `LogicManager` calls `AddStudentCommand#execute()`, passing `Model` as an argument. This method retrieves the adds the student to the TAHelper system.
Throughout the process, error handling (e.g checking for duplicate email or id, making sure references passed are not null) is utilised to mitigate potential errors and ensure valid execution.

Step 6. Finally, a `CommandResult` is created and the student is added to the TAHelper system.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** A unique email and id is required when adding students into the TAHelper system, as well as user have to specify all fields, name, email and id in order to add a new student successfully.
  - Pros: Ensures that all students can be identified in 2 ways, email and id. This helps facilitate other commands such as search and add student to classes as checking the email or id for those commands checks the whole system to find a match, rather than a subset of the system.
  - Cons: An individual, at least in the context of NUS, can be uniquely identified by either their email E....@u.nus.edu, or by their Student ID. Therefore, specifying both may not be required and may cause extra work.

* **Alternative 2:** Allow user to specify more information about themselves such as year of study, course of study, just to name a few. This way we can support even more commands that searches say based on course of study, year of study etc.
  - Pros: Search, add, delete, sort commands all can become more specfic, and the commands can make use of more information to achieve its desired outcome as well, instead of solely relying on email or id, which although present in the system, may not be readily available or easily remembered by the users themselves.
  - Cons: The addition of these fields to the system could lead to increase complexity of the codebase and increased coupling between components in the codebase. This will make the codebase harder to debug and maintain. Also, these field possibly being optional may lead to an increase in the number of null values and thus null checks in the system, which can make the codes in the codebase harder to reason about and refactor in the future.

### \[Implemented\] Delete student

The implemented add mechanism is facilitated by the abstract `DeleteStudentCommand` along with its specific commands `DeleteStudentByEmailCommand`, `DeleteStudentByIdCommand` and `DeleteStudentClassByIndexCommand`, as well as the parser `DeleteStudentCommandParser`.

`DeleteStudentCommandParser` implements the `Parser` interface and its operations.

`DeleteStudentCommand` extends the `Command` class and contains auxillary operations that supports the mechanism, such as retrieving the target tutorial class. Each of the following commands further extends `DeleteStudentCommand` based on its specific functionality:

- `DeleteStudentByEmailCommand` — Delete student based on specified email.
- `DeleteStudentByIdCommand` — Delete student based on specified student id.
- `DeleteStudentByIndexCommand` — Delete student based on specified index (viewable from the UI).

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/delete_student id/A01234567X`

<puml src="diagrams/DeleteStudentSequence.puml" alt="DeleteStudentSequence" />

Step 1. The user executes `/delete_student id/A01234567X` command to delete the particular student with id `A01234567X`.
The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the arguments of the command.

Step 2. The `AddressBookParser` then creates a new `DeleteStudentCommandParser` and calls `DeleteStudentCommandParser#parse()`, with `id/A01234567X` as the argument.

Step 3. The `DeleteStudentCommandParser` parses the arguments to determine what parameter is used to specify the target student (email, index or id).

<box type="info" seamless>

**Important Note:** The id/email/index must be specified. To determine the target student, only one prefix should used per command. If there are multiple prefixes, the target priority is as follows: By Index -> By Student ID -> By Email

</box>

Step 4. Based on the prefix used, `DeleteStudentCommandParser` creates the specific `DeleteStudentCommand` accordingly. Each command contains a specific predicate to find the student.

Step 5. `LogicManager` calls `DeleteStudentCommand#execute()`, passing `Model` as an argument. This method retrieves the student to delete using the defined predicate. Throughout the process, error handling (e.g checking for invalid student) is utilised to mitigate potential discrepancies and ensure valid execution.

Step 6. Finally, a `CommandResult` is created and the student is deleted.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** Seperate each specific command into a different class, while overlapping code is abstracted to an abstract class.
  - Pros: Specific commands are instantiated and thus can be easily traced and is more readable. Any reusable code is defined in the abstract class which can then be reused by the subclasses.
  - Cons: May have duplicate code to a certain extent.

* **Alternative 2:** Lump into one generic command that handles each parameter accordingly.
  - Pros: Reduces duplicate code and much cleaner (i.e only 1 command class is executed).
  - Cons: The logic handling may be slightly more complex and messy within the class as all parameters have to be dealt with seperately (potentially using different logic).

### \[Implemented\] Add student to tutorial class

The implemented add mechanism is facilitated by the abstract `AddStudentToClassCommand` along with its specific commands `AddStudentToClassByEmailCommand`, `AddStudentToClassByIdCommand` and `AddStudentToClassByIndexCommand`, as well as the parser `AddStudentToClassCommandParser`.

`AddStudentToClassCommandParser` implements the `Parser` interface and its operations.

`AddStudentToClassCommand` extends the `Command` class and contains auxillary operations that supports the mechanism, such as retrieving the target tutorial class. Each of the following commands further extends `AddStudentToClassCommand` based on its specific functionality:

- `AddStudentToClassByEmailCommand` — Add student based on specified email to a tutorial class.
- `AddStudentToClassByIdCommand` — Add student based on specified student id to a tutorial class.
- `AddStudentToClassByIndexCommand` — Add student based on specified index (viewable from the UI) to a tutorial class

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/add_student_to_class id/A01234567X module/CS2103T tutorial/T09`

<puml src="diagrams/AddStudentToClassSequence.puml" alt="AddStudentToClassSequence" />

Step 1. The user executes `/add_student_to_class id/A01234567X module/CS2103T tutorial/T09` command to add the particular student with id `A01234567X` to the module `CS2103T` and tutorial class `T09`.
The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the arguments of the command.

Step 2. The `AddressBookParser` then creates a new `AddStudentToClassCommandParser` and calls `AddStudentToClassCommandParser#parse()`, with `id/A01234567X`, `module/CS2103T` and `tutorial/T09` as the arguments.

Step 3. The `AddStudentToClassCommandParser` parses the arguments to determine what parameter is used to specify the target student (email, index or id). Additionally, the `ModuleCode` and `TutorialClass` is determined.

<box type="info" seamless>

**Important Note:** The tutorial class and module code must be specified. To determine the target student, only one prefix should used per command. If there are multiple prefixes, the target priority is as follows: By Index -> By Student ID -> By Email

</box>

Step 4. Based on the prefix used, `AddStudentToClassCommandParser` creates the specific `AddStudentToClassCommand` accordingly. Each command contains a specific predicate to find the student.

Step 5. `LogicManager` calls `AddStudentToClassCommand#execute()`, passing `Model` as an argument. This method retrieves the target module and tutorial class. Then, the method retrieves the student to add using the defined predicate. Throughout the process, error handling (e.g checking for invalid student/module/tutorial) is utilised to mitigate potential discrepancies and ensure valid execution.

Step 6. Finally, a `CommandResult` is created and the student is added to the tutorial class.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** Seperate each specific command into a different class, while overlapping code is abstracted to an abstract class.
  - Pros: Specific commands are instantiated and thus can be easily traced and is more readable. Any reusable code is defined in the abstract class which can then be reused by the subclasses.
  - Cons: May have duplicate code to a certain extent.

* **Alternative 2:** Lump into one generic command that handles each parameter accordingly.
  - Pros: Reduces duplicate code and much cleaner (i.e only 1 command class is executed).
  - Cons: The logic handling may be slightly more complex and messy within the class as all parameters have to be dealt with seperately (potentially using different logic).

### \[Implemented\] Delete student from class

The implemented add mechanism is facilitated by the abstract `DeleteStudentFromClassCommand` along with its specific commands `DeleteStudentFromClassByEmailCommand`, `DeleteStudentFromClassByIdCommand` and `DeleteStudentFromClassByIndexCommand`, as well as the parser `DeleteStudentFromClassCommandParser`.

`DeleteStudentFromClassCommandParser` implements the `Parser` interface and its operations.

`DeleteStudentFromClassCommand` extends the `Command` class and contains auxillary operations that supports the mechanism, such as retrieving the target tutorial class. Each of the following commands further extends `DeleteStudentCommand` based on its specific functionality:

- `DeleteStudentFromClassByEmailCommand` — Delete student based on specified email from a tutorial class.
- `DeleteStudentFromClassByIdCommand` — Delete student based on specified student id from a tutorial class.
- `DeleteStudentByIndexCommand` — Delete student based on specified index (viewable from the UI) from a tutorial class

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/delete_student_from_class id/A01234567X module/CS2103T tutorial/T09`

<puml src="diagrams/DeleteStudentFromClassSequence.puml" alt="DeleteStudentFromClassSequence" />

Step 1. The user executes `/delete_student_from_class id/A01234567X module/CS2103T tutorial/T09` command to delete the particular student with id `A01234567X` from the module `CS2103T` and tutorial class `T09`.
The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the arguments of the command.

Step 2. The `AddressBookParser` then creates a new `DeleteStudentFromClassCommandParser` and calls `DeleteStudentFromClassCommandParser#parse()`, with `id/A01234567X`, `module/CS2103T` and `tutorial/T09` as the arguments.

Step 3. The `DeleteStudentFromClassCommandParser` parses the arguments to determine what parameter is used to specify the target student (email, index or id). Additionally, the `ModuleCode` and `TutorialClass` is determined.

<box type="info" seamless>

**Important Note:** The tutorial class and module code must be specified. To determine the target student, only one prefix should used per command. If there are multiple prefixes, the target priority is as follows: By Index -> By Student ID -> By Email

</box>

Step 4. Based on the prefix used, `DeleteStudentFromClassCommandParser` creates the specific `DeleteStudentFromClassCommand` accordingly. Each command contains a specific predicate to find the student.

Step 5. `LogicManager` calls `DeleteStudentFromClassCommand#execute()`, passing `Model` as an argument. This method retrieves the target module and tutorial class. Then, the method retrieves the student to delete using the defined predicate. Throughout the process, error handling (e.g checking for invalid student/module/tutorial) is utilised to mitigate potential discrepancies and ensure valid execution.

Step 6. Finally, a `CommandResult` is created and the student is deleted from the tutorial class.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** Seperate each specific command into a different class, while overlapping code is abstracted to an abstract class.
  - Pros: Specific commands are instantiated and thus can be easily traced and is more readable. Any reusable code is defined in the abstract class which can then be reused by the subclasses.
  - Cons: May have duplicate code to a certain extent.

* **Alternative 2:** Lump into one generic command that handles each parameter accordingly.
  - Pros: Reduces duplicate code and much cleaner (i.e only 1 command class is executed).
  - Cons: The logic handling may be slightly more complex and messy within the class as all parameters have to be dealt with seperately (potentially using different logic).

### \[Implemented\] List students of class

The implementation of adding a class is facilitated by the `ListStudentsOfClassCommand` and `ListStudentsOfClassCommandParser`. `ListStudentsOfClassCommandParser` implements the `Parser` interface and it's operations. `ListStudentsOfClassCommand` extends the
`Command` class and contains auxillary operations that supports the mechanism.

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/class_list_students module/CS2103T tutorial/T09`

<puml src="diagrams/ListStudentsOfClassSequence.puml" alt="ListStudentsOfClassSequence" />

Execution steps:
Step 1. The user inputs and executes `/class_list_students module/CS2103T tutorial/T09` command to list students of the unique tutorial `T09` of a module `CS2103T`.

The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the arguments of the command.

Step 2. The `AddressBookParser` then creates a new `ListStudentsOfClassCommandParser` and calls `ListStudentsOfClassCommandParser#parse()`, with `module/CS2103T`, `tutorial/T09` as the arguments for the function.

Step 3. The `ListStudentsOfClassCommandParser` parses the arguments and get the values of the user input associated with the prefixes, from there determine the tutorial of the module to delete.

<box type="info" seamless>

**Important Note:** All fields must be specified. There must be a valid value for module and tutorial.
Additionally, module and tutorial must match with one of the values already present in the system to get achieve a successful delete.
Tags here are optional and need not be specified.

</box>

Step 4. Based on the prefixes, `ListStudentsOfClassCommandParser` creates an `ListStudentsOfClassCommand` object. Each command contains all the required prefixes and values to used to create the command object.

Step 5. `LogicManager` calls `ListStudentsOfClassCommand#execute()`, passing `Model` as an argument. This method retrieves the adds the student to the TAHelper system.
Throughout the process, error handling (e.g checking for duplicate email or id, making sure references passed are not null) is utilised to mitigate potential errors and ensure valid execution.

Step 6. Finally, a `CommandResult` is created and the student is added to the TAHelper system.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** Lists all students with their tutorial classes and modules in the result display panel.
  - Pros: Ensures that all students are displayed correctly.
  - Cons: Users may want to view all the student of a certain module but are unable to do so.

### \[Implemented\] Search for students

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

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** Only one prefix allowed per command.
  - Pros: Easy to implement.
  - Cons: Does not allow users to fine tune searches based on multiple fields.
- **Alternative 2:** Allow for multiple prefixes.
  - Pros: Users can filter searches to a higher degree
  - Cons: Handling combinations of different fields could be complex

### \[Implemented\] Sort students

The implemented search mechanism is facilitated by `SortStudentCommand` and `SortStudentCommandParser`.
`SortStudentCommandParser` implements the `Parser` interface and it's operations. `SortStudentCommand` extends the
`Command` class with the ability to update `Model`'s filtered person list using `Predicate`. It supports the following
`Predicate`:

- `name` — Sort students based on name.
- `email` — Sort students based on email.
- `id` — Sort students based on student id.

Given below is an example usage scenario and how the search mechanism behaves at each step.

Example: `/sort_student by/id`

<puml src="diagrams/SortStudentSequence.puml" alt="SortStudentSequence" />

Step 1. The user executes `/sort_student by/id` command to sort student based on the value of their id.
The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the
arguments of the command.

Step 2. The `AddressBookParser` then creates a new `SortStudentCommandParser` and calling
`SortStudentCommandParser#parse()`, with `by/id` as the argument.

Step 3. The `SortStudentCommandParser` parses the arguments to determine which prefix the user is searching in.

<box type="info" seamless>

**Note:** Only one input: name or email or id can be used per command. If there are multiple inputs, the method will throw an exception.

</box>

Step 4. `SortStudentCommandParser` creates `SearchStudentCommand` and gets the value `id` from the prefix, passing the predicate
as an argument into the command.

Step 5. `LogicManager` calls `SortStudentCommand#execute()`, passing `Model` as an argument. This method calls
`AddressBook#getSortedPersonList()` with the given predicate, returning the sorted list in `AddressBook` with the sorted order of the students.

Step 6. Finally, a `CommandResult` is created and the sorted list of students is displayed to the user.

### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** Only one prefix allowed per command.
  - Pros: Easy to implement.
  - Cons: Does not allow users to fine tune searches based on multiple fields.
- **Alternative 2:** Allow for multiple prefixes.
  - Pros: Users can filter searches to a higher degree
  - Cons: Handling combinations of different fields could be complex

### \[Implemented\] Add class

The implementation of adding a class is facilitated by the `AddClassCommand` and `AddClassCommandParser`. `AddClassCommandParser` implements the `Parser` interface and it's operations. `AddClassCommand` extends the
`Command` class and contains auxillary operations that supports the mechanism.

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/add_class module/CS2103T tutorial/T09`

<puml src="diagrams/AddClassSequence.puml" alt="AddClassSequence" />

Execution steps:
Step 1. The user inputs and executes `/add_class module/CS2103T tutorial/T09` command to add a module `CS2103T`, along with
a unique tutorial `T09`

The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the arguments of the command.

Step 2. The `AddressBookParser` then creates a new `AddClassCommandParser` and calls `AddClassCommandParser#parse()`, with `module/CS2103T`, `tutorial/T09` as the arguments for the function.

Step 3. The `AddClassCommandParser` parses the arguments and get the values of the user input associated with the prefixes, from there determine the tutorial of the module to add.

<box type="info" seamless>

**Important Note:** All fields must be specified. There must be a valid value for module and tutorial.
Additionally, module and tutorial must be unique compared to the values already present in the system to get achieve a successful add.
Tags here are optional and need not be specified.

</box>

Step 4. Based on the prefixes, `AddClassCommandParser` creates an `AddClassCommand` object. Each command contains all the required prefixes and values to used to create the command object.

Step 5. `LogicManager` calls `AddClassCommand#execute()`, passing `Model` as an argument. This method retrieves the adds the student to the TAHelper system.
Throughout the process, error handling (e.g checking for duplicate module code or tutorial, making sure references passed are not null) is utilised to mitigate potential errors and ensure valid execution.

Step 6. Finally, a `CommandResult` is created and the class is added to the TAHelper system.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** A unique module code and tutorial class is required when adding classes into the TAHelper system, as well as user have to specify all fields, module code and tutorial class in order to add a new class successfully.
  - Pros: Ensures that all classes are unique and not repeated. This helps facilitate other commands such as add student to classes to find a match in class easily without duplicates.
  - Cons: Users may inadvertently provide incorrect or non-existent module codes or tutorial class identifiers, leading to errors in the system. This could result in frustration and a poor user experience.

* **Alternative 2:** Allow user to specify only the module code when adding classes
  - Pros: Users can add classes without needing to specify the tutorial class immediately, allowing for greater flexibility in the workflow. They can add the class first and then specify the tutorial class later, as needed. The input process is also streamlined, reducing the burden on users. This simplicity can lead to faster data entry and a more intuitive user experience.
  - Cons: Users may inadvertently create duplicate classes if they do not specify the tutorial class identifier accurately or if they forget to add it later. This could result in redundancy and inconsistencies within the system.

### \[Implemented\] Delete class

The implementation of adding a class is facilitated by the `DeleteClassCommand` and `DeleteClassCommandParser`. `DeleteClassCommandParser` implements the `Parser` interface and it's operations. `DeleteClassCommand` extends the
`Command` class and contains auxillary operations that supports the mechanism.

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/delete_class module/CS2103T tutorial/T09`

<puml src="diagrams/DeleteClassSequence.puml" alt="DeleteClassSequence" />

Execution steps:
Step 1. The user inputs and executes `/delete_class module/CS2103T tutorial/T09` command to delete a module `CS2103T`, along with
a unique tutorial `T09`

The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the arguments of the command.

Step 2. The `AddressBookParser` then creates a new `DeleteClassCommandParser` and calls `DeleteClassCommandParser#parse()`, with `module/CS2103T`, `tutorial/T09` as the arguments for the function.

Step 3. The `AddClassCommandParser` parses the arguments and get the values of the user input associated with the prefixes, from there determine the tutorial of the module to delete.

<box type="info" seamless>

**Important Note:** All fields must be specified. There must be a valid value for module and tutorial.
Additionally, module and tutorial must match with one of the values already present in the system to get achieve a successful delete.
Tags here are optional and need not be specified.

</box>

Step 4. Based on the prefixes, `DeleteClassCommandParser` creates an `DeleteClassCommand` object. Each command contains all the required prefixes and values to used to create the command object.

Step 5. `LogicManager` calls `DeleteClassCommand#execute()`, passing `Model` as an argument. This method retrieves the adds the student to the TAHelper system.
Throughout the process, error handling (e.g checking for duplicate module code or tutorial, making sure references passed are not null) is utilised to mitigate potential errors and ensure valid execution.

Step 6. Finally, a `CommandResult` is created and the class is deleted from the TAHelper system.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** A unique module code and tutorial class is required when deleting classes from the TAHelper system, as well as user have to specify all fields, module code and tutorial class in order to delete a new class successfully.
  - Pros: Ensures that the class is only deleted if there is a match in the system. Also ensure that the other tutorial classes of the module is not deleted if no tutorial class is specified.
  - Cons: Users may inadvertently provide incorrect or non-existent module codes or tutorial class identifiers, leading to errors in the system. This could result in frustration and a poor user experience.

* **Alternative 2:** Allow user the option to delete the entire module without specifying the tutorial class.
  - Pros: Users can delete modules without needing to specify the tutorial class, allowing for greater ease in the workflow. This allows users who are no longer teaching the module to remove all the information with one command.
  - Cons: Users may accidentally delete the entire module if they forgot to specify the tutorial class identifier and this may lead to great data damage. A separate command to delete module would be better.

### \[Implemented\] Delete module

The implementation of adding a class is facilitated by the `DeleteModuleCommand` and `DeletModuleCommandParser`. `DeleteModuleCommandParser` implements the `Parser` interface and it's operations. `DeleteModuleCommand` extends the
`Command` class and contains auxillary operations that supports the mechanism.

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/delete_module module/CS2103T`

<puml src="diagrams/DeleteModuleSequence.puml" alt="DeleteModuleSequence" />

Execution steps:
Step 1. The user inputs and executes `/delete_module module/CS2103T` command to delete a module `CS2103T`.

The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the arguments of the command.

Step 2. The `AddressBookParser` then creates a new `DeleteModuleCommandParser` and calls `DeleteModuleCommandParser#parse()`, with `module/CS2103T` as the argument for the function.

Step 3. The `DeleteClassCommandParser` parses the arguments and get the values of the user input associated with the prefixes, from there determine the module to delete.

<box type="info" seamless>

**Important Note:** All fields must be specified. There must be a valid value for module.
Additionally, module must match with one of the values already present in the system to get achieve a successful delete.
Tags here are optional and need not be specified.

</box>

Step 4. Based on the prefixes, `DeleteModuleCommandParser` creates an `DeleteModuleCommand` object. Each command contains all the required prefixes and values to used to create the command object.

Step 5. `LogicManager` calls `DeleteModuleCommand#execute()`, passing `Model` as an argument. This method retrieves the adds the student to the TAHelper system.
Throughout the process, error handling (e.g checking for duplicate module code, making sure references passed are not null) is utilised to mitigate potential errors and ensure valid execution.

Step 6. Finally, a `CommandResult` is created and the module is deleted from the TAHelper system.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** A unique module code is required when deleting modules from the TAHelper system.
  - Pros: Ensures that the class is only deleted if there is a match in the system.
  - Cons: Users may accidentally use this command instead of `/delete_class`. This will result in huge data loss.

### \[Implemented\] List class

The implementation of adding a class is facilitated by the `ListClassCommand` and `ListClassCommandParser`. `ListClassCommandParser` implements the `Parser` interface and it's operations. `ListClassCommand` extends the
`Command` class.

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/List_class`

<puml src="diagrams/ListClassSequence.puml" alt="ListClassSequence" />

Execution steps:
Step 1. The user inputs and executes `/list_class` command to list all the class available.

The `execute` initiates the execution of the command by directly accessing the model. It begins by ensuring that the model contains data, specifically a list of modules. If the list is empty, it returns a message indicating there are no modules.

Step 2. It then iterates through each module, appending its string representation followed by a colon. Within each module iteration, it iterates through its tutorial classes, appending their string representations separated by commas.

Step 3. The result string is then trimmed and `CommandResult`.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** Lists all modules and their tutorial classes in the result display panel.
  - Pros: Ensures that all classes are displayed correctly.
  - Cons: Users may want to view tutorial classes of certain modules only but are unable to do so.

### \[Implemented\] Add team

The implementation of adding a class is facilitated by the `AddTeamCommand` and `AddTeamCommandParser`. `AddTeamCommandParser` implements the `Parser` interface and it's operations. `AddTeamCommand` extends the
`Command` class and contains auxillary operations that supports the mechanism.

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/add_team module/CS2103 tutorial/T09 name/Team 1 size/5`

<puml src="diagrams/AddTeamSequence.puml" alt="AddTeamSequence" />

Execution steps:
Step 1. The user inputs and executes `/add_team module/CS2103 tutorial/T09 name/Team 1 size/5` command to add a team `Team 1` of size `5` to the tutorial class `T09` of module `CS2103T`

The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the arguments of the command.

Step 2. The `AddressBookParser` then creates a new `AddTeamCommandParser` and calls `AddTeamCommandParser#parse()`, with `name/Team 1`, `size/5`, `module/CS2103T`, `tutorial/T09` as the arguments for the function.

Step 3. The `AddClassCommandParser` parses the arguments and get the values of the user input associated with the prefixes, from there determine the team to add.

<box type="info" seamless>

**Important Note:** All fields must be specified. There must be a valid value for name, size, module and tutorial.
Additionally, name, module and tutorial must be unique compared to the values already present in the system to get achieve a successful add.
Tags here are optional and need not be specified.

</box>

Step 4. Based on the prefixes, `AddTeamCommandParser` creates an `AddTeamCommand` object. Each command contains all the required prefixes and values to used to create the command object.

Step 5. `LogicManager` calls `AddTeamCommand#execute()`, passing `Model` as an argument. This method retrieves the adds the student to the TAHelper system.
Throughout the process, error handling (e.g checking for duplicate module code or tutorial, making sure references passed are not null) is utilised to mitigate potential errors and ensure valid execution.

Step 6. Finally, a `CommandResult` is created and the team is added to the TAHelper system.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** A unique team name, module code and tutorial class is required when adding classes into the TAHelper system, as well as user have to specify all fields, team name, module code and tutorial class in order to add a new team successfully.
  - Pros: Ensures that all teams are unique and not repeated. This helps facilitate other commands such as allocate student to teams to find a match in team easily without duplicates.
  - Cons: Users may inadvertently provide incorrect or non-existent module codes or tutorial class identifiers, leading to errors in the system. This could result in frustration and a poor user experience.

* **Alternative 2:** Allow user to not specify team size when adding teams
  - Pros: Users can add teams without needing to specify the team size immediately, allowing for greater flexibility in the workflow. They can add the team first and then specify the team size later, as needed. The input process is also streamlined, reducing the burden on users. This simplicity can lead to faster data entry and a more intuitive user experience.
  - Cons: Users may inadvertently add too many students to the team if they do not specify the team size identifier accurately or if they forget to add it later. This could result in errors within the system.

### \[Implemented\] Delete team

The implementation of adding a class is facilitated by the `DeleteTeamCommand` and `DeleteTeamCommandParser`. `DeleteTeamCommandParser` implements the `Parser` interface and it's operations. `DeleteTeamCommand` extends the
`Command` class and contains auxillary operations that supports the mechanism.

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/delete_team module/CS2103T tutorial/T09 name/Team 1`

<puml src="diagrams/DeleteTeamSequence.puml" alt="DeleteTeamSequence" />

Execution steps:
Step 1. The user inputs and executes `/delete_team module/CS2103T tutorial/T09 name/Team 1` command to delete a team `Team 1` in tutorial `T09` of module `CS2103T`.

The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the arguments of the command.

Step 2. The `AddressBookParser` then creates a new `DeleteTeamCommandParser` and calls `DeleteTeamCommandParser#parse()`, with `name/Team 1`, `module/CS2103T`, `tutorial/T09` as the arguments for the function.

Step 3. The `DeleteTeamCommandParser` parses the arguments and get the values of the user input associated with the prefixes, from there determine the tutorial of the module to delete.

<box type="info" seamless>

**Important Note:** All fields must be specified. There must be a valid value for module and tutorial.
Additionally, module and tutorial must match with one of the values already present in the system to get achieve a successful delete.
Tags here are optional and need not be specified.

</box>

Step 4. Based on the prefixes, `DeleteTeamCommandParser` creates an `DeleteTeamCommand` object. Each command contains all the required prefixes and values to used to create the command object.

Step 5. `LogicManager` calls `DeleteTeamCommand#execute()`, passing `Model` as an argument. This method retrieves the adds the student to the TAHelper system.
Throughout the process, error handling (e.g checking for duplicate module code or tutorial, making sure references passed are not null) is utilised to mitigate potential errors and ensure valid execution.

Step 6. Finally, a `CommandResult` is created and the team is deleted from the TAHelper system.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** A unique team name, module code and tutorial class is required when deleting teams from the TAHelper system, as well as user have to specify all fields, team name, module code and tutorial class in order to delete a team successfully.
  - Pros: Ensures that the team is only deleted if there is a match in the system. Also ensure that the other teams of the tutorial class is not deleted if no team is specified.
  - Cons: Users may inadvertently provide incorrect or non-existent team or module codes or tutorial class identifiers, leading to errors in the system. This could result in frustration and a poor user experience.

### \[Implemented\] Allocate student to team

The implemented add mechanism is facilitated by the abstract `AllocateStudentToTeamCommand` along with its specific commands `AllocateStudentToTeamByEmailCommand`, `AllocateStudentToTeamByIdCommand` and `AllocateStudentToTeamByIndexCommand`, as well as the parser `AllocateStudentToTeamCommandParser`.

`AllocateStudentToTeamCommandParser` implements the `Parser` interface and its operations.

`AllocateStudentToTeamCommand` extends the `Command` class and contains auxillary operations that supports the mechanism, such as retrieving the target tutorial class. Each of the following commands further extends `AllocateStudentToTeamCommand` based on its specific functionality:

- `AllocateStudentToTeamByEmailCommand` — Allocate student based on specified email to a team.
- `AllocateStudentToTeamByIdCommand` — Allocate student based on specified student id to a team.
- `AllocateStudentToTeamByIndexCommand` — Allocate student based on specified index (viewable from the UI) to a team.

Given below is an example usage scenario and how the add mechanism behaves at each step.

Example: `/allocate_team id/A01234567X module/CS2103T tutorial/T09 team/Team 1`

<puml src="diagrams/AllocateStudentToTeamSequence.puml" alt="AllocateStudentToTeamSequence" />

Step 1. The user executes `/allocate_team id/A01234567X module/CS2103T tutorial/T09 team/Team 1` command to add the particular student with id `A01234567X` to team `Team 1` in the tutorial class `T09` of module `CS2103T`.
The `execute` command calls `AddressBookParser#parseCommand()`, which extracts the command word of the command and the arguments of the command.

Step 2. The `AddressBookParser` then creates a new `AllocateStudentToTeamCommandParser` and calls `AllocateStudentToTeamCommandParser#parse()`, with `id/A01234567X`, `team/Team 1`, `module/CS2103T` and `tutorial/T09` as the arguments.

Step 3. The `AllocateStudentToTeamCommandParser` parses the arguments to determine what parameter is used to specify the target student (email, index or id). Additionally, the `TutorialTeam`, `ModuleCode` and `TutorialClass` is determined.

<box type="info" seamless>

**Important Note:** The team, tutorial class and module code must be specified. To determine the target student, only one prefix should used per command. If there are multiple prefixes, the target priority is as follows: By Index -> By Student ID -> By Email

</box>

Step 4. Based on the prefix used, `AllocateStudentToTeamCommandParser` creates the specific `AllocateStudentToTeamCommand` accordingly. Each command contains a specific predicate to find the student.

Step 5. `LogicManager` calls `AllocateStudentToTeamCommand#execute()`, passing `Model` as an argument. This method retrieves the target module and tutorial class. Then, the method retrieves the student to add using the defined predicate. Throughout the process, error handling (e.g checking for invalid student/module/tutorial/team) is utilised to mitigate potential discrepancies and ensure valid execution.

Step 6. Finally, a `CommandResult` is created and the student is added to the tutorial class.

#### Design considerations:

**Aspect: Modularity and extensibility:**

- **Alternative 1 (current choice):** Seperate each specific command into a different class, while overlapping code is abstracted to an abstract class.
  - Pros: Specific commands are instantiated and thus can be easily traced and is more readable. Any reusable code is defined in the abstract class which can then be reused by the subclasses.
  - Cons: May have duplicate code to a certain extent.

* **Alternative 2:** Lump into one generic command that handles each parameter accordingly.
  - Pros: Reduces duplicate code and much cleaner (i.e only 1 command class is executed).
  - Cons: The logic handling may be slightly more complex and messy within the class as all parameters have to be dealt with seperately (potentially using different logic).

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

| Priority | Iteration | As a …​ | I want to …​                                                                   | So that I can…​                                                                     |
| -------- | --------- | ------- | ------------------------------------------------------------------------------ | ----------------------------------------------------------------------------------- |
| `* * *`  | v1.2      | TA      | add new students to a class                                                    | maintain an up-to-date list of enrolled students.                                   |
| `* * *`  | v1.2      | TA      | add partial info of students                                                   | still add students even if I don’t have all their information.                      |
| `* * *`  | v1.2      | TA      | delete a student from my class if they drop the module/class                   | keep my class list accurate and up-to-date.                                         |
| `*  `    | v1.2      | TA      | search for my students based on their NUS ID, emails, names or tutorial groups | locate details of students without having to go through the entire list             |
| `* * *`  | v1.2      | TA      | view all students and their particulars                                        | have a comprehensive overview of the enrolled students in my class.                 |
| `* * `   | v1.2      | TA      | view all the tutorial classes and their student composition                    | have an overview of the classes that I am teaching.                                 |
| `* *`    | v1.2      | TA      | add a tutorial class that I am teaching.                                       | track a tutorial class and the students in it.                                      |
| `* *`    | v1.2      | TA      | remove a tutorial class that I am teaching.                                    | remove any unrelated classes that I do not want to no longer want to keep track of. |
| `* `     | v1.2      | TA      | add students to a tutorial class                                               | assign students to a tutorial class and teams within the class.                     |
| `* * `   | v1.3      | TA      | edit a student's information                                                   | amend a student's detail in case there are any errors or changes.                   |
| `*`      | v1.3      | TA      | sort students based on their name, student ID or email.                        | easily organise and manage student records.                                         |
| `* * *`  | v1.3      | TA      | create a new team for a tutorial class                                         | segregate students to teams within a tutorial class.                                |
| `* * *`  | v1.3      | TA      | delete a team from a tutorial class                                            | remove unnecessary teams and organise my classes.                                   |

_{More to be added}_

### Use cases

(For all use cases below, the **System** is the `TA Helper` and the **Actor** is the `TA`, unless specified otherwise)

#### Use case 1: Add new students

**Actor**: User

**System**: TAHelper

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

**Actor**: User

**System**: TAHelper

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

**Actor**: User

**System**: TAHelper

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

**Actor**: User

**System**: TAHelper

**MSS:**

1. User wants to view all students' information.
2. System displays all the student's information (name, email, student id).
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

**Actor**: User

**System**: TAHelper

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

**Actor**: User

**System**: TAHelper

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

**Actor**: User

**System**: TAHelper

**MSS**

1. User wants to view all classes.
2. System shows a list of all available classes.
   Use case ends.

**Extensions**

- 1a. Invalid input command.
  - 1a1. Return an error indicating command not recognised and provides the correct command format.
  - Use case ends.
- 1b. Additional arguments are specified after the command.
  - 1b1. System will ignore those arguments and execute the command as usual.
- 2a. There are no existing classes.
  - 2a1. System will return a message indicating that there are no existing classes in the list.
    <br>

#### Use case 8: Allocate Student to tutorial team

**Actor**: User

**System**: TAHelper

**MSS**
1. User specifies the student id of the student, the module code, tutorial class, and the tutorial team name
in the tutorial class to allocate the student into.
2. User enters the command and along with the details.
3. System allocates student into tutorial team and displays a message. 
   Use case ends.

**Extensions**

- 1a. The specified student is not in the system.
  - 1a1. TAHelper returns an error indicating that student is not in the system.
  - Use case ends.
- 1b. The specified student is not in the tutorial class of the specified module.
  - 1b1. TAHelper returns an error indicating that student needs to be in the specified tutorial class of the specified module first.
  - Use case ends.
- 1c. The specified tutorial team is not in the tutorial class.
  - 1c1: TAHelper returns an error indicating that the team does not exist in the tutorial class.
  - Use case ends.
- 1d. The tutorial class is not in the system.
  - 1d1: TAHelper returns an error indicating that the tutorial class is not in the specified module.
  - Use case ends.

### Use case 9: Randomly allocating a list of students in the tutorial class into teams.

**Actor**: User

**System**: TAHelper

**MSS**
1. User specifies the module code, tutorial class, and number of teams to split the list of students in the tutorial class into.
2. User enters the command along with the details.
3. System allocates the list of students in the tutorial class into different tutorial teams and displays a message.
   Use case ends.

**Extensions**

- 1a. The specified tutorial class does not exist under the module specified.
  - 1a1. TAHelper returns an error message.
  - Use case ends.
- 1b. The specified module does not exist.
  - 1b1. TAHelper returns an error message indicating that module does not exist in the system.
  - Use case ends.
- 1c. The specified number of teams is invalid.
  - 1c1. TAHelper returns an error message indicating that the number of teams is invalid.
  - Use case ends.

---
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
10. Should provide a comprehensive and well-designed developer documentation to guide developer on how to improve and develop TAHelper further.

### Glossary

- **Mainstream OS**: Windows, Linux, Unix, MacOS
- **Private contact detail**: A contact detail that is not meant to be shared with others
- **TA (Teaching Assistant)**: An individual who is responsible for teaching a tutorial class of University students.
- **TAHelper**: A contact management application to help TAs keep track of students in classes they teach
- **GUI**: Graphical User Interface
- **MSS**: Main Success Scenario

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

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

---
### Adding a person
1. Adding a new person into the TAHelper system.

  1. Prerequisites: NIL.

  1. Test case 1: Entering `/add_student name/Dohn Joe email/johndoe@gmail.com id/A0123456A` for the first time<br>
      Expected: A new student is successfully added into the TAHelper system.

  2. Test case 2: Entering `/add_student name/Dohn Joe email/johndoe@gmail.com id/A6543210A` with the same email<br>
      Expected: An error message displayed. A person with the same email cannot be added because a student with the same email already exist in the TAHelper system.
  
  3. Test case 3: `/add_student name/Dohn Joe email/johndoe@gmail.com id/0123`
      Expected: An error message is displayed as the input id value is invalid.

  4. Test case 4: `/add_student name/john email/john@gmail.com id/A9876543A tag/Tutee`
      Expected: A new student is successfully added into the TAHelper system.


---
### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.


---
### Allocating a student to a team in tutorial class.
1. Allocating a student to a team in the tutorial class by studentId, email or index that they belong to in the tutorial class list.

   1. Prerequisites: 
      1. The student (Person's object) should already been in the module's tutorial class list of students.
      2. The module and tutorial class in that module must already exist in the system.
      3. The team to allocate the student into must exist in that tutorial class.
      4. The student to allocate must already exist in TAHelper system.

   2. To set up these prerequisites: visit [] to add student into tutorial class, and visit [] to add tutorial team into the tutorial class.

   3. Test case 1 (Allocating by student id): `/allocate_team id/A9876543A module/CS2101 tutorial/T02 team/Team1`
      Expected: Student successfully added to Team1 of tutorial T02.

   4. Test case 2 (Allocating by email) (Assuming Test case 1 is not executed on the same person): `/allocate_team email/Jared@example.com module/CS2101 tutorial/T02 team/Team1`
      Expected: Student successfully added to Team1 of tutorial T02.

   5. Test case 3 (Allocating a student that is not in that tutorial group): `/allocate_team id/A1111111Z module/CS2101 tutorial/T02 team/Team1`
      Expected: An error message is displayed indicating that the student needs to be in the tutorial group first.

   6. Test case 4 (Allocating a student that is not in the TAHelper system): `/allocate_team id/A0987654A module/CS2101 tutorial/T02 team/Team1`
      Expected: An error message is played indicating that the student is not in TAHelper system.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

---

## **Appendix: Planned Enhancements**

## Planned Enhancements
---

### Name value regular expression (regex) validation improvements

**Current implementation**
1. Currently, the name class employs a slightly less strict regular expression in order to validate the name that users input when adding a new Person. This means that currently, the system accepts a Person's name that is solely consisting of integers.
which in most cases, is not culturally accepted or possible in the world. Furthermore, it does not support names with higher complexity such as "Jai S/O John". However, such names are perfectly acceptable formats and such formats appear in many names in Singapore and around the world.

**Proposed enhancement(s)**
1. We look to implement a stricter validation regular expression (regex) for the name valid value so that the system is able to accept names that have special symbols in them.
2. Furthermore, we also want to implement a regex expression that ensures that a Person's name cannot solely contain all integer values.

---

### Email value regular expression (regex) validation improvements

**Current implementation**
1. Currently, the email class employs a slightly less strict regular expression in order to validate the email that users input when adding a new Person. This means that user are able to input email format that is not a valid email by convention.
Current implementation only mainly checks if the `@` symbol is present, and does not check if a valid domain has been entered.

**Proposed enhancement(s)**
1. We look to implement a stricter validation regular expression (regex) for the email valid value so that the system is only able to accept email values that are more appropriate and valid in terms of real world context.
This enhancements could come in the form of ensure that a valid domain name has been passed in, as well as ensuring that the `.com` format for the wider public or the `u.nus.edu.sg` format for example, is specified in the context of NUS students.

---

### More detailed error messages for AddStudentCommand

**Current implementation**
1. Currently, when a user tries to add a student with the same email or student id as any student in the system, the system will display a generic error message that tells user the person they want to add already exist in the system.
However, it does not tell users which value, email or student id, clashes with another Person object in the system.

**Proposed enhancement(s)**
1. We look to implement a more detailed error message that specifies which field, email or id, or both, that violated the unique valid policy of a person object in the system.
This way, it will improve user experience as users are able to fix the issue with more specific aid/help.


---

### Add column labels on UI to improve readability.

**Current implementation**
1. Current implementation for the columns does not show what the entries in the columns represent. Although we can safely assume that upon seeing the entries in each column, our target audience would be able to understand what the column means,
but it can sometimes get confusing and overwhelming, especially for the last column, which handles the entries of students.

**Proposed enhancement(s)**
1. Firstly, we look to implement a label that tells users that the left column represents the list of modules, middle column represents the list of tutorial classes in that module,
and the right column represents the list of students.
2. Secondly, we want to implement even more specific label, such as when user enters the module to view the list of classes for that module, the label for the middle column (the column displaying the list of tutorial classes) should show that something like
'CS2101 tutorial classes', something that is specific to the module.
3. Thirdly, similar to point 2, we want to implement even more specific person column label, such as when user enters to view the list of students in a tutorial class, the label shows something like 'T01's class list'.
This will greatly aid user's readability, and it is an enhancement we want to make.
