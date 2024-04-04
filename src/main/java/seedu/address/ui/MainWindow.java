package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteClassCommand;
import seedu.address.logic.commands.ListClassesCommand;
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
        if (initiallyDisplayModuleListPanel()) {
            switchToModuleListPanel();
        } else {
            switchToPersonListPanel();
        }

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
            this::handleTutorialCardClicked);
        tutorialListPanelPlaceholder.getChildren().add(tutorialListPanel.getRoot());

        focusedView = moduleListPanel;
    }

    /**
     * Determines whether to initially display the module list panel.
     *
     * @return True if the module list panel should be initially displayed, false otherwise.
     */
    private boolean initiallyDisplayModuleListPanel() {
        return logic.isInitialModuleListPanelDisplayed();
    }

    /**
     * Switches to the person list panel.
     */
    void switchToPersonListPanel() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        focusedView = personListPanel;
    }

    /**
     * Switches to the module list panel.
     */
    void switchToModuleListPanel() {
        ObservableList<ModuleCode> moduleObservableList = FXCollections
            .observableList(logic.getAddressBook().getModuleList());
        moduleListPanel = new ModuleListPanel(moduleObservableList, this::handleModuleCardClicked);
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());
        focusedView = moduleListPanel;

    }

    private void switchToSortedPersonListPanel() {
        personListPanel = new PersonListPanel(logic.getAddressBook().getSortedPersonList());
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
     * Returns true if the command requires module view and
     * false if the command does not.
     *
     * @return true if command requires module view
     */
    public static boolean useModuleView(String commandText) {
        String commandWord = commandText.split(" ")[0];
        return commandWord.equals(ListClassesCommand.COMMAND_WORD)
            || commandWord.equals(AddClassCommand.COMMAND_WORD)
            || commandWord.equals(DeleteClassCommand.COMMAND_WORD);
    }

    /**
     * Returns true if the command requires sorted view.
     */
    public static boolean useSortedView(String commandText) {
        String commandWord = commandText.split(" ")[0];
        return commandWord.equals(SortStudentCommand.COMMAND_WORD);
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

            clearPanels();

            if (useModuleView(commandText)) {
                switchToModuleListPanel();
            } else if (useSortedView(commandText)) {
                switchToSortedPersonListPanel();
            } else {
                switchToPersonListPanel();
            }
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    private void clearPanels() {
        personListPanelPlaceholder.getChildren().clear();
        moduleListPanelPlaceholder.getChildren().clear();
        tutorialListPanelPlaceholder.getChildren().clear();
    }

    private void handleModuleCardClicked(ModuleCode moduleCode) {
        tutorialListPanel.displayTutorialClassesForModule(moduleCode);
    }
    private void handleTutorialCardClicked(TutorialClass tutorialClass) {
        personListPanel.displayPersonsForModule(tutorialClass);
    }
}
