package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> implements SelectedArea {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.focusedProperty().addListener((arg, oldVal, focused) -> {
            if (focused) {
                personListView.setStyle("-fx-border-color: #264780; -fx-border-width: 1;");
            } else {
                personListView.setStyle("");
            }
        });
    }

    @Override
    public void selectedArea() {
        personListView.requestFocus();
    }

    @Override
    public boolean isSelected() {
        return personListView.isFocused();
    }
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }
    /**
     * Displays the list of persons associated with a specific tutorial team.
     *
     * @param team The tutorial team for which persons are to be displayed.
     */
    public void displayPersonsForTeam(TutorialTeam team) {
        ArrayList<Person> personList = team.getStudents();
        ObservableList<Person> persons = FXCollections.observableArrayList(personList);
        personListView.setItems(persons);
    }
    /**
     * Displays the list of persons associated with a specific tutorial class.
     *
     * @param tutorialClass The tutorial class for which persons are to be displayed.
     */
    public void displayPersonsForModule(TutorialClass tutorialClass) {
        ArrayList<Person> personList = tutorialClass.getStudents();
        ObservableList<Person> person = FXCollections.observableArrayList(personList);
        personListView.setItems(person);
    }

}
