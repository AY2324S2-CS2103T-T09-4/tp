package seedu.address.ui;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * Panel containing the list of tutorial classes.
 */
public class TutorialListPanel extends UiPart<Region> implements SelectedArea {
    private static final String FXML = "TutorialListPanel.fxml";
    @FXML
    protected ListView<TutorialClass> tutorialListView;
    private final Logger logger = LogsCenter.getLogger(TutorialListPanel.class);

    private PersonListPanel personListPanel;
    private Consumer<TutorialClass> tutorialCardClickHandler;
    private Model model;



    /**
     * Creates a {@code TutorialListPanel} with the given {@code ObservableList}.
     */
    public TutorialListPanel(ObservableList<TutorialClass> tutorialClassList,
                             Consumer<TutorialClass> tutorialCardClicker,
                             PersonListPanel personListPanel) {
        super(FXML);
        this.tutorialCardClickHandler = tutorialCardClicker;
        this.personListPanel = personListPanel;
        tutorialListView.setItems(tutorialClassList);
        tutorialListView.setCellFactory(listView -> new TutorialListViewCell());
        tutorialListView.focusedProperty().addListener((arg, oldVal, focused) -> {
            if (focused) {
                tutorialListView.setStyle("-fx-border-color: #264780; -fx-border-width: 1;");
            } else {
                tutorialListView.setStyle("");
            }
        });
    }

    @Override
    public void selectedArea() {
        tutorialListView.requestFocus();
    }

    @Override
    public boolean isSelected() {
        return tutorialListView.isFocused();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TutorialClass} using a {@code TutorialClassCard}.
     */
    class TutorialListViewCell extends ListCell<TutorialClass> {
        @Override
        protected void updateItem(TutorialClass tutorialClass, boolean empty) {
            super.updateItem(tutorialClass, empty);

            if (empty || tutorialClass == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TutorialClassCard(tutorialClass, personListPanel, model).getRoot());
                setOnMouseClicked(event -> tutorialCardClickHandler.accept(tutorialClass));
            }
        }
    }
    /**
     * Displays the tutorial classes for a given module.
     *
     * @param moduleCode The module code for which tutorial classes are to be displayed.
     */
    public void displayTutorialClassesForModule(ModuleCode moduleCode) {
        ArrayList<TutorialClass> tutorialClassesList = moduleCode.getTutorialClasses();
        ObservableList<TutorialClass> tutorialClasses = FXCollections.observableArrayList(tutorialClassesList);
        tutorialListView.setItems(tutorialClasses);
    }
    private void handleTutorialCardClicked(TutorialClass tutorialClass) {
        // Call the method in TutorialListPanel to display tutorial classes for the selected module
        if (personListPanel != null) {
            personListPanel.displayPersonsForModule(tutorialClass);
        }
    }
}
