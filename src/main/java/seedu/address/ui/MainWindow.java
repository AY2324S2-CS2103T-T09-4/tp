package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddClassCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.AddTeamCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteClassCommand;
import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ListClassesCommand;
import seedu.address.logic.commands.ListStudentsCommand;
import seedu.address.logic.commands.ListStudentsOfClassCommand;
import seedu.address.logic.commands.RandomTeamAllocationCommand;
import seedu.address.logic.commands.SearchStudentCommand;
import seedu.address.logic.commands.ViewTeamCommand;
import seedu.address.logic.commands.addstudenttoclasscommands.AddStudentToClassCommand;
import seedu.address.logic.commands.allocatestudenttoteamcommands.AllocateStudentToTeamCommand;
import seedu.address.logic.commands.deletestudentcommands.DeleteStudentCommand;
import seedu.address.logic.commands.deletestudentfromclasscommands.DeleteStudentFromClassCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.sortstudentcommands.SortStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ModuleListPanel moduleListPanel;
    private TutorialListPanel tutorialListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private SelectedArea focusedView;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;
    @FXML
    private StackPane moduleListPanelPlaceholder;
    @FXML
    private StackPane tutorialListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        moduleListPanel = new ModuleListPanel(logic.getAddressBook().getModuleList(), this::handleModuleCardClicked);
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());

        tutorialListPanel = new TutorialListPanel(logic.getAddressBook().getTutorialList(),
            this::handleTutorialCardClicked, personListPanel);
        tutorialListPanelPlaceholder.getChildren().add(tutorialListPanel.getRoot());

        focusedView = moduleListPanel;
    }

    private void switchToSortedPersonListPanel() {
        personListPanel = new PersonListPanel(logic.getAddressBook().getSortedPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }
    private void displayAllPersonListPanel() {
        personListPanel = new PersonListPanel(logic.getAddressBook().getPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }
    private void switchToSearchPersonListPanel() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    private void listStudentsOfClassPanel() {
        personListPanel = new PersonListPanel(logic.getAddressBook().getStudentsInTutorialClassList());
        personListPanelPlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }
    private void listStudentsOfTeamPanel() {
        personListPanel = new PersonListPanel(logic.getAddressBook().getStudentsInTeamList());
        personListPanelPlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Shows the primary stage of the application.
     */
    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
            (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }
    /**
     * Returns true if the command requires is clear command and
     * false if the command does not.
     *
     * @return true if command is clear command
     */
    public static boolean useClearView(String commandText) {
        String commandWord = commandText.split(" ")[0];
        return commandWord.equals(ClearCommand.COMMAND_WORD);
    }
    /**
     * Returns true if the command requires module view and
     * false if the command does not.
     *
     * @return true if command requires module view
     */
    public static boolean useModuleView(String commandText) {
        String commandWord = commandText.split(" ")[0];
        return commandWord.equals(ListClassesCommand.COMMAND_WORD)
            || commandWord.equals(AddClassCommand.COMMAND_WORD)
            || commandWord.equals(DeleteClassCommand.COMMAND_WORD)
            || commandWord.equals(DeleteModuleCommand.COMMAND_WORD);
    }
    /**
     * Returns true if the command requires person view and
     * false if the command does not.
     *
     * @return true if command requires person view
     */
    public static boolean usePersonView(String commandText) {
        String commandWord = commandText.split(" ")[0];
        return commandWord.equals(AddStudentCommand.COMMAND_WORD)
            || commandWord.equals(DeleteStudentCommand.COMMAND_WORD)
            || commandWord.equals(EditCommand.COMMAND_WORD);
    }
    /**
     * Returns true if the command requires tutorial view and
     * false if the command does not.
     *
     * @return true if command requires tutorial view
     */

    public static boolean useTutorialView(String commandText) {
        String commandWord = commandText.split(" ")[0];
        return commandWord.equals(AddTeamCommand.COMMAND_WORD)
            || commandWord.equals(DeleteTeamCommand.COMMAND_WORD)
            || commandWord.equals(RandomTeamAllocationCommand.COMMAND_WORD);
    }

    /**
     * Returns true if the command requires sorted view.
     */
    public static boolean useSortedView(String commandText) {
        String commandWord = commandText.split(" ")[0];
        return commandWord.equals(SortStudentCommand.COMMAND_WORD);
    }
    /**
     * Returns true if the command requires list view.
     */
    public static boolean allStudentView(String commandText) {
        String commandWord = commandText.split(" ")[0];
        return commandWord.equals(ListStudentsCommand.COMMAND_WORD);
    }
    /**
     * Returns true if the command requires search result view.
     */
    public static boolean searchStudentView(String commandText) {
        String commandWord = commandText.split(" ")[0];
        return commandWord.equals(SearchStudentCommand.COMMAND_WORD);
    }
    /**
     * Returns true if the command requires the list of students in tutorial class view.
     */
    public static boolean listStudentOfTutorialClassView(String commandText) {
        String commandWord = commandText.split(" ")[0];
        return commandWord.equals(ListStudentsOfClassCommand.COMMAND_WORD)
            || commandWord.equals(AddStudentToClassCommand.COMMAND_WORD)
            || commandWord.equals(DeleteStudentFromClassCommand.COMMAND_WORD);
    }
    /**
     * Returns true if the command requires the list of students in tutorial team view.
     */
    public static boolean listStudentOfTeamView(String commandText) {
        String commandWord = commandText.split(" ")[0];
        return commandWord.equals(ViewTeamCommand.COMMAND_WORD)
            || commandWord.equals(AllocateStudentToTeamCommand.COMMAND_WORD);
    }
    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }
            if (useSortedView(commandText)) {
                switchToSortedPersonListPanel();
            }
            if (allStudentView(commandText)) {
                displayAllPersonListPanel();
            }
            if (searchStudentView(commandText)) {
                switchToSearchPersonListPanel();
            }
            if (listStudentOfTutorialClassView(commandText)) {
                listStudentsOfClassPanel();
            }
            if (listStudentOfTeamView(commandText)) {
                listStudentsOfTeamPanel();
            }
            if (usePersonView(commandText)) {
                personListPanel = new PersonListPanel(logic.getAddressBook().getPersonList());
                personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
            }
            if (useModuleView(commandText)) {
                moduleListPanel = new ModuleListPanel(logic.getAddressBook().getModuleList(),
                    this::handleModuleCardClicked);
                moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());

                tutorialListPanel = new TutorialListPanel(logic.getAddressBook().getTutorialClassInModules(),
                    this::handleTutorialCardClicked, personListPanel);
                tutorialListPanelPlaceholder.getChildren().add(tutorialListPanel.getRoot());
            }
            if (useTutorialView(commandText)) {
                tutorialListPanel = new TutorialListPanel(logic.getAddressBook().getTutorialClassInModules(),
                    this::handleTutorialCardClicked, personListPanel);
                tutorialListPanelPlaceholder.getChildren().add(tutorialListPanel.getRoot());

                personListPanel = new PersonListPanel(logic.getAddressBook().getStudentsInTeamList());
                personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

            }
            if (useClearView(commandText)) {
                personListPanelPlaceholder.getChildren().clear();
                moduleListPanelPlaceholder.getChildren().clear();
                tutorialListPanelPlaceholder.getChildren().clear();
            }
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    private void handleModuleCardClicked(ModuleCode moduleCode) {
        tutorialListPanel.displayTutorialClassesForModule(moduleCode);
    }
    private void handleTutorialCardClicked(TutorialClass tutorialClass) {
        personListPanel.displayPersonsForModule(tutorialClass);
    }
}
