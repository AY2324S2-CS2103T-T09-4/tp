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
import seedu.address.model.module.ModuleCode;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleCard extends UiPart<Region> {

    private static final String FXML = "ModuleCard.fxml";

    public final ModuleCode moduleCode;
    @FXML
    protected Label moduleCodeLabel;
    @FXML
    protected Label descriptionLabel;
    @FXML
    private HBox cardPane;
    @FXML
    private Circle circle;
    @FXML
    private Region cardRegion;
    /**
     * Creates a {@code ModuleCard} with the given {@code Module}.
     */
    public ModuleCard(ModuleCode moduleCode) {
        super(FXML);
        this.moduleCode = moduleCode;
        moduleCodeLabel.setText(moduleCode.getModule().toString());;
        descriptionLabel.setText(moduleCode.getDescription());
        updateImage();

    }
    /**
     * Updates the image displayed within a circle shape.
     *
     * This method updates the image displayed within a circle shape by setting the stroke color
     * of the circle to ROSYBROWN, filling the circle with the provided image, and applying a drop shadow effect
     * to enhance visual appearance.
     */
    public void updateImage() {
        Image moduleImage = new Image("images/group4.png");
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
        if (!(other instanceof ModuleCard)) {
            return false;
        }

        // state check
        ModuleCard card = (ModuleCard) other;
        return moduleCode.equals(card.moduleCode);
    }

}
