package seedu.address.ui;

import org.junit.jupiter.api.BeforeAll;

import javafx.embed.swing.JFXPanel;
/**
 * The JavaFxInitializer class provides a method to initialize the JavaFX environment
 * before running any JavaFX-related tests.
 */
public class JavaFxInitializer {
    @BeforeAll
    public static void initializeJavaFX() {
        new JFXPanel(); // Initializes JavaFX environment
    }
}
