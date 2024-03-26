package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.ModuleCode;

/**
 * Panel containing the list of module codes.
 */
public class ModuleListPanel extends UiPart<Region> implements SelectedArea {
    private static final String FXML = "ModuleListPanel.fxml";
    @FXML
    protected ListView<ModuleCode> moduleListView;
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);
    private TutorialListPanel tutorialListPanel;
    private Consumer<ModuleCode> moduleCardClickHandler;

    /**
     * Creates a {@code ModuleListPanel} with the given {@code ObservableList}.
     */
    public ModuleListPanel(ObservableList<ModuleCode> moduleCodeList, Consumer<ModuleCode> moduleCardClickHandler) {
        super(FXML);
        this.moduleCardClickHandler = moduleCardClickHandler;
        moduleListView.setItems(moduleCodeList);
        moduleListView.setCellFactory(listView ->new ModuleListViewCell());
        moduleListView.focusedProperty().addListener((arg, oldVal, focused) -> {
            if (focused) {
                moduleListView.setStyle("-fx-border-color: #264780; -fx-border-width: 1;");
            } else {
                moduleListView.setStyle("");
            }
        });
    }

    @Override
    public void selectedArea() {
        moduleListView.requestFocus();
    }

    @Override
    public boolean isSelected() {
        return moduleListView.isFocused();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ModuleCode} using a {@code ModuleCard}.
     */
    class ModuleListViewCell extends ListCell<ModuleCode> {

        @Override
        protected void updateItem(ModuleCode moduleCode, boolean empty) {
            super.updateItem(moduleCode, empty);

            if (empty || moduleCode == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleCard(moduleCode).getRoot());
                setOnMouseClicked(event -> moduleCardClickHandler.accept(moduleCode));
            }
        }
    }

    private void handleModuleCardClicked(ModuleCode moduleCode) {
        // Call the method in TutorialListPanel to display tutorial classes for the selected module
        if (tutorialListPanel != null) {
            tutorialListPanel.displayTutorialClassesForModule(moduleCode);
        }
    }

}
