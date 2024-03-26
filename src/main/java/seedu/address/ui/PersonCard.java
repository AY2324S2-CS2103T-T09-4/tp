package seedu.address.ui;

import java.io.File;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label stuId;
    @FXML
    private Label module;
    @FXML
    private Label email;
    @FXML
    private Label tutorial;
    @FXML
    private FlowPane tags;

    @FXML
    private Circle circle;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        email.setText(person.getEmail().value);
        stuId.setText(person.getStudentId().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        updateImage();
    }
    /**
     * Updates the image displayed in a circle shape based on the person's name.
     *
     * This method attempts to update the image displayed within a circle shape based on the person's name.
     * It first constructs the file path using the person's name and attempts to load the corresponding image.
     * If the image file does not exist, a default image (student.png) is used instead.
     * The circle shape is then styled with the image and a drop shadow effect for visual enhancement.
     *
     * @throws Exception if an error occurs during image loading or styling
     */
    public void updateImage() {
        try {
            String path = "images/" + person.getName().toString() + ".png";
            File file = new File(path);
            if (!file.exists()) {
                Image defaultImage = new Image("images/student.png");
                circle.setStroke(Color.ROSYBROWN);
                circle.setFill(new ImagePattern(defaultImage));
                circle.setEffect(new DropShadow(+10d, 0d, +2d, Color.ROSYBROWN));
            } else {
                Image newImage = new Image(file.toURI().toString());
                circle.setStroke(Color.ROSYBROWN);
                circle.setFill(new ImagePattern(newImage));
                circle.setEffect(new DropShadow(+10d, 0d, +2d, Color.ROSYBROWN));
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonCard)) {
            return false;
        }

        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
            && person.equals(card.person);
    }
}
