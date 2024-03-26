package seedu.address.ui;

/**
 * Represents a selectable area in the user interface.
 *
 * Implementations of this interface should provide methods to set focus on the UI element
 * and to check if the focus is currently on the UI element.
 */
public interface SelectedArea {

    /**
     * Sets the focus on this UI element.
     */
    void selectedArea();

    /**
     * Returns true if the focus is on this UI element.
     */
    boolean isSelected();
}
