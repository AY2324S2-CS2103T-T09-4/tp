package seedu.address.ui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.model.Model;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;

/**
 * An UI component that displays information of a {@code TutorialClass}.
 */
public class TutorialClassCard extends UiPart<Region> {

    private static final String FXML = "TutorialClassCard.fxml";

    public final TutorialClass tutorialClass;
    public final PersonListPanel personListPanel;
    public final Model model;
    @FXML
    protected ComboBox<TutorialTeam> teamComboBox;
    @FXML
    private Label tutorialClassLabel;
    @FXML
    private HBox cardPane;
    @FXML
    private Circle circle;

    /**
     * Creates a {@code TutorialClassCard} with the given {@code TutorialClass}.
     */
    public TutorialClassCard(TutorialClass tutorialClass, PersonListPanel personListPanel, Model model) {
        super(FXML);
        this.model = model;
        this.tutorialClass = tutorialClass;
        this.personListPanel = personListPanel;
        tutorialClassLabel.setText(tutorialClass.toString());
        updateImage();
        updateTeamComboBox(tutorialClass.getTeams());
    }

    @FXML
    private void initialize() {
        // Initialize the teamComboBox
        teamComboBox.setOnAction(event -> handleTeamSelection());
    }

    public void setTutorialTeams(ArrayList<TutorialTeam> teams) {
        // Update the team ComboBox with the given list of teams
        updateTeamComboBox(teams);
    }
    /**
     * Handles team selection from the ComboBox.
     */
    private void handleTeamSelection() {
        TutorialTeam selectedTeam = teamComboBox.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            personListPanel.displayPersonsForTeam(selectedTeam); // Update PersonListPanel
        }
    }

    /**
     * Update the team ComboBox with the given list of teams.
     * @param teams List of teams to be displayed in the ComboBox
     */
    private void updateTeamComboBox(ArrayList<TutorialTeam> teams) {
        if (teams != null) {
            ObservableList<TutorialTeam> teamOptions = FXCollections.observableArrayList(teams);
            teamComboBox.setItems(teamOptions);
        } else {
            // If the list of teams is null, clear the ComboBox
            teamComboBox.setItems(FXCollections.emptyObservableList());
        }
    }
    /**
     * Updates the image displayed in a circle shape.
     *
     * This method updates the image displayed within a circle shape. It sets the stroke color
     * of the circle to ROSYBROWN, fills the circle with the provided image, and applies a drop shadow effect
     * to the circle for enhanced visual appearance.
     */
    public void updateImage() {
        Image moduleImage = new Image("images/damith2.png");
        circle.setStroke(Color.ROSYBROWN);
        circle.setFill(new ImagePattern(moduleImage));
        circle.setEffect(new DropShadow(+10d, 0d, +2d, Color.ROSYBROWN));
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialClassCard)) {
            return false;
        }

        // state check
        TutorialClassCard card = (TutorialClassCard) other;
        return tutorialClass.equals(card.tutorialClass);
    }
}
