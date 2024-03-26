package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.model.module.TutorialClass;

/**
 * An UI component that displays information of a {@code TutorialClass}.
 */
public class TutorialClassCard extends UiPart<Region> {

    private static final String FXML = "TutorialClassCard.fxml";

    public final TutorialClass tutorialClass;
    @FXML
    private Label tutorialClassLabel;
    @FXML
    private HBox cardPane;
    @FXML
    private Circle circle;

    /**
     * Creates a {@code TutorialClassCard} with the given {@code TutorialClass}.
     */
    public TutorialClassCard(TutorialClass tutorialClass) {
        super(FXML);
        this.tutorialClass = tutorialClass;
        tutorialClassLabel.setText(tutorialClass.toString());
        updateImage();
    }
    /**
     * Updates the image displayed in a circle shape.
     *
     * This method updates the image displayed within a circle shape. It sets the stroke color
     * of the circle to ROSYBROWN, fills the circle with the provided image, and applies a drop shadow effect
     * to the circle for enhanced visual appearance.
     */
    public void updateImage() {
        Image moduleImage = new Image("images/class.png");
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
